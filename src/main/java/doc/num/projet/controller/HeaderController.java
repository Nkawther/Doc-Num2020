package doc.num.projet.controller;

import java.util.Date;

import javax.inject.Inject;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import doc.num.projet.model.Header;
import doc.num.projet.repository.HeaderRepository;

@Controller
public class HeaderController {
    @Inject
    HeaderRepository headerrepo;

    @RequestMapping(value = "/addHeader", method = RequestMethod.POST)
    public String addHeader(Model model, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date authDate,
            @RequestParam String namersv, @RequestParam String nametrm, @RequestParam String authRef) {
        Header h = new Header(1, nametrm, namersv, authRef, authDate);
        headerrepo.save(h);
        model.addAttribute("idHeader", h.getId());
        return "writing";
    }

    @RequestMapping(value = "/Header")
    public String header() {

        return "writing";
    }
}
