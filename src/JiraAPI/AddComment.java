package JiraAPI;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;

import static io.restassured.RestAssured.*;

public class AddComment {
    public static void main(String args[]) {

        RestAssured.baseURI = "http://localhost:8250";
        String issueId = "10000";
        String headerContent = "application/json";
        String comment = "This is my first comment in the Credit Card Masking issue";
        final String addCommentBody = "{\n" +
                "    \"body\": \""+comment+"\",\n" +
                "    \"visibility\": {\n" +
                "        \"type\": \"role\",\n" +
                "        \"value\": \"Administrators\"\n" +
                "    }\n" +
                "}";
        String userName = "pronit29";
        String passWord = "Kritisha@09";
        final String addLoginBody = "{ \n" +
                "    \"username\": \""+userName+"\",\n" +
                "    \"password\": \""+passWord+"\"\n" +
                "}";


        final String addCommentResource = "/rest/api/2/issue/{issueId}/comment";
        final String loginResource = "/rest/auth/1/session";

        SessionFilter session = new SessionFilter();
        //Login Scenario

        String loginToken = given().log().all()
                .header("Content-Type", headerContent)
                .body(addLoginBody)
                .filter(session)
                .when().post(loginResource)
                .then().log().all().assertThat().statusCode(200)
                        .extract().response().asString();

        //Add the comment Scenario
        given().log().all().pathParams("issueId", issueId)
                .header("Content-Type", headerContent)
                .body(addCommentBody)
                .filter(session)
                .when().post(addCommentResource)
                .then().log().all().assertThat().statusCode(201);


    }
}
