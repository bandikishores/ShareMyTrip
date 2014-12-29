package com.bandi.sharemytrip.data;

import java.util.Date;

import com.bandi.misc.Misc;

public class Trip 
{
	private String id; /* Trip Name */
	private double estTripCost;
	private String description;
	private String createdDttm;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/* get Trip Name */
	public String getId() {
		return id;
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

	/* set Trip Name */
	public void setId(String id) {
		this.id = id;
	}

	public double getEstTripCost() {
		return estTripCost;
	}

	public void setEstTripCost(double cost) {
		this.estTripCost = cost;
	}

	// Will be used by the ArrayAdapter in the ListView
	@Override
	public String toString() {
		return id;
	}

	public String getName() {
		return getId();
	}

	public void setName(String tripName) {
		setId(tripName);
	}
}
