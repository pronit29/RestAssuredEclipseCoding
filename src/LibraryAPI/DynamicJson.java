package LibraryAPI;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import reusablemethods.Payloads;
import reusablemethods.ReusableMethods;

import static io.restassured.RestAssured.*;

public class DynamicJson {

    private static final String addBookResource = "/Library/Addbook.php";

    @BeforeClass
    public void baseUrl()
    {
        RestAssured.baseURI = "http://216.10.245.166";
    }

    @DataProvider(name = "BooksData")
    public Object[][] getData()
    {
        //Arrays - Collection of Elements
        //Multi-Dimensional Arrays - Collection of Arrays
        return new Object[][]
                {
                        {"ipouyh", 34232},
                        {"iyugjg", 97662},
                        {"khuffy", 987553}
                };
    }

    @Test(dataProvider = "BooksData")
    public void addBook(String isbn, int aisle)
    {
        String addBookResponse = given().header("Content-Type","application/json")
                .body(Payloads.addBookPayload(isbn, aisle))
                .when()
                .post(addBookResource)
                .then().assertThat().statusCode(200)
                .extract().response().asString();

        JsonPath js = ReusableMethods.rawToJson(addBookResponse);
        String id = js.getString("ID");
        System.out.println("<-- ID of the New Book -->" + id);

    }
}
