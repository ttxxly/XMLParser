package com.demo.dom4j;

import java.io.InputStream;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.XPath;
import org.dom4j.io.SAXReader;

public class dom4j {
	public static void main(String[] args) throws Exception {
		dom4jXpath("mysql");
	}

	/**
	 * dom4j常规方式解析 -> 使用命名空间方式解析
	 * @throws Exception
	 */
	private static void dom4j() throws Exception {
		// 创建SaxReader对象
		SAXReader reader = new SAXReader();
		InputStream in = dom4j.class.getClassLoader().getResourceAsStream("datasource.xml");
		// 获取到文档对象
		Document document = reader.read(in);
		// 获取根元素对象
		Element rootElement = document.getRootElement();
		// System.out.println(rootElement);
		// 获取根元素下面所有子元素
		// List<Element> elements = rootElement.elements();
		// 获取根元素下面指定元素名称的子元素集合
		List<Element> elements = rootElement.elements("dataSource");

		for (Element el : elements) {
			System.out.println(el.attributeValue("id") + "-----" + el.attributeValue("class"));
			// 获取el元素下面所有子元素
			List<Element> childList = el.elements("property");

			for (Element childEl : childList) {
				String name = childEl.attributeValue("name");
				String text = childEl.getTextTrim();
				System.out.println(name + "----------" + text);
			}
			System.out.println("------------------------------------------");
		}
	}
	
	/**
	 * dom4j方式解析使用XPath 
	 * @throws Exception
	 */
	private static void dom4jXpath(String dataSourceId) throws Exception {
		// 创建SaxReader对象
		SAXReader reader = new SAXReader();
		InputStream in = dom4j.class.getClassLoader().getResourceAsStream("datasource.xml");
		// 获取到文档对象
		Document document = reader.read(in);
		
		XPath xpath = document.createXPath("dataSources/dataSource[@id='"+dataSourceId+"']");
		Element dataSourceNode = (Element) xpath.selectSingleNode(document);
		System.out.println(dataSourceNode.attributeValue("id")+"----"+dataSourceNode.attributeValue("class"));
		
		xpath = document.createXPath("property");
		List<Element> propList = xpath.selectNodes(dataSourceNode);
		
		for (Element el : propList) {
		 	String name = el.attributeValue("name");
		 	String text = el.getTextTrim();
		 	System.out.println(name+"--------------"+text);
		}
	}
}
