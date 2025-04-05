package basics;

import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class day4 {


    @Test(priority = 1)
    void testJsonresponse()
    {

       /* given()
                .contentType("application/json")
        .when()
                .get("https://fakerestapi.azurewebsites.net/api/v1/Books")
        .then()
                .statusCode(200)
                .header("Content-Type","application/json; charset=utf-8; v=1.0")
                .body("[2].title",equalTo("Book 3"));*/

       Response res= given()
                .contentType("application/json")
        .when()
                .get("https://fakerestapi.azurewebsites.net/api/v1/Books");


        Assert.assertEquals(res.getStatusCode(),200);
        Assert.assertEquals(res.header("Content-Type"),"application/json; charset=utf-8; v=1.0");

        String bookname=res.jsonPath().get("[2].title").toString();
        Assert.assertEquals(bookname,"Book 3");
    }


    @Test
    void testJsonbodyData()
    {
        Response res= given()
                .contentType("application/json")
                .when()
                .get("https://fakerestapi.azurewebsites.net/api/v1/Books");

        JSONArray jo= new JSONArray(res.asString());
        System.out.println(jo.length());


        for(int i=0;i<jo.length();i++)
        {
            String booktitle=jo.getJSONObject(i).get("title").toString();
            System.out.println(booktitle);

            if(booktitle.equalsIgnoreCase("Book 2"))
            {
                System.out.println("found");
                break;
            }
        }
        double totalprice=0;
        for(int i=0;i<jo.length();i++)
        {
            String price=jo.getJSONObject(i).get("title").toString();
            System.out.println(price);
            totalprice= totalprice+Double.parseDouble(price);
            System.out.println(totalprice);
        }

      /*  JSONObject jo= new JSONObject(res.asString()); //converting response to json object


        for(int i=0;i<jo.getJSONArray("book").length();i++)
        {
            String booktitle=jo.getJSONArray("book").getJSONObject(i).get("title").toString();
            System.out.println(booktitle);
        }

        double totalprice=0;
        for(int i=0;i<jo.getJSONArray("book").length();i++)
        {
            String price=jo.getJSONArray("book").getJSONObject(i).get("price").toString();
            System.out.println(price);
            totalprice= totalprice+Double.parseDouble(price);
            System.out.println(totalprice);
        }*/
    }
}
