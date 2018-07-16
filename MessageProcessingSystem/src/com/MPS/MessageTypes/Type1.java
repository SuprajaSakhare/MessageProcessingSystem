package com.MPS.MessageTypes;

import com.MPS.ProductDetails.Product;

public class Type1 extends Message {

	public Type1() {}
	public Type1(Product product, long price){
		
		super(product,price);
	}

}
