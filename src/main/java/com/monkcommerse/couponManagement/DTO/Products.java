package com.monkcommerse.couponManagement.DTO;

public class Products {
	
	private Integer product_id;
	
	private Integer quantity;
	
	private Integer price;


	public Products() {
		super();
	}


	public Products(Integer product_id, Integer quantity, Integer price) {
		super();
		this.product_id = product_id;
		this.quantity = quantity;
		this.price = price;
	}


	public Integer getProduct_id() {
		return product_id;
	}


	public void setProduct_id(Integer product_id) {
		this.product_id = product_id;
	}


	public Integer getQuantity() {
		return quantity;
	}


	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}


	public Integer getPrice() {
		return price;
	}


	public void setPrice(Integer price) {
		this.price = price;
	}

	

	
	
	
	

}
