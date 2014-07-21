package com.paypal.psc.WishTree.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.codehaus.jackson.JsonProcessingException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.paypal.psc.WishTree.Service.GetItemDetail;
import com.paypal.psc.WishTree.Service.PlaceOrder;
import com.paypal.psc.WishTree.model.Item;
import com.paypal.psc.WishTree.model.Order;
import com.paypal.psc.WishTree.model.Response;
@Controller
@RequestMapping(value = "/p1")
public class PersonController {

	@RequestMapping(value = "/detail", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	HttpEntity<?> addMerchantAndSite(@RequestBody String body)
			throws JSONException, JsonProcessingException, IOException {
		String username, url;
		int piwik_id;
		JSONObject jsonObj;
		JSONArray sitesArray;
		Response response = new Response();
		try {
			jsonObj = new JSONObject(body);
		}
		catch (Exception e) {
			response.setStatus("Failure");
			response.setO(null);
			return null;
		}
		GetItemDetail gt = new GetItemDetail();
		Properties prop = new Properties();
		//prop.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("prop.properties"));
		String auth = "AgAAAA**AQAAAA**aAAAAA**j3FwUw**nY+sHZ2PrBmdj6wVnY+sEZ2PrA2dj6wFk4GhDJKKqA+dj6x9nY+seQ**V8UCAA**AAMAAA**BCnnjkaxDJN8QQ5pRYqsVEf3Ev/BmIjJ6CJ9vlAgTEkfSnypf1Lkb1ysObI1/H13J6ydyYBDKZQFH6J8kveXTp6UChEZLesqxWcJMQKFleFSWzI/FThH0P5wC5BiRDLrZesraq1pbOjGNXJ/BhQegyxB9VZSGe2JyMj0CfgmHPhvjr7rd9DYGMgpHB0ez/+KCPKXlBzq0/TuuFELHpuShqZdTxG2TOEcTcGdZV/9jL+dYjpVLK518HRSCvKAs36gMyStxg7uOadlYYWbDSfzqmekS3rs0hLBkeiuYdKRkbG5PVsOE9RwTH3OYJsZ9t8yFEqE/fGvUm9thxzwCfTfn6q7kOVLmp+UHZ+yumi/FC052P7hNpfTUQzDRGCQSyYo0NMtGHdP9HkmwutJJ45SMceyyWmau95b5QzWn1jp5jP0uF6BV/E70XIaNa7VH7CZm88W/ptpvcHSdf7PjwSLhj9w5iewksTU3ydnZK6ixDW408dxGEunlcl/b5bX+KeBRkljFzcrubpz2sbdzFDFT9OCzra5EGlTFsJOvjKRmkysWVVnkYLoGF0flBOeK4Mctqhz/NWPMebA9rfmXk1FHSzpu3Hk4+d9p+Q9Q0AgqNaj1CfLZyvDo9nHmmC7CBHw7cZzbu81E6YARdo68KVwhSQP51jtf514WFP/xTrZF1JgiObTS/UyX7NMUlZEln4VB3IXUo/4/cvulUwUogmeI45dlPtAgMCYz5L7GmjGdJbRye1fK7j4cjHd5Nf2Mnec";
		Item t = gt.GetItemDetails(jsonObj.getString("itemId"), auth);
		response.setO(t);
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add("Access-Control-Allow-Origin", "*");
		headers.add("Access-Control-Max-Age","3600");
		headers.add("Access-Control-Allow-Methods","POST, GET, OPTIONS, DELETE");
		headers.add("Access-Control-Allow-Headers", "X-Requested-With,Host,User-Agent,Accept,Accept-Language,Accept-Encoding,Accept-Charset,Keep-Alive,Connection,Referer,Origin");
		headers.add("Access-Control-Allow-Credentials","true");
		
		HttpEntity y = new ResponseEntity<Response>(response, headers, HttpStatus.OK);
		return y;

	}
 
	@RequestMapping(value = "/tranId", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	HttpEntity<?> getTranId(@RequestBody String body)
			throws JSONException, JsonProcessingException, IOException {
		String username, url;
		int piwik_id;
		JSONObject jsonObj;
		JSONArray sitesArray;
		Response response = new Response();
		try {
			jsonObj = new JSONObject(body);
		}
		catch (Exception e) {
			response.setStatus("Failure");
			response.setO(null);
			return null;
		}
		String auth = "AgAAAA**AQAAAA**aAAAAA**j3FwUw**nY+sHZ2PrBmdj6wVnY+sEZ2PrA2dj6wFk4GhDJKKqA+dj6x9nY+seQ**V8UCAA**AAMAAA**BCnnjkaxDJN8QQ5pRYqsVEf3Ev/BmIjJ6CJ9vlAgTEkfSnypf1Lkb1ysObI1/H13J6ydyYBDKZQFH6J8kveXTp6UChEZLesqxWcJMQKFleFSWzI/FThH0P5wC5BiRDLrZesraq1pbOjGNXJ/BhQegyxB9VZSGe2JyMj0CfgmHPhvjr7rd9DYGMgpHB0ez/+KCPKXlBzq0/TuuFELHpuShqZdTxG2TOEcTcGdZV/9jL+dYjpVLK518HRSCvKAs36gMyStxg7uOadlYYWbDSfzqmekS3rs0hLBkeiuYdKRkbG5PVsOE9RwTH3OYJsZ9t8yFEqE/fGvUm9thxzwCfTfn6q7kOVLmp+UHZ+yumi/FC052P7hNpfTUQzDRGCQSyYo0NMtGHdP9HkmwutJJ45SMceyyWmau95b5QzWn1jp5jP0uF6BV/E70XIaNa7VH7CZm88W/ptpvcHSdf7PjwSLhj9w5iewksTU3ydnZK6ixDW408dxGEunlcl/b5bX+KeBRkljFzcrubpz2sbdzFDFT9OCzra5EGlTFsJOvjKRmkysWVVnkYLoGF0flBOeK4Mctqhz/NWPMebA9rfmXk1FHSzpu3Hk4+d9p+Q9Q0AgqNaj1CfLZyvDo9nHmmC7CBHw7cZzbu81E6YARdo68KVwhSQP51jtf514WFP/xTrZF1JgiObTS/UyX7NMUlZEln4VB3IXUo/4/cvulUwUogmeI45dlPtAgMCYz5L7GmjGdJbRye1fK7j4cjHd5Nf2Mnec";
		Order orderOb = new Order();
		orderOb.setPrice(jsonObj.getString("price"));
		orderOb.setCurrency(jsonObj.getString("currency"));
		orderOb.setItemId(jsonObj.getString("itemId"));
		orderOb.setEndUserIP();
		
		PlaceOrder po = new PlaceOrder(orderOb, auth);
		String transId = po.getTransactionId();
		response.setO(transId);
		Gson gson = new Gson();
		String apiJson = gson.toJson(response);
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add("Access-Control-Allow-Origin", "*");
		headers.add("Access-Control-Max-Age","3600");
		headers.add("Access-Control-Allow-Methods","POST, GET, OPTIONS, DELETE");
		headers.add("Access-Control-Allow-Headers", "X-Requested-With,Host,User-Agent,Accept,Accept-Language,Accept-Encoding,Accept-Charset,Keep-Alive,Connection,Referer,Origin");
		HttpEntity y = new ResponseEntity<Response>(response, headers, HttpStatus.OK);
		return y;

	}
	
	

	private void GetItemDetails(String string, String property) {
		// TODO Auto-generated method stub
		
	}
}