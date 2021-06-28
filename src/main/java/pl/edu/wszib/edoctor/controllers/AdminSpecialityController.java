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
@RequestMapping("/admin")
public class AdminSpecialityController {

    @Resource
    SessionObject sessionObject;

    @Autowired
    IUserService userService;

    @Autowired
    IDoctorService doctorService;

    @Autowired
    ISpecialityService specialityService;
    @RequestMapping(value = "/specialities", method = RequestMethod.GET)
    public String showAllSpecialities(Model model){
        if(!this.sessionObject.isLogged() || this.sessionObject.getLoggedUser().getRole() != User.Role.ADMIN) {
            return "redirect:/login";
        }
        model.addAttribute("isLogged", this.sessionObject.isLogged());
        model.addAttribute("role", this.sessionObject.isLogged() ? this.sessionObject.getLoggedUser().getRole().toString() : null);
        model.addAttribute("specialities", this.specialityService.getAll());
        model.addAttribute("info", this.sessionObject.getInfo());
        return "admin/specialities";
    }

    @RequestMapping(value = "/addspeciality", method = RequestMethod.GET)
    public String addSpeciality(Model model){
        if(!this.sessionObject.isLogged() || this.sessionObject.getLoggedUser().getRole() != User.Role.ADMIN) {
            return "redirect:/login";
        }
        model.addAttribute("isLogged", this.sessionObject.isLogged());
        model.addAttribute("role", this.sessionObject.isLogged() ? this.sessionObject.getLoggedUser().getRole().toString() : null);
        model.addAttribute("speciality", new Speciality());
        model.addAttribute("info", this.sessionObject.getInfo());
        return "admin/addspeciality";
    }
    @RequestMapping(value = "/addspeciality", method = RequestMethod.POST)
    public String addSpeciality(@ModelAttribute Speciality speciality){
        if(!this.sessionObject.isLogged() || this.sessionObject.getLoggedUser().getRole() != User.Role.ADMIN) {
            return "redirect:/login";
        }
        if(this.specialityService.save(speciality)) {
            this.sessionObject.setInfo("Dodano nową specjalizację.");
        }else {
            this.sessionObject.setInfo("Błąd");
        }
        return "redirect:/admin/specialities";
    }
    @RequestMapping(value = "/deletespeciality/{specialityId}", method = RequestMethod.GET)
    public String deleteSpeciality(@PathVariable int specialityId, Model model){
        if(!this.sessionObject.isLogged() || this.sessionObject.getLoggedUser().getRole() != User.Role.ADMIN) {
            return "redirect:/login";
        }
        Speciality speciality = this.specialityService.getSpecialityById(specialityId);
        model.addAttribute("speciality", speciality);
        model.addAttribute("isLogged", this.sessionObject.isLogged());
        model.addAttribute("role", this.sessionObject.isLogged() ? this.sessionObject.getLoggedUser().getRole().toString() : null);
        model.addAttribute("info", this.sessionObject.getInfo());
        return "admin/deletespeciality";
    }

    @RequestMapping(value = "/deletespeciality/{specialityId}", method = RequestMethod.POST)
    public String deleteSpeciality(@ModelAttribute Speciality speciality){
        if(!this.sessionObject.isLogged() || this.sessionObject.getLoggedUser().getRole() != User.Role.ADMIN) {
            return "redirect:/login";
        }
        if (this.specialityService.delete(speciality)){
            this.sessionObject.setInfo("Usunięto specjalizację.");
        }else {
            this.sessionObject.setInfo("Błąd");
        }
        return "redirect:/admin/specialities";
    }

    @RequestMapping(value = "/editspeciality/{specialityId}", method = RequestMethod.GET)
    public String editSpeciality(@PathVariable int specialityId, Model model) {
        if(!this.sessionObject.isLogged() || this.sessionObject.getLoggedUser().getRole() != User.Role.ADMIN) {
            return "redirect:/login";
        }
        Speciality speciality = this.specialityService.getSpecialityById(specialityId);
        model.addAttribute("speciality", speciality);
        model.addAttribute("isLogged", this.sessionObject.isLogged());
        model.addAttribute("role", this.sessionObject.isLogged() ? this.sessionObject.getLoggedUser().getRole().toString() : null);
        model.addAttribute("info", this.sessionObject.getInfo());
        return "admin/editspeciality";
    }
    @RequestMapping(value = "/editspeciality/{specialityId}", method = RequestMethod.POST)
    public String editSpeciality(@ModelAttribute Speciality speciality) {
        if(!this.sessionObject.isLogged() || this.sessionObject.getLoggedUser().getRole() != User.Role.ADMIN) {
            return "redirect:/login";
        }
        if (this.specialityService.update(speciality)){
            this.sessionObject.setInfo("Zapisano zmiany.");
        }else {
            this.sessionObject.setInfo("Błąd");
        }
        return "redirect:/admin/specialities";
    }
}
