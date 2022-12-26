package OAuth2;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import reusablemethods.ReusableMethods;

import static io.restassured.RestAssured.given;

public class OAuthTest {

    public static void main(String args[]) throws InterruptedException {

        RestAssured.baseURI = "https://rahulshettyacademy.com";
        final String browserURL = "https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php";
        final String oauthResource = "oauth2/v4/token";
        String oauthCode = "4%2F0AWgavdceLApfqR2yu3V9DBZO4h0TtXYdDVsV7qdvqRXUzT8IOshXUgQZH0AJXueuoSpkKA";
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


        //Building the Test
        String response = given().queryParam("access_token", accessToken)
                .when().get(resource)
                .asString();

        System.out.println(response);

    }
}
