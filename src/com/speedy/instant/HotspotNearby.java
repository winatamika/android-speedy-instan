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

import android.util.Log;

import com.google.gson.Gson;

public class HotspotNearby {
	
	protected List<Result> urlStream(String url, double latitude, double longitude) {
		List<Result> results = null;
		
		try {
			Log.d("debug", url);
			InputStream source = retrieveStream(url);
			String m = "";
			int charByte = -1;
			while((charByte = source.read()) != -1) {
				m += (char) charByte;
			}
			Log.d("error", m);
			Gson gson = new Gson();
			
			Reader reader = new InputStreamReader(source);
			JSONObject o = new JSONObject(m);
			JSONArray d = o.getJSONArray("data");
			String jsonWrapper = String.format("{\"data\":%s}", d.toString());
	
			ResultResponse responses = gson.fromJson(jsonWrapper,
					ResultResponse.class);
			results = responses.results;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		return results;
		
	}
	
	protected InputStream retrieveStream(String url) {

		DefaultHttpClient client = new DefaultHttpClient();

		HttpGet getRequest = new HttpGet(url);

		try {

			Log.d("HTTP","cobaaaaaaaaa");
			HttpResponse getResponse = client.execute(getRequest);
			final int statusCode = getResponse.getStatusLine()
					.getStatusCode();

			Log.d("HTTP",""+ statusCode);
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
