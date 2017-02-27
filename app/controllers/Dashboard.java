package controllers;

import models.User;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.matches;

/**
 * User: vuongnq
 * Date: 01/02/2017
 */
@Security.Authenticated(Secured.class)
public class Dashboard extends Controller {

    public Result index() {
        return ok(matches.render(User.findByEmail(request().username())));
    }
}
