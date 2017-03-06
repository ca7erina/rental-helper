package models.matcher;

import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.Index;
import play.data.format.Formats;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by vuongnguyen on 04/03/2017.
 */

@Entity
public class MatchingInfo extends Model {
    @Id
    @Column(unique = true)
    public Long id;

    public Long requestedUserId;

    public Long incomingRequestId;

    public Long matchedUserId;

    @Formats.DateTime(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date createdDate;

    @Formats.DateTime(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date updatedDate;

    public Boolean active;

    @Index
    public Long userId;

    public static final Model.Finder<Long, MatchingInfo> find = new
            Model.Finder<Long, MatchingInfo>(Long.class, MatchingInfo.class);

    /**
     * @param id
     * @return a user
     */
    public static List<MatchingInfo> findByUserId(Long id) {
        return find.where().eq("user_id", id).findList();
    }

    public static MatchingInfo getExistingRequestedUser(Long userId, Long requestedUserId) {
        return find
                .where()
                .eq("user_id", userId)
                .eq("requestedUserId", requestedUserId)
                .eq("active", true)
                .findUnique();
    }

    public static MatchingInfo getExistingIncomingUser(Long userId, Long incomingRequestId) {
        return find
                .where()
                .eq("user_id", userId)
                .eq("incomingRequestId", incomingRequestId)
                .eq("active", true)
                .findUnique();
    }
}
