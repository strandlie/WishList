package com.strandlie.lambda.giftgroup;

import java.util.List;

import common.APIRequest;

public class GiftGroupRequest extends APIRequest {
	
	private List<Integer> personsInGiftGroup;
	
	@Override
	public String toString() {
		StringBuilder string = new StringBuilder();
		string.append(super.toString());
		if (!(personsInGiftGroup == null)) {
			string.append("\nIDs of persons in giftGroup: \n");
			
			for (Integer id : personsInGiftGroup) {
				string.append(id.toString() + "\n");
			}
		}
		return string.toString();
	}
	
	public void setName(String name) {
		this.fields().put("name", name);
	}
	
	public String getName() {
		return this.fields().getOrDefault("name", null);
	}
	
	public void setPictureURL(String pictureURL) {
		this.fields().put("pictureURL", pictureURL);
	}
	
	public String getPictureURL() {
		return this.fields().getOrDefault("pictureURL", null);
	}
	
	public void setDescription(String description) {
		this.fields().put("description", description);
	}
	
	public String getDescription() {
		return this.fields().getOrDefault("description", null);
	}
	
	public void setPersonsInGiftGroup(List<Integer> persons) {
		this.personsInGiftGroup = persons;
	}
	
	public List<Integer> getPersonsInGiftGroup() {
		return this.personsInGiftGroup;
	}
}
