package controllers.profile;

import models.User;
import models.UserProfile;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import play.mvc.Result;
import views.html.profile.createprofile;
import views.html.profile.viewprofile;

import static play.data.Form.form;

public class CreateProfile extends Controller {

    public Form<UserProfile> profileForm = form(UserProfile.class);

    public Result index() {
        return ok(createprofile.render(profileForm));
    }

    public Result submit() {
        User user = User.findByEmail(session().get("email"));
        Form<UserProfile> filledForm = profileForm.bindFromRequest();

        if (filledForm.hasErrors()) {
            return badRequest(createprofile.render(filledForm));
        } else {
            MultipartFormData body = request().body().asMultipartFormData();
            FilePart picture = body.getFile("image");
            UserProfile newProfile = filledForm.get();
            String filePath = "public/user_pictures/" + newProfile.name + ".png";

            newProfile.image = picture.getFile();
            newProfile.saveImage(picture.getFile(), filePath);
            newProfile.userId = user.id;
            newProfile.save();

            return ok(viewprofile.render(user, newProfile));
        }
    }
}
