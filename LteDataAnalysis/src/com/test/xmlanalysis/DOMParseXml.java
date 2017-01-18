package com.test.xmlanalysis;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
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

import com.test.data.Measurement;
import com.test.util.LogUtil;
import com.test.util.Util;

/**
 * DOM方式解析xml
 */
public class DOMParseXml
{

	public static void main(String[] args)
	{
		LogUtil.setLevel(LogUtil.DEBUG);
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		double[] expRsrp = new double[96];
		String date = "TD-LTE_MRS_NSN_OMC_87301_20160705";
		for (int m = 0; m < 96; m++)
			
			
		{
			try
			{
				Map<String, Measurement> meaMap = new HashMap<>(); // key是mrName，value是measurement
				DocumentBuilder db = dbf.newDocumentBuilder();
				/* 注意导入Document对象时，要导入org.w3c.dom.Document包下的 */
				
				
				String min=new DecimalFormat("0000").format(m*1500%6000);
				String hour=new DecimalFormat("00").format( (m!=0)?(m *1500 /6000%24):0);
				
				
				Document document = db.parse("data/"+date+hour+min+".xml");
//				Document document = db.parse("data/TD-LTE_MRS_NSN_OMC_87301_20160705000000.xml");// 传入文件名可以是相对路径也可以是绝对路径
				// 获取所有measurement节点的集合
				NodeList measureList = document.getElementsByTagName("measurement");

				LogUtil.v("一共有 " + measureList.getLength() + " 个Measurement");
				// 遍历每一个measurement节点
				for (int i = 0; i < measureList.getLength(); i++)
				{
					Measurement measurement = new Measurement();

					LogUtil.v("=================下面开始遍历第 " + (i + 1) + " 个Measurement的内容=================");
					Node measurementNode = measureList.item(i);
					// 获取该measurement节点的所有属性集合
					NamedNodeMap attrs = measurementNode.getAttributes();
					Node attr = attrs.item(0);
					// 获取属性名
					LogUtil.v("属性名：" + attr.getNodeName());
					// 获取属性值
					LogUtil.v("--属性值" + attr.getNodeValue());

					measurement.setMrName(attr.getNodeValue());// 设置mrName

					// 解析book节点的子节点
					NodeList childNodes = measurementNode.getChildNodes();
					// 遍历childNodes获取每个节点的节点名和节点值
					LogUtil.v("第 " + (i + 1) + " 个Measurement共有 " + childNodes.getLength() + " 个子节点");
					for (int k = 0; k < childNodes.getLength(); k++)
					{
						Node node = childNodes.item(k);

						// 区分出text类型的node以及element类型的node
						if (node.getNodeType() == Node.ELEMENT_NODE)
						{

							if (node.getNodeName().equals("smr"))
							{
								// 获取了element类型节点的节点名
								LogUtil.v("第 " + (k + 1) + " 个节点的节点名：" + node.getNodeName());
								// 获取了element类型节点的节点值
								LogUtil.v("--节点值是：" + node.getFirstChild().getNodeValue());
							
								measurement.setSmr(node.getFirstChild().getNodeValue());// 设置smr

							} else if (node.getNodeName().equals("object"))
							{
								NamedNodeMap objectAttrs = node.getAttributes();
								Node objectAttr = objectAttrs.item(0);
								NodeList vNodeList = node.getChildNodes();
								Node vNode = vNodeList.item(1);
								// 获取了element类型节点的节点名
								LogUtil.v("第 " + (k + 1) + " 个节点的节点名：" + vNode.getNodeName());
								// 获取了element类型节点的节点值
								LogUtil.v("--节点值是：" + vNode.getFirstChild().getNodeValue());

								measurement.getObjects().put(objectAttr.getNodeValue(),
										vNode.getFirstChild().getNodeValue());
							
							}
						}
					}
					LogUtil.v("======================结束遍历第" + (i + 1) + "个measurement的内容=================");

					meaMap.put(attr.getNodeValue(), measurement);
				}
				String[] rsrpString = meaMap.get("MR.RSRP").getObjects().get("22349057").split(" ");
				double[] rsrqDouble = new double[rsrpString.length];
				for (int j = 0; j < rsrpString.length; j++)
				{
					rsrqDouble[j] = Double.parseDouble(rsrpString[j]);
				}

				LogUtil.d(String.valueOf(Util.calRsrpExp(rsrqDouble)));
				expRsrp[m]=Util.calRsrpExp(rsrqDouble);
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

		}
	}

}