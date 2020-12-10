package doc.num.projet.controller;

import java.util.Date;
import java.util.UUID;

import javax.inject.Inject;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import doc.num.projet.model.Auth;
import doc.num.projet.model.Header;
import doc.num.projet.repository.AuthRepository;
import doc.num.projet.repository.HeaderRepository;

@Controller
public class AuthController {

    @Inject
    AuthRepository authrepo;
    @Inject
    HeaderRepository headerrepo;

    @RequestMapping(value = "/auth", method = RequestMethod.POST)
    public String addrequest(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateV, @RequestParam Long idHeader,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateRef) {
        System.err.println("auth controller");
        UUID refAuth = UUID.randomUUID();

        Auth auth = new Auth(refAuth.toString(), dateRef, date, dateV, idHeader);
        authrepo.save(auth);
        if (headerrepo.findAllByOrderById().contains(headerrepo.findHeaderById(idHeader))) {
            Header h = headerrepo.findHeaderById(idHeader);
            h.getLsMessage().add(auth);
            h.setAuthRef(refAuth.toString());
            h.setAuthDate(dateRef);
            headerrepo.save(h);
        }
        return "redirect:writing";
    }
}
