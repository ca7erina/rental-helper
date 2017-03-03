package controllers.account;

import models.User;
import models.UserPreferences;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import play.mvc.Result;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static play.test.Helpers.*;
import static play.test.Helpers.fakeRequest;
import static play.test.Helpers.route;

/**
 * Created by chenxiaoxue on 3/3/17.
 * Test EditProference controller
 */
public class EditPreferenceTest {

    @Before
    public void setUp() {
        start(fakeApplication(inMemoryDatabase()));
        userLogin();
    }

    @Test
    public void EditUserPreferenceTest() {
        Map<String, String> newPreference = new HashMap<>();
        newPreference.put("locationPref", "Dublin 1");
        newPreference.put("genderPref", "Female");
        newPreference.put("studentPref", "Student");
        newPreference.put("userId", "2");
        Result result = route(fakeRequest("POST", controllers.routes.EditPreferences.submit().toString()).bodyForm(newPreference));
        assertEquals(SEE_OTHER, result.status());
        assertEquals(controllers.routes.EditPreferences.index().toString(), result.redirectLocation());
        // check if user preferences all changed by this request.
        User thisUser = User.findByEmail("admin@gmail.com");
        UserPreferences thisUserPreference = UserPreferences.findByUserId(thisUser.id);
        assertEquals(thisUserPreference.genderPref, newPreference.get("genderPref"));
        assertEquals(thisUserPreference.locationPref, newPreference.get("locationPref"));
        assertEquals(thisUserPreference.studentPref, newPreference.get("studentPref"));
    }

    @Test
    public void EditUserPreferenceWithEmptyLocation() {
        Map<String, String> newPreference = new HashMap<>();
        newPreference.put("locationPref", "");
        newPreference.put("genderPref", "Female");
        newPreference.put("studentPref", "Student");
        newPreference.put("userId", "2");
        Result result = route(fakeRequest("POST", controllers.routes.EditPreferences.submit().toString()).bodyForm(newPreference));
        assertEquals(BAD_REQUEST, result.status());
    }

    @Test
    public void EditUserPreferenceWithEmptyGender() {
        Map<String, String> newPreference = new HashMap<>();
        newPreference.put("locationPref", "Dublin 1");
        newPreference.put("genderPref", "");
        newPreference.put("studentPref", "Student");
        newPreference.put("userId", "2");
        Result result = route(fakeRequest("POST", controllers.routes.EditPreferences.submit().toString()).bodyForm(newPreference));
        assertEquals(BAD_REQUEST, result.status());
    }

    @Test
    public void EditUserPreferenceWithEmptyProfessional() {
        Map<String, String> newPreference = new HashMap<>();
        newPreference.put("locationPref", "Dublin 1");
        newPreference.put("genderPref", "Female");
        newPreference.put("studentPref", "");
        newPreference.put("userId", "2");
        Result result = route(fakeRequest("POST", controllers.routes.EditPreferences.submit().toString()).bodyForm(newPreference));
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
