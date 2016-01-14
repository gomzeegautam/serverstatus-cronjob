package com.cron;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class ServerStatusCheck {

	static Map urlMap = new HashMap();
	final static Logger logger = Logger.getLogger(ServerStatusCheck.class);

	static void checkServerStatus(String server) throws IOException {
		int statusCode = 0;
		String urlStr = "";
		String jsonFilePath = urlMap.get("jsonFile").toString();
		
		if(server.equals("dev")){
			urlStr = urlMap.get("dev").toString();
		} else {
			urlStr = urlMap.get("prod").toString();
		}

		try
		{
			URL url = new URL(urlStr);
			HttpURLConnection http = (HttpURLConnection)url.openConnection();
			statusCode = http.getResponseCode();

			if(Record.readRecord(jsonFilePath, server) == -1){
				if(statusCode != 200){
					logger.info("Status: " + statusCode);
					SendMail.sendSimpleMessage("Issue with server!!",server + ", server is returning the following status:", Integer.toString(statusCode));
					logger.info("Problem with '" + server +"' server, returning response with code: " + statusCode);
					Record.enterRecord(statusCode, new Date().toString(), urlStr, jsonFilePath, server);
				}
			} else {
				if(statusCode == 200){
					Record.removeRecord(jsonFilePath, server);
					SendMail.sendSimpleMessage("Server issue is resolved!!",server + ", server is up with following status:", Integer.toString(statusCode));
				}
			}
		}catch(IOException e)
		{
			if(Record.readRecord(jsonFilePath, server) == -1){
				logger.error("Status: " + statusCode);
				logger.error("Problem with '" + server +"' server: " + e.getMessage());
				Record.enterRecord(111, new Date().toString(), urlStr, jsonFilePath, server);
				SendMail.sendSimpleMessage("Server down alert!!",server + ", server is down with following status:", e.getMessage());
			}
		} 
	}

	public static void main(String[] args) throws IOException {
		try {
			String log4JPropertyFile = args[0];
			Properties prop = new Properties();
			prop.load(new FileInputStream(log4JPropertyFile));
			PropertyConfigurator.configure(prop);

		}catch(Exception e){
			logger.error(e.getMessage());
		}

		urlMap.put("dev", "http://abcxyz.com");  // Your domain URL
		urlMap.put("prod", "http://abcxyz.com"); // Your domain URL
		urlMap.put("jsonFile", args[1]);

		String servers[] = {"dev","prod"};
		for(int i=0;i<servers.length;i++){
			ServerStatusCheck.checkServerStatus(servers[i]);
		}
	}
}