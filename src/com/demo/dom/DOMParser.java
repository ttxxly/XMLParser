package com.demo.dom;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * 使用DOM解析XML文件
 * 
 * 把XML文件转换后为流程在把流在内存中构建一个DOM模型，使用对应API操作DOM树
 * 
 * @author Administrator
 *
 */
public class DOMParser {

	public static void main(String[] args) {
		long start = System.nanoTime();
		try {
			// 创建一个文档构建工厂对象
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			// 通过工厂对象创建一个文档构建对象
			DocumentBuilder documentBuilder = builderFactory.newDocumentBuilder();
			// 吧XML转换为输入流操作
			InputStream inputStream = DOMParser.class.getClassLoader().getResourceAsStream("datasource.xml");
			// 通过文档构建对象构建一个文档对象
			Document document = documentBuilder.parse(inputStream);
			// 获取文档中的根元素
			Element rootElement = document.getDocumentElement();
			// 获取根元素先所有dataSource子节点
			NodeList nodeList = rootElement.getChildNodes();

			for (int i = 0; i < nodeList.getLength(); i++) {
				/**
				 * 3: 换行节点->文本节点 8: 注释节点 1: 元素节点
				 */
				Node node = nodeList.item(i);
				// 判断元素节点才操作
				if (Node.ELEMENT_NODE == node.getNodeType()) {
					// 读取属性节点的值
					String clazz = node.getAttributes().getNamedItem("class").getNodeValue();
					String id = node.getAttributes().getNamedItem("id").getNodeValue();
					System.out.println("class="+clazz);
					System.out.println("id="+id);
					// 获取元子节点
					NodeList datasourceNodes = node.getChildNodes();
					for (int j = 0; j < datasourceNodes.getLength(); j++) {
						Node dataSourceNode = datasourceNodes.item(j);
						
						if (Node.ELEMENT_NODE == dataSourceNode.getNodeType()) {
							// 获取属性的值
							String nameValue = dataSourceNode.getAttributes().getNamedItem("name").getNodeValue();
							String contentValue = dataSourceNode.getTextContent();
							System.out.println(nameValue+"="+contentValue);
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		long end = System.nanoTime();
		System.out.println(end - start);
	}
	
	
	

}
