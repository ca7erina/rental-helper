package controllers;

import models.User;
import models.UserPreferences;

import controllers.Application;

import models.utils.AppException;
import models.utils.Hash;
import play.Logger;
import play.Configuration;
import play.data.Form;
import static play.data.Form.form;
import play.data.validation.Constraints;
import play.i18n.Messages;
import play.mvc.Result;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import play.Logger;
import views.html.editpreferences;
// import views.html.profile.viewprofile;
import play.mvc.Controller;

public class EditPreferences extends Controller {

public Form<UserPreferences> preferencesForm = form( UserPreferences.class );

public Result index() {
        User user = User.findByEmail(session().get("email"));
        UserPreferences preferences = UserPreferences.findByUserId(user.id);
        if (preferences != null) {
          preferencesForm = preferencesForm.fill(preferences);
          System.out.println( preferences.locationPref );
        } else {
          System.out.println("Nullllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllll");
        }
        return ok(editpreferences.render(user,preferencesForm));

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
