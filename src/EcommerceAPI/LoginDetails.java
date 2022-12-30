package EcommerceAPI;

import ECommercePOJO.LoginPOJO;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class LoginDetails {
    final static String filePath = "C:\\Users\\Pronit Kundu\\practice-workspace\\RestAssuredPracticeProject\\src\\resources\\LoginCredentials.txt";

    static LoginPOJO obj = new LoginPOJO();

    // read text file to HashMap
    static Map<String, String> mapFromFile
            = HashMapFromTextFile();

    public static String setPassword() {

        String userPass = "";
        for (Map.Entry<String, String> entry :
                mapFromFile.entrySet()) {
//            System.out.println(entry.getKey() + " : "
//                    + entry.getValue());
            if (entry.getKey().equalsIgnoreCase("userpassword")) {
                obj.setUserPassword(entry.getValue());
                userPass = obj.getUserPassword();
                //System.out.println(userPass);
            }
            }
        return userPass;
        }

    public static String setEmail() {

        String userEmail = "";
        for (Map.Entry<String, String> entry :
                mapFromFile.entrySet()) {
//            System.out.println(entry.getKey() + " : "
//                    + entry.getValue());
            if(entry.getKey().equalsIgnoreCase("useremail"))
            {
                obj.setUserEmail(entry.getValue());
                userEmail = obj.getUserEmail();
                //System.out.println(userEmail);
            }
        }
        return userEmail;
    }

    public static Map<String, String> HashMapFromTextFile()
    {

        Map<String, String> map
                = new HashMap<String, String>();
        BufferedReader br = null;

        try {

            // create file object
            File file = new File(filePath);

            // create BufferedReader object from the File
            br = new BufferedReader(new FileReader(file));

            String line = null;

            // read file line by line
            while ((line = br.readLine()) != null) {

                // split the line by :
                String[] parts = line.split("= ");

                // first part is name, second is number
                String variableName = parts[0].trim();
                String value = parts[1].trim();

                // put name, number in HashMap if they are
                // not empty
                if (!variableName.equals("") && !value.equals("")) {
                    map.put(variableName, value);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {

            // Always close the BufferedReader
            if (br != null) {
                try {
                    br.close();
                }
                catch (Exception e) {
                };
            }
        }

        return map;
    }
}

