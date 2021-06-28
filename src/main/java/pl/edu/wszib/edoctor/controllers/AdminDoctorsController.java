package pl.edu.wszib.edoctor.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.edu.wszib.edoctor.model.Doctor;
import pl.edu.wszib.edoctor.model.Patient;
import pl.edu.wszib.edoctor.model.User;
import pl.edu.wszib.edoctor.services.IDoctorService;
import pl.edu.wszib.edoctor.services.ISpecialityService;
import pl.edu.wszib.edoctor.services.IUserService;
import pl.edu.wszib.edoctor.session.SessionObject;

import javax.annotation.Resource;

@Controller
@RequestMapping("/admin")
public class AdminDoctorsController {

    @Resource
    SessionObject sessionObject;

    @Autowired
    IUserService userService;

    @Autowired
    IDoctorService doctorService;

    @Autowired
    ISpecialityService specialityService;

    @RequestMapping(value = "/doctors", method = RequestMethod.GET)
    public String showAllDoctors(Model model){
        if(!this.sessionObject.isLogged() || this.sessionObject.getLoggedUser().getRole() != User.Role.ADMIN) {
            return "redirect:/login";
        }
        model.addAttribute("isLogged", this.sessionObject.isLogged());
        model.addAttribute("role", this.sessionObject.isLogged() ? this.sessionObject.getLoggedUser().getRole().toString() : null);
        model.addAttribute("doctors", this.doctorService.getAll());
        model.addAttribute("info", this.sessionObject.getInfo());
        return "admin/doctors";
    }

    @RequestMapping(value = "/adddoctor", method = RequestMethod.GET)
    public String addDoctor(Model model){
        if(!this.sessionObject.isLogged() || this.sessionObject.getLoggedUser().getRole() != User.Role.ADMIN) {
            return "redirect:/login";
        }
        model.addAttribute("isLogged", this.sessionObject.isLogged());
        model.addAttribute("role", this.sessionObject.isLogged() ? this.sessionObject.getLoggedUser().getRole().toString() : null);
        model.addAttribute("doctor", new Doctor());
        model.addAttribute("user", new User());
        model.addAttribute("info", this.sessionObject.getInfo());
        return "admin/adddoctor";
    }
    @RequestMapping(value = "/adddoctor", method = RequestMethod.POST)
    public String addDoctor(@ModelAttribute Doctor doctor, @ModelAttribute User user){
        if(!this.sessionObject.isLogged() || this.sessionObject.getLoggedUser().getRole() != User.Role.ADMIN) {
            return "redirect:/login";
        }
        if(this.doctorService.save(doctor, user)) {
            this.sessionObject.setInfo("Dodano nowego lekarza.");
        }else {
            this.sessionObject.setInfo("Błąd");
        }
        return "redirect:/admin/doctors";
    }
    @RequestMapping(value = "/deletedoctor/{doctorId}", method = RequestMethod.GET)
    public String deleteDoctor(@PathVariable int doctorId, Model model){
        if(!this.sessionObject.isLogged() || this.sessionObject.getLoggedUser().getRole() != User.Role.ADMIN) {
            return "redirect:/login";
        }
        Doctor doctor = this.doctorService.getDoctorByDoctorId(doctorId);
        model.addAttribute("doctor", doctor);
        model.addAttribute("isLogged", this.sessionObject.isLogged());
        model.addAttribute("role", this.sessionObject.isLogged() ? this.sessionObject.getLoggedUser().getRole().toString() : null);
        model.addAttribute("info", this.sessionObject.getInfo());
        return "admin/deletedoctor";
    }

    @RequestMapping(value = "/deletedoctor/{doctorId}", method = RequestMethod.POST)
    public String deleteDoctor(@ModelAttribute Doctor doctor){
        if(!this.sessionObject.isLogged() || this.sessionObject.getLoggedUser().getRole() != User.Role.ADMIN) {
            return "redirect:/login";
        }
        if(this.doctorService.delete(doctor)){
            this.sessionObject.setInfo("Usunięto lekarza.");
        }else {
            this.sessionObject.setInfo("Błąd");
        }
        return "redirect:/admin/doctors";
    }

    @RequestMapping(value = "/editdoctor/{doctorId}", method = RequestMethod.GET)
    public String editDoctor(@PathVariable int doctorId, Model model) {
        if(!this.sessionObject.isLogged() || this.sessionObject.getLoggedUser().getRole() != User.Role.ADMIN) {
            return "redirect:/login";
        }
        Doctor doctor = this.doctorService.getDoctorByDoctorId(doctorId);
        model.addAttribute("doctor", doctor);
        model.addAttribute("isLogged", this.sessionObject.isLogged());
        model.addAttribute("role", this.sessionObject.isLogged() ? this.sessionObject.getLoggedUser().getRole().toString() : null);
        model.addAttribute("info", this.sessionObject.getInfo());
        return "admin/editdoctor";
    }
    @RequestMapping(value = "/editdoctor/{doctorId}", method = RequestMethod.POST)
    public String editDoctor(@ModelAttribute Doctor doctor) {
        if(!this.sessionObject.isLogged() || this.sessionObject.getLoggedUser().getRole() != User.Role.ADMIN) {
            return "redirect:/login";
        }
        if(this.doctorService.update(doctor)){
            this.sessionObject.setInfo("Zapisano zmiany");
        }else {
            this.sessionObject.setInfo("Błąd");
        }
        return "redirect:/admin/doctors";
    }
}
