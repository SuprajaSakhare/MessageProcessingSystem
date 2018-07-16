package com.MPS.Helpers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import com.MPS.MessageTypes.Message;
import com.MPS.MessageTypes.Type1;
import com.MPS.MessageTypes.Type2;
import com.MPS.MessageTypes.Type3;
import com.MPS.OperationDetails.Operation;
import com.MPS.ProductDetails.Product;

public class ProcessHelper {

	static int i=0;
	public String parse(String next, List<Type1> type1, List<Type2> type2, List<Type3> type3) {
		String str=next;
		String[] temp=str.split(" ");
		try{		
			   if(str.contains("at") && !str.contains("of")){
				Type1 message=new Type1();
				message.setProduct(Product.valueOf(temp[0].toUpperCase()));
				message.setPrice(Long.parseLong(temp[2].substring(0, temp[2].length()-1)));
				type1.add(message);
				return "Type1";
			}
			else if(str.contains("at")){
				Type2 message=new Type2();
				message.setProduct(Product.valueOf(temp[3].toUpperCase()));
				message.setPrice(Long.parseLong(temp[5].substring(0, temp[5].length()-1)));
				message.setQuantity(Integer.parseInt(temp[0]));
				type2.add(message);
				return "Type2";
			}
			else{
				Type3 message=new Type3();
				message.setProduct(Product.valueOf(temp[2].toUpperCase()));
				message.setOperation(Operation.valueOf(temp[0].toUpperCase()));

				Operation op=Operation.valueOf(temp[0].toUpperCase());
				if(op.MUL == op){
					message.setPrice(Long.parseLong(temp[1]));
				}else{
					message.setPrice(Long.parseLong(temp[1].substring(0, temp[1].length()-1)));
				}
				type3.add(message);
				return "Type3";
			}
		}catch(IllegalArgumentException e)
		{
			System.out.println("Illegal Argument Exception");
			temp=null;
		}
		return null;

	}
	public void processMessage(String type, Message input, Map<Product, Message> processedMessages) {
		Message temp=processedMessages.get(input.getProduct());
		if(type.equals("Type1")){
			Type1 msg1=(Type1) input;
			Type1 newMessage=new Type1();
			if(temp==null){
				processedMessages.put(msg1.getProduct(),msg1);

			}else{
				Long price=temp.getPrice()+msg1.getPrice();
				newMessage.setPrice(price);
				newMessage.setProduct(msg1.getProduct());
				processedMessages.put(newMessage.getProduct(), newMessage);
			}
		}
		else{
			Type2 msg2=(Type2) input;
			Type2 newMessage=new Type2();
			if(temp==null){
				newMessage.setPrice((msg2.getPrice())*(msg2.getQuantity()));
				newMessage.setProduct(msg2.getProduct());
				newMessage.setQuantity(msg2.getQuantity());
				processedMessages.put(msg2.getProduct(),newMessage);
			}else{
				long price=temp.getPrice()+((msg2.getPrice())*(msg2.getQuantity()));
				newMessage.setPrice(price);
				newMessage.setProduct(msg2.getProduct());
				newMessage.setQuantity(msg2.getQuantity());
				processedMessages.put(newMessage.getProduct(), newMessage);
				price=0;
			}
		}
		temp=null;

	}

	public void adjustMessage(String type, Message input, Map<Product, Message> processedMessages, List<Type1> type1 ){

		long totalPrice=processedMessages.get(input.getProduct()).getPrice();
		Type3 type3=(Type3) input;
		long price=0;
		int quantity;

		Iterator<Type1> i=type1.listIterator();
		while(i.hasNext()){
			Type1 a=(Type1) i.next();
			if(a.getProduct().equals(input.getProduct())){

				price=a.getPrice();
				break;
			}

		}
		if(type.equals("Type3")){
			if(type1.isEmpty()) {
				System.out.println("There are no products to change price");
			}
			quantity=(int) (totalPrice/price);
			if(type3.getOperation().equals(Operation.ADD)){
			}
			else if(type3.getOperation().equals(Operation.SUB)){
			}
			else if(type3.getOperation().equals(Operation.MUL)){
			}


		}

	}
	public void log(List<Type1> type1, List<Type2> type2, List<Type3> type3, Map<Product, Message> processedMessages, String fileName) {

		int appleCount=0;
		int bananaCount=0;
		int guavaCount=0;
		int mangoCount=0;


		Iterator<Type1> i1=type1.iterator();
		Iterator<Type2> i2=type2.iterator();

		File directory = new File("./");
		String path=directory.getAbsolutePath().replace("\\", "/");
		path=path.substring(0,path.length()-2);
		

		while(i1.hasNext()){
			Type1 type=(Type1)i1.next();
			if(type.getProduct().equals(Product.APPLE)){
				appleCount++;
			}
			else if(type.getProduct().equals(Product.BANANA)){
				bananaCount++;
			}
			else if(type.getProduct().equals(Product.GUAVA)){
				guavaCount++;
			}
			else if(type.getProduct().equals(Product.MANGO)){
				mangoCount++;
			}
		}
		while(i2.hasNext()){
			Type2 type=(Type2)i2.next();
			if(type.getProduct().equals(Product.APPLE)){
				appleCount=appleCount+type.getQuantity();
			}
			else if(type.getProduct().equals(Product.BANANA)){
				bananaCount=bananaCount+type.getQuantity();
			}
			else if(type.getProduct().equals(Product.GUAVA)){
				guavaCount=guavaCount+type.getQuantity();
			}
			else if(type.getProduct().equals(Product.MANGO)){
				mangoCount=mangoCount+type.getQuantity();
			}

		}


		try {
			PrintWriter pw=new PrintWriter(new FileOutputStream(new File(path+fileName),true));
			pw.println();
			pw.write("-------------------------------------------------");
			pw.println();
			pw.append("APPLE:        "+appleCount+"   			 "+processedMessages.get(Product.APPLE).getPrice());
			System.out.println("APPLE:        "+appleCount+"   		"+processedMessages.get(Product.APPLE).getPrice());
			pw.println();
			pw.append("BANANA:       "+bananaCount+"   			 "+processedMessages.get(Product.BANANA).getPrice());
			System.out.println("BANANA:       "+bananaCount+"   		"+processedMessages.get(Product.BANANA).getPrice());
			pw.println();
			pw.append("GUAVA:        "+guavaCount+"   			 "+processedMessages.get(Product.GUAVA).getPrice());
			System.out.println("GUAVA:        "+guavaCount+"   		"+processedMessages.get(Product.GUAVA).getPrice());
			pw.println();
			pw.append("MANGO:        "+mangoCount+"   			 "+processedMessages.get(Product.MANGO).getPrice());
			System.out.println("MANGO:        "+mangoCount+"   		"+processedMessages.get(Product.MANGO).getPrice());
			pw.println();
			
			
			pw.close();


		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



	}


}




