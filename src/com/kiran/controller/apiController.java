package com.kiran.controller;

import java.util.Hashtable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kiran.model.baseResponse;
import com.kiran.model.countResponse;
import com.kiran.model.getResponse;
import com.kiran.model.listResponse;

@RestController
@RequestMapping("/api")
public class apiController 
{
	Hashtable<String, String> ht1 = new Hashtable<String, String>();
	Hashtable<String, Integer> ht2 = new Hashtable<String, Integer>();
	
	private static final String successStatus = "Success";
	private static final String errorStatus = "Error";
	private static final int code_success = 200;
	private static final int auth_failure = 404;
	
	@RequestMapping(value="/storeurl", method = RequestMethod.GET)
	public baseResponse storeurl(@RequestParam(value = "url") String url)
	{
		baseResponse response = new baseResponse();
		
		String urlStr = url;
		String shortKey = generateShortKey(urlStr);
		
		if(ht1.containsKey(urlStr))
		{
			response.setStatus(successStatus);
			response.setCode(code_success);
			response.setMessage("URL already exists");
		}
		else
		{
			ht1.put(urlStr, shortKey);
			ht2.put(shortKey, 0);
			response.setStatus(successStatus);
			response.setCode(code_success);
			response.setMessage("URL is successfully added");
		}
		return response;
	}
	
	@RequestMapping(value="/get", method = RequestMethod.GET)
	public getResponse get(@RequestParam(value = "url") String url)
	{
		getResponse response = new getResponse();
		
		String urlStr = url;
		String shortKey;
		int count;
		
		if(ht1.containsKey(urlStr))
		{
			shortKey = ht1.get(urlStr);
			count = ht2.get(shortKey);
			count++;
			ht2.put(shortKey, count);
			
			response.setStatus(successStatus);
			response.setCode(code_success);
			response.setKey(shortKey);
			response.setMessage("Count is successfully updated");
		}
		else
		{
			response.setStatus(successStatus);
			response.setCode(code_success);
			response.setMessage("URL does not exists");
		}
		return response;
	}
	
	@RequestMapping(value="/count", method = RequestMethod.GET)
	public countResponse count(@RequestParam(value = "url") String url)
	{
		countResponse response = new countResponse();
		
		String urlStr = url;
		String shortKey;
		int count;
		
		if(ht1.containsKey(urlStr))
		{
			shortKey = ht1.get(urlStr);
			count = ht2.get(shortKey);
			
			response.setStatus(successStatus);
			response.setCode(code_success);
			response.setMessage("Latest count");
			response.setCount(count);
		}
		else
		{
			response.setStatus(successStatus);
			response.setCode(code_success);
			response.setMessage("URL does not exists");
		}
		return response;
	}
	
	@RequestMapping(value="/list", method = RequestMethod.GET)
	public listResponse list(@RequestParam(value = "page") int page, @RequestParam(value = "size") int size)
	{
		listResponse response = new listResponse();
		
		Hashtable<String, Integer> ht3 = new Hashtable<String, Integer>();
		int htSize = ht2.size();
		Object[] ks = ht2.keySet().toArray();
		
		int startingIndex = size * (page - 1);
		int j = size;
		for(int i = startingIndex; i < htSize; i++)
		{
			if(j > 0)
			{
				ht3.put((String)ks[i], ht2.get(ks[i]));
				System.out.println(ks[i] + " " + ht2.get(ks[i]));
				j--;
			}
			else
			{
				break;
			}
		}
		
		response.setStatus(successStatus);
		response.setCode(code_success);
		response.setHt(ht3);
		return response;
	}
	
	private String generateShortKey(String urlStr) 
	{
		String shortKey = urlStr.substring(0, 4);
		return shortKey;
	}
}
