package controllers.account.settings;

import controllers.Secured;
import models.User;
import play.Logger;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

/**
 * Match Setting page
 * Created by vuongnguyen on 03/02/2017.
 */

@Security.Authenticated(Secured.class)
public class Match extends Controller {

    /**
     * Match page settings
     *
     * @return index settings
     */
    public Result index() {
        return new Password().index();
    }
}

