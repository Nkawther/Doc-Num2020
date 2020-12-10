package doc.num.projet.controller;

import java.text.ParseException;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import doc.num.projet.model.Header;
import doc.num.projet.model.Message;
import doc.num.projet.model.User;
import doc.num.projet.repository.HeaderRepository;
import doc.num.projet.repository.MessageRepository;
import doc.num.projet.repository.UserRepository;

@Controller
@SessionAttributes(value = "currentUser", types = { Long.class })
public class InitController {
    @Inject
    HeaderRepository headerrepo;

    @Inject
    MessageRepository msgrepo;

    @Inject
    UserRepository userrepo;

    @RequestMapping("/")
    public String enterIndex(Model m) {
        m.addAttribute("pageName", "Connection");
        return "connexion";
    }

    @RequestMapping("/initParse")
    public String enterInside() {
        return "redirect:Parse";
    }

    @RequestMapping("/connecter")
    public String connection(Model m, @RequestParam String userName, HttpSession s) {
        System.err.println(userName);
        try {
            Long userId = userrepo.findUserByName(userName).getId();
            System.err.println(userId);
            s.setAttribute("currentUser", userId);

        } catch (NullPointerException e) {
            System.err.print("Caught the NullPointerException");
            User errUser = new User(userName);
            userrepo.save(errUser);
            s.setAttribute("currentUser", errUser.getId());
        }
        m.addAttribute("pageName", "Index");
        return "redirect:index";
    }

    @RequestMapping("/index")
    public String afficher(Model m) {
        m.addAttribute("pageName", "Connection");
        return "index";
    }

    @RequestMapping("/writing")
    public String writing(Model m, HttpSession s) {
        m.addAttribute("pageName", "Writing XML");
        m.addAttribute("lsHeader", headerrepo.findAll());
        System.err.println(headerrepo.findAll());
        m.addAttribute("vide", "");
        m.addAttribute("lsUsername", userrepo.findAllByOrderById());
        if (headerrepo.count() == 0) {
            m.addAttribute("vide", "No file");
        }
        return "writing";
    }

    @RequestMapping("/header")
    public String header(Model m, HttpSession s) {
        Long userId = (Long) s.getAttribute("currentUser");
        m.addAttribute("nameTrans", userrepo.findUserById(userId).getName());
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
        System.err.println(headerrepo.findAll());
        m.addAttribute("vide", "");
        if (headerrepo.count() == 0) {
            m.addAttribute("vide", "No file");
        }
        m.addAttribute("lsUsername", userrepo.findAllByOrderById());
        System.err.println(userrepo.findAll());
        return "reading";

    }

    @RequestMapping("readinggg")
    public String creadinggg(Model m, @RequestParam long ind) {
        m.addAttribute("pageName", "Reading XML");
        m.addAttribute("lsHeader", headerrepo.findAll());
        m.addAttribute("vide", "");
        if (headerrepo.count() == 0) {
            m.addAttribute("vide", "No file");
        }
        m.addAttribute("lsUsername", userrepo.findAllByOrderById());
        m.addAttribute("generer", "kdcbsdj");
        m.addAttribute("id", ind);
        return "reading";

    }

    @RequestMapping("/readingg")
    public String creadingg(Model m) {
        m.addAttribute("pageName", "Reading XML");
        m.addAttribute("lsHeader", headerrepo.findAll());
        m.addAttribute("vide", "");
        if (headerrepo.count() == 0) {
            m.addAttribute("vide", "No file");
        }
        m.addAttribute("lsUsername", userrepo.findAllByOrderById());
        m.addAttribute("erreur", "File does not exist");
        return "reading";

    }

    @RequestMapping("add-a-message")
    public String addmessage(Model m, @RequestParam long ind) {
        Header h = headerrepo.findHeaderById(ind);
        m.addAttribute("lsUsername", userrepo.findAllByOrderById());
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
        m.addAttribute("authRef", h.getAuthRef());
        m.addAttribute("lsHeader", headerrepo.findAll());
        m.addAttribute("vide", "");

        return "writing";
    }

    @RequestMapping("/endsession")
    public String endSessionHandlingMethod(SessionStatus status) {
        status.setComplete();
        return "redirect:";
    }
}