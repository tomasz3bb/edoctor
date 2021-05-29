package pl.edu.wszib.edoctor.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.edu.wszib.edoctor.model.Speciality;
import pl.edu.wszib.edoctor.model.User;
import pl.edu.wszib.edoctor.services.IDoctorService;
import pl.edu.wszib.edoctor.services.ISpecialityService;
import pl.edu.wszib.edoctor.services.IUserService;
import pl.edu.wszib.edoctor.session.SessionObject;

import javax.annotation.Resource;

@Controller
public class AdminSpecialityController {

    @Resource
    SessionObject sessionObject;

    @Autowired
    IUserService userService;

    @Autowired
    IDoctorService doctorService;

    @Autowired
    ISpecialityService specialityService;
    @RequestMapping(value = "/admin_specialities", method = RequestMethod.GET)
    public String showAllSpecialities(Model model){
        if(!this.sessionObject.isLogged() || this.sessionObject.getLoggedUser().getRole() != User.Role.ADMIN) {
            return "redirect:/login";
        }
        model.addAttribute("isLogged", this.sessionObject.isLogged());
        model.addAttribute("role", this.sessionObject.isLogged() ? this.sessionObject.getLoggedUser().getRole().toString() : null);
        model.addAttribute("specialities", this.specialityService.getAllSpecialities());
        return "admin_specialities";
    }

    @RequestMapping(value = "/admin_addspeciality", method = RequestMethod.GET)
    public String addSpeciality(Model model){
        if(!this.sessionObject.isLogged() || this.sessionObject.getLoggedUser().getRole() != User.Role.ADMIN) {
            return "redirect:/login";
        }
        model.addAttribute("isLogged", this.sessionObject.isLogged());
        model.addAttribute("role", this.sessionObject.isLogged() ? this.sessionObject.getLoggedUser().getRole().toString() : null);
        model.addAttribute("speciality", new Speciality());
        return "admin_addspeciality";
    }
    @RequestMapping(value = "/admin_addspeciality", method = RequestMethod.POST)
    public String addSpeciality(@ModelAttribute Speciality speciality){
        if(!this.sessionObject.isLogged() || this.sessionObject.getLoggedUser().getRole() != User.Role.ADMIN) {
            return "redirect:/login";
        }
        if(this.specialityService.addSpeciality(speciality)) {
            return "redirect:/panel";
        } else {
            return "redirect:/admin_addspeciality";
        }
    }
    @RequestMapping(value = "/admin_deletespeciality/{specialityId}", method = RequestMethod.GET)
    public String deleteSpeciality(@PathVariable int specialityId, Model model){
        if(!this.sessionObject.isLogged() || this.sessionObject.getLoggedUser().getRole() != User.Role.ADMIN) {
            return "redirect:/login";
        }
        Speciality speciality = this.specialityService.getSpecialityById(specialityId);
        model.addAttribute("speciality", speciality);
        model.addAttribute("isLogged", this.sessionObject.isLogged());
        model.addAttribute("role", this.sessionObject.isLogged() ? this.sessionObject.getLoggedUser().getRole().toString() : null);
        return "admin_deletespeciality";
    }

    @RequestMapping(value = "/admin_deletespeciality/{specialityId}", method = RequestMethod.POST)
    public String deleteSpeciality(@ModelAttribute Speciality speciality){
        if(!this.sessionObject.isLogged() || this.sessionObject.getLoggedUser().getRole() != User.Role.ADMIN) {
            return "redirect:/login";
        }
        this.specialityService.deleteSpeciality(speciality);
        return "redirect:/panel";
    }

    @RequestMapping(value = "/admin_editspeciality/{specialityId}", method = RequestMethod.GET)
    public String editSpeciality(@PathVariable int specialityId, Model model) {
        if(!this.sessionObject.isLogged() || this.sessionObject.getLoggedUser().getRole() != User.Role.ADMIN) {
            return "redirect:/login";
        }
        Speciality speciality = this.specialityService.getSpecialityById(specialityId);
        model.addAttribute("speciality", speciality);
        model.addAttribute("isLogged", this.sessionObject.isLogged());
        model.addAttribute("role", this.sessionObject.isLogged() ? this.sessionObject.getLoggedUser().getRole().toString() : null);
        return "admin_editspeciality";
    }
    @RequestMapping(value = "/admin_editspeciality/{specialityId}", method = RequestMethod.POST)
    public String editSpeciality(@ModelAttribute Speciality speciality) {
        if(!this.sessionObject.isLogged() || this.sessionObject.getLoggedUser().getRole() != User.Role.ADMIN) {
            return "redirect:/login";
        }

        this.specialityService.updateSpeciality(speciality);

        return "redirect:/panel";
    }
}
