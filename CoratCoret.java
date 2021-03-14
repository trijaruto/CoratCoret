package art.code.anggwa.importer.processor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;
import au.com.bytecode.opencsv.bean.CsvToBean;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class CoratCoret {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String[] defaultExtension = { "txt" };
		char defaultDelim = '|';		
		File file = new File("d:\\test3.txt");
		
		
		if(file.exists()){
			List<CoratCoretBean> listCoratCoretBeans = extractCsv(file, defaultDelim);
			
			for(CoratCoretBean ccb : listCoratCoretBeans){
				System.out.println("Time : "+ccb.getTime());
				System.out.println("Code : "+ccb.getCode());
				System.out.println("Price : "+ccb.getPrice());
			}		
		}	
	}
	
	
	private static List<CoratCoretBean> extractCsv(File file, char sDelim) {
		List<CoratCoretBean> l = Collections.emptyList();
		try {
			CSVReader reader = new CSVReader(new FileReader(file), sDelim);
			ColumnPositionMappingStrategy<CoratCoretBean> strat = new ColumnPositionMappingStrategy<CoratCoretBean>();
			strat.setType(CoratCoretBean.class);
			String[] columns = new String[] { "Time", "Code", "Price" };
			strat.setColumnMapping(columns);

			CsvToBean<CoratCoretBean> csv = new CsvToBean<CoratCoretBean>();
			l = csv.parse(strat, reader);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return l;
	}
	
	
	public void produceToKafka(List<CoratCoretBean> listCoratCoretBeans ){	
	      String topicName = "bitstocktest";
	      
	      // create instance for properties to access producer configs   
	      Properties props = new Properties();	      	   
	      props.put("bootstrap.servers", "localhost:9092");	      	   
	      props.put("acks", "all");	      
	      props.put("retries", 0);	      
	      props.put("batch.size", 16384);	      
	      props.put("linger.ms", 1);	     
	      props.put("buffer.memory", 33554432);
	      
	      props.put("key.serializer", "org.apache.kafka.common.serializa-tion.StringSerializer");
	         
	      props.put("value.serializer", "org.apache.kafka.common.serializa-tion.StringSerializer");
	      
	      Producer<String, String> producer = new KafkaProducer<String, String>(props);
	            
	      for(int i = 0; i < listCoratCoretBeans.size(); i++)
	         producer.send(new ProducerRecord<String, String>(topicName, Integer.toString(i), Integer.toString(i)));
	         System.out.println(“Message sent successfully”);
	         producer.close();
	   }
	}

}
