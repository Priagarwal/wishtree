package com.paypal.psc.WishTree.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class Response {
	String status;
	String result;
	String error_code;
	Object o;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getError_code() {
		return error_code;
	}
	public void setError_code(String error_code) {
		this.error_code = error_code;
	}
	public Object getO() {
		return o;
	}
	public void setO(Object o) {
		this.o = o;
	}
	
	//JSONObject sitesArrayObj;
	
}
