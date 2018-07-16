package com.MPS.MessageTypes;

import com.MPS.ProductDetails.Product;

public class Type3 extends Message{

	Enum operation;
		public Type3() {}
		
		public Type3(Product product, long price, Enum operation) {
		
		super(product, price);
		this.operation=operation;
	}
		public Enum getOperation() {
			return operation;
		}
		public void setOperation(Enum operation) {
			this.operation = operation;
		}

}
