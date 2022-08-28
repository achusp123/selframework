package test.Core;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.smartcardio.ATR;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;


/*import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.helpers.DefaultHandler;*/
public class XmlReaderNew {
	
	//public static Map ReadXmlData(String  fileLocation,String NodeName,String strAttributeval){
		public static Map ReadXmlData(String  fileLocation,String NodeName){
		String strNodeName;
		String strNodeValue;
		DocumentBuilderFactory dbFactory;
		DocumentBuilder dBuilder;
		File inputFile;
		Map<String,String> map = new HashMap<String, String>();
		try{
			Document doc = null;
			inputFile = new File(String.valueOf(System.getProperty("user.dir")) + "//src//test//resources//TestData//" + fileLocation + ".xml");
//			inputFile= new File(System.getProperty("user.dir")+"//TestData//"+fileLocation+".xml");
			dbFactory =DocumentBuilderFactory.newInstance();
			 dBuilder =dbFactory.newDocumentBuilder();
			 doc= dBuilder.parse(inputFile);
			 Node ele = doc.getParentNode();
			 doc.getDocumentElement().normalize();
		NodeList nlist =(NodeList) doc.getElementsByTagName(NodeName).item(0);
		for(int temp=0;temp<nlist.getLength();temp++){
		Node nNode =nlist.item(temp);
		if(nNode.getNodeType()==Node.ELEMENT_NODE){
			Element eElement =(Element)nNode;
			strNodeName =eElement.getNodeName();
			strNodeValue = eElement.getTextContent();
			map.put(strNodeName,strNodeValue);	
			
		}
	}
}
		catch(Exception ex){
			ex.printStackTrace();
		}
		
		return map;
		
	}

}
