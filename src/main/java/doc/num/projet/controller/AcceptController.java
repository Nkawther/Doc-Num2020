package doc.num.projet.controller;

import java.util.Date;

import javax.inject.Inject;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import doc.num.projet.model.Accept;
import doc.num.projet.repository.AcceptRepository;

@Controller
public class AcceptController {
    @Inject
    AcceptRepository acceptrepo;

    @RequestMapping(value = "/accept", method = RequestMethod.POST)
    public String addrequest(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateV, @RequestParam String id) {
        System.err.println("accept controller");
        Accept a = new Accept(date, dateV, id);
        acceptrepo.save(a);
        return "writing";
    }
}
