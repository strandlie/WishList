package com.strandlie.lambda.item;

import com.strandlie.lambda.common.APIRequest;

public class ItemRequest extends APIRequest {
	
	
	
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
