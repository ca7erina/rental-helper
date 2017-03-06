package controllers;

import controllers.core.MatcherService;
import models.User;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import java.util.ArrayList;
import java.util.List;

import static play.libs.Json.toJson;

/**
 * Matches page controller
 */

@Security.Authenticated(Secured.class)
public class Matches extends Controller {


    public Result index() {
        return ok(views.html.matches.render(User.findByEmail(request().username())));
    }

    //TODO
    public Result getMatchedList() {
        List<User> matchedUsers = new ArrayList<>();
        User user_1 = User.findByEmail("a@gmail.com");
        User user_2 = User.findByEmail("admin@gmail.com");
        matchedUsers.add(0, user_1);
        matchedUsers.add(1, user_2);
        return ok(toJson(matchedUsers));
    }

    //TODO
    public Result getWaitingList() {
        User user = User.findByEmail(session().get("email"));
        return ok(toJson(user.getRequestedUsers(user)));
    }

    //TODO
    public Result getNewSuggestionMatches() {
        User user = User.findByEmail(session().get("email"));
        List<User> newMatches = MatcherService.getMatchedUsers(user.fullname);

        return ok(toJson(newMatches));
    }

    public Result getNewRequestList() {
        User user = User.findByEmail(session().get("email"));
        return ok(toJson(user.getIncomingRequests(user)));
    }

    /**
     *
     * @param username
     * @return
     */
    public Result sendMatchRequest(String username) {
        User user = User.findByEmail(session().get("email"));
        User requestedUser = User.findByFullname(username.replace(".", " "));
        user.addRequestedUser(user, requestedUser.id);

        return index();
    }

    /**
     * Cancel the previous match request for the user
     * @param username
     * @return
     */
    public Result cancelMatchRequest(String username) {
        User user = User.findByEmail(session().get("email"));
        User requestedUser = User.findByFullname(username.replace(".", " "));

        return index();
    }

    // TODO
    public Result confirmMatch(String username) {
        User user = User.findByEmail(session().get("email"));
        User requestedUser = User.findByFullname(username.replace(".", " "));

        return index();
    }

    // TODO
    public Result rejectMatch(String username) {
        User user = User.findByEmail(session().get("email"));
        User requestedUser = User.findByFullname(username.replace(".", " "));

        return index();
    }
}
