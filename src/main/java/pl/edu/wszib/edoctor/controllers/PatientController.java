package pl.edu.wszib.edoctor.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.edu.wszib.edoctor.model.Patient;
import pl.edu.wszib.edoctor.model.User;
import pl.edu.wszib.edoctor.services.IPatientService;
import pl.edu.wszib.edoctor.services.IUserService;
import pl.edu.wszib.edoctor.session.SessionObject;

import javax.annotation.Resource;

@Controller
public class PatientController {

    @Resource
    SessionObject sessionObject;

    @Autowired
    IPatientService patientService;

    @Autowired
    IUserService userService;

    @RequestMapping(value = "/patient_app", method = RequestMethod.GET)
    public String showCurrentPatientApp(Model model){
        if (!this.sessionObject.isLogged()){
            return "redirect:/login";
        }
        User loggedUser = this.sessionObject.getLoggedUser();
        model.addAttribute("loggedPatientApp", this.patientService.getAppointmentByPatient(loggedUser.getUserId()));
        model.addAttribute("role", this.sessionObject.isLogged() ? this.sessionObject.getLoggedUser().getRole().toString() : null);
        model.addAttribute("isLogged", this.sessionObject.isLogged());
        return "patient_app";
    }
    @RequestMapping(value = "/patient_doctors", method = RequestMethod.GET)
    public String showAllDoctorsByPatient(Model model){
        if (!this.sessionObject.isLogged()){
            return "redirect:/login";
        }
        Patient loggedPatient = this.patientService.getPatientByUserId(this.sessionObject.getLoggedUser().getUserId());
        model.addAttribute("patientDoctors", this.patientService.getAllDoctorsByPatient(loggedPatient));
        model.addAttribute("role", this.sessionObject.isLogged() ? this.sessionObject.getLoggedUser().getRole().toString() : null);
        model.addAttribute("isLogged", this.sessionObject.isLogged());
        return "patient_doctors";
    }
}
