package models;

import com.avaje.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.*;

@Entity
public class UserPreferences extends Model {

    @Id
    @Column(unique = true)
    public Long preferenceId;

    @Column
    @Constraints.Required
    public String locationPref;

    @Column
    @Constraints.Required
    public String genderPref;

    @Column
    @Constraints.Required
    public String studentPref;

    @OneToOne
    @JoinColumn(name = "id")
    public User user;

    @Column(unique = true)
    public Long userId;


    public static Model.Finder<Long, UserPreferences> find = new Model.Finder<Long, UserPreferences>(Long.class, UserPreferences.class);

    public static UserPreferences findByUserId(Long id) {
        return find.where().eq("user_id", id).findUnique();
    }

    public void set(UserPreferences newPreferences) {
        locationPref = newPreferences.locationPref;
        genderPref = newPreferences.genderPref;
        studentPref = newPreferences.studentPref;
    }
}
