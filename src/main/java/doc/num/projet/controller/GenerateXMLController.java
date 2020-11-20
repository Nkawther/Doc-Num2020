package doc.num.projet.controller;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;

import javax.inject.Inject;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import doc.num.projet.model.AttributRole;
import doc.num.projet.model.Barter;
import doc.num.projet.model.Header;
import doc.num.projet.repository.HeaderRepository;

@Controller
public class GenerateXMLController {
    @Inject
    HeaderRepository headerrepo;
    
    @RequestMapping("xml-file")
    public String addmessage(Model m, @RequestParam long ind) {
        Header h = headerrepo.findHeaderById(ind);

        try {

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    
            Document doc = docBuilder.newDocument();
            Element file = doc.createElement("file");
            doc.appendChild(file);
    
            Element header = doc.createElement("header");

            Attr idF = doc.createAttribute("idF");
            idF.setValue(String.valueOf(h.getId()));

            Element idMsg = doc.createElement("idMsg");
            idMsg.appendChild(doc.createTextNode(String.valueOf(h.getNbMsg())));
            
            Element transmitter = doc.createElement("transmitter");
            Attr idUsertrans = doc.createAttribute("idUser");
            idUsertrans.setValue(String.valueOf(h.getNameTransmitter()));
            transmitter.setAttributeNode(idUsertrans);

            Element receiver = doc.createElement("receiver");
            Attr idUserrec = doc.createAttribute("IdUser");
            idUserrec.setValue(String.valueOf(h.getNameReceiver()));
            receiver.setAttributeNode(idUserrec);

            Element authRef = doc.createElement("authRef");
            authRef.appendChild(doc.createTextNode(String.valueOf(h.getAuthRef())));
            Element authDate = doc.createElement("authDate");
            authDate.appendChild(doc.createTextNode(String.valueOf(h.getAuthDate())));
    
            header.setAttributeNode(idF);
            header.appendChild(idMsg);
            header.appendChild(transmitter);
            header.appendChild(receiver);
            header.appendChild(authRef);
            header.appendChild(authDate);
            file.appendChild(header);

            Element body = doc.createElement("body");
            Element listMsg = doc.createElement("listMsg");

            for(int i=0; i<h.getLsMessage().size(); i++){
                Element message =  doc.createElement("message");
                Element dateMsg = doc.createElement("dateMsg");
                dateMsg.appendChild(doc.createTextNode(String.valueOf(h.getLsMessage().get(i).getDate())));
                Element validityDuration = doc.createElement("validityDuration");
                validityDuration.appendChild(doc.createTextNode(String.valueOf(h.getLsMessage().get(i).getDate())));
                Element barter = doc.createElement("barter");
                if(h.getLsMessage().get(i).getRoles().contains(AttributRole.BARTER)){ //BARTER
                    Barter b = ((Barter) h.getLsMessage().get(i));
                    Element sndObjectList = doc.createElement("sndObjectList");
                    Element rcvObjectList = doc.createElement("rcvObjectList");
                    for(int j=0; j<b.getSndObjectList().size();j++){
                        Element o = doc.createElement("Object");
                        Element objectName = doc.createElement("objectName");
                        objectName.appendChild(doc.createTextNode(String.valueOf(b.getSndObjectList().get(j).getObjectName())));
                        Element objectDetails = doc.createElement("objectDetails");
                        objectDetails.appendChild(doc.createTextNode(String.valueOf(b.getSndObjectList().get(j).getObjectDetails())));
                        Element objectImage = doc.createElement("objectImage");
                        o.appendChild(objectName);
                        o.appendChild(objectDetails);
                        o.appendChild(objectImage);
                        rcvObjectList.appendChild(o);
                    }
                    barter.appendChild(rcvObjectList);
                    for(int j=0; j<b.getSndObjectList().size();j++){
                        Element o = doc.createElement("Object");
                        Element objectName = doc.createElement("objectName");
                        objectName.appendChild(doc.createTextNode(String.valueOf(b.getSndObjectList().get(j).getObjectName())));
                        Element objectDetails = doc.createElement("objectDetails");
                        objectDetails.appendChild(doc.createTextNode(String.valueOf(b.getSndObjectList().get(j).getObjectDetails())));
                        Element objectImage = doc.createElement("objectImage");
                        o.appendChild(objectName);
                        o.appendChild(objectDetails);
                        o.appendChild(objectImage);
                        sndObjectList.appendChild(o);
                    }
                    barter.appendChild(sndObjectList);
                    Element idPrevMsg = doc.createElement("idPrevMsg");
                    idPrevMsg.appendChild(doc.createTextNode(String.valueOf(b.getIdPrevMsg())));
                    barter.appendChild(idPrevMsg);
                }
                
                message.appendChild(dateMsg);
                message.appendChild(validityDuration);
                message.appendChild(barter);
                listMsg.appendChild(message);
            }
            
            body.appendChild(listMsg);
            file.appendChild(body);

           //write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("file.xml"));
    
           //Output to console for testing
           //StreamResult result = new StreamResult(System.out);
    
            transformer.transform(source, result);
    
            System.out.println("File saved!");
        
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
        
        return "writing";
    }
}
