package doc.num.projet.controller;

import java.util.Date;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import doc.num.projet.model.Object;
import doc.num.projet.model.Request;
import doc.num.projet.repository.RequestRepository;

@Controller
public class RequestController {
    
    @Inject
    RequestRepository requestrepo;

    @RequestMapping(
        value = "/add-request",
        method = RequestMethod.POST
    )
    public String addrequest( @RequestParam Date date,
                            @RequestParam Date dateV,
                            @RequestParam String objectnamercv, 
                            @RequestParam String objectdetailsrcv,
                            @RequestParam String id){
        Object o = new Object(objectnamercv, objectdetailsrcv);
        Request r = new Request(date, dateV, id, o);
        requestrepo.save(r);
        return "writing";
    }
}
