# CouponManagement_API


### Scenarios done :
 1. Applicable-coupons   
    First get all the coupons from database using a for loop check which type of cupon is it and call the funtion for respective calculation and add the Discount information to List< Discounts> returns this list as response.

    ```
    for (Coupons coupon : allCoupons) {
			if (coupon.getCouponType().equalsIgnoreCase("cart-wise")) {  			  // checks coupon type
				Discount cartWiseDiscount = calculateCartWiseDiscount(cart, coupon);  // calls calculation stategy
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
    ```


    1. Cart-wise Coupons    
        Has access to Cart Items so simply iterated through and calculated total price if it exceeds threshold applies a discount of x%. Also calculates finalPrice(totalPrice - discount)

    2. Product-wise Coupons     
        has cart items as parameter calls getCouponById to lookup discount from coupon displays final price of single product(totalPrice - discount)

 2. Apply-coupon/{id}:      
    first gets the coupon to apply(findByCouponID), checks for type and calls related method to calculate discount explained as above only applies this coupon, rest prices are calculated as normal deductes the discounted amount from total amount. Keeps track of discounted amount for individial items, totalPrice, total discount and finalAmount.


 3. Post coupon:  
    gets it as Coupon object which has couponId, couponType and @ElementCollection Map<String, Integer> details which stores it in a different table as pairs of keys and values. Uses repository.save(entity) method to save it in database. 

 4. Get all Coupons  
    self explanatory, calls a method in service class that has access to repository which calls findAll method and returns List< Coupons>

 5. Get Coupons by id    
    similarly calls a method in service class that calls findByCouponID method which returns Coupon object.

###  Unimplemented Cases:
6.  BxGy Coupons       
    ex. 1. buy 3(x) product1 get product2 (repetation 2)   
    ex2. buy product1 and product2 get product3 (repetation 2)

    to implment both types of examples store it in a map so we can access it by keys, if both keys exists then we check their quantity if getproduct * repetation is more than qunatity(x* rep > quantity ) simply add repetation amount(2 product2's).
    calculate discount(2 * price of product2) deduct it from totalCart amount return the updatedCart object.

7.  Put Coupons(coupons/{couponId})     
    To implement this get a @RequestBody Coupons  payload search for existing coupon(findByCouponId) change the attributes that needs to be changed using setters and save the entity.


### Limitations:

1. In order to add any new type of coupon **CouponManagementService** needs to be updated to handle the calculation strategy for the given coupon, current implementation uses simple if statements to check what current coupon is and calls the designated function to calculate the discounts based on given strategy. 

    > To fix this some king of interface is needed to define a basic strategy then each new strategy(i.e. new type of coupon) would be picked to with the help of a function that keeps track of all the coupon types and respective calculation strategy. 

2. The current Coupon entity consists of a **map<String, Integer>** to track details of coupons this leads to not being able to implement BxGy scenario as it has deeper nested objects to specify which products you have to buy to get other products for free,

    > For this to resolve I would have to implement a **map<String, Object>** 

     

### Assumptions:

```
POST /applicable-coupons:

{ "cart":{  
    "items": [  
        {"product_id": 1, "quantity": 6, "price": 50},   
        {"product_id": 2, "quantity": 3, "price": 30},     
        {"product_id": 3, "quantity": 2, "price": 25}   
    ]
}}

Response:
{
"applicable_coupons": [
        {
            "coupon_id": 1,
            "type": "cart-wise",
            "discount": 30       // 10% of 300
        },
        {
            "coupon_id": 3,
            "type": "bxgy",
            "discount": 50      // Buy 6 of Product X or Y, Get 2 of Product Z Free (2x $25)
        }
    ]
}


```

1. In above example for **cart-wise** coupon total amount is **written as 300** however it **amounts to 440** (50 *6 + 30 *3 + 25 *2) discount **should be 44**(10% of 440)

2. similarly while appling coupons only one of them is applied and price is calculated (+= item*quantity) and discount is deducted from total cart price(440 - 44 for above example).

3. For "cart-wise" discount calculated as (totalprice*discount)/100 and for "product_wise" discount calculated as (price - discount) so **5$ off insted of 5% off.**