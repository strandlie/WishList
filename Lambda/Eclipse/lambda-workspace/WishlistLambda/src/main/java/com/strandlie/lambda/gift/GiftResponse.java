package com.strandlie.lambda.gift;

import com.strandlie.lambda.common.APIResponse;

public class GiftResponse extends APIResponse {
	
	private boolean giftIsAdded;
	private boolean giftIsUpdated;
	private boolean giftIsDeleted;

	public boolean getGiftIsAdded() {
		return giftIsAdded;
	}

	public void setGiftIsAdded(boolean giftIsAdded) {
		this.giftIsAdded = giftIsAdded;
	}

	public boolean getGiftIsUpdated() {
		return giftIsUpdated;
	}

	public void setGiftIsUpdated(boolean giftIsUpdated) {
		this.giftIsUpdated = giftIsUpdated;
	}

	public boolean getGiftIsDeleted() {
		return giftIsDeleted;
	}

	public void setGiftIsDeleted(boolean giftIsDeleted) {
		this.giftIsDeleted = giftIsDeleted;
	}

	public void setGiverID(int id) {
		this.fields().put("giverID", Integer.toString(id));
	}
	
	public Integer getGiverID() {
		String temp_id = this.fields().getOrDefault("giverID", null);
		if (temp_id == null) {
			return null;
		}
		return Integer.parseInt(temp_id);
	}
	
	
	public void setQuantity(int quantity) {
		this.fields().put("quantity", Integer.toString(quantity));
	}
	
	public Integer getQuantity() {
		String temp_quant = this.fields().getOrDefault("quantity", null);
		if (temp_quant == null) {
			return null;
		}
		return Integer.parseInt(temp_quant);
	}
	
	public void setRecepientID(int id) {
		this.fields().put("recepientID", Integer.toString(id));
	}
	
	public Integer getRecepientID() {
		String temp_id = this.fields().getOrDefault("recepientID", null);
		if (temp_id == null) {
			return null;
		}
		return Integer.parseInt(temp_id);
	}
	
	public void setItemID(int id) {
		this.fields().put("itemID", Integer.toString(id));
	}
	
	public Integer getItemID() {
		String temp_id = this.fields().getOrDefault("itemID", null);
		if (temp_id == null) {
			return null;
		}
		return Integer.parseInt(temp_id);
	
	}
}
