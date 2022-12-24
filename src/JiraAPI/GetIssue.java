package JiraAPI;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;

import static io.restassured.RestAssured.*;

public class GetIssue {
    public static void main(String args[]) {

        RestAssured.baseURI = "http://localhost:8250";
        String issueId = "10000";
        String headerContent = "application/json";
        String userName = "pronit29";
        String passWord = "Kritisha@09";
        final String addLoginBody = "{ \n" +
                "    \"username\": \""+userName+"\",\n" +
                "    \"password\": \""+passWord+"\"\n" +
                "}";

        final String loginResource = "/rest/auth/1/session";
        final String getResource = "/rest/api/2/issue/{issueId}";

        SessionFilter session = new SessionFilter();
        //Login Scenario

        String loginToken = given().log().all()
                .header("Content-Type", headerContent)
                .body(addLoginBody)
                .filter(session)
                .when().post(loginResource)
                .then().log().all().assertThat().statusCode(200)
                .extract().response().asString();

        //Get Issue from the JIRA board using Rest Assured
        given().log().all().filter(session).pathParams("issueId", issueId)
                .queryParam("fields", "comment")
                .when().get(getResource)
                .then().log().all().assertThat().statusCode(200)
                .extract().response().asString();




    }
}
