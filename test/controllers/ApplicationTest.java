package controllers;

import org.junit.Before;
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
public class ApplicationTest extends WithApplication {

    @Before
    public void setUp() {
        start(fakeApplication(inMemoryDatabase()));
    }

    @Test
    public void testLoginFailWithNoParameters() {
        Result result = route(fakeRequest("POST", routes.Application.authenticate().toString()));
        assertEquals(BAD_REQUEST, result.status());
    }

    @Test
    public void testLoginSuccessWithCorrectPassword() {
        Map<String, String> data = new HashMap<>();
        data.put("email", "admin@gmail.com");
        data.put("password", "123456");
        Result result = route(fakeRequest("POST", routes.Application.authenticate().toString()).bodyForm(data));
        assertEquals(SEE_OTHER, result.status());
        assertEquals(routes.Dashboard.index().toString(), result.redirectLocation());
    }

    @Test
    public void testLoginFailWithIncorrectPassword() {
        Map<String, String> data = new HashMap<>();
        data.put("email", "admin@gmail.com");
        data.put("password", "wrongPassword");
        Result result = route(fakeRequest("POST", routes.Application.authenticate().toString()).bodyForm(data));
        assertEquals(BAD_REQUEST, result.status());
    }

    @Test
    public void testLoginFailWithIncorrectUsername() {
        Map<String, String> data = new HashMap<>();
        data.put("email", "admin2222@gmail.com");
        data.put("password", "123456");
        Result result = route(fakeRequest("POST", routes.Application.authenticate().toString()).bodyForm(data));
        assertEquals(BAD_REQUEST, result.status());
    }

    @Test
    public void testLoginFailWithEmptyUsername() {
        Map<String, String> data = new HashMap<>();
        data.put("email", "");
        data.put("password", "123456");
        Result result = route(fakeRequest("POST", routes.Application.authenticate().toString()).bodyForm(data));
        assertEquals(BAD_REQUEST, result.status());
    }

    @Test
    public void testLoginFailWithEmptyPassword() {
        Map<String, String> data = new HashMap<>();
        data.put("email", "admin@gmail.com");
        data.put("password", "");
        Result result = route(fakeRequest("POST", routes.Application.authenticate().toString()).bodyForm(data));
        assertEquals(BAD_REQUEST, result.status());
    }

}
