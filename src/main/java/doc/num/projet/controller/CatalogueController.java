package doc.num.projet.controller;

import java.util.Date;
//import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import doc.num.projet.repository.CatalogueRepository;
import doc.num.projet.model.Catalogue;
import doc.num.projet.model.Object;

public class CatalogueController {

    @Autowired
    CatalogueRepository catrepo;

    @RequestMapping(value = "/add-cat", method = RequestMethod.POST)
    public String addcat(@RequestParam Date date, @RequestParam Date dateV, @RequestParam Date catDate,
            @RequestParam String objectnameinit, @RequestParam String objectdetailsinit) {
        Object initobj = new Object(objectnameinit, objectdetailsinit);
        Catalogue cat = new Catalogue(date, dateV, catDate, initobj);
        catrepo.save(cat);
        return "writing";
    }

}
