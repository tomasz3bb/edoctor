package pl.edu.wszib.edoctor.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.edu.wszib.edoctor.model.Office;
import pl.edu.wszib.edoctor.model.User;
import pl.edu.wszib.edoctor.services.IOfficeService;
import pl.edu.wszib.edoctor.services.ISpecialityService;
import pl.edu.wszib.edoctor.services.IUserService;
import pl.edu.wszib.edoctor.session.SessionObject;

import javax.annotation.Resource;

@Controller
@RequestMapping("/admin")
public class AdminOfficeController {

    @Resource
    SessionObject sessionObject;

    @Autowired
    IUserService userService;

    @Autowired
    IOfficeService officeService;

    @Autowired
    ISpecialityService specialityService;

    @RequestMapping(value = "/office", method = RequestMethod.GET)
    public String showAllOffice(Model model){
        if(!this.sessionObject.isLogged() || this.sessionObject.getLoggedUser().getRole() != User.Role.ADMIN) {
            return "redirect:/login";
        }
        model.addAttribute("isLogged", this.sessionObject.isLogged());
        model.addAttribute("role", this.sessionObject.isLogged() ? this.sessionObject.getLoggedUser().getRole().toString() : null);
        model.addAttribute("offices", this.officeService.getAll());
        model.addAttribute("info", this.sessionObject.getInfo());
        return "admin/office";
    }

    @RequestMapping(value = "/addoffice", method = RequestMethod.GET)
    public String addOffice(Model model){
        if(!this.sessionObject.isLogged() || this.sessionObject.getLoggedUser().getRole() != User.Role.ADMIN) {
            return "redirect:/login";
        }
        model.addAttribute("isLogged", this.sessionObject.isLogged());
        model.addAttribute("role", this.sessionObject.isLogged() ? this.sessionObject.getLoggedUser().getRole().toString() : null);
        model.addAttribute("office", new Office());
        model.addAttribute("info", this.sessionObject.getInfo());
        return "admin/addoffice";
    }
    @RequestMapping(value = "/addoffice", method = RequestMethod.POST)
    public String addOffice(@ModelAttribute Office office){
        if(!this.sessionObject.isLogged() || this.sessionObject.getLoggedUser().getRole() != User.Role.ADMIN) {
            return "redirect:/login";
        }
        if(this.officeService.save(office)) {
            this.sessionObject.setInfo("Dodano nowy gabinet.");
        }else {
            this.sessionObject.setInfo("Błąd");
        }
        return "redirect:/admin/office";
    }
    @RequestMapping(value = "/deleteoffice/{officeId}", method = RequestMethod.GET)
    public String deleteOffice(@PathVariable int officeId, Model model){
        if(!this.sessionObject.isLogged() || this.sessionObject.getLoggedUser().getRole() != User.Role.ADMIN) {
            return "redirect:/login";
        }
        Office office = this.officeService.getById(officeId);
        model.addAttribute("office", office);
        model.addAttribute("isLogged", this.sessionObject.isLogged());
        model.addAttribute("role", this.sessionObject.isLogged() ? this.sessionObject.getLoggedUser().getRole().toString() : null);
        model.addAttribute("info", this.sessionObject.getInfo());
        return "admin/deleteoffice";
    }

    @RequestMapping(value = "/deleteoffice/{officeId}", method = RequestMethod.POST)
    public String deleteOffice(@ModelAttribute Office office){
        if(!this.sessionObject.isLogged() || this.sessionObject.getLoggedUser().getRole() != User.Role.ADMIN) {
            return "redirect:/login";
        }
        if(this.officeService.delete(office)){
            this.sessionObject.setInfo("Usunięto gabinet.");
        }else {
            this.sessionObject.setInfo("Błąd");
        }
        return "redirect:/admin/office";
    }

    @RequestMapping(value = "/editoffice/{officeId}", method = RequestMethod.GET)
    public String editOffice(@PathVariable int officeId, Model model) {
        if(!this.sessionObject.isLogged() || this.sessionObject.getLoggedUser().getRole() != User.Role.ADMIN) {
            return "redirect:/login";
        }
        Office office = this.officeService.getById(officeId);
        model.addAttribute("office", office);
        model.addAttribute("isLogged", this.sessionObject.isLogged());
        model.addAttribute("role", this.sessionObject.isLogged() ? this.sessionObject.getLoggedUser().getRole().toString() : null);
        model.addAttribute("info", this.sessionObject.getInfo());
        return "admin/editoffice";
    }
    @RequestMapping(value = "/editoffice/{officeId}", method = RequestMethod.POST)
    public String editOffice(@ModelAttribute Office office) {
        if(!this.sessionObject.isLogged() || this.sessionObject.getLoggedUser().getRole() != User.Role.ADMIN) {
            return "redirect:/login";
        }
        if(this.officeService.update(office)){
            this.sessionObject.setInfo("Zapisano zmiany");
        }else {
            this.sessionObject.setInfo("Błąd");
        }
        return "redirect:/admin/office";
    }
}
