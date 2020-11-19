package doc.num.projet.controller;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import doc.num.projet.model.AuthRequest;
import doc.num.projet.repository.AuthRequestRepository;

@Controller
public class AuthRequestController {
    @Autowired
    AuthRequestRepository authrepo;

    @RequestMapping(value = "/authreq", method = RequestMethod.POST)
    public String addauth(@RequestParam String date, @RequestParam String validity, @RequestParam Long idHeader)
            throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date dateS = formatter.parse(date);
        Date dateVS = formatter.parse(validity);
        AuthRequest authreq = new AuthRequest(dateS, dateVS, idHeader);

        authrepo.save(authreq);
        return "redirect:header";
    }

}