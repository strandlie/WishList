package com.strandlie.lambda.wish;

import com.strandlie.lambda.common.APIResponse;

public class WishResponse extends APIResponse {
	
	private boolean wishIsAdded;
	private boolean wishIsUpdated;
	private boolean wishIsDeleted;
	
	public boolean getWishIsAdded() {
		return wishIsAdded;
	}
	public void setWishIsAdded(boolean wishIsAdded) {
		this.wishIsAdded = wishIsAdded;
	}
	public boolean getWishIsUpdated() {
		return wishIsUpdated;
	}
	public void setWishIsUpdated(boolean wishIsUpdated) {
		this.wishIsUpdated = wishIsUpdated;
	}
	public boolean getWishIsDeleted() {
		return wishIsDeleted;
	}
	public void setWishIsDeleted(boolean wishIsDeleted) {
		this.wishIsDeleted = wishIsDeleted;
	}
	
	public void setWisherID(int id) {
		this.fields().put("wisherID", Integer.toString(id));
	}
	
	public int getWisherID() {
		return getIntFromMap("wisherID");
	}
	
	public void setItemID(int id) {
		this.fields().put("itemID", Integer.toString(id));
	}
	
	public int getItemID() {
		return getIntFromMap("itemID");
	}
	
	public void setQuantity(int quantity) {
		this.fields().put("quantity", Integer.toString(quantity));
	}
	
	public int getQuantity() {
		return getIntFromMap("quantity");
	}

}
