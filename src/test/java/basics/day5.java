package basics;

import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
public class day5 {

    @Test
    void jsonschemavalidator()
    {
        given()
                .when().get("https://fakerestapi.azurewebsites.net/api/v1/Books")
                .then()
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("jsonschema.json"));

    }
}
