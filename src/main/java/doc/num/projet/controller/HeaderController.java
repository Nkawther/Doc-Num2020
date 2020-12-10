package doc.num.projet.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import doc.num.projet.model.Header;
import doc.num.projet.model.User;
import doc.num.projet.repository.HeaderRepository;
import doc.num.projet.repository.UserRepository;

import java.util.UUID;

@Controller
@SessionAttributes({ "currentUser", "currentUserName" })
public class HeaderController {
    @Inject
    HeaderRepository headerrepo;
    @Inject
    UserRepository userrepo;

    @RequestMapping(value = "/respondFile")
    public String respondFile(Model m, @RequestParam Long id, HttpSession s) throws ParseException {
        Long userId = (Long) s.getAttribute("currentUser");
        m.addAttribute("nameTrans", userrepo.findUserById(userId).getName());
        if (headerrepo.count() == 0) {
            if (id == 0) {
                m.addAttribute("authoRef", "");
            }
            m.addAttribute("Error", "Create file before");
            m.addAttribute("vide", "No file");
        } else {
            m.addAttribute("Error", "");
            m.addAttribute("vide", "");
            if (id != 0) {
                Header header = headerrepo.findHeaderById(id);
                System.err.println(header.getIdReceiver());
                System.err.println(header.getAuthDate());
                User userRec = userrepo.findUserById(header.getIdReceiver());
                User userTra = userrepo.findUserById(header.getIdTransmitter());
                System.err.println(userRec.getName());
                System.err.println(userTra.getName());

                m.addAttribute("authoRef", header.getAuthRef());
                m.addAttribute("nameTrans", userRec.getName());
                m.addAttribute("nameRec", userTra.getName());

                try {
                    String dateS = header.getAuthDate().toString();
                    System.err.println(dateS);
                    String[] dateTAB = dateS.split(" ");
                    m.addAttribute("authDate", dateTAB[0]);
                } catch (NullPointerException e) {
                    m.addAttribute("authDate", "");
                }
            } else {
                m.addAttribute("authoRef", "");
            }
        }

        m.addAttribute("lsHeader", headerrepo.findAll());
        m.addAttribute("lsUsername", userrepo.findAllByOrderById());
        return "header";
    }

    @RequestMapping(value = "/deleteFile")
    public String deleteFile(Model m, @RequestParam Long id, HttpSession s) throws ParseException {
        Long userId = (Long) s.getAttribute("currentUser");
        m.addAttribute("nameTrans", userrepo.findUserById(userId).getName());
        headerrepo.deleteById(id);
        if (headerrepo.count() == 0) {
            m.addAttribute("Error", "Create file before");
            m.addAttribute("vide", "No file");
        } else {
            m.addAttribute("Error", "");
            m.addAttribute("vide", "");
        }

        m.addAttribute("lsHeader", headerrepo.findAll());
        m.addAttribute("lsUsername", userrepo.findAllByOrderById());
        return "header";
    }

    @RequestMapping(value = "/addHeader", method = RequestMethod.POST)
    public String addHeader(@RequestParam int nbMsg,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date authDate, @RequestParam String namersv,
            @RequestParam String nametrm, @RequestParam String authRef) {
        System.err.println("tr" + nametrm + "rc" + namersv);
        User r;
        User t = userrepo.findUserByName(nametrm);
        try {
            r = userrepo.findUserByName(namersv);
            System.err.println(r.getId());
        } catch (NullPointerException e) {
            System.err.print("Caught the NullPointerException");
            r = new User(namersv);
            userrepo.save(r);
        }
        System.err.println(("last"));
        Header h = new Header(nbMsg, t.getId(), r.getId(), authRef, authDate);
        System.err.println("save");
        headerrepo.save(h);
        System.err.println("la");
        return "redirect:writing";
    }

}
