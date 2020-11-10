package doc.num.projet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ControllerInit {

    @RequestMapping("/")
    public String enterInside() {
        return "redirect:index";
    }

    @RequestMapping("/index")
    public String afficher(Model m) {
        m.addAttribute("pageName", "Index");
        return "index";
    }

    @RequestMapping("/writing")
    public String writing(Model m) {
        m.addAttribute("pageName", "Writing XML");
        return "writing";
    }

    @RequestMapping("/reading")
    public String creading(Model m) {
        m.addAttribute("pageName", "Reading XML");
        return "reading";
    }
}