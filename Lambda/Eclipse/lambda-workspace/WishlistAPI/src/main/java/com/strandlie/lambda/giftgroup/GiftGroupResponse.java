package com.strandlie.lambda.giftgroup;

import java.util.List;

import com.strandlie.lambda.common.APIResponse;

public class GiftGroupResponse extends APIResponse {
	
	private boolean giftGroupIsAdded;
	private boolean giftGroupIsUpdated;
	private boolean giftGroupIsDeleted;
	private boolean personsAreAddedToGiftGroup;
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
	
	/**
	 * @return the giftGroupIsAdded
	 */
	public boolean getGiftGroupIsAdded() {
		return giftGroupIsAdded;
	}
	
	/**
	 * @param giftGroupIsAdded the giftGroupIsAdded to set
	 */
	public void setGiftGroupIsAdded(boolean giftGroupIsAdded) {
		this.giftGroupIsAdded = giftGroupIsAdded;
	}
	
	/**
	 * @return the giftGroupIsUpdated
	 */
	public boolean getGiftGroupIsUpdated() {
		return giftGroupIsUpdated;
	}
	
	/**
	 * @param giftGroupIsUpdated the giftGroupIsUpdated to set
	 */
	public void setGiftGroupIsUpdated(boolean giftGroupIsUpdated) {
		this.giftGroupIsUpdated = giftGroupIsUpdated;
	}
	
	/**
	 * @return the giftGroupIsDeleted
	 */
	public boolean getGiftGroupIsDeleted() {
		return giftGroupIsDeleted;
	}
	
	/**
	 * @param giftGroupIsDeleted the giftGroupIsDeleted to set
	 */
	public void setGiftGroupIsDeleted(boolean giftGroupIsDeleted) {
		this.giftGroupIsDeleted = giftGroupIsDeleted;
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
	
	
	/**
	 * @return the personsAreAddedToGiftGroup
	 */
	public boolean getPersonsAreAddedToGiftGroup() {
		return personsAreAddedToGiftGroup;
	}

	/**
	 * @param personsAreAddedToGiftGroup the personsAreAddedToGiftGroup to set
	 */
	public void setPersonsAreAddedToGiftGroup(boolean personsAreAddedToGiftGroup) {
		this.personsAreAddedToGiftGroup = personsAreAddedToGiftGroup;
	}

	public void setPersonsInGiftGroup(List<Integer> persons) {
		this.personsInGiftGroup = persons;
	}
	
	public List<Integer> getPersonsInGiftGroup() {
		return this.personsInGiftGroup;
	}

}
