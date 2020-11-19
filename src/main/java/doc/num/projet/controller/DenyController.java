package doc.num.projet.controller;

import java.util.Date;

import javax.inject.Inject;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import doc.num.projet.model.Deny;
import doc.num.projet.repository.DenyRepository;

@Controller
public class DenyController {
    @Inject
    DenyRepository denyrepo;

    @RequestMapping(value = "/deny", method = RequestMethod.POST)
    public String addrequest(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateV, @RequestParam String id,
            @RequestParam String reason, @RequestParam Long idHeader) {
        System.err.println("deny controller");
        Deny d = new Deny(date, dateV, id, reason, idHeader);
        denyrepo.save(d);
        return "redirect:header";
    }
}
