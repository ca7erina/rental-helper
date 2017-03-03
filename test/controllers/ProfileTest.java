package controllers;

import models.User;
import models.UserProfile;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import play.mvc.Result;
import play.test.WithApplication;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static play.test.Helpers.*;

/**
 * Tests for Edit Profile
 */
public class ProfileTest extends WithApplication {

    @Before
    public void setUp() {
        start(fakeApplication(inMemoryDatabase()));
        userLogin();
    }

    @Test
    public void editProfileTestSuccess() {
        Map<String, String> newProfile = new HashMap<>();
        newProfile.put("name", "newName");
        newProfile.put("age", "2");
        newProfile.put("gender", "male");
        newProfile.put("bio", "Empty Bio");
        newProfile.put("userId", "2");
        Result result = route(fakeRequest("POST", controllers.profile.routes.EditProfile.submit().toString()).bodyForm(newProfile));
        assertEquals(SEE_OTHER, result.status());
        assertEquals(controllers.profile.routes.EditProfile.view().toString(), result.redirectLocation());
        // check if user info all changed by the request.
        User thisUser = User.findByEmail("admin@gmail.com");
        UserProfile thisUserProfile = UserProfile.findByUserId(thisUser.id);
        assertEquals(thisUserProfile.name, newProfile.get("name"));
        assertEquals(thisUserProfile.age, newProfile.get("age"));
        assertEquals(thisUserProfile.gender, newProfile.get("gender"));
        assertEquals(thisUserProfile.bio, newProfile.get("bio"));
    }

    @Test
    public void editProfileFailwithEmptyName() {
        Map<String, String> newProfile = new HashMap<>();
        newProfile.put("name", "");
        newProfile.put("age", "2");
        newProfile.put("gender", "male");
        newProfile.put("bio", "Empty Bio");
        newProfile.put("userId", "2");
        Result result = route(fakeRequest("POST", controllers.profile.routes.EditProfile.submit().toString()).bodyForm(newProfile));
        assertEquals(BAD_REQUEST, result.status());
    }

    @Test
    public void editProfileFailwithEmptyAge() {
        Map<String, String> newProfile = new HashMap<>();
        newProfile.put("name", "newName");
        newProfile.put("age", "");
        newProfile.put("gender", "male");
        newProfile.put("bio", "Empty Bio");
        newProfile.put("userId", "2");
        Result result = route(fakeRequest("POST", controllers.profile.routes.EditProfile.submit().toString()).bodyForm(newProfile));
        assertEquals(BAD_REQUEST, result.status());
    }

    @Test
    public void editProfileFailwithEmptyGender() {
        Map<String, String> newProfile = new HashMap<>();
        newProfile.put("name", "newName");
        newProfile.put("age", "2");
        newProfile.put("gender", "");
        newProfile.put("bio", "Empty Bio");
        newProfile.put("userId", "2");
        Result result = route(fakeRequest("POST", controllers.profile.routes.EditProfile.submit().toString()).bodyForm(newProfile));
        assertEquals(BAD_REQUEST, result.status());
    }

    @Test
    public void editProfileFailwithEmptyBio() {
        Map<String, String> newProfile = new HashMap<>();
        newProfile.put("name", "newName");
        newProfile.put("age", "2");
        newProfile.put("gender", "male");
        newProfile.put("bio", "");
        newProfile.put("userId", "2");
        Result result = route(fakeRequest("POST", controllers.profile.routes.EditProfile.submit().toString()).bodyForm(newProfile));
        assertEquals(BAD_REQUEST, result.status());
    }

    @After
    public void tearDown() {
        userLogout();
    }

    private void userLogin() {
        Map<String, String> data = new HashMap<>();
        data.put("email", "admin@gmail.com");
        data.put("password", "123456");
        route(fakeRequest("POST", controllers.routes.Application.authenticate().toString()).bodyForm(data));
    }

    private void userLogout() {
        route(fakeRequest("GET", controllers.routes.Application.logout().toString()));
    }

}
