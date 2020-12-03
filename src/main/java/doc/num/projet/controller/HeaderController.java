package doc.num.projet.controller;

import java.util.Date;

import javax.inject.Inject;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import doc.num.projet.model.Header;
import doc.num.projet.model.User;
import doc.num.projet.repository.HeaderRepository;
import doc.num.projet.repository.UserRepository;

@Controller
public class HeaderController {
    @Inject
    HeaderRepository headerrepo;
    @Inject
    UserRepository userrepo;

    @RequestMapping(value = "/addHeader", method = RequestMethod.POST)
    public String addHeader(@RequestParam int nbMsg, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date authDate,
            @RequestParam String namersv, @RequestParam String nametrm, @RequestParam String authRef) {

        User t = new User(nametrm);
        User r = new User(namersv);
        userrepo.save(t);
        userrepo.save(r);
        Header h = new Header(nbMsg, t.getId(), r.getId(), authRef, authDate);
        headerrepo.save(h);
        return "redirect:writing";
    }

}
