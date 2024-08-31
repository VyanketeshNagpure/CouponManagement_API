package com.monkcommerse.couponManagement.entities;

import java.util.Map;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Coupons {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer couponID;
	
	private String couponType;
	
	@ElementCollection
	private Map<String, Integer> details;

	public Coupons(Integer couponID, String couponType, Map<String, Integer> details) {
		super();
		this.couponID = couponID;
		this.couponType = couponType;
		this.details = details;
	}

	public Coupons() {
		super();
	}

	public Integer getCouponID() {
		return couponID;
	}

	public void setCouponID(Integer couponID) {
		this.couponID = couponID;
	}

	public String getCouponType() {
		return couponType;
	}

	public void setCouponType(String couponType) {
		this.couponType = couponType;
	}

	public Map<String, Integer> getDetails() {
		return details;
	}

	public void setDetails(Map<String, Integer> details) {
		this.details = details;
	}
	
	
	
	
}
