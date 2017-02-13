package models;

import play.data.format.Formats;
import play.data.validation.Constraints;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Id;
import com.avaje.ebean.Model;
import java.util.*;

@Entity
public class UserProfile extends Model{

  @Id
  @Column(unique=true)
  public Long id;

  /*@Constraints.Required
  @Formats.NonEmpty*/
  @Column(unique=true)
  public String name;

  @Constraints.Required
  @Formats.NonEmpty
  @Column(unique=true)
  public String gender;

  @Constraints.Required
  @Formats.NonEmpty
  @Column(unique=true)
  public String age;

  @Constraints.Required
  @Formats.NonEmpty
  @Column(unique=true)
  public String bio;

  /*
  @OneToOne(mappedBy="id")
  public User user;

  public static Finder<Long,UserProfile> find = new Finder<Long,UserProfile>( Long.class, UserProfile.class );

  /*
  public static UserProfile findByEmail(String email) {
      return find.where().eq("email", email).findUnique();
  }*/


}
