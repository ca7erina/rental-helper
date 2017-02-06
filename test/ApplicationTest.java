import static org.junit.Assert.assertEquals;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.*;

import org.junit.Test;
import play.mvc.Result;
import play.test.WithApplication;

import static play.test.Helpers.fakeRequest;


/**
 * Basic Tests
 */
public class ApplicationTest extends WithApplication {

    @Test
    public void simpleCheck() {
        int a = 1 + 1;
        assertEquals(a, 2);

    }

    @Test
    public void testCallIndex() {
        Result result = route(
                fakeRequest()
        );
        assertEquals(OK, result.status());
    }
}
