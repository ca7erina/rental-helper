package controllers.account.settings;

import org.junit.Before;
import org.junit.Test;
import play.mvc.Result;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.*;
import static play.test.Helpers.inMemoryDatabase;

/**
 * Created by vuongnguyen on 13/02/2017.
 */
public class EmailTest {
    @Before
    public void setUp() {
        start(fakeApplication());
    }

    @Test
    public void testEmailIndex() {
        Result result = route(fakeRequest("GET", controllers.account.settings.routes.Email.index().toString()));
        assertEquals(SEE_OTHER, result.status());
    }
}
