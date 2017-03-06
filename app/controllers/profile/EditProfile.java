package controllers.profile;

import models.User;
import models.UserProfile;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import play.mvc.Result;
import views.html.profile.editprofile;
import views.html.profile.viewprofile;

import static play.data.Form.form;

public class EditProfile extends Controller {

    public Form<UserProfile> profileForm = form(UserProfile.class);
    public Result GO_VIEW = redirect(controllers.profile.routes.EditProfile.view());

    public Result index() {
        User user = User.findByEmail(session().get("email"));
        UserProfile profile = UserProfile.findByUserId(user.id);
        profileForm = profileForm.fill(profile);

        return ok(editprofile.render(user, profileForm));
    }

    public Result submit() {
        User user = User.findByEmail(session().get("email"));
        Form<UserProfile> filledForm = profileForm.bindFromRequest();

        if (filledForm.hasErrors()) {
            return badRequest(editprofile.render(user, profileForm));
        } else {
            MultipartFormData body = request().body().asMultipartFormData();
            FilePart picture = null;
            if (body != null && body.getFile("image") != null) {
                picture = body.getFile("image");
            }

            UserProfile profile = null;

            if (user != null) {
                profile = UserProfile.findByUserId(user.id);
            } else {
                profile = UserProfile.findByUserId(filledForm.get().userId);
            }
            profile.set(filledForm.get());

            if (picture != null && picture.getFile() != null) {
                profile.image = picture.getFile();
                String filePath = "public/user_pictures/" + user.email + ".png";
                profile.saveImage(picture.getFile(), filePath);
            }

            profile.save();
            return GO_VIEW;
        }
    }

    public Result view() {
        User user = User.findByEmail(session().get("email"));
        UserProfile profile = UserProfile.findByUserId(user.id);
        return (ok(viewprofile.render(user, profile)));
    }

    public Result viewOther(String username) {
        User user = User.findByFullname(username.replace("."," "));
        UserProfile profile = UserProfile.findByUserId(user.id);
        return(ok(viewprofile.render(user,profile)));
    }
}
