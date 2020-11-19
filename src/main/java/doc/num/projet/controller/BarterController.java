package doc.num.projet.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import doc.num.projet.model.Barter;
import doc.num.projet.model.Objects;
import doc.num.projet.repository.BarterRepository;
import doc.num.projet.repository.ObjectsRepository;

@Controller
public class BarterController {

    @Inject
    BarterRepository barterrepo;

    @Autowired
    ObjectsRepository objrepo;

    @RequestMapping(value = "/add-barter", method = RequestMethod.POST)
    public String addrequest(@RequestParam String date, @RequestParam String dateV, @RequestParam String objectnamercv,
            @RequestParam String objectdetailsrcv, @RequestParam String objectnamesnd,
            @RequestParam String objectdetailssnd, @RequestParam String id, @RequestParam Long idHeader)
            throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date dateS = formatter.parse(date);
        Date dateVS = formatter.parse(dateV);
        Objects orcv = new Objects(objectnamercv, objectdetailsrcv);
        Objects osnd = new Objects(objectnamesnd, objectdetailssnd);
        objrepo.save(orcv);
        objrepo.save(osnd);
        Barter b = new Barter(dateS, dateVS, id, orcv, osnd, idHeader);
        barterrepo.save(b);
        return "redirect:header";
    }
}