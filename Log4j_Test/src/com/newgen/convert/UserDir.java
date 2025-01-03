package com.newgen.convert;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.properties.PropertiesConfigurationBuilder;


public class UserDir {
	
	static Logger logger = LogManager.getLogger(UserDir.class);

	public static void main(String[] args) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		
		//Log4j log = new Log4j();
		settingLogFiles();
		
		String configPath = System.getProperty("user.dir") + File.separator + "conf" + File.separator +  "Config.properties";
		System.out.println("configPath :"+configPath);
		//FileWriter writer = new FileWriter(System.getProperty("user.dir")+  File.separator +    "Failed_PN_Files.txt", true);
		try(BufferedWriter writer = new BufferedWriter(new FileWriter(System.getProperty("user.dir")+  File.separator +    "Failed_PN_Files.txt", true))){
			  writer.write("Gurdip" + "\n"); // do something with the file we've opened
			  //writer.newLine();
			  writer.write("Rajiv");
			Properties property = new Properties();
			try {
	        	FileInputStream inputStream = new FileInputStream(configPath);
	        	property.load(inputStream);
	        	inputStream.close();
	        	 
	        	}catch(Exception e) {
	        		e.printStackTrace();
	        	} 
	    	
	        String GENERATE_DEBUG_LOGS = property.getProperty("GENERATE_DEBUG_LOGS");
	        logger.info("GENERATE_DEBUG_LOGS : "+GENERATE_DEBUG_LOGS);
	        String bucket = property.getProperty("bucket");
	        logger.info("Bucket : "+GENERATE_DEBUG_LOGS);
	        String ENDPOINT = property.getProperty("ENDPOINT");
	        logger.info("ENDPOINT : "+ENDPOINT);
	        String GENERATE_AND_USE_ENDPOINT = System.getenv("GENERATE_AND_USE_ENDPOINT");
	        String REGION = property.getProperty("REGION");
	        System.out.println("REGION : "+REGION);
	        String ROLE = property.getProperty("ROLE");
	        System.out.println("ROLE : "+ROLE);
	        String DESTINATION = property.getProperty("DESTINATION");
	        System.out.println("DESTINATION :"+DESTINATION);
	        String MEDIA_CONVERT_QUEUE = property.getProperty("MEDIA_CONVERT_QUEUE");
	        System.out.println("MEDIA_CONVERT_QUEUE : "+MEDIA_CONVERT_QUEUE);
	        String MEDIA_CONVERT_QUEUE_RESERVED = System.getenv("MEDIA_CONVERT_QUEUE_RESERVED");
	        String STRETCH_TO_OUTPUT = property.getProperty("STRETCH_TO_OUTPUT");
	        System.out.println("STRETCH_TO_OUTPUT : "+STRETCH_TO_OUTPUT);
	        String VIDEO_WIDTH = property.getProperty("VIDEO_WIDTH");
	        System.out.println("VIDEO_HEIGHT : "+VIDEO_WIDTH);
	        String VIDEO_HEIGHT = property.getProperty("VIDEO_HEIGHT");
	        System.out.println("VIDEO_HEIGHT : "+VIDEO_HEIGHT);
	        
	        boolean flag = validateMediaConvertProperties(property);
	        if(flag) {
	        	System.out.println("Success");
	        }
	        else {
	        	System.out.println("Failed");
	        }
	                   
	        
			}
			catch(IOException e){
			  // handle the exception
				e.printStackTrace();
			}
		
				
	}

	private static void settingLogFiles() {
		// TODO Auto-generated method stub

		 System.out.println("Inside settingLogFiles");
		 InputStream is = null;
		 try {
			 
			 String filePath = System.getProperty("user.dir")  + File.separator + "Properties" +  File.separator + "log4j2.properties";
			 System.out.println("Log filePath : "+filePath);
			 is = new BufferedInputStream(new FileInputStream(filePath));
			 
			 Properties ps = new Properties();
			 //ps.load(new FileInputStream(filePath));
			 ps.load(is);
			 //is.close();
			
			
			PropertiesConfigurationBuilder test = new PropertiesConfigurationBuilder();
			test.setRootProperties(ps);
			//logger.info("Success");
		 }catch(Exception e) {
			 e.printStackTrace();
		 }
	 
	}

	
	 
	private static boolean validateMediaConvertProperties(Properties property) {
		// TODO Auto-generated method stub
		if((property.getProperty("GENERATE_DEBUG_LOGS") != null && !property.getProperty("GENERATE_DEBUG_LOGS").isEmpty())
		 		   && (property.getProperty("bucket") != null && !property.getProperty("bucket").isEmpty())
		 		   && (property.getProperty("ENDPOINT") != null && !property.getProperty("ENDPOINT").isEmpty())
		 		   && (property.getProperty("REGION") != null && !property.getProperty("REGION").isEmpty())
		 		   && (property.getProperty("ROLE") != null && !property.getProperty("ROLE").isEmpty()) 
		 		   && (property.getProperty("DESTINATION") != null && !property.getProperty("DESTINATION").isEmpty())
		 		   && (property.getProperty("MEDIA_CONVERT_QUEUE") != null && !property.getProperty("MEDIA_CONVERT_QUEUE").isEmpty())
		 		   && (property.getProperty("STRETCH_TO_OUTPUT") != null && !property.getProperty("STRETCH_TO_OUTPUT").isEmpty())
		 		   && (property.getProperty("VIDEO_WIDTH") != null && !property.getProperty("VIDEO_WIDTH").isEmpty())
		 		   && (property.getProperty("VIDEO_HEIGHT") != null && !property.getProperty("VIDEO_HEIGHT").isEmpty())) {
			return true;
		}
		else {
			return false;
		}
		
	}
	
	 

}
