package com.speedy.instant;

import com.google.gson.annotations.SerializedName;

public class UserProfile {
	
	@SerializedName("username")
	String username;
	
	@SerializedName("user_email")
	String user_email;
	
	@SerializedName("user_company")
	String user_company;
	
	@SerializedName("user_website")
	String user_website;
	

}
