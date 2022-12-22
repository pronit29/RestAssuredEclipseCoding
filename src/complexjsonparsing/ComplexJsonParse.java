package complexjsonparsing;

import io.restassured.path.json.JsonPath;
import reusablemethods.Payloads;
import reusablemethods.*;

public class ComplexJsonParse {

	public static void main(String[] args) 
	{
		JsonPath js = ReusableMethods.rawToJson(Payloads.coursePrice());
		
		//CASE 1: Print No of courses returned by API
		int courseCount = js.getInt("courses.size()");
		System.out.println("<-- COURSE COUNT --> "+courseCount);
		
		//CASE 2: Print Purchase Amount
		int purchaseAmount = js.get("dashboard.purchaseAmount");
		System.out.println("<-- PURCHASE AMOUNT --> "+purchaseAmount);
		
		//CASE 3: Print Title of the first course
		
		String titleOfFirstCourse = js.getString("courses[0].title");
		System.out.println("<-- TITLE OF FIRST COURSE --> "+titleOfFirstCourse);
		
		//CASE 4: Print All course titles and their respective Prices
		
		for(int i=0; i<courseCount; i++)
		{
			String courseTitle = js.getString("courses["+i+"].title");
			int price = js.getInt("courses["+i+"].price");
			System.out.println("<-- COURSE TITLE OF "+i+" "+"ELEMENT--> "+courseTitle);
			System.out.println("<-- PRICE OF "+i+" "+"ELEMENT--> "+price);
		}
		
		//CASE 5: Print no of copies sold by RPA Course
		
		for(int j=0; j<courseCount; j++)
		{
			String courseTitle = js.getString("courses["+j+"].title");
//			System.out.println("<-- COURSE TITLE OF "+j+" "+"ELEMENT--> "+courseTitle);
			if(courseTitle.equals("RPA"))
			{
				int numberOfRPACopies = js.getInt("courses["+j+"].copies");
				System.out.println("<-- COPIES OF RPA --> "+numberOfRPACopies);
			}
		}
		
		//CASE 6: Verify if Sum of all Course prices matches with Purchase Amount --> SumValidation Class
		
	}

}
