package OAuth2;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;

import POJOClasses.APIPojo;
import POJOClasses.GetCourse;
import POJOClasses.WebAutomationPojo;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import reusablemethods.ReusableMethods;

public class OAuthTest {

    public static void main(String args[]) throws InterruptedException {

        GetCourse obj = new GetCourse();

        RestAssured.baseURI = "https://rahulshettyacademy.com";
        final String browserURL = "https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php";
        final String oauthResource = "oauth2/v4/token";
        String oauthCode = "4%2F0AWgavdfZ0mEI8pij9an-IlktxDdWkAbUONVKU6seUzdpBM0cOGMa5Su2L581aibponHyCQ";
        final String oauthClientId = "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com";
        final String oauthClientSecret = "erZOWM9g3UtwNRj340YYaK_W";
        final String oauthRedirectURI = "https://rahulshettyacademy.com/getCourse.php";
        final String oauthGrantType = "authorization_code";
        final String oauthScope = "https://www.googleapis.com/auth/userinfo.email";
        final String oauthURL = "https://accounts.google.com/o/oauth2/v2/auth";
        final String oauthResponseType = "code";
        final String resource = "/getCourse.php";
        final String emailXpath = "//input[@id='identifierId']";
        final String passwordXpath = "//input[@type='password']";
        String[] courseTitle = {"Selenium Webdriver Java", "Cypress", "Protractor"};


        // This UI web automation cannot be done due to new restrictions made by google

//        System.setProperty("webdriver.chrome.driver", "src/main/resources/Drivers/chromedriver.exe");
//        WebDriver driver = new ChromeDriver();
//        driver.get(browserURL);
//
//        driver.findElement(By.xpath(emailXpath)).sendKeys("pronit29@gmail.com");
//        driver.findElement(By.xpath(emailXpath)).sendKeys(Keys.ENTER);
//        Thread.sleep(3000);
//
//        driver.findElement(By.xpath(passwordXpath)).sendKeys("Kritisha@09");
//        driver.findElement(By.xpath(passwordXpath)).sendKeys(Keys.ENTER);
//        Thread.sleep(2000);
//
//        String currentUrl = driver.getCurrentUrl();
//        String partialUrl = currentUrl.split("code=")[1];
//        String code = partialUrl.split("&scope")[0];
//        System.out.print(code);


        //Building the Get Request to generate the code

//        String getCodeResponse = given().queryParam("scope", oauthScope).queryParam("auth_url", oauthURL)
//                .queryParam("client_id", oauthClientId).queryParam("response_type", oauthResponseType)
//                .queryParam("redirect_uri", oauthRedirectURI)
//                .when().log().all().get(oauthURL)
//                .asString();
//
//        System.out.println(getCodeResponse);


        //Building the exchange code for receiving the access token

        String oauthResponse = given().queryParam("code", oauthCode).urlEncodingEnabled(false)
                .queryParam("client_id", oauthClientId).queryParam("client_secret", oauthClientSecret)
                .queryParam("redirect_uri", oauthRedirectURI).queryParam("grant_type", oauthGrantType)
                .when().log().all().post("https://www.googleapis.com/"+oauthResource)
                .asString();
        System.out.println(oauthResponse);

        JsonPath js = ReusableMethods.rawToJson(oauthResponse);
        String accessToken = js.getString("access_token");
        System.out.println(accessToken);


        //Building the Test using POJO Class
        GetCourse gc = given().queryParam("access_token", accessToken)
                .expect().defaultParser(Parser.JSON)
                .when().get(oauthRedirectURI)
                        .as(GetCourse.class);
        System.out.println(gc.getLinkedIn());
        //System.out.println(gc.getCourses().getApi().get(1).getCourseTitle());

        //Extracting the price of the SoapUI Webservices testing course
        List<APIPojo> apiCourses = gc.getCourses().getApi();
        for(int i=0; i<apiCourses.size(); i++)
        {
            String apicourseTitles = apiCourses.get(i).getCourseTitle();
            if(apicourseTitles.equalsIgnoreCase("SoapUI Webservices testing"))
            {
                String priceCourse = apiCourses.get(i).getPrice();
                System.out.println("<-- PRICE OF THE COURSE --> "+priceCourse);
            }
        }

        //Print all the Course Titles of WebAutomation
        ArrayList<String> arr = new ArrayList<String>();
        List<WebAutomationPojo> webAutomationCourses = gc.getCourses().getWebAutomation();
        for(int j=0; j<webAutomationCourses.size(); j++)
        {
            String courseTitles = webAutomationCourses.get(j).getCourseTitle();
            arr.add(courseTitles);
        }

        List<String> expectedList = Arrays.asList(courseTitle);
        Assert.assertTrue(arr.equals(expectedList));


    }
}
