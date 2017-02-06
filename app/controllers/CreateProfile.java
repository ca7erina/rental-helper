package controllers;

import models.Profile;

import controllers.Application;
import models.User;
import models.utils.AppException;
import models.utils.Hash;
import play.Logger;
import play.Configuration;
/*import play.data.Form;*/
import play.data.validation.Constraints;
import play.i18n.Messages;
import play.mvc.Result;
import views.html.profile.createprofile;

import static play.data.Form.form;
import play.mvc.Controller;

public class CreateProfile extends Controller{

  public final Form<Profile> profileForm = form( Profile.class);

  public Result index() {
      return ok(create.render(profileForm));    /*createprofile.render(User.findByEmail(request().username()),form(Application.Register.class)));*/
  }

  public Result submit() {

      Form<Profile> filledForm = profileForm.bindFromRequest();
      Profile created = filledForm.get();

  }
}
