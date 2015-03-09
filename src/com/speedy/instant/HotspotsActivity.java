package com.speedy.instant;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.SequenceInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializer;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class HotspotsActivity extends Activity { 
	
	private TextView textView;
	private ProgressBar progressBar;
	private Button update;
	private ArrayList<String[]> allResults = new ArrayList<String[]>();
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hotspots);
		setTitle("My Hotspot");
		
		textView = (TextView) findViewById(R.id.textView1); 
		progressBar = (ProgressBar) findViewById(R.id.progressBar1);
		
		DownloadWebServiceTask task = new DownloadWebServiceTask();
		task.execute(new String[] {
				"http://speedyinstan.com/api3/nearby.php?Lat=-6.251195&Rad=100000&Lng=106.907689" });

	  
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_hotspots, menu);
		return true;
	}
	
	private class DownloadWebServiceTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... urls) {
			
			String response = "";
			// TODO Auto-generated method stub
			for (String url :urls) {
				try {
					InputStream source = retrieveStream(url);
					String m = "";
					int charByte = -1;
					while((charByte = source.read()) != -1) {
						m += (char) charByte;
					}
					Log.d("error", m);
					Gson gson = new Gson();
//					String beginning = "{\"status\":\"success\",\"total\":5,\"page\":0,\"limit\":5,\"radius\":\"10000\",\"data\":";
//					String end = "}";
//					List<InputStream> streams = Arrays.asList(
//							new ByteArrayInputStream(beginning.getBytes()),
//							source, new ByteArrayInputStream(end.getBytes()));
//					InputStream story = new SequenceInputStream(
//							Collections.enumeration(streams));
					
					Reader reader = new InputStreamReader(source);
					JSONObject o = new JSONObject(m);
					JSONArray d = o.getJSONArray("data");
					String jsonWrapper = String.format("{\"data\":%s}", d.toString());
					Log.d("jsonwrapper", jsonWrapper);
					ResultResponse responses = gson.fromJson(jsonWrapper,
							ResultResponse.class);
					List<Result> results = responses.results;

					
					for (Result result :results) {
						allResults.add(new String[] {result.name, result.address,
								result.latitude,
								result.longitude});
						Log.d("aa", result.name);
					}
//					JSONObject obj = new JSONObject(m);
//					Log.d("tuhe", obj.getString("data"));
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return response;
		}
		

		@Override
		protected void onPostExecute(String result) {
			progressBar.setVisibility(View.INVISIBLE);
			final Context context = HotspotsActivity.this;
			ListView ls2 = new ListView(context);
			/*
			 * m_lv1.setClickable(true); m_lv1.setFastScrollEnabled(true);
			 * m_lv1.setItemsCanFocus(true);
			 */

			// clear previous results in the LV
			ls2.setAdapter(null);

			// Creating a button - Load More
			Button btnLoadMore = new Button(context);
			btnLoadMore.setText("Add Hotspot");
			btnLoadMore.setBackgroundColor(Color.RED);
			 
			// Adding button to listview at footer
			ls2.addFooterView(btnLoadMore);
			
			
			
			// populate
			ArrayList<LocationHotspot> locationsHotspot = new ArrayList<LocationHotspot>();
			LocationHotspot locationHotspot;
			for (int i = 0; i < allResults.size(); i++) {
				locationHotspot = new LocationHotspot(allResults.get(i)[0], allResults.get(i)[1], allResults.get(i)[2], allResults.get(i)[3]);
				locationsHotspot.add(locationHotspot);
			}
			LocationAdapter lvAdapter = new LocationAdapter(context, locationsHotspot);
			
			
			/**
	         * Listening to listview single row selected
	         * **/
	        ls2.setOnItemClickListener(new OnItemClickListener() {
	 
	            @Override
	            public void onItemClick(AdapterView<?> parent, View view,
	                    int position, long id) {
	            	Log.d("ada", "oke");
	            }
	        });	
	        
	        ls2.setAdapter(lvAdapter);
			
			setContentView(ls2);

		}
		
		
		private InputStream retrieveStream(String url) {

			DefaultHttpClient client = new DefaultHttpClient();

			HttpGet getRequest = new HttpGet(url);

			try {

				HttpResponse getResponse = client.execute(getRequest);
				final int statusCode = getResponse.getStatusLine()
						.getStatusCode();

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
