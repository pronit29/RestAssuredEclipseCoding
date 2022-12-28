package SerializeTest;

import SerializeTestsPOJO.GMapsSerialize;
import SerializeTestsPOJO.LocationPOJO;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class GMapSerializeTest {
    public static void main(String args[]) {

        RestAssured.baseURI = "https://rahulshettyacademy.com";
        String resourceURI = "/maps/api/place/add/json";

        //Initializing the object of the base POJO class
        GMapsSerialize gmapSerial = new GMapsSerialize();
        LocationPOJO locPoj = new LocationPOJO();
        List<String> myList = new ArrayList<String>();
        myList.add("shoe park");
        myList.add("shop");
        locPoj.setLatitude(-23.443432);
        locPoj.setLongitude(43.3534523);

        //Inserting data into the JSON which we are creating using the POJO class
        gmapSerial.setAccuracy(50);
        gmapSerial.setAddress("29, side layout, cohen 09");
        gmapSerial.setLanguage("French-IN");
        gmapSerial.setPhone_number("(+91) 983 893 3937");
        gmapSerial.setWebsite("http://google.com");
        gmapSerial.setName("Frontline house");
        gmapSerial.setTypes(myList);
        gmapSerial.setLocation(locPoj);

        //Given
        given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
                .body(gmapSerial)

                //When
                .when().post(resourceURI)

                //Then
                .then().log().all().assertThat().statusCode(200);



    }
}
