package com.test.xmlanalysis.mro;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.test.util.LogUtil;

import calcu.Calculator;
import calcu.RsrpCalcutor;
import calcu.RsrqCalcutor;
import calcu.SinrCalcutor;

import com.test.bean.Measurement;
import com.test.bean.MroObject;
import com.test.bean.MroValue;
import com.test.util.CalUtil;

/**
 * DOM方式解析xml
 */
public class DOMParseXmlMRO
{
	public final static String   CELL="87301";  //小区  87301  97327
	public final static String   DATE="20170205";//时间  20160705  20170205~20170307
//	public final static String   MRNAME="MR.SinrUL";//提取的数据   MR.SinrUL  MR.RSRQ MR.RSRP
//	public final static String   ID="22349057";//id号       87301:22349057,22349058,22349059
//												    // 97327:24915713,24915723
	
	public static void main(String[] args)
	{
		
		LogUtil.setLevel(LogUtil.DEBUG);
		
		String filePath="out/";
//		String fileName=CELL+"_"+DATE+"_"+ID+".txt";
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		 //	 相对路径  "87301/20170205/TD-LTE_MRS_NSN_OMC_87301_20170205"; 
		String uri = CELL+"_MRO"+"/"+DATE+"/TD-LTE_MRO_NSN_OMC_"+CELL+"_"+DATE; 
		
		
		
		//存放96个文件中所有的Object ， key是 第 m个 文件， value是  一个文件中所有的Object
		Map<Integer, List<MroObject>> allData=new HashMap<>();
		//提取96个文件中所有数据
		for (int m = 0; m < 96; m++)
		{
			try
			{
				DocumentBuilder db = dbf.newDocumentBuilder();
				String min=new DecimalFormat("0000").format(m*1500%6000);
				String hour=new DecimalFormat("00").format( (m!=0)?(m *1500 /6000%24):0);
				/* 注意导入Document对象时，要导入org.w3c.dom.Document包下的 */
				Document document = db.parse(uri+hour+min+".xml");
				
				
				List<MroObject> mroObjectList=new ArrayList<>();
				// 获取所有object节点的集合
				NodeList objectList = document.getElementsByTagName("object");
				
				LogUtil.v("一共有 " + objectList.getLength() + " 个object");
				// 遍历每一个object节点
				for (int i = 0; i < objectList.getLength(); i++)
				{
					MroObject mroObject = new MroObject();
					LogUtil.v("=================下面开始遍历第 " + (i + 1) + " 个object的内容=================");
					
					
					Node objectNode = objectList.item(i);
					// 获取该object节点的所有属性集合
					
//					 TimeStamp="2017-02-04T23:59:54.397">
					NamedNodeMap attrs = objectNode.getAttributes();
					
					//属性id
					Node id = attrs.item(0);
						// 获取属性名
					LogUtil.v("属性名：" + id.getNodeName());
						// 获取属性值
					LogUtil.v("--属性值" + id.getNodeValue());
						
					mroObject.setId(id.getNodeValue());
						
						
						//属性MmeUeS1apId
						Node mmeUeS1apId = attrs.item(1);
						// 获取属性名
						LogUtil.v("属性名：" + mmeUeS1apId.getNodeName());
						// 获取属性值
						LogUtil.v("--属性值" + mmeUeS1apId.getNodeValue());
						mroObject.setMmeUeS1apId(mmeUeS1apId.getNodeValue());
						
						
						
						
						//属性MmeGroupId
						Node mmeGroupId = attrs.item(2);
						// 获取属性名
						LogUtil.v("属性名：" + mmeGroupId.getNodeName());
						// 获取属性值
						LogUtil.v("--属性值" + mmeGroupId.getNodeValue());
						mroObject.setMmeGroupId(mmeGroupId.getNodeValue());
						
						
						
						//属性MmeCode
						Node mmeCode = attrs.item(3);
						// 获取属性名
						LogUtil.v("属性名：" + mmeCode.getNodeName());
						// 获取属性值
						LogUtil.v("--属性值" + mmeCode.getNodeValue());
						mroObject.setMmeCode(mmeCode.getNodeValue());
						
						//属性timeStamp
						Node timeStamp = attrs.item(4);
						// 获取属性名
						LogUtil.v("属性名：" + timeStamp.getNodeName());
						// 获取属性值
						LogUtil.v("--属性值" + timeStamp.getNodeValue());
						mroObject.setTimeStamp(timeStamp.getNodeValue());
					
					
				
				
					// 解析object节点的子节点
					NodeList childNodes = objectNode.getChildNodes();
					// 遍历childNodes获取每个节点的节点名和节点值
					LogUtil.v("第 " + (i + 1) + " 个object共有 " + childNodes.getLength() + " 个子节点");
					
					List<MroValue> vList=new ArrayList<>();
					
					for (int k = 0; k < childNodes.getLength(); k++)
					{
						Node node = childNodes.item(k);
						// 区分出text类型的node以及element类型的node
						if (node.getNodeType() == Node.ELEMENT_NODE)
						{
							// 获取了element类型节点的节点名
							LogUtil.v("第 " + (k + 1) + " 个节点的节点名：" + node.getNodeName());
							// 获取了element类型节点的节点值
							String value=node.getFirstChild().getNodeValue();
							LogUtil.v("--节点值是：" + value);
							String[] valueArray=value.split(" ");
							//判断属于哪个Measurement的 Object
							if(valueArray.length==27)
							{
								vList.add(new MroValue(node.getFirstChild().getNodeValue()));
							}
							
						}
					}
					LogUtil.v("======================结束遍历第" + (i + 1) + "个object的内容=================");

					if(!vList.isEmpty())
					{
						mroObject.setValues(vList);
					}else {
						mroObject=null;
					}
					
					if(mroObject!=null)
					{
						mroObjectList.add(mroObject);
					}
				
					
				}
				
				
				
				//将 第m个文件 中的所有measurement添加到 allData
				allData.put(m, mroObjectList);
				
			} catch (ParserConfigurationException e)
			{
				e.printStackTrace();
			} catch (SAXException e)
			{
				e.printStackTrace();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
			
		}//读取完所有文件
		
		
		
		
		
//		//计算 一天96个文件的  加权平均值
//		Calculator calculator=new Calculator(new SinrCalcutor(), allData, ID);
//		calculator.cal();
//		LogUtil.d(calculator.getResultStr());
//		//写入文件中
//		calculator.writeToFile(filePath,fileName);
		
		
		
	}

}