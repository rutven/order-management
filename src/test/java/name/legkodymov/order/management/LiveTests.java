package name.legkodymov.order.management;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.preemptive;

/**
 * Created by sergei on 21/08/2017.
 *
 * @author Sergei Legkodymov - rutven@gmail.com
 */
@SpringBootTest(classes = { Application.class }, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class LiveTests {

    private static final String API_ROOT = "http://localhost:8081/product";

//    @BeforeAll
//    public static void setUp() {
//        RestAssured.authentication = preemptive().basic("john", "123");
//    }

    // @Test
    // public void whenGetAllBooks_thanOk() {
    //     RestAssured.authentication = preemptive().basic("john", "123");
    //     Response response = RestAssured.get(API_ROOT);
    //     assertEquals(HttpStatus.OK.value(), response.getStatusCode());
    // }

}
