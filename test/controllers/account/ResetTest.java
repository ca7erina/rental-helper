package controllers.account;

import org.junit.Before;
import org.junit.Test;
import play.i18n.Messages;
import play.mvc.Result;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.*;

/**
 * Created by vuongnguyen on 13/02/2017.
 * This class served as Testing class for Reset Page
 */
public class ResetTest {
    @Before
    public void setUp() {
        start(fakeApplication(inMemoryDatabase()));
    }

    @Test
    public void testResetIndex() {
        Result result = route(fakeRequest("GET", routes.Reset.ask().toString()));
        assertEquals(OK, result.status());
    }

    @Test
    public void testResetPassword() {
        Map<String, String> data = new HashMap<>();
        data.put("email", "admin@gmail.com");
        Result result = route(fakeRequest("POST", routes.Reset.ask().toString()).bodyForm(data));
        assertEquals(OK, result.status());
        assertTrue(contentAsString(result).contains(Messages.get("resetpassword.mailsent")));
    }

    @Test
    public void testResetPasswordFailWithEmptyEmail() {
        Map<String, String> data = new HashMap<>();
        data.put("email", "");
        Result result = route(fakeRequest("POST", routes.Reset.ask().toString()).bodyForm(data));
        assertEquals(BAD_REQUEST, result.status());
    }
}
