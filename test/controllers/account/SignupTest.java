package controllers.account;

import models.User;
import org.junit.Before;
import org.junit.Test;
import play.Logger;
import play.mvc.Result;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.*;

/**
 * Created by vuongnguyen on 13/02/2017.
 * This class served as Testing class for Signup Page
 */
public class SignupTest {
    @Before
    public void setUp() {
        start(fakeApplication(inMemoryDatabase()));
    }

    @Test
    public void testSignupIndex() {
        Logger.info(controllers.account.Signup.created().toString());
        Result result = route(fakeRequest("GET", routes.Signup.create().toString()));
        assertEquals(OK, result.status());
    }

    @Test
    public void testSignupSuccess() {
        Map<String, String> data = new HashMap<>();
        data.put("email", "register@gmail.com");
        data.put("fullname", "New Register");
        data.put("inputPassword", "123456");
        Result result = route(fakeRequest("POST", routes.Signup.save().toString()).bodyForm(data));
        assertEquals(OK, result.status());
        // check if there is a new use in the system.
        User newUser = User.findByEmail("register@gmail.com");
        assertEquals(newUser.fullname, "New Register");
    }

    @Test
    public void testSignupFailWithoutFormData() {
        Result result = route(fakeRequest("POST", routes.Signup.save().toString()));
        assertEquals(BAD_REQUEST, result.status());
    }

    @Test
    public void testSignupFailWithEmptyEmail() {
        Map<String, String> data = new HashMap<>();
        data.put("email", "");
        data.put("fullname", "New Register");
        data.put("inputPassword", "123456");
        Result result = route(fakeRequest("POST", routes.Signup.save().toString()).bodyForm(data));
        assertEquals(BAD_REQUEST, result.status());
    }

    @Test
    public void testSignupFailWithEmptyFullname() {
        Map<String, String> data = new HashMap<>();
        data.put("email", "register@gmail.com");
        data.put("fullname", "");
        data.put("inputPassword", "123456");
        Result result = route(fakeRequest("POST", routes.Signup.save().toString()).bodyForm(data));
        assertEquals(BAD_REQUEST, result.status());
    }

    @Test
    public void testSignupFailWithEmptyPassword() {
        Map<String, String> data = new HashMap<>();
        data.put("email", "register@gmail.com");
        data.put("fullname", "New Register");
        data.put("inputPassword", "");
        Result result = route(fakeRequest("POST", routes.Signup.save().toString()).bodyForm(data));
        assertEquals(BAD_REQUEST, result.status());
    }

    @Test
    public void testSignupFailWithLongEmail() {
        String longEmail = "longlonglonglonglonglonglonglonglolonglonglonglonglonglonglonglong@gmail.com";
        Map<String, String> data = new HashMap<>();
        data.put("email", longEmail);
        data.put("fullname", "New Register");
        data.put("inputPassword", "");
        Result result = route(fakeRequest("POST", routes.Signup.save().toString()).bodyForm(data));
        assertEquals(BAD_REQUEST, result.status());
    }

    @Test
    public void testSignupFailWithExistingEmail() {
        Map<String, String> data = new HashMap<>();
        data.put("email", "admin@gmail.com");
        data.put("fullname", "Admin");
        data.put("inputPassword", "");
        Result result = route(fakeRequest("POST", routes.Signup.save().toString()).bodyForm(data));
        assertEquals(BAD_REQUEST, result.status());
    }

}
