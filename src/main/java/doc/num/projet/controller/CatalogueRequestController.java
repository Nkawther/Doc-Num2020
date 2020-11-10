package doc.num.projet.controller;

import java.util.Date;
//import javax.inject.Inject;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import doc.num.projet.repository.CatalogueRequestRepository;
import doc.num.projet.model.CatalogueRequest;

import org.springframework.stereotype.Controller;

@Controller
public class CatalogueRequestController {

    @Autowired
    CatalogueRequestRepository nocatrepo;

    @RequestMapping(value = "/add-catreq", method = RequestMethod.POST)
    public String addcat(@RequestParam String date, @RequestParam String dateV) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date dateS = formatter.parse(date);
        Date dateVS = formatter.parse(dateV);
        CatalogueRequest catreq = new CatalogueRequest(dateS, dateVS);

        nocatrepo.save(catreq);
        return "writing";
    }

}
