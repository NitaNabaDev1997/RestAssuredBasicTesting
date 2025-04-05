package basics;


import org.testng.annotations.Test;


import java.util.HashMap;

import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.*;


public class day1 {
int id;

    @Test(priority = 1)
    void getUser()
    {
        given().
                when()
                .get("https://reqres.in/api/users?page=2")
        .then()
                .statusCode(200)
                .body("page",equalTo(2))
                .log().all();
    }

    @Test(priority = 2)
    void createUser()
    {
        HashMap data = new HashMap<>();
        data.put("name","naba");
        data.put("job","trainer");

        id=given()
                .contentType("application/json")
                .body(data)
        .when()
                .post("https://reqres.in/api/users?page=2")
                .jsonPath().getInt("id");

       /* .then()
                .statusCode(201)

                .log().all();*/
    }

    @Test(priority = 3, dependsOnMethods ={"createUser"} )
    void updateUser()
    {
        HashMap data = new HashMap<>();
        data.put("name","nabanita");
        data.put("job","trainerqa");

       given()
                .contentType("application/json")
                .body(data)
        .when()
                .post("https://reqres.in/api/users/"+id)
        .then()
                .statusCode(201)
                .log().all();
    }


    @Test(priority = 4)
    void deleteUser()
    {
        given()
        .when()
                .delete("https://reqres.in/api/users/"+id)
        .then()
                .statusCode(204)
                .log().all();
    }


}
