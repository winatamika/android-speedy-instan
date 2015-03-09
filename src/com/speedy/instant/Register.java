package com.speedy.instant;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.speedy.instant.MainActivity.DownloadWebTask;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class Register extends Activity {
	Button buttonRegister;
	Button buttonLinkToLogin;
	EditText inputUsername;
	EditText inputEmail;
	TextView registerErrorMsg;
	ImageView logo;
	ProgressBar pb;
	boolean isInternetPresent = false;
	ConnectionDetector cd;
	
	String user_login;
	String user_email;
	String error_msg = "";

	// JSON Response node names
	private static String KEY_SUCCESS = "success";
	private static String KEY_ERROR = "error";
	private static String KEY_ERROR_MSG = "error_msg";
	private static String KEY_UID = "uid";
	private static String KEY_NAME = "name";
	private static String KEY_EMAIL = "email";
	private static String KEY_CREATED_AT = "created_at";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);

		// Importing all assets like buttons, text fields
		inputUsername = (EditText) findViewById(R.id.registerUsername);
		inputEmail = (EditText) findViewById(R.id.registerEmail);
		buttonRegister = (Button) findViewById(R.id.buttonRegister);
		registerErrorMsg = (TextView) findViewById(R.id.register_error);
		logo = (ImageView) findViewById(R.id.imageView1);
		pb = (ProgressBar) findViewById(R.id.progressBar1);
		
		 // creating connection detector class instance
        cd = new ConnectionDetector(getApplicationContext());
		
		
		inputEmail.setOnEditorActionListener(new TextView.OnEditorActionListener() {
	        @Override
	        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
	            if (actionId == EditorInfo.IME_ACTION_DONE) {
	            	
	            	 // get Internet status
	                isInternetPresent = cd.isConnectingToInternet();
	                
	                if (isInternetPresent) {

		            	inputUsername.setVisibility(View.GONE);
						inputEmail.setVisibility(View.GONE);
						buttonRegister.setVisibility(View.GONE);
						registerErrorMsg.setVisibility(View.GONE);
						pb.setVisibility(View.VISIBLE);
						
						user_login = inputUsername.getText().toString();
						user_email = inputEmail.getText().toString();
						
						DownloadWebTask task = new DownloadWebTask();
						task.execute(new String[] { "http://speedyinstan.com/api3/user.php?action=1register&user_login="+user_login+"&user_email="+user_email+"" });
	                } else {
	                	//display in short period of time
	                	Toast.makeText(getApplicationContext(), "You don't have internet connection", Toast.LENGTH_SHORT).show();
	                }

	                return true;
	            }
	            return false;
	        }

	    });
		
		
		

		// Register Button Click event
		buttonRegister.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				// get Internet status
                isInternetPresent = cd.isConnectingToInternet();
                
                if (isInternetPresent) {

	            	inputUsername.setVisibility(View.GONE);
					inputEmail.setVisibility(View.GONE);
					buttonRegister.setVisibility(View.GONE);
					registerErrorMsg.setVisibility(View.GONE);
					pb.setVisibility(View.VISIBLE);
					
					user_login = inputUsername.getText().toString();
					user_email = inputEmail.getText().toString();
					
					DownloadWebTask task = new DownloadWebTask();
					task.execute(new String[] { "http://speedyinstan.com/api3/user.php?action=1register&user_login="+user_login+"&user_email="+user_email+"" });
                } else {
                	//display in short period of time
                	Toast.makeText(getApplicationContext(), "You don't have internet connection", Toast.LENGTH_SHORT).show();
                }
				// String username = inputUsername.getText().toString();
				// String email = inputEmail.getText().toString();
				// UserFunctions userFunction = new UserFunctions();
				// JSONObject json = userFunction.registerUser(username, email);
				//
				// // check for login response
				// try {
				// if (json.getString(KEY_SUCCESS) != null) {
				// registerErrorMsg.setText("");
				// String res = json.getString(KEY_SUCCESS);
				// if(Integer.parseInt(res) == 1){
				// // user successfully registred
				// // Store user details in SQLite Database
				// DatabaseHandler db = new
				// DatabaseHandler(getApplicationContext());
				// JSONObject json_user = json.getJSONObject("user");
				//
				// // Clear all previous data in database
				// userFunction.logoutUser(getApplicationContext());
				// db.addUser(json_user.getString(KEY_NAME),
				// json_user.getString(KEY_EMAIL), json.getString(KEY_UID),
				// json_user.getString(KEY_CREATED_AT));
				// // Launch Dashboard Screen
				// Intent dashboard = new Intent(getApplicationContext(),
				// Dashboard.class);
				// // Close all views before launching Dashboard
				// dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				// startActivity(dashboard);
				// // Close Registration Screen
				// finish();
				// }else{
				// // Error in registration
				// registerErrorMsg.setText("Error occured in registration");
				// }
				// }
				// } catch (JSONException e) {
				// e.printStackTrace();
				// }
			}
		});

	}

	public class DownloadWebTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... urls) {
			String response = "";
			
			//
			// TODO Auto-generated method stub
			for (String url : urls) {
				try {
					InputStream source = retrieveStream(url);
					String m = "";
					int charByte = -1;
					while ((charByte = source.read()) != -1) {
						m += (char) charByte;
					}
					Log.d("error", m);
//					Gson gson = new Gson();
//
//					Reader reader = new InputStreamReader(source);
//					JSONObject o = new JSONObject(m);
//					JSONArray d = o.getJSONArray("data");
//					String jsonWrapper = String.format("{\"data\":%s}",
//							d.toString());
//
//					ResultResponse responses = gson.fromJson(jsonWrapper,
//							ResultResponse.class);
//					List<Result> results = responses.results;

					// JSONObject obj = new JSONObject(m);
					// Log.d("tuhe", obj.getString("data"));
					
//					if (m.equalsIgnoreCase("")) {
						error_msg = m;
//					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return response;
		}

		@Override
		protected void onPostExecute(String result) {
				pb.setVisibility(View.INVISIBLE);
				inputUsername.setVisibility(View.VISIBLE);
				inputEmail.setVisibility(View.VISIBLE);
				buttonRegister.setVisibility(View.VISIBLE);
				registerErrorMsg.setVisibility(View.VISIBLE);
				registerErrorMsg.setText(error_msg.replace("\"", ""));
				logo.setVisibility(View.VISIBLE);
		}

		protected InputStream retrieveStream(String url) {

			DefaultHttpClient client = new DefaultHttpClient();

			HttpGet getRequest = new HttpGet(url);

			try {
				
				Log.d("HTTP", "cobaaaaaaaaa");
				HttpResponse getResponse = client.execute(getRequest);
				final int statusCode = getResponse.getStatusLine()
						.getStatusCode();

				Log.d("HTTP", "" + statusCode);
				if (statusCode != HttpStatus.SC_OK) {
					Log.e("error", "masuk");
					Log.w(getClass().getSimpleName(), "Error " + statusCode
							+ " for URL " + url);
					return null;
				}

				HttpEntity getResponseEntity = getResponse.getEntity();
				return getResponseEntity.getContent();

			} catch (IOException e) {

				Log.e("error", "masukss");
				getRequest.abort();
				Log.w(getClass().getSimpleName(), "Error for URL " + url, e);
			}

			return null;

		}

	}
	
	
}