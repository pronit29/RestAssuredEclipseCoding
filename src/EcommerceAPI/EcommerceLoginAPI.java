package EcommerceAPI;

import ECommercePOJO.LoginPOJO;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import reusablemethods.ReusableMethods;

import static io.restassured.RestAssured.given;

public class EcommerceLoginAPI {
    public static void main(String args[]) {

        RestAssured.baseURI = "https://rahulshettyacademy.com/";
        final String loginResourceURI = "api/ecom/auth/login";
        LoginPOJO loginPojo = new LoginPOJO();
        String userEmail = "";
        String userPassword = "";

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

    }


}
