package Files;

import Pojo.LoginRequest;
import Pojo.LoginResponse;
import Pojo.Orders;
import Pojo.OrdersDeatils;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class EcommerceAPITest {
    public static void main(String[] args) {
        RequestSpecification req= new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
                .setContentType(ContentType.JSON).build();

        LoginRequest loginRequest= new LoginRequest();
        loginRequest.setUserEmail("nabanita@gmail.com");
        loginRequest.setUserPassword("Nita1234");

        RequestSpecification rerLogin=given().log().all().spec(req).body(loginRequest);
        LoginResponse loginResponse=rerLogin.when().post("/api/ecom/auth/login").then().log().all().extract()
                .response().as(LoginResponse.class);


        String tokenId=loginResponse.getToken();
        System.out.println(tokenId);
        String userId=loginResponse.getUserId();
        System.out.println(userId);




        //Add Product
        RequestSpecification addProductReqSpec= new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
                .addHeader("authorization",tokenId).build();

        RequestSpecification addProduct=given().log().all().spec(addProductReqSpec).param("productName","Laptop")
                .param("productAddedBy",userId).param("productCategory","fashion")
                .param("productSubCategory","shirts").param("productPrice","11500")
                .param("productDescription","Addias Originals").param("productFor","women")
                .multiPart("productImage",new File("D:\\Picture1.jpg"));

        String addProductresponse=addProduct.when().post("/api/ecom/product/add-product").
                then().log().all().extract().response().asString();

        JsonPath js= new JsonPath(addProductresponse);
        String productId=js.get("productId");
        System.out.println(productId);



        //CreateOrder
        RequestSpecification createOrderReqSpec=new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
                .addHeader("authorization",tokenId).setContentType(ContentType.JSON).build();

        OrdersDeatils ordersDeatils= new OrdersDeatils();
        ordersDeatils.setProductOrderedId(productId);
        ordersDeatils.setCountry("India");

        List<OrdersDeatils> ordersDeatilsList= new ArrayList<OrdersDeatils>();
        ordersDeatilsList.add(ordersDeatils);
        Orders orders= new Orders();
        orders.setOrders(ordersDeatilsList);


        RequestSpecification createOrderReq=given().log().all().spec(createOrderReqSpec).body(orders);
        String createProdResponse=createOrderReq.when().post("/api/ecom/order/create-order").then().log().all().extract().response()
                .asString();

        JsonPath js2= new JsonPath(createProdResponse);
        String orderId=js2.get("orders[0]");
        System.out.println(createProdResponse);




        //DeleteProduct

        RequestSpecification deleteProductReqSpec=new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
                .addHeader("authorization",tokenId).setContentType(ContentType.JSON).build();

        RequestSpecification deleteProdReq=given().log().all().spec(deleteProductReqSpec).pathParams("productId",productId);

        String deleteProdRes=deleteProdReq.when().delete("/api/ecom/product/delete-product/{productId}").then().log().all()
                .extract().response().asString();

        JsonPath js1= new JsonPath(deleteProdRes);
        Assert.assertEquals(
                "Product Deleted Successfully",js1.get("message")
        );


        //Delete Order

        RequestSpecification deleteOrderReqSpec= new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
                .addHeader("authorization",tokenId).setContentType(ContentType.JSON).build();

        RequestSpecification deleteOrderReq=given().log().all().spec(deleteOrderReqSpec).pathParams("orderId",orderId);

        String deleteOrderRes=deleteOrderReq.when().delete("/api/ecom/order/delete-order/{orderId}")
                .then().log().all().extract().response().asString();

        JsonPath js3=new JsonPath(deleteOrderRes);
        Assert.assertEquals("Orders Deleted Successfully",js3.get("message"));

    }

}
