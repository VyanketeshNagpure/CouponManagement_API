package com.monkcommerse.couponManagement.DTO;

import java.util.List;

public class Cart {
	
	private List<Products> items;
	
	

	public Cart() {
		super();
	}

	public Cart(List<Products> items) {
		super();
		this.items = items;
	}

	public List<Products> getItems() {
		return items;
	}

	public void setItems(List<Products> items) {
		this.items = items;
	}
	
	

}
