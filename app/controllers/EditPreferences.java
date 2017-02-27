package controllers;

import models.User;
import models.UserPreferences;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.editpreferences;

import static play.data.Form.form;

public class EditPreferences extends Controller {

    public Form<UserPreferences> preferencesForm = form(UserPreferences.class);

    public Result index() {
        User user = User.findByEmail(session().get("email"));
        UserPreferences preferences = UserPreferences.findByUserId(user.id);
        if (preferences != null) {
            preferencesForm = preferencesForm.fill(preferences);
        }
        return ok(editpreferences.render(user, preferencesForm));
    }

    public Result submit() {
        User user = User.findByEmail(session().get("email"));
        Form<UserPreferences> filledForm = preferencesForm.bindFromRequest();
        UserPreferences preferences = UserPreferences.findByUserId(user.id);

        if (preferences != null) {
            preferences.set(filledForm.get());
            preferences.save();
        } else {
            UserPreferences newPreferences = new UserPreferences();
            newPreferences.set(filledForm.get());
            newPreferences.userId = user.id;
            newPreferences.save();
        }

        return redirect(controllers.routes.EditPreferences.index());
    }
}
