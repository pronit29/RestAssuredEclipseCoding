package EcommerceAPI;

import ECommercePOJO.CreateOrderPOJO;
import ECommercePOJO.LoginPOJO;
import ECommercePOJO.OrdersPOJO;
import com.fasterxml.jackson.databind.util.ArrayBuilders;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import reusablemethods.ReusableMethods;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class EcommerceCreateOrderAPI {

    public static void main(String args[]) {

        RestAssured.baseURI = "https://rahulshettyacademy.com/";
        final String loginResourceURI = "api/ecom/auth/login";
        final String createProductResourceURI = "api/ecom/product/add-product";
        final String createOrderResourceURI = "api/ecom/order/create-order";
        LoginPOJO loginPojo = new LoginPOJO();
        String userEmail = "";
        String userPassword = "";

        //Login API
        RequestSpecification req = new RequestSpecBuilder().setContentType(ContentType.JSON)
                .setBaseUri(RestAssured.baseURI)
                .build();

        ResponseSpecification res = new ResponseSpecBuilder().expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .build();

        loginPojo.setUserEmail(LoginDetails.setEmail());
        userEmail = loginPojo.getUserEmail();

        loginPojo.setUserPassword(LoginDetails.setPassword());
        userPassword = loginPojo.getUserPassword();

        RequestSpecification requestBody = given()
                .log().all()
                .spec(req).body(loginPojo);

        Response responseBody = requestBody
                .when().post(loginResourceURI)
                .then().log().all()
                .spec(res)
                .extract().response();

        String response = responseBody.asString();
        System.out.println(response);

        JsonPath js = ReusableMethods.rawToJson(response);
        String session_token = js.getString("token");
        String userId = js.getString("userId");
        System.out.println("<-- SESSION TOKEN --> "+session_token);
        System.out.println("<-- USER ID --> "+userId);

        //Creating the Product API

        RequestSpecification req1 = new RequestSpecBuilder().setContentType(ContentType.MULTIPART)
                .setBaseUri(RestAssured.baseURI)
                .build();

        ResponseSpecification res1 = new ResponseSpecBuilder().expectStatusCode(201)
                .expectContentType(ContentType.JSON)
                .build();

        HashMap<String,String> formMap = new HashMap<String,String>();
        formMap.put("productName", "qwerty");
        formMap.put("productAddedBy", userId);
        formMap.put("productCategory", "fashion");
        formMap.put("productSubCategory", "shirts");
        formMap.put("productPrice", "11500");
        formMap.put("productDescription", "Addias Originals");
        formMap.put("productFor", "women");

        File img = new File("C:\\Users\\Pronit Kundu\\practice-workspace\\RestAssuredPracticeProject\\src\\resources\\images.png");
        RequestSpecification requestBodyCreateProduct = given()
                .log().all()
                .header("Authorization",session_token)
                .spec(req1)
                .formParams(formMap)
                .multiPart("productImage", img, "images/png");

        Response responseBodyCreateProduct = requestBodyCreateProduct
                .when().post(createProductResourceURI)
                        .then().log().all().spec(res1)
                        .extract().response();

        String responseBodyCreateProductString = responseBodyCreateProduct.asString();
        System.out.println(responseBodyCreateProductString);

        JsonPath js1 = ReusableMethods.rawToJson(responseBodyCreateProductString);
        String responseCreateProd = js1.getString("productId");
        System.out.println(responseCreateProd);

        //Creating the Order API

        OrdersPOJO ordersPoj = new OrdersPOJO();
        HashMap<String,String> hMap = new HashMap<String,String>();
        hMap.put("country","India");
        hMap.put("productOrderedId",responseCreateProd);
        List<Map> myList = new ArrayList<Map>();
        myList.add(hMap);
        ordersPoj.setOrders(myList);

        RequestSpecification requestBodyCreateOrder = given()
                .log().all()
                .header("Authorization",session_token)
                .spec(req)
                .body(ordersPoj);


        Response responseBodyCreateOrder = requestBodyCreateOrder
                .when().post(createOrderResourceURI)
                .then().log().all().spec(res1)
                .extract().response();

        String responseBodyCreateOrderString = responseBodyCreateOrder.asString();
        System.out.println(responseBodyCreateOrderString);

        JsonPath js2 = ReusableMethods.rawToJson(responseBodyCreateOrderString);
        String orderId = js2.getString("orders");
        System.out.println("<-- ORDER ID --> "+orderId);

    }

}
