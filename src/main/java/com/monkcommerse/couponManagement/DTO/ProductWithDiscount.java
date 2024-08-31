package com.monkcommerse.couponManagement.DTO;

public class ProductWithDiscount extends Products {
	
	private float total_discount = 0;

	public ProductWithDiscount() {
		super();
	}

	public ProductWithDiscount(Integer product_id, Integer quantity, Integer price,Float total_discount) {
		super(product_id, quantity, price);
		this.total_discount = total_discount != null ? total_discount : 0;
	}

	public float getTotalDiscount() {
		return total_discount;
	}

	public void setTotalDiscount(float total_discount) {
		this.total_discount = total_discount;
	}
	
	
	
	

}
