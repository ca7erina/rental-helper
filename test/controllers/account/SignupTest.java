package controllers.account;

import org.junit.Before;
import org.junit.Test;
import play.Logger;
import play.mvc.Result;

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
}
