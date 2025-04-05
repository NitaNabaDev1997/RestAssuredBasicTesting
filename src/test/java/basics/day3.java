package basics;

import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.testng.annotations.Test;


import java.util.Map;

import static io.restassured.RestAssured.*;

public class day3 {

    @Test
    void queryandpathparams()
    {
        given()
                .pathParams("mypath","users")
                .queryParam("page",2)
                .queryParam("id",2)
       .when()
                .get("https://reqres.in/api/{mypath}")
       .then()
                .statusCode(200)
                .log().all();

    }


    @Test
    void testCookies()
    {
        given()
        .when()
                .get("https://www.google.com/")
        .then()
                .cookies("AEC","AVcja2c-jA-hXvheftrwV-IfcHfXP5ddCw9U1Xs05JmgBxKZ1lrNTlmxLw")
                .log().all();
    }


   @Test
    void getCookies()
    {
        Response res=given()
                .when()
                .get("https://www.google.com/");

        //get single cookie
      //  String cookie_value=res.getCookie("AEC");

        //get all cookies
        Map<String,String> cookies=res.getCookies();

        System.out.println(cookies.keySet());

        for(String k:cookies.keySet())
        {
            String cookie_value=res.cookie(k);
            System.out.println(k+"         "+cookie_value);
        }
    }



    @Test
    void getHeaders()
    {
        Response res=given().when().get("https://www.google.com/");

        String header_value=res.getHeader("Content-Type");
        System.out.println(header_value);

        Headers myheaders=res.getHeaders();

        for(Header hd:myheaders)
        {
            System.out.println(hd.getName()+"   "+hd.getValue());
        }
    }

   @Test
    void testlogs()
    {

        given()
                .when()
                .get("https://www.google.com/")
                .then()
               // .log().body();
                //.log().cookies();
                .log().headers();

    }

}
