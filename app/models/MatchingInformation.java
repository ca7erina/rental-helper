package models;

import com.avaje.ebean.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Created by vuongnguyen on 04/03/2017.
 */
@Entity
public class MatchingInformation extends Model {
    @Id
    @Column(unique = true)
    public Long id;

    public Long requestedUserId;

    public Long incomingRequestId;

    public Long matchedUserId;

    @ManyToOne(optional = false)
    public User user;

    public Long userId;

    public Boolean active;

    public static Model.Finder<Long, MatchingInformation> find = new
            Model.Finder<Long, MatchingInformation>(Long.class, MatchingInformation.class);

    /**
     * @param id
     * @return a user
     */
    public static MatchingInformation findByUserId(Long id) {
        return find.where().eq("user_id", id).findUnique();
    }
}
