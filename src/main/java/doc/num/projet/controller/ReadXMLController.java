package doc.num.projet.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ReadXMLController {

    public Document doc;

    @RequestMapping(value = "/Parse", method = RequestMethod.POST)
    public String readXml(@RequestParam String file) {
        try {
            File document_xml = new File(file);
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
    public String aandsXml(Model m) {
        Element e, nbmsg = doc.getDocumentElement();
        NodeList messages, message;
        int p = 0;
        // List<Integer> idprev = new ArrayList<>();
        // List<Integer> idmsg = new ArrayList<>();

        // recuperation de la racine
        Element racine = this.doc.getDocumentElement();
        NodeList file = ((NodeList) racine);
        for (int a = 0; a < file.getLength(); a++) {
            if (file.item(a).getNodeType() == Node.ELEMENT_NODE) {
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
                                System.err.println("  " + nbmsg.getNodeName() + " = " + nbmsg.getTextContent());
                                m.addAttribute("nbMsg", nbmsg.getTextContent());
                            }
                            if (e.getNodeName().equals("transmitter")) {
                                System.err.println("  " + e.getNodeName() + " idUser = " + e.getAttribute("idUser"));
                                m.addAttribute("transmetter", e.getAttribute("idUser"));
                            }
                            if (e.getNodeName().equals("receiver")) {
                                System.err.println("  " + e.getNodeName() + " idUser = " + e.getAttribute("idUser"));
                                m.addAttribute("receiver", e.getAttribute("idUser"));
                            }
                            if (e.getNodeName().equals("authRef")) {
                                System.err.println("  " + e.getNodeName() + " = " + e.getTextContent());
                                m.addAttribute("authRef", e.getTextContent());
                            }
                            if (e.getNodeName().equals("authDate")) {
                                System.err.println("  " + e.getNodeName() + " = " + e.getTextContent());
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
                                                    m.addAttribute("dateMsg", e.getTextContent());
                                                }
                                                if (e.getNodeName().equals("validityDuration")) {
                                                    System.err.println(
                                                            "    " + e.getNodeName() + " : " + e.getTextContent());
                                                    m.addAttribute("validityDuration", e.getTextContent());
                                                }
                                                /* TYPE Msg */
                                                if (e.getNodeName().equals("barter")) { // Barter
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
                                                                        if (snd.item(x)
                                                                                .getNodeType() == Node.ELEMENT_NODE) {
                                                                            e = (Element) snd.item(x);
                                                                            System.err.println(snd.item(x).getNodeName()
                                                                                    + " : "
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
                                                                                    System.err.println("    "
                                                                                            + e.getNodeName() + " : "
                                                                                            + e.getTextContent());
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                } else {
                                                                    System.err.println("    " + e.getNodeName() + " : "
                                                                            + e.getTextContent()); // idprev dans barter
                                                                    // idprev.add(Integer.parseInt(e.getTextContent()));
                                                                }
                                                            }
                                                        }
                                                    }
                                                } else {
                                                    if (e.getNodeName().equals("request")) { // request
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
                                                                        if (rcv.item(x)
                                                                                .getNodeType() == Node.ELEMENT_NODE) {
                                                                            e = (Element) rcv.item(x);
                                                                            System.err.println(rcv.item(x).getNodeName()
                                                                                    + " : "
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
                                                                                    System.err.println("    "
                                                                                            + e.getNodeName() + " : "
                                                                                            + e.getTextContent());
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                } else {
                                                                    System.err.println("    " + e.getNodeName() + " : "
                                                                            + e.getTextContent()); // idprev of request
                                                                    // idprev.add(Integer.parseInt(e.getTextContent()));
                                                                }
                                                            }
                                                        }
                                                    } else {
                                                        if (e.getNodeName().equals("donation")) { // donation
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
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    } else {
                                                                        System.err.println("    " + e.getNodeName()
                                                                                + " : " + e.getTextContent()); // idprev
                                                                                                               // de
                                                                                                               // donation
                                                                        // idprev.add(Integer.parseInt(e.getTextContent()));
                                                                    }
                                                                }
                                                            }
                                                        } else {
                                                            if (e.getNodeName().equals("accept")) { // accept
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
                                                                    }
                                                                }
                                                            } else {
                                                                if (e.getNodeName().equals("deny")) { // deny
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
                                                                        }
                                                                    }
                                                                } else {
                                                                    if (e.getNodeName().equals("auth")) { // auth
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
                                                                            }
                                                                        }
                                                                    } else {
                                                                        if (e.getNodeName().equals("authRequest")) { // authRequest
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
                                                                        } else {
                                                                            if (e.getNodeName().equals("nocat")) { // nocat
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
                                                                                    }
                                                                                }
                                                                            } else {
                                                                                if (e.getNodeName()
                                                                                        .equals("catRequest")) { // catRequest
                                                                                    m.addAttribute("typeMsg",
                                                                                            e.getNodeName());
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
                                                                                } else {
                                                                                    if (e.getNodeName().equals("cat")) { // cat
                                                                                        m.addAttribute("typeMsg",
                                                                                                e.getNodeName());
                                                                                        System.out.println(
                                                                                                e.getNodeName());
                                                                                        NodeList type = (NodeList) message
                                                                                                .item(j); // liste des
                                                                                                          // attributs
                                                                                                          // de cat
                                                                                        for (int k = 0; k < type
                                                                                                .getLength(); k++) {
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
                                                                                    }
                                                                                    if (e.getNodeName()
                                                                                            .equals("errorMsg")) { // errorMsg
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
        return "redirect:reading";
    }
}
