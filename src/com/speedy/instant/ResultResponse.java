package com.speedy.instant;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class ResultResponse {
	
	@SerializedName("data")
	public List<Result> results;

}
