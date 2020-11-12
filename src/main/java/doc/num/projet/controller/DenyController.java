package doc.num.projet.controller;

import java.util.Date;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import doc.num.projet.model.Deny;
import doc.num.projet.repository.DenyRepository;

public class DenyController {
    @Inject
    DenyRepository denyrepo;

    @RequestMapping(value = "/deny", method = RequestMethod.POST)
    public String addrequest(@RequestParam Date date, @RequestParam Date dateV, @RequestParam String id,
            @RequestParam String reason) {
        System.err.println("deny controller");
        Deny d = new Deny(date, dateV, id, reason);
        denyrepo.save(d);
        return "writing";
    }
}