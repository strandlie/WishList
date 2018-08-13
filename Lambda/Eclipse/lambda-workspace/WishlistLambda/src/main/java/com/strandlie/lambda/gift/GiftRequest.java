package com.strandlie.lambda.gift;

import com.strandlie.lambda.common.APIRequest;


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
		return getIntFromMap("giverID");
	}
	
	
	public void setRecepientID(int id) {
		this.fields().put("recepientID", Integer.toString(id));
	}
	
	public Integer getRecepientID() {
		return getIntFromMap("recepientID");
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
