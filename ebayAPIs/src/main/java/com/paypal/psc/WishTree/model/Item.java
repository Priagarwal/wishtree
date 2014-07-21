package com.paypal.psc.WishTree.model;



public class Item {

	String itemId;
	String price;
	final String quantity = "1";
	String endUserIP;
	final String action = "Purchase";
	String currency;
	String sellerName;
	String galleryUrl;
	String sellerFeedbackScore;
	String itemTitle;
	
	public String getItemTitle() {
		return itemTitle;
	}
	public void setItemTitle(String itemTitle) {
		this.itemTitle = itemTitle;
	}
	public String getSellerName() {
		return sellerName;
	}
	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}
	public String getGalleryUrl() {
		return galleryUrl;
	}
	public void setGalleryUrl(String galleryUrl) {
		this.galleryUrl = galleryUrl;
	}
	public String getSellerFeedbackScore() {
		return sellerFeedbackScore;
	}
	public void setSellerFeedbackScore(String sellerFeedbackScore) {
		this.sellerFeedbackScore = sellerFeedbackScore;
	}
	public String getSellerEmail() {
		return sellerEmail;
	}
	public void setSellerEmail(String sellerEmail) {
		this.sellerEmail = sellerEmail;
	}
	public String getSellerStatus() {
		return sellerStatus;
	}
	public void setSellerStatus(String sellerStatus) {
		this.sellerStatus = sellerStatus;
	}
	public String getSellerLocation() {
		return sellerLocation;
	}
	public void setSellerLocation(String sellerLocation) {
		this.sellerLocation = sellerLocation;
	}
	String sellerEmail;
	String sellerStatus;
	String sellerLocation;
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
	
	public String getQuantity() {
		return quantity;
	}
	public String getEndUserIP() {
		return endUserIP;
	}
	public void setEndUserIP(String endUserIP) {
		this.endUserIP = endUserIP;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getAction() {
		return action;
	}
	


}
