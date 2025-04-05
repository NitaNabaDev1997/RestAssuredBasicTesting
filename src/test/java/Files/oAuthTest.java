package Files;

import Pojo.API;
import Pojo.GetCourse;
import Pojo.WebAutomation;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

public class oAuthTest {

    @Test
    public void oauthtest()
    {
      String response=  given()
                .formParams("client_id","692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
                .formParams("client_secret","erZOWM9g3UtwNRj340YYaK_W")
                .formParams("grant_type","client_credentials")
                .formParams("scope","trust")
                .when().log().all()
                .post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token").asString();

        System.out.println(response);

        JsonPath js=ReusableMethods.rawtoJson(response);
        String accesstoken=js.get("access_token");


        GetCourse response2=given()
                .queryParam("access_token",accesstoken)
                .when().log().all()
                .get("https://rahulshettyacademy.com/oauthapi/getCourseDetails").as(GetCourse.class);

        System.out.println(response2.getLinkedIn()+"  "+response2.getInstructor());
        //System.out.println(response2.getCourses().getWebAutomation().get(0).getCourseTitle());

 /*       List<API> apis=response2.getCourses().getApi();

        for(int i=0;i<apis.size();i++)
        {
            if(apis.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing"))
            {
                System.out.println(apis.get(i).getPrice());

            }
        }*/

        List<WebAutomation> webAutomations=response2.getCourses().getWebAutomation();

        for(WebAutomation w:webAutomations)
        {
            System.out.println(w.getCourseTitle());
        }
    }
}
