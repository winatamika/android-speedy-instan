package com.speedy.instant;

import java.util.ArrayList;
import java.util.List;
 

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
 

import android.content.Context;
//import android.util.Base64;
 
public class UserFunctions {
     
    private JSONParser jsonParser;
     
    // Testing in localhost using wamp or xampp 
    // use http://10.0.2.2/ to connect to your localhost ie http://localhost/
    private static String loginURL = "http://speedyinstan.com/api2/userv2.php";
    private static String registerURL = "http://speedyinstan.com/api2/userv2.php";
     
    private static String login_action = "3login";
    private static String register_action = "1.5register";
     
    // constructor
    public UserFunctions(){
        jsonParser = new JSONParser();
    }
     
    /**
     * function make Login Request
     * @param username
     * @param password
     * */
    public JSONObject loginUser(String username, String password){
        // Building Parameters
        //List<NameValuePair> params = new ArrayList<NameValuePair>();
        String param = "action=3login&username=" + username + "&password=" + password;
        //String httpheader="Basic "+ Base64.encodeToString(param.getBytes(), Base64.DEFAULT);
		return null;

        //params.add(new BasicNameValuePair("data", Base64.encode(param.getBytes(), Base64.NO_WRAP).toString()));
        
//        params.add(new BasicNameValuePair("action", login_action));
//        params.add(new BasicNameValuePair("email", username));
//        params.add(new BasicNameValuePair("password", password));
       // JSONObject json = jsonParser.getJSONFromUrl(loginURL, httpheader);
        
        //return json;
    }
     
    /**
     * function make Register Request
     * @param username
     * @param email
     * */
    public JSONObject registerUser(String username, String email){
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("action", register_action));
        params.add(new BasicNameValuePair("username", username));
        params.add(new BasicNameValuePair("user_email", email));
         
        // getting JSON Object
        JSONObject json = null;
        //JSONObject json = jsonParser.getJSONFromUrl(registerURL, params);
        
        return json;
    }
     
    /**
     * Function get Login status
     * */
    public boolean isUserLoggedIn(Context context){
        DatabaseHandler db = new DatabaseHandler(context);
        int count = db.getRowCount();
        if(count > 0){
            // user logged in
            return true;
        }
        return false;
    }
     
    /**
     * Function to logout user
     * Reset Database
     * */
    public boolean logoutUser(Context context){
        DatabaseHandler db = new DatabaseHandler(context);
        db.resetTables();
        return true;
    }
     
}