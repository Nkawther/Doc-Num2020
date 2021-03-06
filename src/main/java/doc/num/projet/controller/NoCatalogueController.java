package doc.num.projet.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
//import javax.inject.Inject;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import doc.num.projet.repository.HeaderRepository;
import doc.num.projet.repository.NoCatalogueRepository;
import doc.num.projet.model.Header;
import doc.num.projet.model.NoCatalogue;
import org.springframework.stereotype.Controller;

@Controller
public class NoCatalogueController {

    @Autowired
    NoCatalogueRepository nocatrepo;
    @Inject
    HeaderRepository headerrepo;

    @RequestMapping(value = "/add-nocat", method = RequestMethod.POST)
    public String addcat(@RequestParam String date, @RequestParam String dateV, @RequestParam String reason,
            @RequestParam Long idHeader, @RequestParam String idcat) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date dateS = formatter.parse(date);
        Date dateVS = formatter.parse(dateV);
        NoCatalogue nocat = new NoCatalogue(dateS, dateVS, reason, idHeader, idcat);
        nocatrepo.save(nocat);
        if (headerrepo.findAllByOrderById().contains(headerrepo.findHeaderById(idHeader))) {
            Header h = headerrepo.findHeaderById(idHeader);
            h.getLsMessage().add(nocat);
            headerrepo.save(h);
        }
        return "redirect:writing";
    }

}
