package com.speedy.instant;

import com.google.gson.annotations.SerializedName;

public class Result {
	
	@SerializedName("id")
	public long id;
	
	@SerializedName("name")
	public String name;
	
	@SerializedName("latitude")
	public String latitude;
	
	@SerializedName("longitude")
	public String longitude;
	
	@SerializedName("placetype")
	public String placetype;
	
	@SerializedName("address")
	public String address;
	
	@SerializedName("phone")
	public String phone;
	
	
	
}
