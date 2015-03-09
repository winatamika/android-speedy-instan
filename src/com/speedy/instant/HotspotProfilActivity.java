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
import org.json.JSONObject;

import com.google.gson.Gson;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class HotspotProfilActivity extends Activity {
	
	String id;
	String[] hotspotIdName;
	private TextView alamat;
	private TextView kota;
	private TextView telp;
	private TextView website;
	private TextView tipe;
	private ProgressBar pb;
	private HotspotProfil responses;

	String textAlamat = "(kosong)";
	String textKota = "(kosong)";
	String textTelp = "(kosong)";
	String textWebsite = "(kosong)";
	String textTipe = "(kosong)";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hotspot_profil);
		final Bundle bundle = getIntent().getExtras();
		id = bundle.getString("ID");
		hotspotIdName = id.split("-");
		Log.d("udah masuk", id);
		setTitle(hotspotIdName[1]);
		
		alamat = (TextView) findViewById(R.id.alamat);
		kota = (TextView) findViewById(R.id.kota);
		telp = (TextView) findViewById(R.id.telp);
		website = (TextView) findViewById(R.id.website);
		tipe = (TextView) findViewById(R.id.tipe);
		pb = (ProgressBar) findViewById(R.id.progressBar1);
		
		DownloadWebTask task = new DownloadWebTask();
		task.execute(new String[] {
				"http://speedyinstan.com/api3/hotspot.php?action=8read&post_id="+hotspotIdName[0]
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_hotspot_profil, menu);
		return true;
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
					Gson gson = new Gson();
//
					Reader reader = new InputStreamReader(source);
					JSONObject o = new JSONObject(m);
//					JSONArray d = o.getJSONArray("data");
//					String jsonWrapper = String.format("{\"msg\":%s}",
//							o.toString());
					String jsonWrapper = m.substring(30, m.length()-1);
					Log.d("json wrapper", jsonWrapper);
					//String[] arrayJson = jsonWrapper.split("{");
//					String jsonWrapper = "{\"address\":\"jaljflajf\",\"kota\":\"jakarta\",\"phone\":\"3232323\"}";
					
//					Log.d("json wrapper", arrayJson[1]);
					
					responses = gson.fromJson(jsonWrapper,
							HotspotProfil.class);
					//List<Result> results = responses.results;
					// JSONObject obj = new JSONObject(m);
					// Log.d("tuhe", obj.getString("data"));
					Log.d("masuk", responses.address);
					
					textAlamat = responses.address;
					String textTelp = responses.phone;
					String textWebsite = responses.website;
					String textTipe = responses.aksestype;
//					if (!responses.kota.equals(""))
//						Log.d("masuk",responses.kota);
					
					if (!responses.phone.equals(""))
						Log.d("masuk",responses.phone);
					
					
					Log.d("masuk",responses.website);
					Log.d("masuk",responses.aksestype);
//					if (m.equalsIgnoreCase("")) {
//						error_msg = m;
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
			alamat.setText(textAlamat);
			kota.setText("kosong");
			telp.setText(responses.phone);
			website.setText(responses.website);
			tipe.setText(responses.aksestype);
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
