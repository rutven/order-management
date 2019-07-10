package name.legkodymov.order.management;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.preemptive;
import static org.junit.Assert.assertEquals;

/**
 * Created by sergei on 21/08/2017.
 *
 * @author Sergei Legkodymov - rutven@gmail.com
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class}, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class LiveTests {

    private static final String API_ROOT = "http://localhost:8081/product";

    @Before
    public void setUp() {
        RestAssured.authentication = preemptive().basic("john", "123");
    }

    @Test
    public void whenGetAllBooks_thanOk() {
        Response response = RestAssured.get(API_ROOT);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
    }

}
