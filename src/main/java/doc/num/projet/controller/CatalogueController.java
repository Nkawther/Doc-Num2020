package doc.num.projet.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.stereotype.Controller;
import doc.num.projet.repository.CatalogueRepository;
import doc.num.projet.repository.ObjectsRepository;
import doc.num.projet.model.Catalogue;
import doc.num.projet.model.Objects;

@Controller
public class CatalogueController {

    @Autowired
    CatalogueRepository catrepo;

    @Autowired
    ObjectsRepository objrepo;

    /*
     * @Autowired MessageRepository msgrepo;
     */
    @RequestMapping(value = "/add-cat", method = RequestMethod.POST)
    public String addcat(@RequestParam String date, @RequestParam String dateV, @RequestParam String catDate,
            @RequestParam String[] objectname, @RequestParam String[] objectdetails) throws ParseException {

        System.err.println("test " + objectdetails[0]);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Objects initobj = new Objects(objectname[0], objectdetails[0]);
        objrepo.save(initobj);
        Date dateS = formatter.parse(date);
        Date dateVS = formatter.parse(dateV);
        Date catDateS = formatter.parse(catDate);
        Catalogue cat = new Catalogue(dateS, dateVS, catDateS, initobj);
        int i = 1;
        for (i = 1; i < objectdetails.length && i < objectname.length; i++) {
            Objects o = new Objects(objectname[i], objectdetails[i]);
            objrepo.save(o);
            cat.add(o);
        }

        System.err.println(dateS);
        // msgrepo.save(initobj);

        catrepo.save(cat);
        return "writing";
    }

}
