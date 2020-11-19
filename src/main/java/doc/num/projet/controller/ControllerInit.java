package doc.num.projet.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import doc.num.projet.model.Header;
import doc.num.projet.repository.HeaderRepository;

@Controller
public class ControllerInit {
    @Inject
    HeaderRepository headerrepo;

    @RequestMapping("/")
    public String enterInside() {
        return "redirect:Parse";
    }

    @RequestMapping("/index")
    public String afficher(Model m) {
        m.addAttribute("pageName", "Index");
        return "index";
    }

    @RequestMapping("/writing")
    public String writing(Model m) throws ParseException {
        m.addAttribute("pageName", "Writing XML");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date dateS = formatter.parse("2020-11-20");
        Header h = new Header(0, "New", "New", "0", dateS);
        headerrepo.save(h);
        m.addAttribute("lsHeader", headerrepo.findAll());
        System.err.println(headerrepo.findAll());
        return "writing";
    }

    @RequestMapping("/reading")
    public String creading(Model m) {
        m.addAttribute("pageName", "Reading XML");
        return "reading";
    }
}