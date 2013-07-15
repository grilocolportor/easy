package org.avs.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BufferedHeader;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.UrlEncodedContent;

public class JSONParser {
	
	static InputStream is = null;
	static JSONObject jObj = null;
	static String json = "";

	//construtor
	public JSONParser(){
		
	}
	
	//funcão para resgatar os dados em formato json de uma url
	//utilizando HTTP POST ou GET method
	
	public JSONObject makeHttpRequest(String url, String metodo, List<NameValuePair> params){
		
		//fazendo uma requisção através de um HTTP request
		
		try{
			
			//verificando o método utilizado
			if(metodo == "POST"){
				
				//metodo de requisição e POST
				
				DefaultHttpClient httpCliente = new DefaultHttpClient();
				HttpPost httpPost = new HttpPost(url);
				httpPost.setEntity(new UrlEncodedFormEntity(params));
				
				org.apache.http.HttpResponse httpResponse = httpCliente.execute(httpPost);
				HttpEntity httpEntenty = httpResponse.getEntity();
				is = httpEntenty.getContent();
				
				
			}else	 if(metodo == "GET"){
				
				//metodo de requisição GET
				
				DefaultHttpClient httpCliente = new DefaultHttpClient();
				String paramString = URLEncodedUtils.format(params, "utf-8");
				url += "?" + paramString;
				HttpGet httpGet = new HttpGet(url);
				
				org.apache.http.HttpResponse httpResponse = httpCliente.execute(httpGet);
				HttpEntity httpEntenty = httpResponse.getEntity();
				is = httpEntenty.getContent();
				
			}
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			json = sb.toString();
		} catch (Exception e) {
			Log.e("Buffer Error", "Error converting result " + e.toString());
		}

		// try parse the string to a JSON object
		try {
			jObj = new JSONObject(json);
		} catch (JSONException e) {
			Log.e("JSON Parser", "Error parsing data " + e.toString());
		}

		// return JSON String
		return jObj;

		
	}

}
