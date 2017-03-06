package models;

import com.avaje.ebean.Model;
import models.utils.AppException;
import models.utils.Hash;
import play.data.format.Formats;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
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

    //    @OneToMany(mappedBy = "user")
    @OneToMany()
    public List<MatchingInformation> matchingInformations;

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

    public void addRequestedUser(User user, Long requestedUserId) {
        MatchingInformation matchingInformation = new MatchingInformation();

        matchingInformation.userId = user.id;
        matchingInformation.requestedUserId = requestedUserId;
        matchingInformation.active = true;

        user.matchingInformations.add(matchingInformation);
        user.save();
    }

    /**
     * Get list of requested users
     *
     * @return
     */
    public List<User> getRequestedUsers(User user) {
        List<User> listUser = new ArrayList<User>();
        for (MatchingInformation eachMatching: user.matchingInformations) {
            listUser.add(User.findById(eachMatching.userId));
        }
        return listUser;
    }

    /**
     * Remove the request for matching from the current User
     * @param requestedUserId
     */
    public void removeRequestedUser(User requestedUserId) {

    }

    public List<User> getMatchedUsers(User user) {
        return null;
    }

    public void addMatchedUser(User requestedUserId) {

    }

    public List<User> getIncomingRequests(User user) {
        return null;
    }

    public void addIncomingRequest(User requestingUser) {

    }

    public void removeIncomingRequest(User requestingUser) {

    }
}
