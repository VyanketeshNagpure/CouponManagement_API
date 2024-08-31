package com.monkcommerse.couponManagement.DTO;

public class Discount {
	
	private Integer couponId;
	private String type;
	private Float discount;
	private Float totalPrice;
	private Float finalPrice;
	
	
	
	public Discount() {
		super();
	}
	
	public Discount(Integer couponId, String type, Float discount, Float totalPrice, Float finalPrice) {
		super();
		this.couponId = couponId;
		this.type = type;
		this.discount = discount;
		this.totalPrice = totalPrice;
		this.finalPrice = finalPrice;
	}

	public Integer getCouponId() {
		return couponId;
	}
	public void setCouponId(Integer couponId) {
		this.couponId = couponId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Float getDiscount() {
		return discount;
	}
	public void setDiscount(Float discount) {
		this.discount = discount;
	}

	public Float getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Float totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Float getFinalPrice() {
		return finalPrice;
	}

	public void setFinalPrice(Float finalPrice) {
		this.finalPrice = finalPrice;
	}
	
	
	

}
