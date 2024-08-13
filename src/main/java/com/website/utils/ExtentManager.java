package com.website.utils;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {
	private static ExtentReports extent;
	public static ExtentReports getInstance(String timestamp)
	{
		if(extent==null) {
			createInstance(timestamp);
		}
		return extent;
	}
	public static ExtentReports createInstance(String timeStamp) {
		File file= new File(System.getProperty("user.dir")+"/ExtentReports/"+ timeStamp);
		File file1=new File(
				System.getProperty("user.dir")+"/ExtentReports/"+timeStamp+"/Failed-TestNGSuite.xml");
		file.mkdir();
		try {
			DocumentBuilderFactory documentFactory= DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder= documentFactory.newDocumentBuilder();
			Document document= documentBuilder.newDocument();
			Element root,parameter;
			Attr attr1,attr2,attr3;
			root=document.createElement("suite");
			document.appendChild(root);
			attr1=document.createAttribute("name");
			attr1.setValue("Failed Suits");
			attr2=document.createAttribute("thread-count");
			attr2.setValue("8");
			attr3=document.createAttribute("parallel");
			attr3.setValue("tests");
			root.setAttributeNode(attr1);
			root.setAttributeNode(attr2);
			root.setAttributeNode(attr3);
			parameter= document.createElement("parameter");
			root.appendChild(parameter);
			attr1=document.createAttribute("name");
			attr1=document.createAttribute("value");
			attr1.setValue("browser");
			attr2.setValue("FIrefox");
			parameter.setAttributeNode(attr1);
			parameter.setAttributeNode(attr2);
			TransformerFactory transformerFactory=TransformerFactory.newInstance();
			Transformer transformer;
			transformer=transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			DOMSource domSource = new DOMSource(document);
			StreamResult streamResult= new StreamResult(file1);
			transformer.transform(domSource,streamResult);
					}
		catch(Exception e) {
			
		}
		String rprtName= System.getProperty("user.dir"+"/ExtentReports/"+timeStamp+"/Test-Report.html");
		ExtentSparkReporter sparkReporter = new ExtentSparkReporter("testReport.html");
		sparkReporter.config().setTimeStampFormat("MMM dd, yyyy HH:mm:ss");
		sparkReporter.config().setTheme(Theme.STANDARD);
		sparkReporter.config().setDocumentTitle("Web Automation Project");
		sparkReporter.config().setEncoding("utf-8");
		sparkReporter.config().setReportName("Web Automation Demo Report");
		
		extent = new ExtentReports();
		extent.attachReporter(sparkReporter);
		extent.setSystemInfo("Execution Platform", "Remote machine");
		extent.setSystemInfo("Environment", "QA");
		extent.setSystemInfo("User", "Paridhi Jain");
		
		return extent;
	}

}
