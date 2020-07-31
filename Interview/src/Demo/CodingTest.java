package Demo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class CodingTest {
	
	public static List<Date> dateList = new LinkedList<Date>();
	
	public static Map<String,List<Stock>> read(String[] strArr) throws ParseException{
		Map<String,List<Stock>> mplst =new HashMap<String,List<Stock>>();		
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
			  int count=0;			 
			  line=br.readLine();
			  while((line = br.readLine()) != null){				
					String[] content = line.split(delimiter);
					if(count == 0)
					{
						 dateList.add(new SimpleDateFormat("dd-MMM-yyyy").parse(content[10]));
						  count++;
					}
					
					String[] attributes = {content[0],content[3],content[4],content[10]};
					Stock stock = createStock(attributes);
					
					List<Stock> list_stock = new ArrayList<Stock>();
					if(mplst.containsKey(content[0])){
						list_stock = mplst.get(content[0]);
						list_stock.add(stock);
						mplst.put(content[0],list_stock);
					}
					else{
						list_stock.add(stock);
						mplst.put(content[0], list_stock);
					}
					
														
				}
			  br.close();		
	} catch (IOException e) {	
		e.printStackTrace();
	}	
	
		}	
			
	return mplst;
}

	public static Stock createStock(String[] CSVdata){
		String symbol = CSVdata[0];
		String high = CSVdata[1];
		String low = CSVdata[2];
		String time_stamp = CSVdata[3];
				
		return new Stock(symbol,high,low,time_stamp);
	}

	public static void main(String[] args) throws ParseException, IOException {
		
			
		String file1 = "C:/Users/Hari/Desktop/Abi/iConnect/cm29JAN2020bhav.csv";
		String file2 = "C:/Users/Hari/Desktop/Abi/iConnect/cm30JAN2020bhav.csv";
		String file3 = "C:/Users/Hari/Desktop/Abi/iConnect/cm31JAN2020bhav.csv";
		
		String[] list = {file1,file2,file3};	
		 File csvFile = new File("C:\\Users\\Hari\\Desktop\\Abi\\iConnect\\output.csv"); 
		 PrintWriter out = new PrintWriter(csvFile); 
		 out.print("Symbol");
		 out.print("Time_stamp");
		 out.print("Data range");
		 out.print("Running Average");
		 out.println();
		 
		Map<String,List<Stock>> stocksMap = read(list);     
		Set<String> stockList =stocksMap.keySet();
		Double runninAvergae = 0.0;
		List<Date> sortedList = dateList.stream().sorted().collect(Collectors.toList());
		for(String stockName : stockList )
		{
			runninAvergae = 0.0;
			System.out.println("StockName "+ stockName);
			
			List<Stock> lst = stocksMap.get(stockName);
			for(int j=0;j<sortedList.size();j++)
			{
			String dateFormat = new SimpleDateFormat("dd-MMM-yyyy").format(sortedList.get(j));
			
			for(int i=0;i<lst.size();i++)
			{
				Stock stk= lst.get(i);
				System.out.println(stk.getTimeStamp()+ "Date "+dateFormat);
				if(stk.getTimeStamp().equalsIgnoreCase(dateFormat))
				{
				
				  if(j==0)
				  {
				  runninAvergae=stk.getDataRange();
				  System.out.print(runninAvergae);
				  }
				  else
				  {
					  
					  runninAvergae =(runninAvergae+stk.getDataRange())/(j+1);
					
				  }
				  out.println(stk.getSymbol()+","+stk.getTimeStamp()+","+stk.getDataRange()+","+ runninAvergae);
				}
			}
			
			}
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
		return this.symbol;
	}
	 public String getHigh(){
		 return this.high;
	 }
	 public String getLow(){
		 return this.low;
	 }
	 public String getTimeStamp(){
		 return this.time_stamp;
	 }
	 
	 public double getDataRange(){
		 double value1 =0;
		 double value2 =0;
		 try{
		  value1 = Double.parseDouble(this.high);
		  value2 = Double.parseDouble(this.low);
		 }
		 catch(NumberFormatException ex){
			 System.out.println("Error in converting");
			 System.out.println(this.high + "High : Low "+this.low );
		 }
		 
		double data_range = value1 - value2;
		return data_range;
	 }
	@Override
	public String toString(){
		return this.symbol +" "+this.high+" "+this.low+" "+this.time_stamp+" ";
		
	}
}