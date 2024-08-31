package com.monkcommerse.couponManagement.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.monkcommerse.couponManagement.DTO.Cart;
import com.monkcommerse.couponManagement.DTO.Discount;
import com.monkcommerse.couponManagement.DTO.ProductWithDiscount;
import com.monkcommerse.couponManagement.DTO.Products;
import com.monkcommerse.couponManagement.DTO.UpdatedCart;
import com.monkcommerse.couponManagement.entities.Coupons;
import com.monkcommerse.couponManagement.repositories.CouponRepository;

@Service
public class CouponManagementService {

	@Autowired
	CouponRepository couponRepository;

	public List<Coupons> getAllCoupons() {

		List<Coupons> couponsList = couponRepository.findAll();
		return couponsList;

	}

	public Coupons getCoupon(Integer couponId) {
		Coupons coupon = couponRepository.findByCouponID(couponId);
		return coupon;
	}

	public Coupons saveCouponDetails(Coupons payload) {

		if (payload.getDetails().containsKey("discount")) {
			couponRepository.save(payload);
		}

		return payload;

	}

	public List<Discount> getAllApplicableCoupons(Cart cart) {
		List<Discount> applicableCoupons = new ArrayList<>();
		List<Coupons> allCoupons = getAllCoupons();

		for (Coupons coupon : allCoupons) {
			if (coupon.getCouponType().equalsIgnoreCase("cart-wise")) {
				Discount cartWiseDiscount = calculateCartWiseDiscount(cart, coupon);
				if (cartWiseDiscount != null)
					applicableCoupons.add(cartWiseDiscount);
			}
			if (coupon.getCouponType().equalsIgnoreCase("product-wise")) {
				Discount productWiseDiscount = calculateProductWiseDiscount(cart, coupon);
				if (productWiseDiscount != null)
					applicableCoupons.add(productWiseDiscount);
			}
		}
		return applicableCoupons;
	}

	private Discount calculateCartWiseDiscount(Cart cart, Coupons coupon) {
		Discount applicableDiscount = new Discount();
		List<Products> items = cart.getItems();
		Integer threshold = coupon.getDetails().get("threshold");
		Integer discountPercent = coupon.getDetails().get("discount");
		Float price = 0f;
		
		
		for (Products item : items) {
			price += item.getPrice() * item.getQuantity();
		}
		Float discountedPrice = (price * discountPercent) / 100;

		if (price > threshold) {
			applicableDiscount.setCouponId(coupon.getCouponID());
			applicableDiscount.setType(coupon.getCouponType());
			applicableDiscount.setDiscount(discountedPrice);
			applicableDiscount.setTotalPrice(price);
			applicableDiscount.setFinalPrice(price - discountedPrice);
		}

		return applicableDiscount;

	}

	private Discount calculateProductWiseDiscount(Cart cart, Coupons coupon) {

		Discount applicableDiscount = new Discount();
		List<Products> items = cart.getItems();
		Integer productId = coupon.getDetails().get("product_id");
		Integer discount = coupon.getDetails().get("discount");
		
		
		for (Products item : items) {
			if (item.getProduct_id() == productId) {
				applicableDiscount.setCouponId(coupon.getCouponID());
				applicableDiscount.setType(coupon.getCouponType());
				applicableDiscount.setDiscount((float) discount);
				applicableDiscount.setTotalPrice((float)item.getPrice());
				applicableDiscount.setFinalPrice((item.getPrice() - (float) discount ));
			}
		}
		

		return applicableDiscount;
	}

	public UpdatedCart applyCoupon(Integer couponId, Cart cart) {
		UpdatedCart updatedCart = new UpdatedCart();
		List<Products> items = cart.getItems();
		Coupons coupon = getCoupon(couponId);
		
		if(coupon.getCouponType().equalsIgnoreCase("cart-wise")){
			Discount cartWiseDiscount = calculateCartWiseDiscount(cart, coupon);
			updatedCart.setTotal_discount(cartWiseDiscount.getDiscount());
			updatedCart.setTotal_price(cartWiseDiscount.getTotalPrice());
			updatedCart.setFinal_price(cartWiseDiscount.getFinalPrice());
			List<ProductWithDiscount> itemsWithDiscount = new ArrayList<>();
			
			for(Products item : items) {
				ProductWithDiscount productWithDiscount = new ProductWithDiscount();
				productWithDiscount.setProduct_id(item.getProduct_id());
				productWithDiscount.setQuantity(item.getQuantity());
				productWithDiscount.setPrice(item.getPrice());
				
				itemsWithDiscount.add(productWithDiscount);
			}
			updatedCart.setItems(itemsWithDiscount);
		}
		
		if (coupon.getCouponType().equalsIgnoreCase("product-wise")) {
			Discount productWiseDiscount = calculateProductWiseDiscount(cart, coupon);
			
			List<ProductWithDiscount> itemsWithDiscount = new ArrayList<>();
			float totalDiscount = 0f;
			float totalPrice = 0f;
			
			for(Products item: items) {
				ProductWithDiscount productWithDiscount = new ProductWithDiscount();
				productWithDiscount.setProduct_id(item.getProduct_id());
				productWithDiscount.setQuantity(item.getQuantity());
				productWithDiscount.setPrice(item.getPrice());
				totalPrice += (item.getPrice() * item.getQuantity());
				
				if(item.getProduct_id() == coupon.getDetails().get("product_id")) {
					productWithDiscount.setTotalDiscount(productWiseDiscount.getDiscount());
					totalDiscount += productWiseDiscount.getDiscount();
				}
				itemsWithDiscount.add(productWithDiscount);
			}
			updatedCart.setItems(itemsWithDiscount);
			updatedCart.setTotal_discount(totalDiscount);
			updatedCart.setTotal_price(totalPrice);
			updatedCart.setFinal_price(totalPrice - totalDiscount);
		}
		return updatedCart;
	}

}
