package com.demo.jdom;

import java.io.InputStream;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.xpath.XPath;

/**
 * 使用JDOM解析XML文件
 * 
 * @author Administrator 补全快捷键： Ctrl+2, L
 *
 */
public class JDOMParser {
	public static void main(String[] args) throws Exception {
		//testJdomParser();
		testXPathXMLParser("oracle");
	}

	private static void testJdomParser() throws Exception {
		// 建造者设计模式
		SAXBuilder saxBuilder = new SAXBuilder();
		// 获取XML文件对应输入流
		InputStream in = JDOMParser.class.getClassLoader().getResourceAsStream("datasource.xml");
		// 通过构建器对象构建一个文档对象
		Document document = saxBuilder.build(in);
		// 获取根元素
		Element rootElement = document.getRootElement();

		// 获取根元素下面所有子元素
		// getChildren() 获取所有子元素集合
		// getChildren("元素名称") // 获取指定元素名称的元素集合
		List<Element> childList = rootElement.getChildren("dataSource");

		for (Element element : childList) {
			// 获取id属性值
			String id = element.getAttributeValue("id");
			String clazz = element.getAttributeValue("class");
			System.out.println("id=" + id + "-----class=" + clazz);
			// 获取所有的property子节点
			List<Element> propertyChild = element.getChildren("property");

			for (Element childEl : propertyChild) {
				String name = childEl.getAttributeValue("name"); // 获取元素name属性值
				String text = childEl.getTextTrim(); // 获取元素中的内容

				System.out.println("name=" + name + "=========content=" + text);
			}
			System.out.println("--------------------------");
		}
	}
	
	/**
	 * 使用XPath选取节点
	 * http://www.cnblogs.com/hoojo/archive/2011/08/11/2134638.html XPath解析
	 */
	private static void testXPathXMLParser(String dataSourceId) throws Exception{
		// 建造者设计模式
		SAXBuilder saxBuilder = new SAXBuilder();
		// 获取XML文件对应输入流
		InputStream in = JDOMParser.class.getClassLoader().getResourceAsStream("datasource.xml");
		// 通过构建器对象构建一个文档对象
		Document document = saxBuilder.build(in);
		// 获取根元素
//		Element rootElement = document.getRootElement();
		// path: XPath表达式
		XPath xpath = XPath.newInstance("dataSources/dataSource[@id='"+dataSourceId+"']");
		Element dataSource = (Element) xpath.selectSingleNode(document);
		// "//" 选取配置元素组成集合，不考虑位置
		XPath propXpath = XPath.newInstance("property");
		List<Element> nodes = propXpath.selectNodes(dataSource); // 从选择到dataSource元素下面选择子节点
		for (Element el : nodes) {
			System.out.println(el.getAttributeValue("name")+"-------------"+el.getTextTrim());
		}
	}
}
