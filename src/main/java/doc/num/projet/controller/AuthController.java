package doc.num.projet.controller;

import java.util.Date;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import doc.num.projet.model.Auth;
import doc.num.projet.repository.AuthRepository;

@Controller
public class AuthController {

    @Inject
    AuthRepository authrepo;

    @RequestMapping(value = "/auth", method = RequestMethod.POST)
    public String addrequest(@RequestParam Date date, @RequestParam Date dateV, @RequestParam Long idHeader) {
        System.err.println("auth controller");
        Auth auth = new Auth(date, dateV, idHeader);
        authrepo.save(auth);
        return "redirect:header";
    }
}
