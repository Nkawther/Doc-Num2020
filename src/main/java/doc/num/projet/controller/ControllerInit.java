package doc.num.projet.controller;

import java.text.ParseException;


import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import doc.num.projet.model.Header;
import doc.num.projet.repository.HeaderRepository;

@Controller
public class ControllerInit {
    @Inject
    HeaderRepository headerrepo;

    @RequestMapping("/")
    public String enterInside() {
        return "redirect:Parse";
    }

    @RequestMapping("/index")
    public String afficher(Model m) {
        m.addAttribute("pageName", "Index");
        return "index";
    }

    @RequestMapping("/writing")
    public String writing(Model m) throws ParseException {
        m.addAttribute("pageName", "Writing XML");
        m.addAttribute("lsHeader", headerrepo.findAll());
        m.addAttribute("vide", "");
        if(headerrepo.count()==0){
            m.addAttribute("vide", "No file");
        }
        return "writing";
    }
    @RequestMapping("/header")
    public String header()  {
        return "header";
    }
    @RequestMapping("/reading")
    public String creading(Model m) {
        m.addAttribute("pageName", "Reading XML");
        m.addAttribute("lsHeader", headerrepo.findAll());
        m.addAttribute("vide", "");
        if(headerrepo.count()==0){
            m.addAttribute("vide", "No file");
        }
        return "reading";
    }
    @RequestMapping("add-a-message")
    public String addmessage(Model m, @RequestParam long ind) {
        Header h = headerrepo.findHeaderById(ind);
        if(h.getLsMessage().size()==h.getNbMsg()){
            m.addAttribute("Error", "Error : You can't add message in this file !");
            m.addAttribute("idheader", " ");
        }
        else{
            m.addAttribute("Error", " ");
            m.addAttribute("idheader", ind);
        }
        m.addAttribute("lsHeader", headerrepo.findAll());
        m.addAttribute("vide", "");
        if(headerrepo.count()==0){
            m.addAttribute("vide", "No file");
        }
        return "writing";
    }

}