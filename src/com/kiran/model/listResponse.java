package com.kiran.model;

import java.util.Hashtable;
import java.util.List;

public class listResponse 
{
	private String status;
	private int code;
	private Hashtable<String, Integer> ht;
	
	public String getStatus()
	{
		return status;
	}
	
	public void setStatus(String status)
	{
		this.status = status;
	}
	
	public int getCode()
	{
		return code;
	}
	
	public void setCode(int code)
	{
		this.code = code;
	}

	public Hashtable<String, Integer> getHt() 
	{
		return ht;
	}

	public void setHt(Hashtable<String, Integer> ht) 
	{
		this.ht = ht;
	}
}
