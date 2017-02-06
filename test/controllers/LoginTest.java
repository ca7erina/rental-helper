package controllers;

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
public class LoginTest extends WithApplication {

    @Test
    public void LoginWithEmptyParameters() {
        Result result = route(fakeRequest("POST", routes.Application.authenticate().toString()));
        assertEquals(BAD_REQUEST, result.status());
    }

    @Test
    public void LoginWithCorrectPassword() {
        Map<String, String> data = new HashMap<>();
        data.put("email", "chenx6@tcd.ie");
        data.put("password", "123456");
        Result result = route(fakeRequest("POST", routes.Application.authenticate().toString()).bodyForm(data));
        assertEquals(SEE_OTHER, result.status());
        assertEquals(routes.Dashboard.index().toString(), result.redirectLocation());
    }
}
