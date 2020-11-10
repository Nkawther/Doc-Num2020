package doc.num.projet.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
//import javax.inject.Inject;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.stereotype.Controller;
import doc.num.projet.repository.CatalogueRepository;
import doc.num.projet.repository.MessageRepository;
import doc.num.projet.model.Catalogue;
import doc.num.projet.model.Object;

@Controller
public class CatalogueController {

    @Autowired
    CatalogueRepository catrepo;

    @Autowired
    MessageRepository msgrepo;

    @RequestMapping(value = "/add-cat", method = RequestMethod.POST)
    public String addcat(@RequestParam String date, @RequestParam String dateV, @RequestParam String catDate,
            @RequestParam String objectnameinit, @RequestParam String objectdetailsinit) throws ParseException {
        System.err.println("test " + objectdetailsinit);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Object initobj = new Object(objectnameinit, objectdetailsinit);
        Date dateS = formatter.parse(date);
        Date dateVS = formatter.parse(dateV);
        Date catDateS = formatter.parse(catDate);

        Catalogue cat = new Catalogue(dateS, dateVS, catDateS, initobj);
        System.err.println(dateS);
        msgrepo.save(initobj);

        catrepo.save(cat);
        return "writing";
    }

}
