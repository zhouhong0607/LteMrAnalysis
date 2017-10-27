package com.test.bean;

import java.util.ArrayList;
import java.util.List;

public class MroObject
{
	
	private String id;
	private String mmeUeS1apId;
	private String mmeGroupId;
	private String mmeCode;
	private String timeStamp;
	
	private  List<MroValue> values;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getMmeUeS1apId()
	{
		return mmeUeS1apId;
	}

	public void setMmeUeS1apId(String mmeUeS1apId)
	{
		this.mmeUeS1apId = mmeUeS1apId;
	}

	public String getMmeGroupId()
	{
		return mmeGroupId;
	}

	public void setMmeGroupId(String mmeGroupId)
	{
		this.mmeGroupId = mmeGroupId;
	}

	public String getMmeCode()
	{
		return mmeCode;
	}

	public void setMmeCode(String mmeCode)
	{
		this.mmeCode = mmeCode;
	}

	public String getTimeStamp()
	{
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp)
	{
		this.timeStamp = timeStamp;
	}

	public List<MroValue> getValues()
	{
		return values;
	}

	public void setValues(List<MroValue> values)
	{
		this.values = values;
	}
	
	
	
	
	
	
	
}
