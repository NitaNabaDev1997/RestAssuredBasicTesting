package basics;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class day6 {

    @Test
    void testBasicAuthentication()
    {
        given()
                .auth().basic("postman","password")
        .when()
                .get("https://postman-echp.com/basic-auth")
        .then()
                .statusCode(200)
                .body("authenticated",equalTo(true))
                .log().all();

    }
}
