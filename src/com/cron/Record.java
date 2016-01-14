package com.cron;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;

public class Record {
	final static Logger logger = Logger.getLogger(Record.class);
	static void enterRecord(int statusCode, String date, String url, String filePath, String server) throws IOException{
		
		JSONArray record = new JSONArray();
		record.add("HTTPStatus: " + statusCode);
		record.add("Time: " + date);
		record.add("URL: " + url);
        
        FileWriter file = new FileWriter(filePath+server+"ServerDownJSON.txt");
        try {
            file.write(record.toJSONString());
            logger.info("An entry has been made into JSON File");
        } catch (IOException e) {
            e.printStackTrace();
 
        } finally {
            file.flush();
            file.close();
        }
		
	}
	
	static void removeRecord(String filePath, String server) throws IOException{
		FileWriter file = null;;
		try {
			file = new FileWriter(filePath+server+"ServerDownJSON.txt");
			file.write("");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
            file.flush();
            file.close();
        }
		
	}
	
	static int readRecord(String filePath, String server) throws IOException {
		FileReader file = null;
		int record = 0;
		try {
			file = new FileReader(filePath+server+"ServerDownJSON.txt");
			record = file.read();
		} catch (IOException e) {
			new FileWriter(filePath+server+"ServerDownJSON.txt");
			file = new FileReader(filePath+server+"ServerDownJSON.txt");
			record = file.read();
			return record;
		} finally {
            file.close();
        }
		return record;
	}
}
