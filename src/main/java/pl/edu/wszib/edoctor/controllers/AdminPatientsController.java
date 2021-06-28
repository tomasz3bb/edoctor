package pl.edu.wszib.edoctor.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.edu.wszib.edoctor.model.Patient;
import pl.edu.wszib.edoctor.model.User;
import pl.edu.wszib.edoctor.services.IPatientService;
import pl.edu.wszib.edoctor.services.ISpecialityService;
import pl.edu.wszib.edoctor.services.IUserService;
import pl.edu.wszib.edoctor.session.SessionObject;

import javax.annotation.Resource;

@Controller
@RequestMapping("/admin")
public class AdminPatientsController {
    @Resource
    SessionObject sessionObject;

    @Autowired
    IUserService userService;

    @Autowired
    IPatientService patientService;

    @Autowired
    ISpecialityService specialityService;

    @RequestMapping(value = "/patients", method = RequestMethod.GET)
    public String showAllPatients(Model model){
        if(!this.sessionObject.isLogged() || this.sessionObject.getLoggedUser().getRole() != User.Role.ADMIN) {
            return "redirect:/login";
        }
        model.addAttribute("isLogged", this.sessionObject.isLogged());
        model.addAttribute("role", this.sessionObject.isLogged() ? this.sessionObject.getLoggedUser().getRole().toString() : null);
        model.addAttribute("patients", this.patientService.getAll());
        model.addAttribute("info", this.sessionObject.getInfo());
        return "admin/patients";
    }

    @RequestMapping(value = "/addpatient", method = RequestMethod.GET)
    public String addPatient(Model model){
        if(!this.sessionObject.isLogged() || this.sessionObject.getLoggedUser().getRole() != User.Role.ADMIN) {
            return "redirect:/login";
        }
        model.addAttribute("isLogged", this.sessionObject.isLogged());
        model.addAttribute("role", this.sessionObject.isLogged() ? this.sessionObject.getLoggedUser().getRole().toString() : null);
        model.addAttribute("patient", new Patient());
        model.addAttribute("user", new User());
        model.addAttribute("info", this.sessionObject.getInfo());
        return "admin/addpatient";
    }
    @RequestMapping(value = "/addpatient", method = RequestMethod.POST)
    public String addPatient(@ModelAttribute Patient patient, @ModelAttribute User user){
        if(!this.sessionObject.isLogged() || this.sessionObject.getLoggedUser().getRole() != User.Role.ADMIN) {
            return "redirect:/login";
        }
        if(this.patientService.save(patient, user)) {
            this.sessionObject.setInfo("Dodano nowego pacjenta.");
        }else {
            this.sessionObject.setInfo("Błąd");
        }
        return "redirect:/admin/patients";
    }
    @RequestMapping(value = "/deletepatient/{patientId}", method = RequestMethod.GET)
    public String deletePatient(@PathVariable int patientId, Model model){
        if(!this.sessionObject.isLogged() || this.sessionObject.getLoggedUser().getRole() != User.Role.ADMIN) {
            return "redirect:/login";
        }
        Patient patient = this.patientService.getPatientByPatientId(patientId);
        model.addAttribute("patient", patient);
        model.addAttribute("isLogged", this.sessionObject.isLogged());
        model.addAttribute("role", this.sessionObject.isLogged() ? this.sessionObject.getLoggedUser().getRole().toString() : null);
        model.addAttribute("info", this.sessionObject.getInfo());
        return "admin/deletepatient";
    }

    @RequestMapping(value = "/deletepatient/{patientId}", method = RequestMethod.POST)
    public String deletePatient(@ModelAttribute Patient patient){
        if(!this.sessionObject.isLogged() || this.sessionObject.getLoggedUser().getRole() != User.Role.ADMIN) {
            return "redirect:/login";
        }
        if(this.patientService.delete(patient)){
            this.sessionObject.setInfo("Usunięto pacjenta.");
        }else {
            this.sessionObject.setInfo("Błąd");
        }
        return "redirect:/admin/patients";
    }

    @RequestMapping(value = "/editpatient/{patientId}", method = RequestMethod.GET)
    public String editPatient(@PathVariable int patientId, Model model) {
        if(!this.sessionObject.isLogged() || this.sessionObject.getLoggedUser().getRole() != User.Role.ADMIN) {
            return "redirect:/login";
        }
        Patient patient = this.patientService.getPatientByPatientId(patientId);
        model.addAttribute("patient", patient);
        model.addAttribute("isLogged", this.sessionObject.isLogged());
        model.addAttribute("role", this.sessionObject.isLogged() ? this.sessionObject.getLoggedUser().getRole().toString() : null);
        model.addAttribute("info", this.sessionObject.getInfo());
        return "admin/editpatient";
    }
    @RequestMapping(value = "/editpatient/{patientId}", method = RequestMethod.POST)
    public String editPatient(@ModelAttribute Patient patient) {
        if(!this.sessionObject.isLogged() || this.sessionObject.getLoggedUser().getRole() != User.Role.ADMIN) {
            return "redirect:/login";
        }
        if(this.patientService.update(patient)){
            this.sessionObject.setInfo("Zapisano zmiany.");
        }else {
            this.sessionObject.setInfo("Błąd");
        }
        return "redirect:/admin/patients";
    }
}
