package com.speedy.instant;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import com.speedy.instant.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class Coba extends Activity {
    
    private static String locationURL = "http://speedyinstan.com/api2/location.php";
    private JSONParser jsonParser;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
   
        Button next;
    	next = (Button) findViewById(R.id.buttonLogin);
    	next.setOnClickListener(myhandler);
        
        Button register;
        register = (Button) findViewById(R.id.buttonLinkToRegisterScreen);
        register.setOnClickListener(myhandler2);
    	
    }
    
    View.OnClickListener myhandler = new View.OnClickListener() {
        public void onClick(View v) {
        	Intent intent = new Intent(v.getContext(), MainActivity.class);
	    	startActivity(intent);
        }
      };
      
      View.OnClickListener myhandler2 = new View.OnClickListener() {
          public void onClick(View v) {
          	Intent intent = new Intent(v.getContext(), Register.class);
  	    	startActivity(intent);
          }
        };
  	
	
 
}