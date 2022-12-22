import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.given;

//Validate if Get Place API is working fine as expected
public class GetPlace {
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
        String resourceAddURI = "/maps/api/place/add/json";
        String resourceGetURI = "/maps/api/place/get/json";

        //Pre-requisite - Set up the baseURI
        RestAssured.baseURI = "https://rahulshettyacademy.com";

        //Given
        String responseAdd = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
                .body(jsonBody)

                //When
                .when().post(resourceAddURI)

                //Then
                .then().assertThat().statusCode(200).extract().response().asString();
        
        //System.out.println(response);
        
        //Parsing the response using JsonPath class
        
        JsonPath js = new JsonPath(responseAdd);
        String placeId = js.get("place_id"); //Extracting the place id from the JSON response body
        //System.out.println(placeId);
        
        //Get the place details using the GET method
        
        String responseGet =  given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeId)
        
        .when().get(resourceGetURI)
        
        .then().log().all().assertThat().statusCode(200).extract().response().asString();
        
        JsonPath js1 = new JsonPath(responseGet);
        String address = js1.get("address");
        System.out.println(address);

    }
}
