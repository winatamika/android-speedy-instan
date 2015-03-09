package com.speedy.instant;

public class LocationHotspot {
	private String name;
	private String address;
	private String latitude;
	private String longitude;
	
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public LocationHotspot(String name, String address, String latitude,
			String longitude) {
		super();
		this.name = name;
		this.address = address;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	public LocationHotspot(String name, String address) {
		super();
		this.name = name;
		this.address = address;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
}
