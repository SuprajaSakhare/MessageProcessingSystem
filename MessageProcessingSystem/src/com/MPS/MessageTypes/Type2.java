package com.MPS.MessageTypes;

import com.MPS.ProductDetails.Product;

public class Type2 extends Message{

	int quantity;
	
	public Type2() {}
	
	public Type2(Product product, long price, int quantity) {
		
		super(product, price);
		this.quantity=quantity;
	}
	
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	
}
