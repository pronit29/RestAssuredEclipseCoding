import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

//Validate if Add Place API is working fine as expected
public class AddPlace {
    public static void main(String args[]) {
        final String jsonBody = "{\r\n"
                + "  \"location\": {\r\n"
                + "    \"lat\": -38.383494,\r\n"
                + "    \"lng\": 33.427362\r\n"
                + "  },\r\n"
                + "  \"accuracy\": 50,\r\n"
                + "  \"name\": \"Frontline house\",\r\n"
                + "  \"phone_number\": \"(+91) 983 893 3937\",\r\n"
                + "  \"address\": \"29, side layout, cohen 09\",\r\n"
                + "  \"types\": [\r\n"
                + "    \"shoe park\",\r\n"
                + "    \"shop\"\r\n"
                + "  ],\r\n"
                + "  \"website\": \"http://google.com\",\r\n"
                + "  \"language\": \"French-IN\"\r\n"
                + "}";
        String resourceURI = "/maps/api/place/add/json";

        //Pre-requisite - Set up the baseURI
        RestAssured.baseURI = "https://rahulshettyacademy.com";

        //Given
        given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
                .body(jsonBody)

                //When
                .when().post(resourceURI)

                //Then
                .then().log().all().assertThat().statusCode(200)
                .body("scope", equalTo("APP"))
                .header("Server", equalTo("Apache/2.4.41 (Ubuntu)"));

    }
}
