package doc.num.projet.controller;

import java.util.Date;
import java.text.ParseException;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import doc.num.projet.model.Donation;
import doc.num.projet.model.Header;
import doc.num.projet.model.Objects;
import doc.num.projet.repository.DonationRepository;
import doc.num.projet.repository.HeaderRepository;
import doc.num.projet.repository.ObjectsRepository;

@Controller
public class DonationController {
    @Autowired
    DonationRepository donationrepo;

    @Autowired
    ObjectsRepository objrepo;
    @Inject
    HeaderRepository headerrepo;

    @RequestMapping(value = "/donation", method = RequestMethod.POST)
    public String adddonation(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateV, @RequestParam String objectname,
            @RequestParam String objectdetail, @RequestParam String id, @RequestParam Long idHeader)
            throws ParseException {
                System.err.println("je suis la premier");
        Objects o = new Objects(objectname, objectdetail);
        objrepo.save(o);
        Donation d = new Donation(date, dateV, id, o, idHeader);
        donationrepo.save(d);
        System.err.println("je suis la milieu");
        if (headerrepo.findAllByOrderById().contains(headerrepo.findHeaderById(idHeader))) {
            Header h = headerrepo.findHeaderById(idHeader);
            h.getLsMessage().add(d);
            headerrepo.save(h);
        }
        System.err.println("je suis la dernier");
        return "redirect:reading";
    }
}
