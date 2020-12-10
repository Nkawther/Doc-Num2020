package doc.num.projet.controller;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;

import javax.inject.Inject;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import doc.num.projet.model.Accept;
import doc.num.projet.model.AttributRole;
import doc.num.projet.model.Auth;
import doc.num.projet.model.Barter;
import doc.num.projet.model.Catalogue;
import doc.num.projet.model.Deny;
import doc.num.projet.model.Donation;
import doc.num.projet.model.ErrorMsg;
import doc.num.projet.model.Header;
import doc.num.projet.model.NoCatalogue;
import doc.num.projet.model.Request;
import doc.num.projet.repository.HeaderRepository;
import doc.num.projet.repository.UserRepository;

@Controller
public class GenerateXMLController {
    @Inject
    HeaderRepository headerrepo;
    @Inject
    UserRepository userrepo;

    @RequestMapping(value = "xml-file", method = RequestMethod.POST)
    public String addmessage(Model m, @RequestParam String idLong, @RequestParam String filee) {
        long monLong = Long.parseLong(idLong);
        Header h = headerrepo.findHeaderById(monLong);

        try {

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            Document doc = docBuilder.newDocument();
            Element file = doc.createElement("file");
            doc.appendChild(file);

            Element header = doc.createElement("header");

            Attr idF = doc.createAttribute("idF");
            idF.setValue(String.valueOf(h.getId()));

            Element idMsg = doc.createElement("nbMsg");
            idMsg.appendChild(doc.createTextNode(String.valueOf(h.getNbMsg())));

            Element transmitter = doc.createElement("transmitter");
            Attr idUsertrans = doc.createAttribute("idUser");
            idUsertrans.setValue(String.valueOf(h.getIdTransmitter()));
            transmitter.appendChild(doc.createTextNode(userrepo.findHeaderById(h.getIdTransmitter()).getName()));
            transmitter.setAttributeNode(idUsertrans);

            Element receiver = doc.createElement("receiver");
            Attr idUserrec = doc.createAttribute("idUser");
            idUserrec.setValue(String.valueOf(h.getIdReceiver()));
            receiver.appendChild(doc.createTextNode(userrepo.findHeaderById(h.getIdReceiver()).getName()));
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

            for (int i = 0; i < h.getLsMessage().size(); i++) {
                Element message = doc.createElement("message");
                Attr idmsg = doc.createAttribute("idMsg");
                idmsg.setValue(String.valueOf(h.getLsMessage().get(i).getId()));
                Element dateMsg = doc.createElement("dateMsg");
                dateMsg.appendChild(doc.createTextNode(String.valueOf(h.getLsMessage().get(i).getDate())));
                Element validityDuration = doc.createElement("validityDuration");
                validityDuration.appendChild(doc.createTextNode(String.valueOf(h.getLsMessage().get(i).getDate())));
                message.appendChild(dateMsg);
                message.appendChild(validityDuration);
                message.setAttributeNode(idmsg);
                Element barter = doc.createElement("barter");
                Element request = doc.createElement("request");
                Element donation = doc.createElement("donation");
                Element authrequest = doc.createElement("authRequest");
                Element auth = doc.createElement("auth");
                Element accept = doc.createElement("accept");
                Element deny = doc.createElement("deny");
                Element catRequest = doc.createElement("catRequest");
                Element nocat = doc.createElement("nocat");
                Element cat = doc.createElement("cat");
                Element error = doc.createElement("errorMsg");

                if (h.getLsMessage().get(i).getRoles().contains(AttributRole.BARTER)) { // BARTER
                    Barter b = ((Barter) h.getLsMessage().get(i));
                    Element sndObjectList = doc.createElement("sndObjectList");
                    Element rcvObjectList = doc.createElement("rcvObjectList");
                    for (int j = 0; j < b.getRcvObjectList().size(); j++) {
                        Element o = doc.createElement("object");
                        Element objectName = doc.createElement("objectName");
                        objectName.appendChild(
                                doc.createTextNode(String.valueOf(b.getRcvObjectList().get(j).getObjectName())));
                        Element objectDetails = doc.createElement("objectDetails");
                        objectDetails.appendChild(
                                doc.createTextNode(String.valueOf(b.getRcvObjectList().get(j).getObjectDetails())));
                        Element objectImage = doc.createElement("objectImage");
                        objectImage.appendChild((doc.createTextNode("Any")));
                        o.appendChild(objectName);
                        o.appendChild(objectDetails);
                        o.appendChild(objectImage);
                        rcvObjectList.appendChild(o);
                    }
                    barter.appendChild(rcvObjectList);
                    for (int j = 0; j < b.getSndObjectList().size(); j++) {
                        Element o = doc.createElement("object");
                        Element objectName = doc.createElement("objectName");
                        objectName.appendChild(
                                doc.createTextNode(String.valueOf(b.getSndObjectList().get(j).getObjectName())));
                        Element objectDetails = doc.createElement("objectDetails");
                        objectDetails.appendChild(
                                doc.createTextNode(String.valueOf(b.getSndObjectList().get(j).getObjectDetails())));
                        Element objectImage = doc.createElement("objectImage");
                        objectImage.appendChild((doc.createTextNode("Any")));
                        o.appendChild(objectName);
                        o.appendChild(objectDetails);
                        o.appendChild(objectImage);
                        sndObjectList.appendChild(o);
                    }
                    barter.appendChild(sndObjectList);
                    Element idPrevMsg = doc.createElement("idPrevMsg");
                    idPrevMsg.appendChild(doc.createTextNode(String.valueOf(b.getIdPrevMsg())));
                    barter.appendChild(idPrevMsg);
                    message.appendChild(barter);
                }

                else {
                    if (h.getLsMessage().get(i).getRoles().contains(AttributRole.REQUEST)) { // REQUEST
                        Request b = ((Request) h.getLsMessage().get(i));
                        Element rcvObjectList = doc.createElement("rcvObjectList");
                        for (int j = 0; j < b.getListMsgRcv().size(); j++) {
                            Element o = doc.createElement("object");
                            Element objectName = doc.createElement("objectName");
                            objectName.appendChild(
                                    doc.createTextNode(String.valueOf(b.getListMsgRcv().get(j).getObjectName())));
                            Element objectDetails = doc.createElement("objectDetails");
                            objectDetails.appendChild(
                                    doc.createTextNode(String.valueOf(b.getListMsgRcv().get(j).getObjectDetails())));
                            Element objectImage = doc.createElement("objectImage");
                            objectImage.appendChild((doc.createTextNode("Any")));
                            o.appendChild(objectName);
                            o.appendChild(objectDetails);
                            o.appendChild(objectImage);
                            rcvObjectList.appendChild(o);
                        }
                        request.appendChild(rcvObjectList);

                        Element idPrevMsg = doc.createElement("idPrevMsg");
                        idPrevMsg.appendChild(doc.createTextNode(String.valueOf(b.getIdPrevMsg())));
                        request.appendChild(idPrevMsg);
                        message.appendChild(request);
                    } else {
                        if (h.getLsMessage().get(i).getRoles().contains(AttributRole.DONATION)) { // DONATION
                            Donation b = ((Donation) h.getLsMessage().get(i));
                            Element sndObjectList = doc.createElement("sndObjectList");
                            for (int j = 0; j < b.getListMsgSnd().size(); j++) {
                                Element o = doc.createElement("object");
                                Element objectName = doc.createElement("objectName");
                                objectName.appendChild(
                                        doc.createTextNode(String.valueOf(b.getListMsgSnd().get(j).getObjectName())));
                                Element objectDetails = doc.createElement("objectDetails");
                                objectDetails.appendChild(doc
                                        .createTextNode(String.valueOf(b.getListMsgSnd().get(j).getObjectDetails())));
                                Element objectImage = doc.createElement("objectImage");
                                objectImage.appendChild((doc.createTextNode("Any")));
                                o.appendChild(objectName);
                                o.appendChild(objectDetails);
                                o.appendChild(objectImage);
                                sndObjectList.appendChild(o);
                            }
                            donation.appendChild(sndObjectList);

                            Element idPrevMsg = doc.createElement("idPrevMsg");
                            idPrevMsg.appendChild(doc.createTextNode(String.valueOf(b.getIdPrevMsg())));
                            donation.appendChild(idPrevMsg);
                            message.appendChild(donation);
                        } else {
                            if (h.getLsMessage().get(i).getRoles().contains(AttributRole.AUTHREQUEST)) { // AUTHREQUEST
                                message.appendChild(authrequest);
                            } else {
                                if (h.getLsMessage().get(i).getRoles().contains(AttributRole.AUTH)) { // AUTH
                                    Auth b = ((Auth) h.getLsMessage().get(i));
                                    Element authR = doc.createElement("authRef");
                                    authR.appendChild(doc.createTextNode(String.valueOf(b.getAuthDate())));
                                    auth.appendChild(authR);
                                    message.appendChild(auth);
                                } else {
                                    if (h.getLsMessage().get(i).getRoles().contains(AttributRole.ACCEPT)) { // ACCEPT
                                        Accept b = ((Accept) h.getLsMessage().get(i));
                                        Element id = doc.createElement("idPrevMsg");
                                        id.appendChild(doc.createTextNode(String.valueOf(b.getIdPropositionMsg())));
                                        accept.appendChild(id);
                                        message.appendChild(accept);
                                    } else {
                                        if (h.getLsMessage().get(i).getRoles().contains(AttributRole.DENY)) { // DENY
                                            Deny b = ((Deny) h.getLsMessage().get(i));
                                            Element id = doc.createElement("idPropositionMsg");
                                            id.appendChild(doc.createTextNode(String.valueOf(b.getIdPropositionMsg())));
                                            Element id2 = doc.createElement("reason");
                                            id2.appendChild(doc.createTextNode(String.valueOf(b.getReason())));
                                            deny.appendChild(id);
                                            deny.appendChild(id2);
                                            message.appendChild(deny);
                                        } else {
                                            if (h.getLsMessage().get(i).getRoles().contains(AttributRole.CATREQUEST)) { // CATREQUEST
                                                message.appendChild(catRequest);
                                            } else {
                                                if (h.getLsMessage().get(i).getRoles().contains(AttributRole.NOCAT)) { // NOCAT
                                                    NoCatalogue b = ((NoCatalogue) h.getLsMessage().get(i));
                                                    Element id = doc.createElement("idCatRequestMsg");
                                                    id.appendChild(doc.createTextNode(b.getIdCatRequestMsg()));
                                                    Element id2 = doc.createElement("reason");
                                                    id2.appendChild(doc.createTextNode(String.valueOf(b.getReason())));
                                                    nocat.appendChild(id);
                                                    nocat.appendChild(id2);
                                                    message.appendChild(nocat);
                                                } else {
                                                    if (h.getLsMessage().get(i).getRoles().contains(AttributRole.CAT)) { // CAT
                                                        System.err.println("1");
                                                        Catalogue b = ((Catalogue) h.getLsMessage().get(i));
                                                        System.err.println("2");
                                                        Element id = doc.createElement("idCatRequestMsg");
                                                        id.appendChild(doc.createTextNode(b.getIdCatRequestMsg()));
                                                        Element date = doc.createElement("catDate");
                                                        date.appendChild(
                                                                doc.createTextNode(String.valueOf(b.getCatDate())));
                                                        cat.appendChild(id);
                                                        cat.appendChild(date);
                                                        System.err.println(3);
                                                        for (int j = 0; j < b.getCatObjectList().size(); j++) {
                                                            Element o = doc.createElement("object");
                                                            Element objectName = doc.createElement("objectName");
                                                            objectName.appendChild(doc.createTextNode(String.valueOf(
                                                                    b.getCatObjectList().get(j).getObjectName())));
                                                            Element objectDetails = doc.createElement("objectDetails");
                                                            objectDetails.appendChild(doc.createTextNode(String.valueOf(
                                                                    b.getCatObjectList().get(j).getObjectDetails())));
                                                            Element objectImage = doc.createElement("objectImage");
                                                            objectImage.appendChild((doc.createTextNode("Any")));
                                                            o.appendChild(objectName);
                                                            o.appendChild(objectDetails);
                                                            o.appendChild(objectImage);
                                                            cat.appendChild(o);
                                                        }
                                                        message.appendChild(cat);
                                                    } else {
                                                        if (h.getLsMessage().get(i).getRoles()
                                                                .contains(AttributRole.ERRORMSG)) { // ERRORMSG
                                                            ErrorMsg b = ((ErrorMsg) h.getLsMessage().get(i));
                                                            Attr idMsge = doc.createAttribute("idMsg");
                                                            idMsge.setValue(String.valueOf(b.getIdMsg()));
                                                            Attr idError = doc.createAttribute("idError");
                                                            idError.setValue(String.valueOf(b.getIdError()));
                                                            error.setAttributeNode(idMsge);
                                                            error.setAttributeNode(idError);
                                                            message.appendChild(error);
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

                listMsg.appendChild(message);
            }

            body.appendChild(listMsg);
            file.appendChild(body);

            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "final.dtd");

            transformer.setOutputProperty("{http://xml.apache.org/xslt%7Dindent-amount", "4");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(filee));

            // Output to console for testing
            // StreamResult result = new StreamResult(System.out);

            transformer.transform(source, result);

            System.out.println("File saved!");

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }

        return "redirect:reading";
    }
}
