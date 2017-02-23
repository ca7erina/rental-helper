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
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

@Entity
public class UserProfile extends Model{

  @Id
  @Column(unique=true)
  public Long profileId;


  @Constraints.Required
  @Formats.NonEmpty
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

  @Formats.NonEmpty
  @Column(unique=true)
  public File image;

  @Formats.NonEmpty
  @Constraints.Required
  @Column(unique=true)
  public String bio;


  @OneToOne
  @JoinColumn(name = "id")
  public User user;

  @Column(unique=true)
  public Long userId;


  public static Model.Finder<Long, UserProfile> find = new Model.Finder<Long, UserProfile>(Long.class, UserProfile.class);

  public static UserProfile findByUserId(Long id) {
      return find.where().eq("user_id", id).findUnique();
  }

  public void set(UserProfile profile) {
    name = profile.name;
    age = profile.age;
    gender = profile.gender;
    bio = profile.bio;
   }

   public void saveImage(File file, String filepath) {
     try {
        BufferedImage img = ImageIO.read(file);
        File outputfile = new File(filepath);
        ImageIO.write(img, "png", outputfile);
     } catch(IOException e) {
       System.out.println("Errorrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr");
     }
   }
}
