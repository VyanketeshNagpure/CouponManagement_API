package com.monkcommerse.couponManagement.DTO;

import java.util.List;

public class UpdatedCart {
	
	private List<ProductWithDiscount> items;
	private Float total_price;
	private Float total_discount;
	private Float final_price;
	
	public List<ProductWithDiscount> getItems() {
		return items;
	}
	public void setItems(List<ProductWithDiscount> items) {
		this.items = items;
	}
	public Float getTotal_price() {
		return total_price;
	}

	public void setTotal_price(Float total_price) {
		this.total_price = total_price;
	}
	public Float getTotal_discount() {
		return total_discount;
	}
	public void setTotal_discount(Float total_discount) {
		this.total_discount = total_discount;
	}
	public Float getFinal_price() {
		return final_price;
	}
	public void setFinal_price(Float final_price) {
		this.final_price = final_price;
	}
	
	
	
	

}
