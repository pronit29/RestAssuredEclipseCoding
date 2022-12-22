package complexjsonparsing;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import reusablemethods.Payloads;
import reusablemethods.ReusableMethods;

public class SumValidation 
{
	JsonPath js = ReusableMethods.rawToJson(Payloads.coursePrice());
	int courseCount = js.getInt("courses.size()");
	int purchaseAmount = js.getInt("dashboard.purchaseAmount");
	int sum = 0;
	
	@Test
	public void sumOfCourses()
	{
		for(int i=0; i<courseCount; i++)
		{
			int price = js.getInt("courses["+i+"].price");
			int numberOfCopies = js.getInt("courses["+i+"].copies");
			int amount = price * numberOfCopies;
			System.out.println(amount);
			sum = sum + amount;
		}
		System.out.println("<-- SUM OF ALL THE COURSES --> "+sum);
		Assert.assertEquals(sum, purchaseAmount);
	}

}
