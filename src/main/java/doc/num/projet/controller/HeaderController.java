package doc.num.projet.controller;

import java.util.Date;

import javax.inject.Inject;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import doc.num.projet.model.Header;
import doc.num.projet.model.User;
import doc.num.projet.repository.HeaderRepository;
import doc.num.projet.repository.UserRepository;

import java.util.UUID;

@Controller
public class HeaderController {
    @Inject
    HeaderRepository headerrepo;
    @Inject
    UserRepository userrepo;

    @RequestMapping(value = "/respondFile")
    public String respondFile(Model m, @RequestParam String ind) {
        if (headerrepo.count() == 0) {
            m.addAttribute("Error", "Create file before");
            m.addAttribute("vide", "No file");
        } else {
            m.addAttribute("Error", "");
            m.addAttribute("vide", "");
            if (!ind.equals("0")) {
                m.addAttribute("authoRef", ind);
            } else {
                UUID refAuth = UUID.randomUUID();
                m.addAttribute("authoRef", refAuth);
            }
        }

        m.addAttribute("lsHeader", headerrepo.findAll());
        m.addAttribute("lsUsername", userrepo.findAllByOrderById());
        return "header";
    }

    @RequestMapping(value = "/addHeader", method = RequestMethod.POST)
    public String addHeader(@RequestParam int nbMsg,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date authDate, @RequestParam String namersv,
            @RequestParam String nametrm, @RequestParam String authRef) {

        User t = new User(nametrm);
        User r = new User(namersv);
        userrepo.save(t);
        userrepo.save(r);
        Header h = new Header(nbMsg, t.getId(), r.getId(), authRef, authDate);
        headerrepo.save(h);
        return "redirect:writing";
    }

}
