package models;

import play.data.format.Formats;
import play.data.validation.Constraints;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.JoinColumn;
import javax.persistence.Id;
import com.avaje.ebean.Model;
import java.util.*;

@Entity
public class UserPreferences extends Model {

  @Id
  @Column(unique=true)
  public Long preferenceId;

  @Column
  public String locationPref;

  @Column
  public String genderPref;

  @Column
  public String studentPref;

  @OneToOne
  @JoinColumn(name = "id")
  public User user;

  @Column(unique=true)
  public Long userId;


  public static Model.Finder<Long, UserPreferences> find = new Model.Finder<Long, UserPreferences>(Long.class, UserPreferences.class );

  public static UserPreferences findByUserId(Long id) {
          return find.where().eq("user_id", id).findUnique();
  }

  public void set(UserPreferences newPreferences) {
    locationPref = newPreferences.locationPref;
    genderPref = newPreferences.genderPref;
    studentPref = newPreferences.studentPref;
  }


}
