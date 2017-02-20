package controllers.profile;

import models.UserProfile;
import java.io.File;

import controllers.Application;
import models.User;
import models.utils.AppException;
import models.utils.Hash;
import play.Logger;
import play.Configuration;
import play.data.Form;
import play.data.validation.Constraints;
import play.i18n.Messages;
import play.mvc.Result;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import views.html.profile.editprofile;
import views.html.profile.viewprofile;

import static play.data.Form.form;
import play.mvc.Controller;

public class EditProfile extends Controller{

  public Form<UserProfile> profileForm = form( UserProfile.class);


  public Result index() {
      User user = User.findByEmail(session().get("email"));
      UserProfile profile = UserProfile.find.where().eq("user_id", user.id).findUnique(); //byId(user.id);
      //UserProfile profile = new UserProfile();

      profileForm = profileForm.fill(profile);
      return ok(editprofile.render(user,profileForm));    /*createprofile.render(User.findByEmail(request().username()),form(Application.Register.class)));*/
  }


  public Result submit() {
      User user = User.findByEmail(session().get("email"));
      Form<UserProfile> filledForm = profileForm.bindFromRequest();

      if (filledForm.hasErrors()) {
          return badRequest(editprofile.render(user, profileForm));
      } else {

          MultipartFormData body = request().body().asMultipartFormData();
          FilePart picture = body.getFile("image");
          UserProfile profile = UserProfile.find.where().eq("user_id", user.id).findUnique(); //byId(user.id);

          profile.set(filledForm.get());
          if (picture != null && picture.getFile() != null) {
            profile.image = picture.getFile();
            String filePath = "app/assets/user_pictures/" + profile.name + ".png";
            //filePath.concat(profile.name);
            profile.saveImage(picture.getFile(), filePath);
           }
          profile.save();
          return ok(viewprofile.render(user,profile));
        /* Check resourceFile for null, then extract the File object and process it */
     }


  }

  public Result view() {
      User user = User.findByEmail(session().get("email"));
      UserProfile profile = UserProfile.find.where().eq("user_id", user.id).findUnique(); //byId(user.id);
      return(ok(viewprofile.render(user,profile)));
  }
}
