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
import views.html.profile.createprofile;
import views.html.profile.viewprofile;

import static play.data.Form.form;
import play.mvc.Controller;

public class CreateProfile extends Controller{

  public Form<UserProfile> profileForm = form( UserProfile.class);

  public Result index() {
      return ok(createprofile.render(profileForm));    /*createprofile.render(User.findByEmail(request().username()),form(Application.Register.class)));*/
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
          newProfile.image = picture.getFile();
          String filePath = "app/assets/pictures";
          filePath.concat(newProfile.name);
          newProfile.saveImage(picture.getFile(), filePath);
          newProfile.userId = user.id;
          newProfile.save();

          return ok(viewprofile.render(user,newProfile));

      }
  }
}
