package Demo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class CodingTest {
	
	public static List<Stock> read (String[] strArr){		
		List<Stock> stock_list = new ArrayList<Stock>();		
		for(int i =0;i<strArr.length;i++){			
			String delimiter = ",";
			String line = null;
			String file_name = strArr[i];		
			BufferedReader br = null;
				try {
					br = new BufferedReader(new FileReader(file_name));
					} catch (FileNotFoundException e) {
					e.printStackTrace();
			}
		  try {
			  while((line = br.readLine()) != null){				
					String[] content = line.split(delimiter);					
					String[] attributes = {content[0],content[3],content[4],content[10]};
					Stock stock = createStock(attributes);
					List<Stock> list_stock = new ArrayList<Stock>();
					stock_list.add(stock);
					System.out.println();												
				}
			  br.close();		
	} catch (IOException e) {	
		e.printStackTrace();
	}	
	
		}	
		
	return stock_list;
}

	
	public static Stock createStock(String[] CSVdata){
		String symbol = CSVdata[0];
		String high = CSVdata[1];
		String low = CSVdata[2];
		String time_stamp = CSVdata[3];
		
		
		return new Stock(symbol,high,low,time_stamp);
	}

	public static void main(String[] args) throws FileNotFoundException {
		
		String file1 = "C:/Users/Hari/Desktop/Abi/iConnect/cm29JAN2020bhav.csv";
		String file2 = "C:/Users/Hari/Desktop/Abi/iConnect/cm30JAN2020bhav.csv";
		String file3 = "C:/Users/Hari/Desktop/Abi/iConnect/cm31JAN2020bhav.csv";
		
		String[] list = {file1,file2,file3};		
	    CodingTest.read(list);
	    
	    List<Stock> stocks = read(list);
	     
	   
	   for(int i=0;i<stocks.size();i++){
		   
		   System.out.print(stocks.get(i).getSymbol()+" "+stocks.get(i).getTimeStamp()+" ");
		   System.out.print(stocks.get(i).getDataRange());
		   System.out.println();
	   }
	  	   	   
	   File csvFile = new File("C:\\Users\\Hari\\Desktop\\Abi\\iConnect\\output.csv");
	   PrintWriter out = new PrintWriter(csvFile); 
	      
	   for(Stock s :stocks ){
	   	out.println(s.getSymbol()+" "+s.getTimeStamp()+" "+s.getDataRange());
	   }
	   
	    out.close();
	}
}


class Stock{
	
    String symbol;
	 String high;
	 String low;
	 String time_stamp;
		 
	public Stock(){
		this.symbol = null;
		this.high = null;
		this.low = null;
		this.time_stamp = null;
	}
	
	public Stock(String symbol,String high,String low,String time_stamp){
		this.symbol = symbol;
		this.high = high;
		this.low = low;
		this.time_stamp = time_stamp;
		
	}
	
	public String getSymbol(){
		return symbol;
	}
	 public String getHigh(){
		 return high;
	 }
	 public String getLow(){
		 return low;
	 }
	 public String getTimeStamp(){
		 return time_stamp;
	 }
	 
	 public double getDataRange(){
		 double value1 =0;
		 double value2 =0;
		 try{
		  value1 = Double.parseDouble(high);
		  value2 = Double.parseDouble(low);
		 }
		 catch(NumberFormatException ex){
			 System.out.println("Error in converting");
		 }
		 
		double data_range = value1 - value2;
		return data_range;
	 }
	@Override
	public String toString(){
		return this.symbol +" "+this.high+" "+this.low+" "+this.time_stamp+" ";
		
	}
}	
