package reusablemethods;

public class Payloads 
{
	
	public static String coursePrice()
	{
		String courseBody = "{\r\n"
				+ "\"dashboard\": {\r\n"
				+ "\"purchaseAmount\": 910,\r\n"
				+ "\"website\": \"rahulshettyacademy.com\"\r\n"
				+ "},\r\n"
				+ "\"courses\": [\r\n"
				+ "{\r\n"
				+ "\"title\": \"Selenium Python\",\r\n"
				+ "\"price\": 50,\r\n"
				+ "\"copies\": 6\r\n"
				+ "},\r\n"
				+ "{\r\n"
				+ "\"title\": \"Cypress\",\r\n"
				+ "\"price\": 40,\r\n"
				+ "\"copies\": 4\r\n"
				+ "},\r\n"
				+ "{\r\n"
				+ "\"title\": \"RPA\",\r\n"
				+ "\"price\": 45,\r\n"
				+ "\"copies\": 10\r\n"
				+ "}\r\n"
				+ "]\r\n"
				+ "}";
		return courseBody;
	}
	
	public static String addBookPayload(String isbn, int aisle)
    {
        String addBookPay = "{\n" +
                "\n" +
                "\"name\":\"Learn Basics of Appium Automation\",\n" +
                "\"isbn\":\""+isbn+"\",\n" +
                "\"aisle\":\""+aisle+"\",\n" +
                "\"author\":\"John Moxley\"\n" +
                "}\n";
        return addBookPay;
    }

}
