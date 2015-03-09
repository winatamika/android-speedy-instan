package com.speedy.instant;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import com.speedy.instant.Register.DownloadWebTask;



import com.google.gson.Gson;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class Profiles extends Activity {
	
	private String error_msg;
	private EditText old_pass;
	private EditText new_pass;
	private EditText retype_new_pass;
	private EditText email;
	private EditText company;
	private EditText website;
	private String dataEmail = "";
	private String dataCompany = "";
	private String dataWebsite = "";
	
	private UserProfile dataUser;
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile);
		old_pass = (EditText) findViewById(R.id.old_pass);
		new_pass = (EditText) findViewById(R.id.new_pass);
		retype_new_pass = (EditText) findViewById(R.id.retype_pass);
		email = (EditText) findViewById(R.id.email);
		company = (EditText) findViewById(R.id.company);
		website= (EditText) findViewById(R.id.website);
		
		setTitle("Edit Profiles");
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String savedPref = preferences.getString("username", "");
        String[] dataUser = savedPref.split(";");
		DownloadWebTask task = new DownloadWebTask();
		task.execute(new String[] { "http://speedyinstan.com/api3/userv3.php?action=4get&username="+dataUser[0]+"&password="+dataUser[1] });
		//SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
		//boolean bo = sp.getBoolean("username", true);
        //String savedPref = sp.getString("username", "");
//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
//     // then you use
//     prefs.getBoolean("keystring", true);
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
					Log.d("response get", m);
					Gson gson = new Gson();
					dataUser = gson.fromJson(m, UserProfile.class);
					dataEmail = dataUser.user_email;
					dataCompany = dataUser.user_company;
					dataWebsite = dataUser.user_website;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return response;
		}

		@Override
		protected void onPostExecute(String result) {
			email.setText(dataEmail);
			company.setText(dataCompany);
			website.setText(dataWebsite);
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
