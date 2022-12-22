package restassuredbasics;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.given;

//Validate if Get Place API is working fine as expected
public class DeletePlace {
    public static void main(String args[]) {
        final String jsonBodyAdd = "{\r\n"
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
       
        String newAddress = "70 Summer walk, USA";
        final String resourceAddURI = "/maps/api/place/add/json";
        final String resourceGetURI = "/maps/api/place/get/json";
        final String resourceUpdateURI = "/maps/api/place/update/json";

        //Pre-requisite - Set up the baseURI
        RestAssured.baseURI = "https://rahulshettyacademy.com";

        //Given
        String responseAdd = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
                .body(jsonBodyAdd)

                //When
                .when().post(resourceAddURI)

                //Then
                .then().assertThat().statusCode(200).extract().response().asString();
        
        
        //Parsing the response for Add Place using JsonPath class
        
        
        JsonPath js = new JsonPath(responseAdd);
        String placeId = js.get("place_id"); //Extracting the place id from the JSON response body
        
        //Get the place details using the GET method
        
        //Given
        String responseGet =  given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeId)
        
        //When
        .when().get(resourceGetURI)
        
        //Then
        .then().log().all().assertThat().statusCode(200).extract().response().asString();
        
      //Parsing the response for Get Place using JsonPath class
        
        JsonPath js1 = new JsonPath(responseGet);
        String address = js1.get("address"); // Extracting the address from the JSON response body
          
        //Given
        String responseUpdate = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
        		.body("{\r\n"
                		+ "\"place_id\":\""+placeId+"\",\r\n"
                		+ "\"address\":\""+newAddress+"\",\r\n"
                		+ "\"key\":\"qaclick123\"\r\n"
                		+ "}\r\n"
                		+ "")
        		
        		//When
        		.when().put(resourceUpdateURI)
        		
        		//Then
        		.then().assertThat().statusCode(200)
        		.body("msg", equalTo("Address successfully updated")).extract().response().asString();
        
        //System.out.println(responseUpdate);
        
        JsonPath js2 = new JsonPath(responseUpdate);
        String message = js2.get("msg"); //Extracting the msg from the response body
        System.out.println(message);
        
        //Get the details of the updated Address
        
        //Given
        given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeId)
        
        //When
        .when().get(resourceGetURI)
        
        //Then
        .then().log().all().assertThat().statusCode(200)
        .body("address", equalTo(newAddress));
        

    }
}
