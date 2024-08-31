package com.monkcommerse.couponManagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.monkcommerse.couponManagement.entities.Coupons;

public interface CouponRepository extends JpaRepository<Coupons, Integer>{
	
	Coupons findByCouponID(Integer couponID);
}
