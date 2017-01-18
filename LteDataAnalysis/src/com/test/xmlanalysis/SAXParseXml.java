package com.test.xmlanalysis;

import java.io.File;
import java.util.Stack;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * SAX解析XML
 * 
 */
public class SAXParseXml
{

	public static void main(String[] args) throws Exception
	{
		// step 1: 获得SAX解析器工厂实例
		SAXParserFactory factory = SAXParserFactory.newInstance();

		// step 2: 获得SAX解析器实例
		SAXParser parser = factory.newSAXParser();

		// step 3: 开始进行解析
		// 传入待解析的文档的处理器
		parser.parse(new InputSource("TD-LTE_MRS_NSN_OMC_87301_20160705000000.xml"), new MySAXHandler());

	}
}

class MySAXHandler extends DefaultHandler
{
	// 使用栈这个数据结构来保存
	private Stack<String> stack = new Stack<String>();

	// 数据
	private String title;
	private String author;
	private String year;
	private double price;

	@Override
	public void startDocument() throws SAXException
	{
		System.out.println("start document -> parse begin");
	}

	@Override
	public void endDocument() throws SAXException
	{

		System.out.println("end document -> parse finished");
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
	{
		// 将标签名压入栈
		stack.push(qName);
		// 处理属性
		for (int i = 0; i < attributes.getLength(); ++i)
		{
			String attrName = attributes.getQName(i);
			String attrValue = attributes.getValue(i);
			System.out.println("属性： " + attrName + "=" + attrValue);
		}

	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException
	{
		// 取出标签名
		String tag = stack.peek();

		if ("v".equals(tag))
		{
			title = new String(ch, start, length);

		} 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException
	{
		stack.pop();// 表示该元素解析完毕，需要从栈中弹出标签

		if ("v".equals(qName))
		{
			System.out.println("    title: " + title);
			System.out.println();
		}

	}
}
