package controllers;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import play.mvc.Result;
import play.test.WithApplication;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static play.mvc.Http.Status.*;
import static play.test.Helpers.*;

/**
 * Tests for Login
 */
public class ProfileTest extends WithApplication {

    @Before
    public void setUp() {
        start(fakeApplication(inMemoryDatabase()));
        Map<String, String> data = new HashMap<>();
        data.put("email", "admin@gmail.com");
        data.put("password", "123456");
        route(fakeRequest("POST", routes.Application.authenticate().toString()).bodyForm(data));
    }

    @Test
    public void EditProfileTest() {
        Map<String, String> profile_data = new HashMap<>();
        profile_data.put("name", "Admin");
        profile_data.put("age","2");
        profile_data.put("gender", "male");
        profile_data.put("bio", "Empty Bio");
        profile_data.put("userId", "2");
        Result result = route(fakeRequest("POST", controllers.profile.routes.EditProfile.submit().toString()).bodyForm(profile_data));

        assertEquals(OK, result.status());
        assertEquals(controllers.profile.routes.EditProfile.index().toString(), result.redirectLocation());
    }

    @After
    public void LogOut() {

      route(fakeRequest("GET", routes.Application.logout().toString()));
    }
}
