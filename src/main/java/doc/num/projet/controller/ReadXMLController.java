package doc.num.projet.controller;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ReadXMLController {
    
    public Document doc;
    
    @RequestMapping("/Parse")
    public String readXml(){
        try {
            File document_xml = new File("src/main/resources/static/file.xml");
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
			System.out.println("\n\n -- Affichage sans XPath avec DOM -- ");
		
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return "redirect:stockage";
    }

    @RequestMapping("/stockage")
    public String aandsXml(Model m){
        Element e;
        NodeList message;

        //recuperation de la racine 
        Element racine = this.doc.getDocumentElement();
        Node header = racine.getChildNodes().item(1);
        m.addAttribute("header", header.getNodeName());
        /*HEADER*/
        if(header.getNodeType() == Node.ELEMENT_NODE) {
            e = (Element) header;
            System.err.println(header.getNodeName()+": ID_File -> "+e.getAttribute("idF"));
            m.addAttribute("idF", e.getAttribute("idF"));
        }
         
        NodeList fils = header.getChildNodes();
        if(fils.item(1).getNodeType() == Node.ELEMENT_NODE) {
            e = (Element) fils.item(1);
            System.err.println("  "+e.getNodeName()+" = "+e.getTextContent());
            m.addAttribute("idMsg", e.getTextContent());
        }
        if(fils.item(3).getNodeType() == Node.ELEMENT_NODE) {
            e = (Element) fils.item(3);
            System.err.println("  "+e.getNodeName()+" idUser = "+e.getAttribute("idUser"));
            m.addAttribute("transmetter", e.getAttribute("idUser"));
        }
        if(fils.item(5).getNodeType() == Node.ELEMENT_NODE) {
            e = (Element) fils.item(5);
            System.err.println("  "+e.getNodeName()+" idUser = "+e.getAttribute("idUser"));
            m.addAttribute("receiver", e.getAttribute("idUser"));
        }
        if(fils.item(7).getNodeType() == Node.ELEMENT_NODE) {
            e = (Element) fils.item(7);
            System.err.println("  "+e.getNodeName()+" = "+e.getTextContent());
            m.addAttribute("authRef", e.getTextContent());
        }
        if(fils.item(9).getNodeType() == Node.ELEMENT_NODE) {
            e = (Element) fils.item(9);
            System.err.println("  "+e.getNodeName()+" = "+e.getTextContent());
            m.addAttribute("auth", e.getTextContent());
        }

        /*BODY*/
        Node body = racine.getChildNodes().item(3);
        System.err.println(body.getNodeName());
        m.addAttribute("body", body.getNodeName());
        Node listMsg =  body.getChildNodes().item(1);
        System.err.println("  "+listMsg.getNodeName());
        NodeList messages = listMsg.getChildNodes(); 

        for(int i=0; i<messages.getLength(); i++){
            if(messages.item(i).getNodeType() == Node.ELEMENT_NODE){
                e = (Element) messages.item(i);
                System.err.println("   "+e.getNodeName());
                message = e.getChildNodes();
                for(int j = 0; j<message.getLength(); j++){
                    if(message.item(j).getNodeType() == Node.ELEMENT_NODE){
                        e = (Element) message.item(j);
                        if(e.getNodeName().equals("dateMsg")){
                            System.err.println("    "+e.getNodeName()+" : "+e.getTextContent());
                            m.addAttribute("dateMsg", e.getTextContent());
                        }
                        if(e.getNodeName().equals("validityDuration")){
                            System.err.println("    "+e.getNodeName()+" : "+e.getTextContent());
                            m.addAttribute("validityDuration", e.getTextContent());
                        }
                        /*TYPE Msg*/
                        if(e.getNodeName().equals("barter")){ //Barter
                            m.addAttribute("typeMsg", e.getNodeName());
                            System.out.println(e.getNodeName());
                            NodeList type = (NodeList) message.item(j); //liste des attributs de barter
                            for(int k=0; k<type.getLength(); k++){
                                if(type.item(k).getNodeType() == Node.ELEMENT_NODE){
                                    e = (Element) type.item(k);
                                    if(e.getNodeName().equals("rcvObjectList")){ 
                                        NodeList rcv = (NodeList) type.item(k); //liste des attributs de rcv de barter   
                                        for(int x=0; x<rcv.getLength(); x++){
                                            if(rcv.item(x).getNodeType() == Node.ELEMENT_NODE){
                                                e = (Element) rcv.item(x);
                                                System.err.println(rcv.item(x).getNodeName()+" : "+e.getAttribute("idObject"));
                                                NodeList object = (NodeList) rcv.item(x); //Liste object rcv de barter
                                                for(int y=0; y<object.getLength(); y++){
                                                    if(object.item(y).getNodeType() == Node.ELEMENT_NODE){
                                                        e = (Element) object.item(y);
                                                        System.err.println("    "+e.getNodeName()+" : "+e.getTextContent());
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    else{
                                        if(e.getNodeName().equals("sndObjectList")){ //liste des attributs de snd de barter
                                            NodeList snd = (NodeList) type.item(k);
                                            for(int x=0; x<snd.getLength(); x++){
                                                if(snd.item(x).getNodeType() == Node.ELEMENT_NODE){
                                                    e = (Element) snd.item(x);
                                                    System.err.println(snd.item(x).getNodeName()+" : "+e.getAttribute("idObject"));
                                                    NodeList object = (NodeList) snd.item(x); //Liste object rcv de barter
                                                    for(int y=0; y<object.getLength(); y++){
                                                        if(object.item(y).getNodeType() == Node.ELEMENT_NODE){
                                                            e = (Element) object.item(y);
                                                            System.err.println("    "+e.getNodeName()+" : "+e.getTextContent());
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        else{
                                            System.err.println("    "+e.getNodeName()+" : "+e.getTextContent());
                                        }
                                    }
                                }    
                            }
                        }
                        else{
                            if(e.getNodeName().equals("request")){ //request
                                m.addAttribute("typeMsg", e.getNodeName());
                                System.out.println(e.getNodeName());
                                NodeList type = (NodeList) message.item(j); //liste des attributs de request
                                for(int k=0; k<type.getLength(); k++){
                                    if(type.item(k).getNodeType() == Node.ELEMENT_NODE){
                                        e = (Element) type.item(k);
                                        if(e.getNodeName().equals("rcvObjectList")){ 
                                            NodeList rcv = (NodeList) type.item(k); //liste des attributs de rcv de request  
                                            for(int x=0; x<rcv.getLength(); x++){
                                                if(rcv.item(x).getNodeType() == Node.ELEMENT_NODE){
                                                    e = (Element) rcv.item(x);
                                                    System.err.println(rcv.item(x).getNodeName()+" : "+e.getAttribute("idObject"));
                                                    NodeList object = (NodeList) rcv.item(x); //Liste object rcv de request
                                                    for(int y=0; y<object.getLength(); y++){
                                                        if(object.item(y).getNodeType() == Node.ELEMENT_NODE){
                                                            e = (Element) object.item(y);
                                                            System.err.println("    "+e.getNodeName()+" : "+e.getTextContent());
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        else{
                                            System.err.println("    "+e.getNodeName()+" : "+e.getTextContent());
                                        }
                                    }
                                }                               
                            }
                            else{
                                if(e.getNodeName().equals("donation")){ //donation
                                    m.addAttribute("typeMsg", e.getNodeName());
                                    System.out.println(e.getNodeName());
                                    NodeList type = (NodeList) message.item(j); //liste des attributs de donation
                                    for(int k=0; k<type.getLength(); k++){
                                        if(type.item(k).getNodeType() == Node.ELEMENT_NODE){
                                            e = (Element) type.item(k);
                                            if(e.getNodeName().equals("sndObjectList")){ 
                                                NodeList snd = (NodeList) type.item(k); //liste des attributs de rcv de donation 
                                                for(int x=0; x<snd.getLength(); x++){
                                                    if(snd.item(x).getNodeType() == Node.ELEMENT_NODE){
                                                        e = (Element) snd.item(x);
                                                        System.err.println(snd.item(x).getNodeName()+" : "+e.getAttribute("idObject"));
                                                        NodeList object = (NodeList) snd.item(x); //Liste object snd de donation
                                                        for(int y=0; y<object.getLength(); y++){
                                                            if(object.item(y).getNodeType() == Node.ELEMENT_NODE){
                                                                e = (Element) object.item(y);
                                                                System.err.println("    "+e.getNodeName()+" : "+e.getTextContent());
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            else{
                                                System.err.println("    "+e.getNodeName()+" : "+e.getTextContent());
                                            }
                                        }
                                    }
                                }
                                else{
                                    if(e.getNodeName().equals("accept")){ //accept
                                        m.addAttribute("typeMsg", e.getNodeName());
                                        System.out.println(e.getNodeName());
                                        NodeList type = (NodeList) message.item(j); //liste des attributs de accept
                                        for(int k=0; k<type.getLength(); k++){
                                            if(type.item(k).getNodeType() == Node.ELEMENT_NODE){
                                                e = (Element) type.item(k);
                                                System.err.println("    "+e.getNodeName()+" : "+e.getTextContent());
                                            }
                                        }
                                    }
                                    else{
                                        if(e.getNodeName().equals("deny")){ //deny
                                            m.addAttribute("typeMsg", e.getNodeName());
                                            System.out.println(e.getNodeName());
                                            NodeList type = (NodeList) message.item(j); //liste des attributs de deny
                                            for(int k=0; k<type.getLength(); k++){
                                                if(type.item(k).getNodeType() == Node.ELEMENT_NODE){
                                                    e = (Element) type.item(k);
                                                    System.err.println("    "+e.getNodeName()+" : "+e.getTextContent());
                                                }
                                            }
                                        }
                                        else{
                                            if(e.getNodeName().equals("auth")){ //auth
                                                m.addAttribute("typeMsg", e.getNodeName());
                                                System.out.println(e.getNodeName());
                                                NodeList type = (NodeList) message.item(j); //liste des attributs de auth
                                                for(int k=0; k<type.getLength(); k++){
                                                    if(type.item(k).getNodeType() == Node.ELEMENT_NODE){
                                                        e = (Element) type.item(k);
                                                        System.err.println("    "+e.getNodeName()+" : "+e.getTextContent());
                                                    }
                                                }
                                            }
                                            else{
                                                if(e.getNodeName().equals("authRequest")){ //authRequest
                                                    m.addAttribute("typeMsg", e.getNodeName());
                                                    System.out.println(e.getNodeName());
                                                    NodeList type = (NodeList) message.item(j); //liste des attributs de authRequest
                                                    for(int k=0; k<type.getLength(); k++){
                                                        if(type.item(k).getNodeType() == Node.ELEMENT_NODE){
                                                            e = (Element) type.item(k);
                                                            System.err.println("    "+e.getNodeName()+" : "+e.getTextContent());
                                                        }
                                                    }
                                                }
                                                else{
                                                    if(e.getNodeName().equals("nocat")){ //nocat
                                                        m.addAttribute("typeMsg", e.getNodeName());
                                                        System.out.println(e.getNodeName());
                                                        NodeList type = (NodeList) message.item(j); //liste des attributs de nocat
                                                        for(int k=0; k<type.getLength(); k++){
                                                            if(type.item(k).getNodeType() == Node.ELEMENT_NODE){
                                                                e = (Element) type.item(k);
                                                                System.err.println("    "+e.getNodeName()+" : "+e.getTextContent());
                                                            }
                                                        }
                                                    }
                                                    else{
                                                        if(e.getNodeName().equals("catRequest")){ //catRequest
                                                            m.addAttribute("typeMsg", e.getNodeName());
                                                            System.out.println(e.getNodeName());
                                                            NodeList type = (NodeList) message.item(j); //liste des attributs de catRequest
                                                            for(int k=0; k<type.getLength(); k++){
                                                                if(type.item(k).getNodeType() == Node.ELEMENT_NODE){
                                                                    e = (Element) type.item(k);
                                                                    System.err.println("    "+e.getNodeName()+" : "+e.getTextContent());
                                                                }
                                                            }
                                                        }
                                                        else{
                                                            if(e.getNodeName().equals("cat")){ //cat
                                                                m.addAttribute("typeMsg", e.getNodeName());
                                                                System.out.println(e.getNodeName());
                                                                NodeList type = (NodeList) message.item(j); //liste des attributs de cat
                                                                for(int k=0; k<type.getLength(); k++){
                                                                    if(type.item(k).getNodeType() == Node.ELEMENT_NODE){
                                                                        e = (Element) type.item(k);
                                                                        if(e.getNodeName().equals("object")){
                                                                            NodeList object = (NodeList) type.item(k); //Liste object snd de donation
                                                                            for(int y=0; y<object.getLength(); y++){
                                                                                if(object.item(y).getNodeType() == Node.ELEMENT_NODE){
                                                                                    e = (Element) object.item(y);
                                                                                    System.err.println("    "+e.getNodeName()+" : "+e.getTextContent());
                                                                                }
                                                                            }
                                                                        }
                                                                        else{
                                                                            System.err.println("    "+e.getNodeName()+" : "+e.getTextContent());
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                            if(e.getNodeName().equals("errorMsg")){ //errorMsg
                                                                m.addAttribute("typeMsg", e.getNodeName());
                                                                System.out.println("  "+e.getNodeName()+" : idMsg "+e.getAttribute("idMsg")+" , idError"+ e.getAttribute("idError"));
                                                                NodeList type = (NodeList) message.item(j); //liste des attributs de errorMsg
                                                                for(int k=0; k<type.getLength(); k++){
                                                                    if(type.item(k).getNodeType() == Node.ELEMENT_NODE){
                                                                        e = (Element) type.item(k);
                                                                        System.err.println("    "+e.getNodeName()+" : "+e.getTextContent());
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            
                        }
                    }
                    
                }
            }
        }
        return "reading";
    }
}
