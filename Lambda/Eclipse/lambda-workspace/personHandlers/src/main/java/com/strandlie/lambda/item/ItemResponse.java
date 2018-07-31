package com.strandlie.lambda.item;

import common.APIResponse;

public class ItemResponse extends APIResponse {
	
	private boolean itemIsAdded;
	private boolean itemIsUpdated;
	private boolean itemIsDeleted;
	
	
	public boolean getItemIsAdded() {
		return itemIsAdded;
	}
	public void setItemIsAdded(boolean itemIsAdded) {
		this.itemIsAdded = itemIsAdded;
	}
	
	public void setItemIsUpdated(boolean itemIsUpdated) {
		this.itemIsUpdated = itemIsUpdated;
	}
	
	public boolean getItemIsUpdated() {
		return itemIsUpdated;
	}
	
	public void getItemIsUpdated(boolean itemIsUpdated) {
		this.itemIsUpdated = itemIsUpdated;
	}
	
	public boolean getItemIsDeleted() {
		return itemIsDeleted;
	}
	
	public void setItemIsDeleted(boolean itemIsDeleted) {
		this.itemIsDeleted = itemIsDeleted;
	}
	
	public String getTitle() {
		return this.fields().getOrDefault("title", null);
	}
	
	public void setTitle(String title) {
		this.fields().put("title", title);
	}
	
	public String getDescription() {
		return this.fields().getOrDefault("description", null);
	}
	
	public void setDescription(String description) {
		this.fields().put("description", description);
	}
	
	public String getPictureURL() {
		return this.fields().getOrDefault("pictureURL", null);
	}
	
	public void setPictureURL(String pictureURL) {
		this.fields().put("pictureURL", pictureURL);
	}
	
	public String getWebsiteURL() {
		return this.fields().getOrDefault("websiteURL", null);
	}
	
	public void setWebsiteURL(String websiteURL) {
		this.fields().put("websiteURL", websiteURL);
	}
	
	public Double getPrice() {
		String temp_price = this.fields().getOrDefault("price", null);
		if (temp_price == null) {
			return null;
		}
		return Double.parseDouble(temp_price);
	}
	
	public void setPrice(double price) {
		this.fields().put("price", Double.toString(price));
	}
}
