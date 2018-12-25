package com.strandlie.lambda.wish;

import com.strandlie.lambda.common.APIRequest;

public class WishRequest extends APIRequest {
	
	public void setWisherID(int id) {
		this.fields().put("wisherID", Integer.toString(id));
	}
	
	public Integer getWisherID() {
		return getIntFromMap("wisherID");
	}
	
	public void setItemID(int id) {
		this.fields().put("itemID", Integer.toString(id));
	}
	
	public Integer getItemID() {
		return getIntFromMap("itemID");
	}
	
	public void setQuantity(int quantity) {
		this.fields().put("quantity", Integer.toString(quantity));
	}
	
	public Integer getQuantity() {
		return getIntFromMap("quantity");
	}

}
