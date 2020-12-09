package doc.num.projet.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.stereotype.Controller;
import doc.num.projet.repository.CatalogueRepository;
import doc.num.projet.repository.HeaderRepository;
import doc.num.projet.repository.ObjectsRepository;
import doc.num.projet.model.Catalogue;
import doc.num.projet.model.Header;
import doc.num.projet.model.Objects;

@Controller
public class CatalogueController {

    @Autowired
    CatalogueRepository catrepo;

    @Autowired
    ObjectsRepository objrepo;
    @Inject
    HeaderRepository headerrepo;

    /*
     * @Autowired MessageRepository msgrepo;
     */
    @RequestMapping(value = "/add-cat", method = RequestMethod.POST)
    public String addcat(@RequestParam String date, @RequestParam String dateV, @RequestParam String catDate,
            @RequestParam String[] objectnameinit, @RequestParam String[] objectdetailsinit,
            @RequestParam Long idHeader, @RequestParam String idcat) throws ParseException {

        System.err.println("test " + objectdetailsinit);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        Date dateS = formatter.parse(date);
        Date dateVS = formatter.parse(dateV);
        Date catDateS = formatter.parse(catDate);
        Objects initobj = new Objects(objectnameinit[0], objectdetailsinit[0]);
        objrepo.save(initobj);
        Catalogue cat = new Catalogue(dateS, dateVS, catDateS, initobj, idHeader, idcat);

        for (int i = 1; i < objectnameinit.length; i++) {
            Objects obj = new Objects(objectnameinit[i], objectdetailsinit[i]);
            objrepo.save(obj);
            cat.add(obj);
        }
        System.err.println(dateS);
        // msgrepo.save(initobj);

        catrepo.save(cat);
        if (headerrepo.findAllByOrderById().contains(headerrepo.findHeaderById(idHeader))) {
            Header h = headerrepo.findHeaderById(idHeader);
            h.getLsMessage().add(cat);
            headerrepo.save(h);
        }
        return "redirect:writing";
    }

}
