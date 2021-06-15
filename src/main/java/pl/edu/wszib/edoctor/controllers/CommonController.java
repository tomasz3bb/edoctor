package pl.edu.wszib.edoctor.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.edu.wszib.edoctor.model.Doctor;
import pl.edu.wszib.edoctor.model.User;
import pl.edu.wszib.edoctor.services.IDoctorService;
import pl.edu.wszib.edoctor.session.SessionObject;

import javax.annotation.Resource;
import javax.print.Doc;


@Controller
public class CommonController {

    @Resource
    SessionObject sessionObject;

    @Autowired
    IDoctorService doctorService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String landingPage(){
        return "redirect:/main";
    }

    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String mainPage(Model model){
        model.addAttribute("isLogged", this.sessionObject.isLogged());
        model.addAttribute("role", this.sessionObject.isLogged() ? this.sessionObject.getLoggedUser().getRole().toString() : null);
        return "main";
    }
    @RequestMapping(value = "/about", method = RequestMethod.GET)
    public String aboutPage(Model model){
        model.addAttribute("isLogged", this.sessionObject.isLogged());
        return "about";
    }
    @RequestMapping(value = "/contact", method = RequestMethod.GET)
    public String contactPage(Model model){
        model.addAttribute("isLogged", this.sessionObject.isLogged());
        return "contact";
    }


    @RequestMapping(value = "/doctors", method = RequestMethod.GET)
    public String showAllDoctors(Model model){
        if (!this.sessionObject.isLogged()){
            return "redirect:/login";
        }
        model.addAttribute("allDoctors", this.doctorService.getAll());
        model.addAttribute("isLogged", this.sessionObject.isLogged());
        model.addAttribute("role", this.sessionObject.isLogged() ? this.sessionObject.getLoggedUser().getRole().toString() : null);
        return "doctors";
    }
}
