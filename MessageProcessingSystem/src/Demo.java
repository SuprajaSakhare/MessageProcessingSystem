import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Stream;

import com.MPS.Helpers.ProcessHelper;
import com.MPS.MessageTypes.Message;
import com.MPS.MessageTypes.Type1;
import com.MPS.MessageTypes.Type2;
import com.MPS.MessageTypes.Type3;
import com.MPS.ProductDetails.Product;

public class Demo {

	static int count=0;
	public static void main(String[] args) {
		File directory = new File("./");
		String inputPath,projectPropertiesPath;
		String path=directory.getAbsolutePath().replace("\\", "/");
		
		path=path.substring(0,path.length()-2);
		inputPath=path+"/resources/InputNotifications.txt";
		
		projectPropertiesPath=path+"/resources/Project.properties";
		
		try (Stream<String> stream = Files.lines(Paths.get(inputPath))) {
			
			List<Type1> type1=new ArrayList<Type1>();
			List<Type2> type2=new ArrayList<Type2>();
			List<Type3> type3=new ArrayList<Type3>();

			Map<Product,Message> processedMessages=new HashMap<Product,Message>();
			ProcessHelper helper = new ProcessHelper();
			
		
			FileReader reader=new FileReader(projectPropertiesPath);  
		    Properties p=new Properties();  
		    p.load(reader);  
			
			String log50=p.getProperty("log50");
			String log10=p.getProperty("log10");
		
			System.out.println("Product	  Total Sales   	Amount\n");
			
			Iterator<String> i=stream.iterator();
			
			while(i.hasNext())
			{
				
				count++;
				String s=(String) i.next();
				if(s.isEmpty()) {
					System.out.println("No input found. Checking Nest Message..");
					s=(String) i.next();
				}
					String type=helper.parse(s,type1,type2,type3);
					
										
				while(type!=null && !type.isEmpty()){

					if((count%50)!=0){
						
							if(type.equals("Type1")){
							helper.processMessage("Type1",type1.get(type1.size()-1),processedMessages);
							if(count%10 == 0){
								System.out.println("\nLogging first "+count+" transactions." );
								helper.log(type1,type2,type3,processedMessages,log10);
							
							} 
							break;
						}
						else if(type.equals("Type2")){
							helper.processMessage("Type2",type2.get(type2.size()-1),processedMessages);
							if(count%10 == 0){
								System.out.println("\nLogging first "+count+" transactions.");
								helper.log(type1,type2,type3,processedMessages,log10);
							}
							break;
						}
						else if(type.equals("Type3")){
							if(type1.isEmpty() || type2.isEmpty())
							{
								System.out.println("we can not proceed with this operation. Please add products to operate on!");
							}
							else {
							helper.adjustMessage("Type3", type3.get(type3.size()-1),processedMessages,type1);
							if(count%10 == 0){
								System.out.println("\nLogging first "+count+" transactions." );
								helper.log(type1,type2,type3,processedMessages,log10);
							}
							}
							break;
						}

					}
				
					
					else if(count%50 == 0){
						System.out.println("\nPausing application and logging for first"+count+" transactions.");
						helper.log(type1,type2,type3,processedMessages,log50);
						return;
					}

				}

			}
			
			
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}



