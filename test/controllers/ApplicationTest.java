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
    public void testLoginWithEmptyParameters() {
        Result result = route(fakeRequest("POST", routes.Application.authenticate().toString()));
        assertEquals(BAD_REQUEST, result.status());
    }

    @Test
    public void testLoginWithCorrectPassword() {
        Map<String, String> data = new HashMap<>();
        data.put("email", "admin@gmail.com");
        data.put("password", "123456");
        Result result = route(fakeRequest("POST", routes.Application.authenticate().toString()).bodyForm(data));
        assertEquals(SEE_OTHER, result.status());
        assertEquals(routes.Dashboard.index().toString(), result.redirectLocation());
    }
}
