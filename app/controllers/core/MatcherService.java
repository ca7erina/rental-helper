package controllers.core;

import models.User;
import play.Logger;

import java.util.ArrayList;
import java.util.List;
import models.UserPreferences;

/**
 * Created by vuongnguyen on 14/02/2017.
 *
 * Core service that supports Match Controller.
 * It provides 3 methods:
 * + Agree: List of users that agreed on match request
 * + Waiting: List of users that haven't responded to match request
 * + Matcher: list of all matching user to the current one
 */

public class MatcherService {

    /**
     *
     * @param username email to search the matched users that already agreed with user request
     * @return List of matched users
     */
    public static List<User> getMatchedUsers(String username) {
        User user = User.findByFullname(username);
        UserPreferences preferences = UserPreferences.findByUserId(user.id);
        List<UserPreferences> simPreferences = UserPreferences.find.where().eq("locationPref",preferences.locationPref).findList();
        List<User> simUsers = new ArrayList<>();
        for(int i=0; i<simPreferences.size(); ++i) {
            simUsers.add(i,User.findById(simPreferences.get(i).userId));
        }
        return simUsers;
    }

    /**
     *
     * @param username email to search the waiting users that haven't responded to the user request
     * @return List of waiting users
     */
    public static List<User> getWaitingForUsers(String username) {
        return User.findSimilarityFullname(username);
    }

    /**
     *
     * @param username email to search the suggest users from the matching algorithm
     * @return List of suggestion users
     */
    public static List<User> getNewSuggestionUsers(String username) {
        return User.findSimilarityFullname(username);
    }
}
