package controllers;

import models.UserProfile;

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
import views.html.profile.createprofile;

import static play.data.Form.form;
import play.mvc.Controller;

public class CreateProfile extends Controller{

  public Form<UserProfile> profileForm = form( UserProfile.class);

  public Result index() {
      return ok(createprofile.render(profileForm));    /*createprofile.render(User.findByEmail(request().username()),form(Application.Register.class)));*/
  }

  public Result submit() {
      Form<UserProfile> filledForm = profileForm.bindFromRequest();
      UserProfile created = filledForm.get();
      /*created.id = ctx().session().get(id);*/

      created.save();
      return ok(ctx().session().get("id"));
  }
}
