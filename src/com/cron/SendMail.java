package com.cron;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class SendMail {
	
	public static ClientResponse sendSimpleMessage(String subject, String message, String statusCode){
		Client client = Client.create();
		client.addFilter(new HTTPBasicAuthFilter("api", "PUT YOUR API KEY HERE"));
		WebResource webResource = client.resource("PUT YOUR API URL HERE");
		MultivaluedMapImpl formData = new MultivaluedMapImpl();
		formData.add("from", "abc@xyz.com");
		formData.add("to", "abc@xyz.com");
		formData.add("bcc", "abc@xyz.com,abc@xyz.com");
		formData.add("subject", subject);
		formData.add("text", message + " " + statusCode);

		return (ClientResponse) webResource.type(MediaType.APPLICATION_FORM_URLENCODED).post(ClientResponse.class,formData);
	}
}
