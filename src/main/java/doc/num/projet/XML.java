package doc.num.projet;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XML {
	
	public Document doc;
	
	public XML(String fichier) throws SAXException, IOException, ParserConfigurationException {
		
		File document_xml = new File(fichier);
		//Instanciation de la fabrique de parseurs	
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	
		//activation de la validation (DTD)
		dbFactory.setValidating(true);
		
		//création du parseur DOM
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		this.doc = (Document) dBuilder.parse(document_xml);
		
		//affiche la version de XML
		System.out.println(this.doc.getXmlVersion());
		//affiche l'encodage
		System.out.println(this.doc.getXmlEncoding());
		//affiche s'il s(agit d'un document standalone
		System.out.println(doc.getXmlStandalone()+"\n");
		System.out.println("_____________________________________________");
	}
	
	public Document getDoc() {
		return this.doc;
	}
	
	
	
	public void affichage() {		
		
		//recuperation de la racine 
        Element racine = getDoc().getDocumentElement();
        Node header = racine.getFirstChild();
		System.out.println(header.getNodeName()+": ID_File -> "+header.getAttributes());
        
        /*HEADER*/
        NodeList fils = header.getChildNodes();
        Element e = (Element) fils.item(1);
        System.out.println("  "+e.getNodeName()+" = "+e.getNodeValue());
        e = (Element) fils.item(2);
        System.out.println("  "+fils.item(2).getNodeName()+" idUser = "+e.getAttribute("idUser"));
        e = (Element) fils.item(3);
        System.out.println("  "+fils.item(3).getNodeName()+" idUser = "+e.getAttribute("idUser"));
        e = (Element) fils.item(4);
        System.out.println("  "+e.getNodeName()+" = "+e.getNodeValue());
        e = (Element) fils.item(5);
        System.out.println("  "+e.getNodeName()+" = "+e.getNodeValue());

        /*Node body = racine.getFirstChild();
		for (int i = 0; i<fils.getLength(); i++) 
		{
            if(fils.item(i).getNodeType() == Node.ELEMENT_NODE) {
				final Element elm = (Element) fils.item(i);
				    
				System.out.println("      "+elm.getNodeName()+" : "+elm.getTextContent());
			}
		  /*if(headerNoeuds.item(i).getNodeType() == Node.ELEMENT_NODE) 
		  {
		    final Element head = (Element) headerNoeuds.item(i);
		    
		    System.out.println("   "+head.getNodeName()+" : Sexe -> "+animal.getAttribute("sexe")+" regime -> "+animal.getAttribute("regime"));
		    NodeList fils = animal.getChildNodes();
		    for (int j = 0; j<fils.getLength(); j++) 
			{
			  if(fils.item(j).getNodeType() == Node.ELEMENT_NODE) 
			  {
				  final Element elm = (Element) fils.item(j);
				    
				    System.out.println("      "+elm.getNodeName()+" : "+elm.getTextContent());
			  }
			}	
		  }				
		}*/
	}
	
	public void affichageXPath() {
		
		try {
			XPathFactory fabrique = XPathFactory.newInstance();
			XPath xpath = fabrique.newXPath();
			
			//XPathExpression expr = xpath.compile("//animal[@sexe='M']");
			XPathExpression expr = xpath.compile("//file");
			//XPathExpression expr = xpath.compile("//animal[1]");
	
			
			NodeList nList = (NodeList)expr.evaluate(doc, XPathConstants.NODESET);
			
			for(int i=0; i<nList.getLength(); i++) {
				Node nNode = nList.item(i);
				if(nNode.getNodeType() == Node.ELEMENT_NODE) {
					System.out.println(nNode.getTextContent());
				}
			}
		}catch (XPathExpressionException e) {
		// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
}
