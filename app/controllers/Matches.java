package controllers;

import controllers.core.MatcherService;
import models.User;
import models.matcher.MatchingInfo;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import java.util.Date;
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

    public Result getMatchedList() {
        User user = User.findByEmail(session().get("email"));
        List<User> listMatchedUser = User.getMatchedUsers(user);

        return ok(toJson(listMatchedUser));
    }

    public Result getWaitingList() {
        User user = User.findByEmail(session().get("email"));

        return ok(toJson(User.getRequestedUsers(user)));
    }


    public Result getNewSuggestionMatches() {
        User user = User.findByEmail(session().get("email"));
        List<User> newMatches = MatcherService.getSimilarityUsers(user.fullname);

        return ok(toJson(newMatches));
    }

    /**
     * Get incoming requestes for current user
     *
     * @return Array List of incoming requestes
     */
    public Result getNewRequestList() {
        User user = User.findByEmail(session().get("email"));

        return ok(toJson(User.getIncomingRequests(user)));
    }

    /**
     * @param username
     * @return
     */
    public Result sendMatchRequest(String username) {
        User user = User.findByEmail(session().get("email"));
        User requestedUser = User.findByFullname(username.replace(".", " "));

        User.addRequestedUser(user, requestedUser);

        return index();
    }

    /**
     * Cancel the previous match request for the user
     *
     * @param username
     * @return
     */
    public Result cancelMatchRequest(String username) {
        User user = User.findByEmail(session().get("email"));
        User requestedUser = User.findByFullname(username.replace(".", " "));

        User.removeRequestedUser(user, requestedUser);

        return index();
    }

    /**
     * Accept the request from other user
     *
     * @param username the incoming username
     * @return
     */
    public Result confirmMatch(String username) {
        User currentUser = User.findByEmail(session().get("email"));
        User requestedUser = User.findByFullname(username.replace(".", " "));

        // Update incoming request for current user
        MatchingInfo existingIncomingUser = MatchingInfo.getExistingIncomingUser(currentUser.id, requestedUser.id);
        if (existingIncomingUser != null) {
            existingIncomingUser.incomingRequestId = null;
            existingIncomingUser.matchedUserId = requestedUser.id;
            existingIncomingUser.updatedDate = new Date();

            existingIncomingUser.save();

        }

        // Update matched for requested user
        List<MatchingInfo> matchRequested = MatchingInfo.findByUserId(requestedUser.id);
        for (MatchingInfo user: matchRequested) {
            if (user.active && user.requestedUserId != null) {
                user.requestedUserId = null;
                user.updatedDate = new Date();
                user.matchedUserId = currentUser.id;

                user.save();
            }
        }

        return index();
    }

    /**
     * Reject the request from other user
     *
     * @param username the incoming username
     * @return
     */
    public Result rejectMatch(String username) {
        User user = User.findByEmail(session().get("email"));
        User requestedUser = User.findByFullname(username.replace(".", " "));

        User.removeRequestedUser(user, requestedUser);

        return index();
    }
}
