package com.speedy.instant;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
//import android.widget.SearchView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.speedy.instant.R;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

public class MainActivity extends FragmentActivity implements
		OnInfoWindowClickListener {

	// Google Map
	private GoogleMap googleMap;

	GPSTracker gps;
	
	double radius;
	int limit;

	HotspotNearby hotspotNearby;
	private ArrayList<String[]> allResults = new ArrayList<String[]>();
	
	private ProgressBar progressBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		radius=10000;
		limit=50;
		
		// getWindow().requestFeature(Window.FEATURE_ACTION_BAR);

		setContentView(R.layout.activity_main);
		progressBar = (ProgressBar) findViewById(R.id.progressBar1);
		try {

			// create class object
			gps = new GPSTracker(MainActivity.this);

			// check if GPS enabled
			if (gps.canGetLocation()) {

				double latitude = gps.getLatitude();
				double longitude = gps.getLongitude();

				// \n is for new line
				Toast.makeText(
						getApplicationContext(),
						"Your Location is - \nLat: " + latitude + "\nLong: "
								+ longitude, Toast.LENGTH_LONG).show();
			} else {
				// can't get location
				// GPS or Network is not enabled
				// Ask user to enable GPS/network in settings
				gps.showSettingsAlert();
			}

			double latitude = gps.getLatitude();
			double longitude = gps.getLongitude();
			//String url = "http://speedyinstan.com/api3/nearby.php?Lat="
					//+ latitude + "&Lng=" + longitude + "&Rad=10000&limit=50";
			
			Log.d("latitude : ", ""+latitude);
			Log.d("longitude: ", ""+longitude);

			DownloadWebTask task = new DownloadWebTask();
			task.execute(new String[] { "http://speedyinstan.com/api3/nearby.php?Lat="
					+ latitude + "&Lng=" + longitude + "&Rad="+radius+"&limit="+limit});

		} catch (Exception e) {
			e.printStackTrace();
		}

	}



	public void showMaps(ArrayList<String[]> allResults) {
		// Loading map
		initilizeMap();

		googleMap.setOnInfoWindowClickListener(this);

		// Changing map type
		googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		// googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
		// googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
		// googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
		// googleMap.setMapType(GoogleMap.MAP_TYPE_NONE);

		// Showing / hiding your current location
		googleMap.setMyLocationEnabled(true);

		// Enable / Disable zooming controls
		googleMap.getUiSettings().setZoomControlsEnabled(false);

		// Enable / Disable my location button
		googleMap.getUiSettings().setMyLocationButtonEnabled(true);

		// Enable / Disable Compass icon
		googleMap.getUiSettings().setCompassEnabled(false);

		// Enable / Disable Rotate gesture
		googleMap.getUiSettings().setRotateGesturesEnabled(true);

		// Enable / Disable zooming functionality
		googleMap.getUiSettings().setZoomGesturesEnabled(true);

		for (int i = 0; i < allResults.size(); i++) {
//			addMarkers(allResults.get(i)[0], allResults.get(i)[1],
//					Double.parseDouble(allResults.get(i)[2]),
//					allResults.get(i)[3]);
			MarkerOptions marker = new MarkerOptions()
			.position(
					new LatLng(Double.parseDouble(allResults.get(i)[2]), Double
							.parseDouble(allResults.get(i)[3]))).title(allResults.get(i)[4] + "-"+ allResults.get(i)[0])
			.snippet(allResults.get(i)[1]);
			googleMap.setOnInfoWindowClickListener(this);
			marker.icon(BitmapDescriptorFactory
					.fromResource(R.drawable.wifimarkerbroadband));
		
			googleMap.addMarker(marker);
		}
		CameraPosition cameraPosition = new CameraPosition.Builder()
				.target(new LatLng(gps.getLatitude(), gps.getLongitude()))
				.zoom(15).build();

		googleMap.animateCamera(CameraUpdateFactory
				.newCameraPosition(cameraPosition));

	}

	public class DownloadWebTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... urls) {
			String response = "";
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
					Gson gson = new Gson();

					Reader reader = new InputStreamReader(source);
					JSONObject o = new JSONObject(m);
					JSONArray d = o.getJSONArray("data");
					String jsonWrapper = String.format("{\"data\":%s}",
							d.toString());

					ResultResponse responses = gson.fromJson(jsonWrapper,
							ResultResponse.class);
					List<Result> results = responses.results;

					for (Result result : results) {
						// addMarkers(result.latitude, result.longitude,
						// result.id, result.name);
						// Log.d("aa", result.name);
						allResults.add(new String[] { result.name,
								result.address, result.latitude,
								result.longitude, result.id + "" });
						
					}
					// JSONObject obj = new JSONObject(m);
					// Log.d("tuhe", obj.getString("data"));

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return response;
		}

		@Override
		protected void onPostExecute(String result) {
			progressBar.setVisibility(View.INVISIBLE);

			showMaps(allResults);
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

	@Override
	public void onInfoWindowClick(Marker arg0) {
		Intent intent = new Intent(MainActivity.this, HotspotProfilActivity.class);
		intent.putExtra("ID", arg0.getTitle());
		startActivity(intent);

	}

	// Initiating Menu XML file (menu.xml)
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.layout.menu, menu);
		return true;
	}

	/**
	 * Event Handling for Individual menu item selected Identify single menu
	 * item by it's id
	 * */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.profile:

			Intent intent = new Intent(getApplicationContext(), Profiles.class);
			startActivity(intent);
			return true;

		case R.id.hotspot:

			Intent intents = new Intent(getApplicationContext(),
					HotspotsActivity.class);
			startActivity(intents);
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * function to load map. If map is not created it will create it for you
	 * */
	private void initilizeMap() {
		if (googleMap == null) {

			// Getting reference to the SupportMapFragment of activity_main.xml
			SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.map);

			// Getting GoogleMap object from the fragment
			googleMap = fm.getMap();

			// check if map is created successfully or not
			if (googleMap == null) {
				Toast.makeText(getApplicationContext(),
						"Sorry! unable to create maps", Toast.LENGTH_SHORT)
						.show();
			}

		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		initilizeMap();
	}

}
