package com.speedy.instant;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.AbstractHttpMessage;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.webkit.CookieManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends Activity {
	Button btnLogin;
	Button btnLinkToRegister;
	EditText inputUsername;
	EditText inputPassword;
	TextView loginErrorMsg;
	ProgressBar pb;
	String username;
	String password;
	String error_msg;
	boolean isInternetPresent = false;
	ConnectionDetector cd;

	// JSON Response node names
	private static String KEY_SUCCESS = "success";
	private static String KEY_ERROR = "error";
	private static String KEY_ERROR_MSG = "error_msg";
	private static String KEY_UID = "uid";
	private static String KEY_NAME = "name";
	private static String KEY_EMAIL = "email";
	private static String KEY_CREATED_AT = "created_at";
	private static final String RESPONSE_VALID = "validate sucess";
	private static final String RESPONSE_INVALID = "failed login";
	
	SharedPreferences preferences ;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		preferences = PreferenceManager.getDefaultSharedPreferences(this);
		
		// Importing all assets like buttons, text fields
		inputUsername = (EditText) findViewById(R.id.loginUsername);
		inputPassword = (EditText) findViewById(R.id.loginPassword);
		btnLogin = (Button) findViewById(R.id.buttonLogin);
		btnLinkToRegister = (Button) findViewById(R.id.buttonLinkToRegisterScreen);
		loginErrorMsg = (TextView) findViewById(R.id.login_error);
		pb = (ProgressBar) findViewById(R.id.progressBar1);
		
		 // creating connection detector class instance
        cd = new ConnectionDetector(getApplicationContext());
		
		inputPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
	        @Override
	        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
	            if (actionId == EditorInfo.IME_ACTION_DONE) {
	            	
	            	 // get Internet status
	                isInternetPresent = cd.isConnectingToInternet();
	                
	                if (isInternetPresent) {
	            	

		            	Log.d("password: -> ", inputPassword.getText().toString());
						
						SharedPreferences.Editor editor = preferences.edit();
						editor.putString("username", inputUsername.getText().toString()+";"+inputPassword.getText().toString());
						editor.commit();
						
						username = inputUsername.getText().toString();
						password = inputPassword.getText().toString();
						inputUsername.setVisibility(View.GONE);
						inputPassword.setVisibility(View.GONE);
						btnLogin.setVisibility(View.GONE);
						btnLinkToRegister.setVisibility(View.GONE);
						loginErrorMsg.setVisibility(View.GONE);
						pb.setVisibility(View.VISIBLE);
						DownloadWebTask task = new DownloadWebTask();
						task.execute("http://speedyinstan.com/api3/user.php?action=3login&username="
								+ username + "&password=" + password + "");
	                } else {
	                	//display in short period of time
	                	Toast.makeText(getApplicationContext(), "You don't have internet connection", Toast.LENGTH_SHORT).show();
	                }
	                
	                return true;
	            }
	            return false;
	        }

	    });
		

		// Login button Click Event
		btnLogin.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				
				
				
				 // get Internet status
                isInternetPresent = cd.isConnectingToInternet();
                
                if (isInternetPresent) {
            	

	            	Log.d("password: -> ", inputPassword.getText().toString());
					
					SharedPreferences.Editor editor = preferences.edit();
					editor.putString("username", inputUsername.getText().toString()+";"+inputPassword.getText().toString());
					editor.commit();
					
					username = inputUsername.getText().toString();
					password = inputPassword.getText().toString();
					inputUsername.setVisibility(View.GONE);
					inputPassword.setVisibility(View.GONE);
					btnLogin.setVisibility(View.GONE);
					btnLinkToRegister.setVisibility(View.GONE);
					loginErrorMsg.setVisibility(View.GONE);
					pb.setVisibility(View.VISIBLE);
					DownloadWebTask task = new DownloadWebTask();
					task.execute("http://speedyinstan.com/api3/user.php?action=3login&username="
							+ username + "&password=" + password + "");
                } else {
                	//display in short period of time
                	Toast.makeText(getApplicationContext(), "You don't have internet connection", Toast.LENGTH_SHORT).show();
                }
                
				// // String username = inputUsername.getText().toString();
				// // String password = inputPassword.getText().toString();
				// new Thread() {
				// public void run() {
				// String username = "alfarizka";
				// String password = "BG2013KC";
				// UserFunctions userFunction = new UserFunctions();
				// JSONObject json = userFunction.loginUser(username,
				// password);
				//
				// // check for login response
				// try {
				// if (json.getString(KEY_SUCCESS) != null) {
				// loginErrorMsg.setText("");
				// String res = json.getString(KEY_SUCCESS);
				// if (Integer.parseInt(res) == 1) {
				// // user successfully logged in
				// // Store user details in SQLite Database
				// DatabaseHandler db = new DatabaseHandler(
				// getApplicationContext());
				// JSONObject json_user = json
				// .getJSONObject("user");
				//
				// // Clear all previous data in database
				// userFunction
				// .logoutUser(getApplicationContext());
				// db.addUser(json_user.getString(KEY_NAME),
				// json_user.getString(KEY_EMAIL),
				// json.getString(KEY_UID),
				// json_user.getString(KEY_CREATED_AT));

				// Launch Dashboard Screen

				// // Close all views before launching
				// // Dashboard
				// dashboard
				// .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				// startActivity(dashboard);
				//
				// // Close Login Screen
				// finish();
				// } else {
				// // Error in login
				// loginErrorMsg
				// .setText("Incorrect username/password");
				// }
				// }
				// } catch (JSONException e) {
				// e.printStackTrace();
				// }
				// }
				// }.start();
			}

		});

		// Link to Register Screen
		btnLinkToRegister.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				Intent i = new Intent(getApplicationContext(), Register.class);
				startActivity(i);
			}
		});
	}

	public class DownloadWebTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... urls) {
			String response = "";

			//
			// TODO Auto-generated method stub
			for (String urlString : urls) {
				
//				HttpURLConnection urlConnection = null;
//				try {
//					URL url = new URL(urlString);
////				    urlConnection = (HttpURLConnection) url.openConnection();
////				 
////				    // Set cookies in requests
////				    CookieManager cookieManager = CookieManager.getInstance();
////				    String cookie = cookieManager.getCookie(urlConnection.getURL().toString());
////				    if (cookie != null) {
////				        urlConnection.setRequestProperty("Cookie", cookie);
////				    }
////				    urlConnection.connect();
////				 
////				    // Get cookies from responses and save into the cookie manager
////				    //List<String> cookieList = urlConnection.getHeaderFields().get("Set-Cookie");
////				    Map<String, List<String>> headers = urlConnection.getHeaderFields();
////				    for(String key : headers.keySet()) {
////				    	Log.d("header ["+key+"]", headers.get(key).toString());
////				    }
////				    if (cookieList != null) {
////				        for (String cookieTemp : cookieList) {
////				            cookieManager.setCookie(urlConnection.getURL().toString(), cookieTemp);
////				            Log.d("cookie", cookieTemp);
////				        }
////				    }
//				 
//				    InputStream in = new BufferedInputStream (urlConnection.getInputStream());
//				} catch (IOException e) {
//				    e.printStackTrace();
//				} finally {
//				    if (urlConnection != null) {
//				        urlConnection.disconnect();
//				    }
//				}
				try {
					InputStream source = retrieveStream(urlString);
					String m = "";
					int charByte = -1;
					while ((charByte = source.read()) != -1) {
						m += (char) charByte;
					}
					Log.d("response", m);
					// Gson gson = new Gson();
					//
					// Reader reader = new InputStreamReader(source);
					// JSONObject o = new JSONObject(m);
					// JSONArray d = o.getJSONArray("data");
					// String jsonWrapper = String.format("{\"data\":%s}",
					// d.toString());
					//
					// ResultResponse responses = gson.fromJson(jsonWrapper,
					// ResultResponse.class);
					// List<Result> results = responses.results;

					// JSONObject obj = new JSONObject(m);
					// Log.d("tuhe", obj.getString("data"));

					// if (m.equalsIgnoreCase("")) {
					error_msg = m;
					// }

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return response;
		}

		@Override
		protected void onPostExecute(String result) {
			if (!(error_msg.replace("\"", "").equalsIgnoreCase(RESPONSE_INVALID))) {
				
				
				
				Intent dashboard = new Intent(getApplicationContext(),
						MainActivity.class);
				startActivity(dashboard);
				finish();
			} else {
				loginErrorMsg.setText("Incorrect username and/or password.");
				inputUsername.setVisibility(View.VISIBLE);
				inputPassword.setVisibility(View.VISIBLE);
				btnLogin.setVisibility(View.VISIBLE);
				btnLinkToRegister.setVisibility(View.VISIBLE);
				loginErrorMsg.setVisibility(View.VISIBLE);

				pb.setVisibility(View.INVISIBLE);
			}

		}

		protected InputStream retrieveStream(String url) {

			DefaultHttpClient client = new DefaultHttpClient();

			HttpGet getRequest = new HttpGet(url);
			
			
			try {

				CookieManager cookieManager = CookieManager.getInstance();
				Log.d("HTTP", "cobaaaaaaaaa");
				HttpResponse getResponse = client.execute(getRequest);
				
				for(Header header : getResponse.getAllHeaders()) {
					Log.d("Jembut Api", header.getName() + " -> " + header.getValue());
					if (header.getName().equals("Set-Cookie")) {
						//cookieManager.setCookie(url, header.getValue());
					}
				}
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