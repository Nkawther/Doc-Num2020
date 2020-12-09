package doc.num.projet.controller;

import java.text.ParseException;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import doc.num.projet.model.Header;
import doc.num.projet.model.Message;
import doc.num.projet.model.User;
import doc.num.projet.repository.HeaderRepository;
import doc.num.projet.repository.MessageRepository;
import doc.num.projet.repository.UserRepository;

@Controller
public class ControllerInit {
    @Inject
    HeaderRepository headerrepo;

    @Inject
    MessageRepository msgrepo;

    @Inject
    UserRepository userrepo;

    @RequestMapping("/")
    public String enterIndex() {
        return "redirect:index";
    }

    @RequestMapping("/initParse")
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
        if (headerrepo.count() == 0) {
            m.addAttribute("vide", "No file");
        }
        return "writing";
    }

    @RequestMapping("/header")
    public String header(Model m) {
        m.addAttribute("pageName", "Writing Head");
        m.addAttribute("lsHeader", headerrepo.findAll());
        m.addAttribute("vide", "");
        m.addAttribute("lsUsername", userrepo.findAllByOrderById());
        if (headerrepo.count() == 0) {
            m.addAttribute("vide", "No file");
        }
        return "header";
    }

    @RequestMapping(value = "/getusername")
    public User getCompanyRest(@RequestParam long ind) {
        User lsUser = userrepo.findUserById(ind);
        return lsUser;
    }

    @RequestMapping("/reading")
    public String creading(Model m) {
        m.addAttribute("pageName", "Reading XML");
        m.addAttribute("lsHeader", headerrepo.findAll());
        m.addAttribute("vide", "");
        if (headerrepo.count() == 0) {
            m.addAttribute("vide", "No file");
        }
        m.addAttribute("lsUsername", userrepo.findAllByOrderById());
        return "reading";
    }

    @RequestMapping("add-a-message")
    public String addmessage(Model m, @RequestParam long ind) {
        Header h = headerrepo.findHeaderById(ind);
        List<Message> lmsg = msgrepo.findMessageByIdHeader(ind);
        m.addAttribute("lsMsg", lmsg);
        if (headerrepo.count() == 0) {
            m.addAttribute("Error", "Create file before");
            m.addAttribute("lsHeader", headerrepo.findAll());
            m.addAttribute("vide", "No file");

            return "writing";
        }
        if (h.getLsMessage().size() == h.getNbMsg()) {
            m.addAttribute("Error", "Error : You can't add message in this file !");
            m.addAttribute("idheader", " ");
        } else {
            m.addAttribute("Error", " ");
            m.addAttribute("idheader", ind);
        }
        m.addAttribute("lsHeader", headerrepo.findAll());
        m.addAttribute("vide", "");

        return "writing";
    }

}