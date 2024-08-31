package com.monkcommerse.couponManagement.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.monkcommerse.couponManagement.DTO.Cart;
import com.monkcommerse.couponManagement.DTO.Discount;
import com.monkcommerse.couponManagement.DTO.UpdatedCart;
import com.monkcommerse.couponManagement.entities.Coupons;
import com.monkcommerse.couponManagement.service.CouponManagementService;

@RestController
public class CouponManagementController {

	@Autowired
	CouponManagementService postService;

	@PostMapping("coupons")
	public ResponseEntity<Coupons> postCouponDetails(@RequestBody Coupons payload) {

		postService.saveCouponDetails(payload);

		return ResponseEntity.status(HttpStatus.CREATED).body(payload);

	}

	@GetMapping("coupons/{couponId}")
	public ResponseEntity<Coupons> getCouponById(@PathVariable Integer couponId) {
		Coupons coupon = postService.getCoupon(couponId);
		return ResponseEntity.status(HttpStatus.CREATED).body(coupon);
	}

	@GetMapping("coupons")
	public ResponseEntity<List<Coupons>> getALlCoupons() {
		List<Coupons> coupons = postService.getAllCoupons();

		return ResponseEntity.status(HttpStatus.OK).body(coupons);
	}

	@PostMapping("/applicable-coupons")
	public ResponseEntity<List<Discount>> getAllApplicableCoupons(@RequestBody Cart cart) {

		List<Discount> allApplicableCoupons = postService.getAllApplicableCoupons(cart);

		return ResponseEntity.status(HttpStatus.OK).body(allApplicableCoupons);
	}

	@PostMapping("/applicable-coupons/{couponId}")
	public ResponseEntity<UpdatedCart> applyCouponById(@PathVariable Integer couponId, @RequestBody Cart cart) {

		UpdatedCart updatedCart =  postService.applyCoupon(couponId, cart);
		return  ResponseEntity.status(HttpStatus.OK).body(updatedCart);
	}
}
