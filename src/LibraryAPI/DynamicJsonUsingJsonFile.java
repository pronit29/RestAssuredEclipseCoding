package LibraryAPI;

import io.restassured.RestAssured;
import reusablemethods.ReusableMethods;

import java.io.IOException;

import static io.restassured.RestAssured.given;

//Validate if Add Place API is working fine as expected
public class DynamicJsonUsingJsonFile {
    public static void main(String args[]) throws IOException {
        final String jsonFilePathAddPlace = "C:\\Users\\Pronit Kundu\\practice-workspace\\RestAssuredPracticeProject\\src\\resources\\AddPlace.json";
        final String jsonBody = ReusableMethods.byteToStringJsonContent(jsonFilePathAddPlace);
        String resourceURI = "/maps/api/place/add/json";

        //Pre-requisite - Set up the baseURI
        RestAssured.baseURI = "https://rahulshettyacademy.com";

        //Given
        given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
                .body(jsonBody)

                //When
                .when().post(resourceURI)

                //Then
                .then().log().all().assertThat().statusCode(200);

    }
}
