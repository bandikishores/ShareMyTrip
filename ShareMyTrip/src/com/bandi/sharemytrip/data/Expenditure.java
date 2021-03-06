package com.bandi.sharemytrip.data;

import java.util.Date;

import com.bandi.misc.Misc;

public class Expenditure 
{
	private Integer id; /* Trip Name */
	private Integer placeId;
	private String friendName;
	private double cost;
	private String reason;
	private String createdDttm;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getPlaceId() {
		return placeId;
	}
	public void setPlaceId(Integer placeId) {
		this.placeId = placeId;
	}
	public String getFriendName() {
		return friendName;
	}
	public void setFriendName(String friendName) {
		this.friendName = friendName;
	}
	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}


	public String getCreatedDttm() {
		return createdDttm;
	}

	public void setCreatedDttm(String createdDttm) {
		this.createdDttm = createdDttm;
	}

	public Date getCreatedDttmAsDate() {
		return Misc.getDate(getCreatedDttm());
	}

	public void setCreatedDttm(Date createdDttm) {
		setCreatedDttm(Misc.getDate(createdDttm));
	}

	
}
