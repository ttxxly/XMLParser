package com.demo.sax;

import java.io.InputStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.demo.dom.DOMParser;

/**
 * Sax解析
 * 
 * @author Administrator
 *
 */
public class SaxParserDemo {
	public static void main(String[] args) {
		long start = System.nanoTime();
		// 创建一个Sax工厂对象->工厂设计
		SAXParserFactory factory = SAXParserFactory.newInstance();
		try {
			// 创建解析器
			SAXParser saxParser = factory.newSAXParser();
			// 吧XML转换为输入流操作
			InputStream inputStream = DOMParser.class.getClassLoader().getResourceAsStream("datasource.xml");
			saxParser.parse(inputStream, new DefaultHandler() {
				//解析开始标题文档
	            public void startDocument() throws SAXException {
	                System.out.println("<?xml version= 1.0 encoding= utf-8 ?>");
	            }
	            //解析节点
	            @Override
	            public void startElement(String uri, String localName,
	                    String qName, Attributes attributes) throws SAXException {
	                System.out.print("<"+qName+" ");
	                for (int i = 0; i < attributes.getLength(); i++) {
	                    System.out.print(attributes.getQName(i)+"="+attributes.getValue(i)+" ");
	                }
	                System.out.print(">");
	            }
	       
	            @Override
	            //解析结束
	            public void endElement(String uri, String localName, String qName)
	                    throws SAXException {
	                System.out.print("</"+qName+">");
	            }
	 
	            @Override
	            //解析内容
	            public void characters(char[] ch, int start, int length)
	                    throws SAXException {
	                String string = new String(ch, start, length);
	                System.out.print(string);
	            }

			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		long end = System.nanoTime();
		System.out.println(end - start);
	}
	
	/**
	 * 定义默认处理的内部类
	 * @author Administrator
	 *
	 */
	private static class XMLHanlder extends DefaultHandler {

		@Override
		public void startDocument() throws SAXException {
			System.out.println("解析开始");
		}

		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes)
				throws SAXException {
			System.out.println("开始解析元素"+qName);
			System.out.println(attributes.getValue("id"));
			System.out.println(attributes.getValue("class"));
		}

		@Override
		public void endElement(String uri, String localName, String qName) throws SAXException {
			System.out.println("解析结束");
		}

		@Override
		public void characters(char[] ch, int start, int length) throws SAXException {
			System.out.println("解析内容");
			System.out.println(new String(ch,start, length));
		}
		
	}
}
