package controllers.core;

import models.User;
import models.UserPreferences;
import models.UserProfile;
import models.matcher.MatchingInfo;
import play.mvc.Controller;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vuongnguyen on 14/02/2017.
 * <p>
 * Core service that supports Match Controller.
 * It provides 3 methods:
 * + Agree: List of users that agreed on match request
 * + Waiting: List of users that haven't responded to match request
 * + Matcher: list of all matching user to the current one
 */

public class MatcherService extends Controller {

    /**
     * @param username email to search the matched users that already agreed with user request
     * @return List of matched users
     */
    public static List<User> getSimilarityUsers(String username) {
        User user = User.findByFullname(username);
        UserPreferences preferences = UserPreferences.findByUserId(user.id);
        List<UserPreferences> simPreferences = UserPreferences.find.where().eq("locationPref", preferences.locationPref).findList();
        List<User> simUsers = new ArrayList<>();

        for (int i = 0; i < simPreferences.size(); ++i) {
            User otherUser = User.findById(simPreferences.get(i).userId);
            String otherGender = UserProfile.findByUserId(otherUser.id).gender;

            // Just add users who are not already matched and not in requested lists
            if (!otherUser.id.equals(user.id) && preferences.genderPref.equals(otherGender)) {
                if (!MatchingInfo.checkExistInMatchedAndRequestedIds(user.id, otherUser.id)) {
                    simUsers.add(otherUser);
                }
            }
        }

        return simUsers;
    }
}
