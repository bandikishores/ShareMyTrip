package com.bandi.sharemytrip.data;

import java.util.Date;

import com.bandi.misc.Misc;

public class PaymentSummary 
{
	private Integer id; 
	private Integer expenditureId;
	private String paidForFriendName;
	private String createdDttm;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getExpenditureId() {
		return expenditureId;
	}
	public void setExpenditureId(Integer expenditureId) {
		this.expenditureId = expenditureId;
	}
	public String getPaidForFriendName() {
		return paidForFriendName;
	}
	public void setPaidForFriendName(String paidForFriendName) {
		this.paidForFriendName = paidForFriendName;
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
