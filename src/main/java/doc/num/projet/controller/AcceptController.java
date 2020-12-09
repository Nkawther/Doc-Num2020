package doc.num.projet.controller;

import java.util.Date;

import javax.inject.Inject;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import doc.num.projet.model.Accept;
import doc.num.projet.model.Header;
import doc.num.projet.repository.AcceptRepository;
import doc.num.projet.repository.HeaderRepository;

@Controller
public class AcceptController {
    @Inject
    AcceptRepository acceptrepo;

    @Inject
    HeaderRepository headerrepo;

    @RequestMapping(value = "/accept", method = RequestMethod.POST)
    public String addrequest(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateV, @RequestParam String id,
            @RequestParam Long idHeader) {
        System.err.println("accept controller");
        Accept a = new Accept(date, dateV, id, idHeader);
        acceptrepo.save(a);
        if (headerrepo.findAllByOrderById().contains(headerrepo.findHeaderById(idHeader))) {
            Header h = headerrepo.findHeaderById(idHeader);
            h.getLsMessage().add(a);
            headerrepo.save(h);
        }
        return "redirect:writing";
    }
}
