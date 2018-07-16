package com.MPS.MessageTypes;

import com.MPS.ProductDetails.Product;

public class Message {

	Product product=null;
	long price=0;
	public Message(){}
	public Message(Product product, long price) {
		this.product=product;
		this.price=price;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public long getPrice() {
		return price;
	}
	public void setPrice(long price) {
		this.price = price;
	}
	
	

}
