/*package doc.num.projet;

import org.xml.sax.Attributes;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class MyXMLHandler extends DefaultHandler{

    boolean idMsg = false;
    boolean receiver = false;
    boolean transmitter = false;
    boolean authRef = false;
    boolean authDate = false;
    boolean dateMsg = false;
    boolean validityDuration = false;
    boolean objectName = false;
    boolean objectDetails = false;
    boolean objectImage = false;
    boolean idPrevMsg = false;
	
   //début du parsing
   public void startDocument() {
      System.out.println("Début du parsing\n");
      
   }
   //fin du parsing
   public void endDocument() {
      System.out.println("Fin du parsing\n");
   }  


   public void startElement(String uri, String localName, String pName, String qName, Attributes patts, Attributes qatts) throws SAXException{
	   
	   System.out.println("Starting Element : "+qName);
	   if("header".equals(qName)) {
		   System.out.println("ID_File : "+qatts.getValue("idF"));
       }
       if(qName.compareTo("idMsg")==0) {idMsg = true;}
	   if(qName.compareTo("receiver")==0) {receiver = true;}
	   if(qName.compareTo("transmitter")==0) {transmitter = true;}
	   if(qName.compareTo("authRef")==0) {authRef = true;}
	   if(qName.compareTo("authDate")==0) {authDate = true;}
   }
   
   public void endElement(String uri, String localName, String qName) throws SAXException{
	   System.out.println("End Element :"+qName);
	   if(qName.compareTo("nom")==0) {nom = false;}
	   if(qName.compareTo("race")==0) {race = false;}
	   if(qName.compareTo("habitat")==0) {habitat = false;}
	   if(qName.compareTo("age")==0) {age = false;}
   }

   
   public void characters(char data[], int start, int length) throws SAXException{
      if(race) {
    	  System.out.println("Race : "+new String(data, start, length));
      }
      if(nom) {
    	  System.out.println("Nom : "+new String(data, start, length));
      }
      if(habitat) {
    	  System.out.println("Habitat : "+new String(data, start, length));
      }
      if(age) {
    	  System.out.println("Age : "+new String(data, start, length));
      }
   } 
     
}*/
