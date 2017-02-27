package models;

import com.avaje.ebean.Model;
import play.data.format.Formats;
import play.data.validation.Constraints;

import javax.imageio.ImageIO;
import javax.persistence.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Entity
public class UserProfile extends Model {

    @Id
    @Column(unique = true)
    public Long profileId;


    @Constraints.Required
    @Formats.NonEmpty
    @Column
    public String name;

    @Constraints.Required
    @Formats.NonEmpty
    @Column
    public String gender;

    @Constraints.Required
    @Formats.NonEmpty
    @Column
    public String age;

    public File image;

    @Formats.NonEmpty
    @Constraints.Required
    @Column
    public String bio;


    @OneToOne
    @JoinColumn(name = "id")
    public User user;

    @Column(unique = true)
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
        } catch (IOException e) {
            System.out.println("Errorrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr");
        }
    }
}
