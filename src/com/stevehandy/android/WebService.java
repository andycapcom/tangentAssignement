package com.stevehandy.android;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;
import org.apache.http.NameValuePair;


public class WebService {
	//Webservice URL 	
	private static String URLlogin = "http://staging.tangent.tngnt.co/api-token-auth/";
	private static String URLProfile = "http://staging.tangent.tngnt.co/api/user/me/";
	private static String URLEmployees = "http://staging.tangent.tngnt.co/api/employee/";
	private static String URLEmployee = "http://staging.tangent.tngnt.co/api/employee/me/";
	
	public static String invokeLoginWS(String userName,String passWord) {
		StringBuffer stringBuffer = new StringBuffer("");
	      BufferedReader bufferedReader = null;
		
		try {
	      HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost();
        
             	
        URI uri = new URI(URLlogin);
        
        
        httpPost.setURI(uri);
		
        httpPost.addHeader("Accept", "application/json");
        
        
        HttpParams params = new BasicHttpParams();
        //params.setParameter("d", new JSONObject());
        //System.out.println("uname"+userName+passWord);
        String json = "username : "+ userName + ", password : "+ passWord;
        StringEntity body = new StringEntity(json);
        body.setContentType("application/json");
        
        StringEntity entity = new StringEntity("{}", HTTP.UTF_8);
        entity.setContentType("application/json");
        //httpPost.setEntity(body);
        
        List<NameValuePair> list=new ArrayList<NameValuePair>();  
        list.add(new BasicNameValuePair("username", userName));  
        list.add(new BasicNameValuePair("password", passWord));  
        httpPost.setEntity(new UrlEncodedFormEntity(list)); 
        
        
        
        //execute post request to the given URL 
        HttpResponse httpResponse = httpClient.execute(httpPost);
        
        //receive response as inputstream 
        InputStream inputStream = httpResponse.getEntity().getContent();
        bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        String readLine = bufferedReader.readLine();
        while (readLine != null) {
            stringBuffer.append(readLine);
            stringBuffer.append("\n");
            readLine = bufferedReader.readLine();
        }
       
    } catch (Exception e) {
    	
    	e.printStackTrace();

    } finally {
        if (bufferedReader != null) {
            try {
                bufferedReader.close();
            } catch (IOException e) {

            }
        }
    }
    
    String response = stringBuffer.toString();
    
    
    
    
    return response;
	}
	
	public static String invokeProfileWS(String token) throws IOException {
		URL url = new URL(URLProfile);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	    connection.setRequestMethod("GET");
	    connection.addRequestProperty("Accept","application/json");
	    connection.addRequestProperty("Content-Type","application/json");
	    connection.addRequestProperty("Authorization","Token " + token );

	    try {
	        InputStream in = connection.getInputStream();

	        Scanner scanner = new Scanner(in);
	        scanner.useDelimiter("\\A");

	        boolean hasInput = scanner.hasNext();
	        if (hasInput) {
	            return scanner.next();
	        } else {
	            return null;
	        }
	    } finally {
	        connection.disconnect();
	    }
	}
	
	public static String invokeEmployeesWS(String token) throws IOException {
		URL url = new URL(URLEmployees);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	    connection.setRequestMethod("GET");
	    connection.addRequestProperty("Accept","application/json");
	    connection.addRequestProperty("Content-Type","application/json");
	    connection.addRequestProperty("Authorization","Token " + token );

	    try {
	        InputStream in = connection.getInputStream();

	        Scanner scanner = new Scanner(in);
	        scanner.useDelimiter("\\A");

	        boolean hasInput = scanner.hasNext();
	        if (hasInput) {
	            return scanner.next();
	        } else {
	            return null;
	        }
	    } finally {
	        connection.disconnect();
	    }
	}
	
	public static String invokeEmployeeWS(String token) throws IOException {
		URL url = new URL(URLEmployee);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	    connection.setRequestMethod("GET");
	    connection.addRequestProperty("Accept","application/json");
	    connection.addRequestProperty("Content-Type","application/json");
	    connection.addRequestProperty("Authorization","Token " + token );

	    try {
	        InputStream in = connection.getInputStream();

	        Scanner scanner = new Scanner(in);
	        scanner.useDelimiter("\\A");

	        boolean hasInput = scanner.hasNext();
	        if (hasInput) {
	            return scanner.next();
	        } else {
	            return null;
	        }
	    } finally {
	        connection.disconnect();
	    }
	}
}
