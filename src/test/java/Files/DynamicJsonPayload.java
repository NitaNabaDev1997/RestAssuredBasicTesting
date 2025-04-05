package Files;
import java.io.IOException;

import java.nio.file.Files;

import java.nio.file.Paths;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class DynamicJsonPayload {

    @Test(dataProvider="BookData")
    public void addBook(String aisle,String isbn)
    {
        RestAssured.baseURI="http://216.10.245.166";
        String response=given().log().all().header("Content-Type","application/json").
                body(Payload.AddBook(aisle,isbn)).
                when()
                .post("Library/Addbook.php")
                .then().log().all().assertThat()
                .statusCode(200)
                .extract().response().asString();
        System.out.println(response);

        JsonPath js=ReusableMethods.rawtoJson(response);
        String id=js.getString("ID");
        System.out.println(id);

    }

    @DataProvider(name="BookData")
    public Object[][] getData()
    {
       return new Object[][] {{"nita","12134"},{"indra","2341"},{"nil","23144"}};
    }

    public static String GenerateStringFromResource(String path) throws IOException {
        return new String(Files.readAllBytes(Paths.get(path)));
    }
}
