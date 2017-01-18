package com.test.data;

import java.util.HashMap;
import java.util.Map;
/**
 * Measurement Java Bean
 * @author 周宏
 *2017/1/4
 */
public class Measurement
{
	private String mrName;
	private String smr;
	private Map<String, String> objects;  //key 是 id，value 是  v;

	public Measurement()
	{
		// TODO Auto-generated constructor stub
		mrName = "";
		smr = "";
		objects = new HashMap<>();
	}

	public String getMrName()
	{
		return mrName;
	}

	public void setMrName(String mrName)
	{
		this.mrName = mrName;
	}

	public String getSmr()
	{
		return smr;
	}

	public void setSmr(String smr)
	{
		this.smr = smr;
	}

	public Map<String, String> getObjects()
	{
		return objects;
	}

	public void setObjects(Map<String, String> objects)
	{
		this.objects = objects;
	}

}
