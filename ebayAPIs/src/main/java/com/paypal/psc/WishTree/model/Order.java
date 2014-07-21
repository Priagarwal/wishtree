package com.paypal.psc.WishTree.model;


import java.net.InetAddress;
import java.net.UnknownHostException;

public class Order {
	final String ACTION = "Purchase";
	String itemId;
	String price;
	String quantity;
	String endUserIP;
	String currency;
	
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public void setEndUserIP() {
		String hostIPAddress = "";
		try {
			InetAddress ipAddress = InetAddress.getLocalHost();
			hostIPAddress = ipAddress.getHostAddress();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.endUserIP = hostIPAddress;
	}
	public String getEndUserIP() {
		return endUserIP;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}

}
