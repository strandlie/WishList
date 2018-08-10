package com.strandlie.lambda.gift;

import common.APIRequest;


/**
 * A GiftRequest is always from a giver. A GiftNr (or ID)
 * is then generated and returned, and this ID is unique
 * within each giver. 
 * 
 * A GiftRequest can also have specified Recepient, and Item.
 * If Item is specified, then a quantity also has to be specified.
 * @author hstrandlie
 *
 */
public class GiftRequest extends APIRequest {
	
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
	
	

}
