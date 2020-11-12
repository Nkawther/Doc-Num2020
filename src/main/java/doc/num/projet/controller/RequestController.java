package doc.num.projet.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import doc.num.projet.model.Objects;
import doc.num.projet.model.Request;
import doc.num.projet.repository.ObjectsRepository;
import doc.num.projet.repository.RequestRepository;

@Controller
public class RequestController {

    @Inject
    RequestRepository requestrepo;

    @Inject
    ObjectsRepository objrepo;

    @RequestMapping(value = "/add-request", method = RequestMethod.POST)
    public String addrequest(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateV, @RequestParam String objectnamercv,
            @RequestParam String objectdetailsrcv, @RequestParam String id) throws ParseException {

        Objects o = new Objects(objectnamercv, objectdetailsrcv);
        objrepo.save(o);
        Request r = new Request(date, dateV, id, o);
        requestrepo.save(r);
        return "writing";
    }
}