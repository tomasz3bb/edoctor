package pl.edu.wszib.edoctor.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.edu.wszib.edoctor.model.Doctor;
import pl.edu.wszib.edoctor.model.DoctorSchedule;
import pl.edu.wszib.edoctor.model.Patient;
import pl.edu.wszib.edoctor.model.User;
import pl.edu.wszib.edoctor.services.IDoctorService;
import pl.edu.wszib.edoctor.services.IPatientService;
import pl.edu.wszib.edoctor.services.IUserService;
import pl.edu.wszib.edoctor.session.SessionObject;

import javax.annotation.Resource;
@Controller
public class DoctorController {
    @Resource
    SessionObject sessionObject;

    @Autowired
    IPatientService patientService;

    @Autowired
    IUserService userService;

    @Autowired
    IDoctorService doctorService;

    @RequestMapping(value = "/doctors", method = RequestMethod.GET)
    public String showAllDoctors(Model model){
        if (!this.sessionObject.isLogged()){
            return "redirect:/login";
        }
        model.addAttribute("allDoctors", this.doctorService.getAllDoctors());
        model.addAttribute("isLogged", this.sessionObject.isLogged());
        model.addAttribute("role", this.sessionObject.isLogged() ? this.sessionObject.getLoggedUser().getRole().toString() : null);
        return "doctors";
    }

    @RequestMapping(value = "/currentdoctorschedule/{doctorId}", method = RequestMethod.GET)
    public String currentDoctorSchedule(Model model, @PathVariable int doctorId){
        if(!this.sessionObject.isLogged()) {
            return "redirect:/login";
        }
        model.addAttribute("isLogged", this.sessionObject.isLogged());
        model.addAttribute("role", this.sessionObject.isLogged() ? this.sessionObject.getLoggedUser().getRole().toString() : null);
        model.addAttribute("currentDS", this.doctorService.getCurrentDoctorSchedule(doctorId));
        return "currentdoctorschedule";
    }

    @RequestMapping(value = "/doctor_patients", method = RequestMethod.GET)
    public String showAllPatientsByDoctor(Model model){
        if (!this.sessionObject.isLogged()){
            return "redirect:/login";
        }
        Doctor loggedDoctor = this.doctorService.getDoctorByUserId(this.sessionObject.getLoggedUser().getUserId());
        model.addAttribute("patientsList", this.doctorService.getPatientsByDoctor(loggedDoctor));
        model.addAttribute("role", this.sessionObject.isLogged() ? this.sessionObject.getLoggedUser().getRole().toString() : null);
        model.addAttribute("isLogged", this.sessionObject.isLogged());
        return "doctor_patients";
    }
}
