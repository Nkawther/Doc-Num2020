package doc.num.projet.controller;

import java.text.ParseException;
import java.util.Date;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import doc.num.projet.model.ErrorMsg;
import doc.num.projet.model.Header;
import doc.num.projet.repository.ErrorMsgRepository;
import doc.num.projet.repository.HeaderRepository;

@Controller
public class ErrorMsgController {
    @Autowired
    ErrorMsgRepository errorrepo;

    @Inject
    HeaderRepository headerrepo;

    @RequestMapping(value = "/errormsg", method = RequestMethod.POST)
    public String adderror(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateV, @RequestParam String id,
            @RequestParam String iderror, @RequestParam Long idHeader) throws ParseException {

        ErrorMsg d = new ErrorMsg(id, iderror, date, dateV, idHeader);
        errorrepo.save(d);
        if (headerrepo.findAllByOrderById().contains(headerrepo.findHeaderById(idHeader))) {
            Header h = headerrepo.findHeaderById(idHeader);
            h.getLsMessage().add(d);
            headerrepo.save(h);
        }
        return "redirect:writing";
    }
}
