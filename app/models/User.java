package models;

import com.avaje.ebean.Model;
import models.matcher.MatchingInfo;
import models.utils.AppException;
import models.utils.Hash;
import play.data.format.Formats;
import play.data.validation.Constraints;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


/**
 * User: vuongnq
 * Date: 01/02/2017
 */
@Entity
public class User extends Model {

    protected static int MAX_LIMIT_ROWS = 3;

    @Id
    public Long id;

    @Constraints.Required
    @Formats.NonEmpty
    @Column(unique = true)
    public String email;

    @Constraints.Required
    @Formats.NonEmpty
    @Column(unique = true)
    public String fullname;

    public String confirmationToken;

    @Constraints.Required
    @Formats.NonEmpty
    public String passwordHash;

    @Formats.DateTime(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date dateCreation;

    @Formats.NonEmpty
    public Boolean validated = false;

    @OneToOne(mappedBy = "user")
    public UserProfile profile;

    @OneToOne(mappedBy = "user")
    public UserPreferences preferences;

    public static Model.Finder<Long, User> find = new Model.Finder<Long, User>(Long.class, User.class);

    /**
     * @param id
     * @return a user
     */
    public static User findById(Long id) {
        return find.where().eq("id", id).findUnique();
    }

    /**
     * Retrieve a user from an email.
     *
     * @param email email to search
     * @return a user
     */
    public static User findByEmail(String email) {
        return find.where().eq("email", email).findUnique();
    }

    /**
     * Retrieve a user from a fullname.
     *
     * @param fullname Full name
     * @return a user
     */
    public static User findByFullname(String fullname) {
        return find.where().eq("fullname", fullname).findUnique();
    }

    /**
     * Retrieve similarity users based on their full name and limit to 3 persons
     *
     * @param email email to search
     * @return List of users
     */
    public static List<User> findSimilarityFullname(String email) {
        String fullname = User.findByEmail(email).fullname;
        return find.where().ilike("fullname", "%" + fullname + "%").setMaxRows(User.MAX_LIMIT_ROWS).findList();
    }

    /**
     * Retrieves a user from a confirmation token.
     *
     * @param token the confirmation token to use.
     * @return a user if the confirmation token is found, null otherwise.
     */
    public static User findByConfirmationToken(String token) {
        return find.where().eq("confirmationToken", token).findUnique();
    }

    /**
     * Authenticate a User, from a email and clear password.
     *
     * @param email         email
     * @param clearPassword clear password
     * @return User if authenticated, null otherwise
     * @throws AppException App Exception
     */
    public static User authenticate(String email, String clearPassword) throws AppException {

        // get the user with email only to keep the salt password
        User user = find.where().eq("email", email).findUnique();
        if (user != null) {
            // get the hash password from the salt + clear password
            if (Hash.checkPassword(clearPassword, user.passwordHash)) {
                return user;
            }
        }

        return null;
    }

    public void changePassword(String password) throws AppException {
        this.passwordHash = Hash.createPassword(password);
        this.save();
    }

    /**
     * Confirms an account.
     *
     * @return true if confirmed, false otherwise.
     * @throws AppException App Exception
     */
    public static boolean confirm(User user) throws AppException {
        if (user == null) {
            return false;
        }

        user.confirmationToken = null;
        user.validated = true;
        user.save();

        return true;
    }

    /**
     * @param currentUser   the current user
     * @param requestedUser the user that the current is interested in
     */
    public static void addRequestedUser(User currentUser, User requestedUser) {
        // Add requestedId for the current User
        MatchingInfo existingRequestedUser = MatchingInfo.getExistingRequestedUser(currentUser.id, requestedUser.id);

        if (existingRequestedUser == null) {
            MatchingInfo requestedInfo = new MatchingInfo();
            requestedInfo.requestedUserId = requestedUser.id;
            requestedInfo.active = true;
            requestedInfo.userId = currentUser.id;
            requestedInfo.createdDate = new Date();

            requestedInfo.save();
        } else {
            existingRequestedUser.active = true;
            existingRequestedUser.updatedDate = new Date();

            existingRequestedUser.save();
        }


        // Add incomingRequest for the requestedUser
        MatchingInfo existingIncomingUser = MatchingInfo.getExistingIncomingUser(requestedUser.id, currentUser.id);
        if (existingIncomingUser == null) {
            MatchingInfo incomingInfo = new MatchingInfo();
            incomingInfo.active = true;
            incomingInfo.incomingRequestId = currentUser.id;
            incomingInfo.userId = requestedUser.id;
            incomingInfo.createdDate = null;

            incomingInfo.save();
        } else {
            existingIncomingUser.active = true;
            existingIncomingUser.updatedDate = null;
            existingIncomingUser.save();
        }
    }

    /**
     * Get the list of requested users for the current user
     *
     * @param currentUser the current user
     * @return Array
     */
    public static List<User> getRequestedUsers(User currentUser) {
        List<User> listUser = new ArrayList<>();

        for (MatchingInfo each : MatchingInfo.findByUserId(currentUser.id)) {
            if (each.userId != null && each.active && each.requestedUserId != null) {
                listUser.add(User.findById(each.requestedUserId));
            }
        }

        return listUser;
    }

    /**
     * Remove the request for matching from the current User
     *
     * @param currentUser     the current user
     * @param requestedUserId the requested user
     */
    public static void removeRequestedUser(User currentUser, User requestedUserId) {
        MatchingInfo existingRequestedUser = MatchingInfo.getExistingRequestedUser(currentUser.id, requestedUserId.id);
        if (existingRequestedUser != null) {
            existingRequestedUser.active = false;
            existingRequestedUser.updatedDate = new Date();
            existingRequestedUser.save();
        }
    }

    /**
     * @param currentUser the current User
     * @return Array List of matched User
     */
    public static List<User> getMatchedUsers(User currentUser) {
        List<User> userList = new ArrayList<>();
        HashMap<Long, User> userMap = new HashMap<>();

        for (MatchingInfo each : MatchingInfo.findByUserId(currentUser.id)) {

            if (each.userId != null && each.active && each.matchedUserId != null) {
                userMap.put(each.matchedUserId, User.findById(each.matchedUserId));
            }
        }

        userList.addAll(userMap.values());
        return userList;
    }

    /**
     * @param requestedUserId
     */
    public static void addMatchedUser(User requestedUserId) {

    }

    /**
     * Get the incoming list of users for the current user
     *
     * @param currentUser the current user
     * @return Array
     */
    public static List<User> getIncomingRequests(User currentUser) {
        List<User> listIncomingUser = new ArrayList<>();

        for (MatchingInfo each : MatchingInfo.findByUserId(currentUser.id)) {
            if (each.userId != null && each.active && each.incomingRequestId != null) {
                listIncomingUser.add(User.findById(each.incomingRequestId));
            }
        }

        return listIncomingUser;
    }
}
