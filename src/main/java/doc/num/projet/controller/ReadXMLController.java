package doc.num.projet.controller;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import doc.num.projet.model.Barter;
import doc.num.projet.model.Catalogue;
import doc.num.projet.model.CatalogueRequest;
import doc.num.projet.model.Deny;
import doc.num.projet.model.Donation;
import doc.num.projet.model.ErrorMsg;

import javax.inject.Inject;

import org.apache.tomcat.util.http.fileupload.DeferredFileOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import doc.num.projet.repository.AcceptRepository;
import doc.num.projet.repository.AuthRepository;
import doc.num.projet.repository.AuthRequestRepository;
import doc.num.projet.repository.BarterRepository;
import doc.num.projet.repository.CatalogueRepository;
import doc.num.projet.repository.CatalogueRequestRepository;
import doc.num.projet.repository.DenyRepository;
import doc.num.projet.repository.DonationRepository;
import doc.num.projet.repository.ErrorMsgRepository;
import doc.num.projet.repository.HeaderRepository;
import doc.num.projet.repository.MessageRepository;
import doc.num.projet.repository.NoCatalogueRepository;
import doc.num.projet.repository.ObjectsRepository;
import doc.num.projet.repository.RequestRepository;
import doc.num.projet.repository.UserRepository;
import doc.num.projet.model.Header;
import doc.num.projet.model.NoCatalogue;
import doc.num.projet.model.Objects;
import doc.num.projet.model.Request;
import doc.num.projet.model.Accept;
import doc.num.projet.model.Auth;
import doc.num.projet.model.AuthRequest;

@Controller
public class ReadXMLController {

    @Autowired
    ObjectsRepository objrepo;

    @Inject
    HeaderRepository headerrepo;
    @Inject
    AcceptRepository acceptrepo;
    @Inject
    AuthRepository authrepo;
    @Inject
    AuthRequestRepository authreqrepo;
    @Inject
    BarterRepository barterrepo;
    @Inject
    CatalogueRepository catrepo;
    @Inject
    CatalogueRequestRepository catreqrepo;
    @Inject
    DenyRepository denyrepo;
    @Inject
    DonationRepository donarepo;
    @Inject
    ErrorMsgRepository errormsgrepo;
    @Inject
    MessageRepository msgrepo;
    @Inject
    NoCatalogueRepository nocatrepo;
    @Inject
    RequestRepository reqrepo;
    @Inject
    UserRepository userrepo;

    public Document doc;

    @RequestMapping("/Parse")
    public String readXml() {
        try {
            File document_xml = new File("src/main/resources/static/generer.xml");
            // Instanciation de la fabrique de parseurs
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();

            // activation de la validation (DTD)
            dbFactory.setValidating(true);

            // création du parseur DOM
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            this.doc = (Document) dBuilder.parse(document_xml);

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
    public String aandsXml(Model m) throws DOMException, ParseException {
        Element e, nbmsg = doc.getDocumentElement();
        Header h = new Header();
        NodeList messages, message;
        int p = 0;
        // List<Integer> idprev = new ArrayList<>();
        // List<Integer> idmsg = new ArrayList<>();

        // recuperation de la racine
        Element racine = this.doc.getDocumentElement();
        NodeList file = ((NodeList) racine);
        for (int a = 0; a < file.getLength(); a++) {
            if (file.item(a).getNodeType() == Node.ELEMENT_NODE) {
                /* Lecture du header */
                if (file.item(a).getNodeName().equals("header")) { /* HEADER */

                    NodeList header = ((NodeList) file.item(a));
                    e = ((Element) header);
                    m.addAttribute("header", e.getNodeName());
                    System.err.println(e.getNodeName() + ": ID_File -> " + e.getAttribute("idF"));
                    m.addAttribute("idF", e.getAttribute("idF"));
                    for (int j = 0; j < header.getLength(); j++) {
                        if (header.item(j).getNodeType() == Node.ELEMENT_NODE) {
                            e = (Element) header.item(j);
                            if (e.getNodeName().equals("nbMsg")) {
                                nbmsg = e;
                                h.setNbMsg(Integer.valueOf(e.getTextContent()));
                                System.err.println("  " + nbmsg.getNodeName() + " = " + nbmsg.getTextContent());
                                m.addAttribute("nbMsg", nbmsg.getTextContent());
                            }
                            if (e.getNodeName().equals("transmitter")) {
                                System.err.println("  " + e.getNodeName() + " idUser = " + e.getAttribute("idUser"));
                                h.setIdTransmitter(Long.valueOf(e.getAttribute("idUser")));
                                m.addAttribute("transmetter", e.getAttribute("idUser"));
                            }
                            if (e.getNodeName().equals("receiver")) {
                                System.err.println("  " + e.getNodeName() + " idUser = " + e.getAttribute("idUser"));
                                h.setIdReceiver(Long.valueOf(e.getAttribute("idUser")));
                                m.addAttribute("receiver", e.getAttribute("idUser"));
                            }
                            if (e.getNodeName().equals("authRef")) {
                                System.err.println("  " + e.getNodeName() + " = " + e.getTextContent());
                                h.setAuthRef(e.getTextContent());
                                m.addAttribute("authRef", e.getTextContent());
                            }
                            if (e.getNodeName().equals("authDate")) {
                                System.err.println("  " + e.getNodeName() + " = " + e.getTextContent());
                                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                                h.setAuthDate(formatter.parse(e.getTextContent()));
                                m.addAttribute("auth", e.getTextContent());
                            }

                        }
                    }
                }
                if (file.item(a).getNodeName().equals("body")) {
                    /* BODY */
                    NodeList body = ((NodeList) file.item(a));
                    e = ((Element) body);
                    m.addAttribute("body", e.getNodeName());
                    System.err.println(e.getNodeName());
                    for (int d = 0; d < body.getLength(); d++) {
                        if (body.item(d).getNodeType() == Node.ELEMENT_NODE) {
                            e = (Element) body.item(d);
                            System.err.println("  " + e.getNodeName());
                            messages = ((NodeList) body.item(d));
                            int n = Integer.parseInt(nbmsg.getTextContent()); // convertir le nbmsg en int
                            for (int s = 0; s < messages.getLength(); s++) {
                                if (messages.item(s).getNodeType() == Node.ELEMENT_NODE) {
                                    p++;
                                }
                            }
                            System.err.println(p);
                            if (p <= n) { // Test si le nombre de message est respecté
                                for (int i = 0; i < messages.getLength(); i++) { // parcourir la liste des messages
                                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                                    Date date = new Date();
                                    Date validity = new Date();
                                    if (messages.item(i).getNodeType() == Node.ELEMENT_NODE) {
                                        e = (Element) messages.item(i);
                                        System.err.println("   " + e.getNodeName());
                                        message = e.getChildNodes();
                                        for (int j = 0; j < message.getLength(); j++) {
                                            if (message.item(j).getNodeType() == Node.ELEMENT_NODE) {
                                                e = (Element) message.item(j);
                                                if (e.getNodeName().equals("dateMsg")) {
                                                    System.err.println(
                                                            "    " + e.getNodeName() + " : " + e.getTextContent());
                                                    date = formatter.parse(e.getTextContent());

                                                    m.addAttribute("dateMsg", e.getTextContent());
                                                }
                                                if (e.getNodeName().equals("validityDuration")) {
                                                    System.err.println(
                                                            "    " + e.getNodeName() + " : " + e.getTextContent());
                                                    validity = formatter.parse(e.getTextContent());
                                                    m.addAttribute("validityDuration", e.getTextContent());
                                                }
                                                /* TYPE Msg */
                                                if (e.getNodeName().equals("barter")) { // Barter
                                                    // Initialisation Date et validity
                                                    Barter b = new Barter();
                                                    b.setDate(date);
                                                    b.setValidity(validity);
                                                    m.addAttribute("typeMsg", e.getNodeName());
                                                    System.out.println(e.getNodeName());
                                                    NodeList type = (NodeList) message.item(j); // liste des attributs
                                                                                                // de barter
                                                    for (int k = 0; k < type.getLength(); k++) {
                                                        if (type.item(k).getNodeType() == Node.ELEMENT_NODE) {
                                                            e = (Element) type.item(k);
                                                            if (e.getNodeName().equals("rcvObjectList")) {
                                                                NodeList rcv = (NodeList) type.item(k); // liste des
                                                                                                        // attributs de
                                                                                                        // rcv de barter
                                                                for (int x = 0; x < rcv.getLength(); x++) {
                                                                    Objects osnd = new Objects();
                                                                    if (rcv.item(x)
                                                                            .getNodeType() == Node.ELEMENT_NODE) {
                                                                        e = (Element) rcv.item(x);
                                                                        System.err.println(rcv.item(x).getNodeName()
                                                                                + " : " + e.getAttribute("idObject"));
                                                                        NodeList object = (NodeList) rcv.item(x); // Liste
                                                                                                                  // object
                                                                                                                  // rcv
                                                                                                                  // de
                                                                                                                  // barter
                                                                        for (int y = 0; y < object.getLength(); y++) {

                                                                            if (object.item(y)
                                                                                    .getNodeType() == Node.ELEMENT_NODE) {
                                                                                e = (Element) object.item(y);
                                                                                if (e.getNodeName()
                                                                                        .equals("objectName")) {
                                                                                    osnd.setObjectName(
                                                                                            e.getTextContent());

                                                                                } else if (e.getNodeName()
                                                                                        .equals("objectDetails")) {
                                                                                    osnd.setObjectDetails(
                                                                                            e.getTextContent());

                                                                                    b.addsnd(osnd);
                                                                                    objrepo.save(osnd);

                                                                                }

                                                                                System.err.println(
                                                                                        "    " + e.getNodeName() + " : "
                                                                                                + e.getTextContent());
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            } else {
                                                                if (e.getNodeName().equals("sndObjectList")) { // liste
                                                                                                               // des
                                                                                                               // attributs
                                                                                                               // de snd
                                                                                                               // de
                                                                                                               // barter
                                                                    NodeList snd = (NodeList) type.item(k);
                                                                    for (int x = 0; x < snd.getLength(); x++) {
                                                                        Objects orcv = new Objects();
                                                                        if (snd.item(x)
                                                                                .getNodeType() == Node.ELEMENT_NODE) {
                                                                            e = (Element) snd.item(x);
                                                                            System.err.println(snd.item(x).getNodeName()
                                                                                    + "  : "
                                                                                    + e.getAttribute("idObject"));
                                                                            NodeList object = (NodeList) snd.item(x); // Liste
                                                                                                                      // object
                                                                                                                      // rcv
                                                                                                                      // de
                                                                                                                      // barter
                                                                            for (int y = 0; y < object
                                                                                    .getLength(); y++) {

                                                                                if (object.item(y)
                                                                                        .getNodeType() == Node.ELEMENT_NODE) {
                                                                                    e = (Element) object.item(y);
                                                                                    if (e.getNodeName()
                                                                                            .equals("objectName")) {
                                                                                        orcv.setObjectName(
                                                                                                e.getTextContent());

                                                                                    } else if (e.getNodeName()
                                                                                            .equals("objectDetails")) {
                                                                                        orcv.setObjectDetails(
                                                                                                e.getTextContent());

                                                                                        b.addrcv(orcv);
                                                                                        objrepo.save(orcv);

                                                                                    }
                                                                                    System.err.println("   "
                                                                                            + e.getNodeName() + " : "
                                                                                            + e.getTextContent());
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                } else {
                                                                    System.err.println("    " + e.getNodeName() + " : "
                                                                            + e.getTextContent());
                                                                    b.setIdPrevMsg(e.getTextContent()); // idprev dans
                                                                                                        // barter
                                                                    // idprev.add(Integer.parseInt(e.getTextContent()));
                                                                }
                                                            }
                                                        }
                                                    }
                                                    barterrepo.save(b);
                                                    h.getLsMessage().add(b);
                                                } else {
                                                    if (e.getNodeName().equals("request")) { // request
                                                        // Initialisation Date et validity
                                                        Request r = new Request();
                                                        r.setDate(date);
                                                        r.setValidity(validity);
                                                        m.addAttribute("typeMsg", e.getNodeName());
                                                        System.out.println(e.getNodeName());
                                                        NodeList type = (NodeList) message.item(j); // liste des
                                                                                                    // attributs de
                                                                                                    // request
                                                        for (int k = 0; k < type.getLength(); k++) {
                                                            if (type.item(k).getNodeType() == Node.ELEMENT_NODE) {
                                                                e = (Element) type.item(k);
                                                                if (e.getNodeName().equals("rcvObjectList")) {
                                                                    NodeList rcv = (NodeList) type.item(k); // liste des
                                                                                                            // attributs
                                                                                                            // de rcv de
                                                                                                            // request
                                                                    for (int x = 0; x < rcv.getLength(); x++) {
                                                                        Objects o = new Objects();
                                                                        if (rcv.item(x)
                                                                                .getNodeType() == Node.ELEMENT_NODE) {
                                                                            e = (Element) rcv.item(x);
                                                                            System.err.println(rcv.item(x).getNodeName()
                                                                                    + " BONJOUR: "
                                                                                    + e.getAttribute("idObject"));
                                                                            NodeList object = (NodeList) rcv.item(x); // Liste
                                                                                                                      // object
                                                                                                                      // rcv
                                                                                                                      // de
                                                                                                                      // request
                                                                            for (int y = 0; y < object
                                                                                    .getLength(); y++) {
                                                                                if (object.item(y)
                                                                                        .getNodeType() == Node.ELEMENT_NODE) {
                                                                                    e = (Element) object.item(y);
                                                                                    System.err
                                                                                            .println("  BONJOURssssss  "
                                                                                                    + e.getNodeName()
                                                                                                    + " : "
                                                                                                    + e.getTextContent());
                                                                                    if (e.getNodeName()
                                                                                            .equals("objectName")) {
                                                                                        o.setObjectName(
                                                                                                e.getTextContent());

                                                                                    } else if (e.getNodeName()
                                                                                            .equals("objectDetails")) {
                                                                                        o.setObjectDetails(
                                                                                                e.getTextContent());

                                                                                        r.getListMsgRcv().add(o);
                                                                                        objrepo.save(o);

                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                } else {
                                                                    System.err.println("    " + e.getNodeName() + " : "
                                                                            + e.getTextContent()); // idprev of request
                                                                    r.setIdPrevMsg(e.getTextContent());
                                                                    // idprev.add(Integer.parseInt(e.getTextContent()));
                                                                }
                                                            }
                                                        }
                                                        h.getLsMessage().add(r);
                                                        reqrepo.save(r);
                                                    } else {
                                                        if (e.getNodeName().equals("donation")) { // donation
                                                            // Initialisation Date et validity
                                                            Donation donate = new Donation();
                                                            donate.setDate(date);
                                                            donate.setValidity(validity);
                                                            m.addAttribute("typeMsg", e.getNodeName());
                                                            System.out.println(e.getNodeName());
                                                            NodeList type = (NodeList) message.item(j); // liste des
                                                                                                        // attributs de
                                                                                                        // donation
                                                            for (int k = 0; k < type.getLength(); k++) {
                                                                if (type.item(k).getNodeType() == Node.ELEMENT_NODE) {
                                                                    e = (Element) type.item(k);
                                                                    if (e.getNodeName().equals("sndObjectList")) {
                                                                        NodeList snd = (NodeList) type.item(k); // liste
                                                                                                                // des
                                                                                                                // attributs
                                                                                                                // de
                                                                                                                // rcv
                                                                                                                // de
                                                                                                                // donation
                                                                        for (int x = 0; x < snd.getLength(); x++) {
                                                                            Objects o = new Objects();
                                                                            if (snd.item(x)
                                                                                    .getNodeType() == Node.ELEMENT_NODE) {
                                                                                e = (Element) snd.item(x);
                                                                                System.err.println(snd.item(x)
                                                                                        .getNodeName() + " : "
                                                                                        + e.getAttribute("idObject"));
                                                                                NodeList object = (NodeList) snd
                                                                                        .item(x); // Liste object snd de
                                                                                                  // donation
                                                                                for (int y = 0; y < object
                                                                                        .getLength(); y++) {
                                                                                    if (object.item(y)
                                                                                            .getNodeType() == Node.ELEMENT_NODE) {
                                                                                        e = (Element) object.item(y);
                                                                                        System.err.println("    "
                                                                                                + e.getNodeName()
                                                                                                + " : "
                                                                                                + e.getTextContent());
                                                                                        if (e.getNodeName()
                                                                                                .equals("objectName")) {
                                                                                            o.setObjectName(
                                                                                                    e.getTextContent());

                                                                                        } else if (e.getNodeName()
                                                                                                .equals("objectDetails")) {
                                                                                            o.setObjectDetails(
                                                                                                    e.getTextContent());

                                                                                            donate.getListMsgSnd()
                                                                                                    .add(o);
                                                                                            objrepo.save(o);

                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    } else {
                                                                        donate.setIdPrevMsg(e.getTextContent());
                                                                        System.err.println("    " + e.getNodeName()
                                                                                + " : " + e.getTextContent()); // idprev
                                                                                                               // de
                                                                                                               // donation
                                                                        // idprev.add(Integer.parseInt(e.getTextContent()));
                                                                    }
                                                                }
                                                            }
                                                            h.getLsMessage().add(donate);
                                                            donarepo.save(donate);
                                                        } else {
                                                            if (e.getNodeName().equals("accept")) { // accept
                                                                // Initialisation Date et validity
                                                                Accept acc = new Accept();
                                                                acc.setDate(date);
                                                                acc.setValidity(validity);
                                                                m.addAttribute("typeMsg", e.getNodeName());
                                                                System.out.println(e.getNodeName());
                                                                NodeList type = (NodeList) message.item(j); // liste des
                                                                                                            // attributs
                                                                                                            // de accept
                                                                for (int k = 0; k < type.getLength(); k++) {
                                                                    if (type.item(k)
                                                                            .getNodeType() == Node.ELEMENT_NODE) {
                                                                        e = (Element) type.item(k);
                                                                        System.err.println("    " + e.getNodeName()
                                                                                + " : " + e.getTextContent());
                                                                        acc.setIdPropositionMsg(e.getTextContent());
                                                                    }
                                                                }
                                                                h.getLsMessage().add(acc);
                                                                acceptrepo.save(acc);
                                                            } else {
                                                                if (e.getNodeName().equals("deny")) { // deny

                                                                    // Initialisation Date et validity
                                                                    Deny den = new Deny();
                                                                    den.setDate(date);
                                                                    den.setValidity(validity);
                                                                    m.addAttribute("typeMsg", e.getNodeName());
                                                                    System.out.println(e.getNodeName());
                                                                    NodeList type = (NodeList) message.item(j); // liste
                                                                                                                // des
                                                                                                                // attributs
                                                                                                                // de
                                                                                                                // deny
                                                                    for (int k = 0; k < type.getLength(); k++) {
                                                                        if (type.item(k)
                                                                                .getNodeType() == Node.ELEMENT_NODE) {
                                                                            e = (Element) type.item(k);
                                                                            System.err.println("    " + e.getNodeName()
                                                                                    + " : " + e.getTextContent());
                                                                            if (e.getNodeName()
                                                                                    .equals("idPropositionMsg")) {
                                                                                den.setIdPropositionMsg(
                                                                                        e.getTextContent());
                                                                            } else {
                                                                                den.setReason(e.getTextContent());
                                                                            }
                                                                        }
                                                                    }
                                                                    h.getLsMessage().add(den);
                                                                    denyrepo.save(den);
                                                                } else {
                                                                    if (e.getNodeName().equals("auth")) { // auth
                                                                        // Initialisation Date et validity
                                                                        Auth aut = new Auth();
                                                                        aut.setDate(date);
                                                                        aut.setValidity(validity);
                                                                        m.addAttribute("typeMsg", e.getNodeName());
                                                                        System.out.println(e.getNodeName());
                                                                        NodeList type = (NodeList) message.item(j); // liste
                                                                                                                    // des
                                                                                                                    // attributs
                                                                                                                    // de
                                                                                                                    // auth
                                                                        for (int k = 0; k < type.getLength(); k++) {
                                                                            if (type.item(k)
                                                                                    .getNodeType() == Node.ELEMENT_NODE) {
                                                                                e = (Element) type.item(k);
                                                                                System.err.println(
                                                                                        "    " + e.getNodeName() + " : "
                                                                                                + e.getTextContent());
                                                                                aut.setAuthDate(formatter
                                                                                        .parse(e.getTextContent()));
                                                                            }
                                                                        }
                                                                        h.getLsMessage().add(aut);
                                                                        authrepo.save(aut);
                                                                    } else {
                                                                        if (e.getNodeName().equals("authRequest")) { // authRequest
                                                                            // Initialisation Date et validity
                                                                            AuthRequest autreq = new AuthRequest();
                                                                            autreq.setDate(date);
                                                                            autreq.setValidity(validity);
                                                                            m.addAttribute("typeMsg", e.getNodeName());
                                                                            System.out.println(e.getNodeName());
                                                                            NodeList type = (NodeList) message.item(j); // liste
                                                                                                                        // des
                                                                                                                        // attributs
                                                                                                                        // de
                                                                                                                        // authRequest
                                                                            for (int k = 0; k < type.getLength(); k++) {
                                                                                if (type.item(k)
                                                                                        .getNodeType() == Node.ELEMENT_NODE) {
                                                                                    e = (Element) type.item(k);
                                                                                    System.err.println("    "
                                                                                            + e.getNodeName() + " : "
                                                                                            + e.getTextContent());
                                                                                }
                                                                            }
                                                                            h.getLsMessage().add(autreq);
                                                                            authreqrepo.save(autreq);
                                                                        } else {

                                                                            if (e.getNodeName().equals("nocat")) { // nocat
                                                                                // Initialisation Date et validity
                                                                                NoCatalogue nocat = new NoCatalogue();
                                                                                nocat.setDate(date);
                                                                                nocat.setValidity(validity);
                                                                                m.addAttribute("typeMsg",
                                                                                        e.getNodeName());
                                                                                System.out.println(e.getNodeName());
                                                                                NodeList type = (NodeList) message
                                                                                        .item(j); // liste des attributs
                                                                                                  // de nocat
                                                                                for (int k = 0; k < type
                                                                                        .getLength(); k++) {
                                                                                    if (type.item(k)
                                                                                            .getNodeType() == Node.ELEMENT_NODE) {
                                                                                        e = (Element) type.item(k);
                                                                                        System.err.println("    "
                                                                                                + e.getNodeName()
                                                                                                + " : "
                                                                                                + e.getTextContent());
                                                                                        if (e.getNodeName().equals(
                                                                                                "idCatRequestMsg")) {
                                                                                            nocat.setIdCatRequestMsg(
                                                                                                    e.getTextContent());
                                                                                        } else {
                                                                                            nocat.setReason(
                                                                                                    e.getTextContent());
                                                                                        }
                                                                                    }
                                                                                }
                                                                                h.getLsMessage().add(nocat);
                                                                                nocatrepo.save(nocat);
                                                                            } else {
                                                                                if (e.getNodeName()
                                                                                        .equals("catRequest")) { // catRequest
                                                                                    m.addAttribute("typeMsg",
                                                                                            e.getNodeName());
                                                                                    // Initialisation Date et validity
                                                                                    CatalogueRequest catreq = new CatalogueRequest();
                                                                                    catreq.setDate(date);
                                                                                    catreq.setValidity(validity);
                                                                                    System.out.println(e.getNodeName());
                                                                                    NodeList type = (NodeList) message
                                                                                            .item(j); // liste des
                                                                                                      // attributs de
                                                                                                      // catRequest
                                                                                    for (int k = 0; k < type
                                                                                            .getLength(); k++) {
                                                                                        if (type.item(k)
                                                                                                .getNodeType() == Node.ELEMENT_NODE) {
                                                                                            e = (Element) type.item(k);
                                                                                            System.err.println("    "
                                                                                                    + e.getNodeName()
                                                                                                    + " : "
                                                                                                    + e.getTextContent());
                                                                                        }
                                                                                    }
                                                                                    h.getLsMessage().add(catreq);
                                                                                    catreqrepo.save(catreq);
                                                                                } else {
                                                                                    if (e.getNodeName().equals("cat")) { // cat
                                                                                        m.addAttribute("typeMsg",
                                                                                                e.getNodeName());
                                                                                        // Initialisation Date et
                                                                                        // validity
                                                                                        Catalogue cat = new Catalogue();
                                                                                        cat.setDate(date);
                                                                                        cat.setValidity(validity);
                                                                                        System.out.println(
                                                                                                e.getNodeName());
                                                                                        NodeList type = (NodeList) message
                                                                                                .item(j); // liste des
                                                                                                          // attributs
                                                                                                          // de cat
                                                                                        for (int k = 0; k < type
                                                                                                .getLength(); k++) {
                                                                                            Objects o = new Objects();
                                                                                            if (type.item(k)
                                                                                                    .getNodeType() == Node.ELEMENT_NODE) {
                                                                                                e = (Element) type
                                                                                                        .item(k);
                                                                                                if (e.getNodeName()
                                                                                                        .equals("object")) {
                                                                                                    System.err.println(e
                                                                                                            .getNodeName()
                                                                                                            + " : "
                                                                                                            + e.getAttribute(
                                                                                                                    "idObject"));
                                                                                                    NodeList object = (NodeList) type
                                                                                                            .item(k); // Liste
                                                                                                                      // object
                                                                                                                      // snd
                                                                                                                      // de
                                                                                                                      // donation
                                                                                                    for (int y = 0; y < object
                                                                                                            .getLength(); y++) {
                                                                                                        if (object
                                                                                                                .item(y)
                                                                                                                .getNodeType() == Node.ELEMENT_NODE) {
                                                                                                            e = (Element) object
                                                                                                                    .item(y);
                                                                                                            System.err
                                                                                                                    .println(
                                                                                                                            "    " + e
                                                                                                                                    .getNodeName()
                                                                                                                                    + " : "
                                                                                                                                    + e.getTextContent());
                                                                                                            if (e.getNodeName()
                                                                                                                    .equals("objectName")) {
                                                                                                                o.setObjectName(
                                                                                                                        e.getTextContent());

                                                                                                            } else if (e
                                                                                                                    .getNodeName()
                                                                                                                    .equals("objectDetails")) {
                                                                                                                o.setObjectDetails(
                                                                                                                        e.getTextContent());

                                                                                                                cat.add(o);
                                                                                                                objrepo.save(
                                                                                                                        o);

                                                                                                            }
                                                                                                        }
                                                                                                    }
                                                                                                } else {
                                                                                                    System.err.println(
                                                                                                            "    " + e
                                                                                                                    .getNodeName()
                                                                                                                    + " : "
                                                                                                                    + e.getTextContent());
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                        h.getLsMessage().add(cat);
                                                                                        catrepo.save(cat);
                                                                                    }
                                                                                    if (e.getNodeName()
                                                                                            .equals("errorMsg")) { // errorMsg
                                                                                        // Initialisation Date et
                                                                                        // validity
                                                                                        ErrorMsg errmsg = new ErrorMsg();
                                                                                        errmsg.setDate(date);
                                                                                        errmsg.setValidity(validity);
                                                                                        m.addAttribute("typeMsg",
                                                                                                e.getNodeName());
                                                                                        System.out.println("  "
                                                                                                + e.getNodeName()
                                                                                                + " : idMsg "
                                                                                                + e.getAttribute(
                                                                                                        "idMsg")
                                                                                                + " , idError"
                                                                                                + e.getAttribute(
                                                                                                        "idError"));
                                                                                        NodeList type = (NodeList) message
                                                                                                .item(j); // liste des
                                                                                                          // attributs
                                                                                                          // de errorMsg
                                                                                        for (int k = 0; k < type
                                                                                                .getLength(); k++) {
                                                                                            if (type.item(k)
                                                                                                    .getNodeType() == Node.ELEMENT_NODE) {
                                                                                                e = (Element) type
                                                                                                        .item(k);
                                                                                                // Récupération et
                                                                                                // instentiation du
                                                                                                // errmsg
                                                                                                errmsg.setIdMsg(
                                                                                                        e.getAttribute(
                                                                                                                "idMsg"));
                                                                                                errmsg.setIdError(
                                                                                                        e.getAttribute(
                                                                                                                "idError"));

                                                                                                System.err.println(
                                                                                                        "    " + e
                                                                                                                .getNodeName()
                                                                                                                + " : "
                                                                                                                + e.getTextContent());
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
                            } else {
                                System.err.println("Erreur : le fichier ne porte pas le bon nombre de messages.");
                            }
                        }
                    }
                }
            }
        }
        return "reading";
    }
}
