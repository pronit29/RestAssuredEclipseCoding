package JiraAPI;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import reusablemethods.ReusableMethods;

import static io.restassured.RestAssured.*;

public class ValidateAddedComments {
    public static void main(String args[]) {

        RestAssured.baseURI = "http://localhost:8250";
        String issueId = "10003";
        String headerContent = "application/json";
        String comment = "This is my first comment in the Credit Card Masking issue";
        String actualComment = "";
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
        final String getResource = "/rest/api/2/issue/{issueId}";
        String commentId = "";
        String idComment = "";
        int commentArraySize;

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
        String commentResponse = given().log().all().pathParams("issueId", issueId)
                .header("Content-Type", headerContent)
                .body(addCommentBody)
                .filter(session)
                .when().post(addCommentResource)
                .then().log().all().assertThat().statusCode(201)
                        .extract().response().asString();

        JsonPath js = ReusableMethods.rawToJson(commentResponse);
        commentId = js.getString("id");
        System.out.println("<-- COMMENT ID -->"+commentId);


        //Get Issue from the JIRA board using Rest Assured
        String issueResponse = given().log().all().filter(session).pathParams("issueId", issueId)
                .queryParam("fields", "comment")
                .when().get(getResource)
                .then().log().all().assertThat().statusCode(200)
                .extract().response().asString();

        JsonPath js1 = ReusableMethods.rawToJson(issueResponse);
        commentArraySize = js1.getInt("fields.comment.comments.size()");
        //System.out.println("<-- SIZE OF THE COMMENT ARRAY -->"+commentArraySize);

        for(int i=0;i<commentArraySize;i++) {
            idComment = js1.getString("fields.comment.comments[" + i + "].id");
            //System.out.println("<-- COMMENT ID AT "+i+" ELEMENT -->"+idComment);

            if (idComment.equals(commentId))
            {
                actualComment = js1.getString("fields.comment.comments[" + i + "].body");
                Assert.assertEquals(actualComment, comment);


            }
        }

    }
}
