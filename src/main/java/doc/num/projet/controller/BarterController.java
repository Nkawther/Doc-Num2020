package doc.num.projet.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import doc.num.projet.model.Barter;
import doc.num.projet.model.Object;
import doc.num.projet.repository.BarterRepository;

@Controller
public class BarterController {
    
    @Inject
    BarterRepository barterrepo;

    @RequestMapping(
        value = "/add-barter",
        method = RequestMethod.POST
    )
    public String addrequest( @RequestParam String date,
                            @RequestParam String dateV,
                            @RequestParam String objectnamercv, 
                            @RequestParam String objectdetailsrcv,
                            @RequestParam String objectnamesnd, 
                            @RequestParam String objectdetailssnd,
                            @RequestParam String id) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date dateS = formatter.parse(date);
        Date dateVS = formatter.parse(dateV);
        Object orcv = new Object(objectnamercv, objectdetailsrcv);
        Object osnd = new Object(objectnamesnd, objectdetailssnd);
        Barter b = new Barter(dateS, dateVS, id, orcv, osnd);
        barterrepo.save(b);
        return "writing";
    }
}