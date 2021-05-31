package pl.edu.wszib.edoctor.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.edu.wszib.edoctor.model.Doctor;
import pl.edu.wszib.edoctor.model.Patient;
import pl.edu.wszib.edoctor.model.User;
import pl.edu.wszib.edoctor.services.*;
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

    @Autowired
    IDoctorListService doctorListService;

    @Autowired
    IAppointmentService appointmentService;

    @Autowired
    IDoctorService doctorService;

    @RequestMapping(value = "/patient_app", method = RequestMethod.GET)
    public String showCurrentPatientApp(Model model){
        if (!this.sessionObject.isLogged()){
            return "redirect:/login";
        }
        User loggedUser = this.sessionObject.getLoggedUser();
        model.addAttribute("loggedPatientApp", this.appointmentService.getAppointmentByPatient(loggedUser.getUserId()));
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
        model.addAttribute("doctorsList", this.doctorListService.getDoctorsByPatient(loggedPatient));
        model.addAttribute("role", this.sessionObject.isLogged() ? this.sessionObject.getLoggedUser().getRole().toString() : null);
        model.addAttribute("isLogged", this.sessionObject.isLogged());
        return "patient_doctors";
    }

    @RequestMapping(value = "/patient_doctorConfirm/{doctorId}", method = RequestMethod.GET)
    public String savePatientToDoctor(Model model, @PathVariable int doctorId){
        if(!this.sessionObject.isLogged() || this.sessionObject.getLoggedUser().getRole() != User.Role.Pacjent) {
            return "redirect:/login";
        }
        Doctor doctorFromDB = this.doctorService.getDoctorByDoctorId(doctorId);
        Patient loggedPatient = this.patientService.getPatientByUserId(this.sessionObject.getLoggedUser().getUserId());
        this.doctorListService.savePatientToDoctor(loggedPatient, doctorFromDB);
        model.addAttribute("isLogged", this.sessionObject.isLogged());
        model.addAttribute("role", this.sessionObject.isLogged() ? this.sessionObject.getLoggedUser().getRole().toString() : null);
        return "patient_doctorConfirm";
    }

    @RequestMapping(value = "/patient_doctorConfirm/{docotrId}", method = RequestMethod.POST)
    public String savePatientToDoctor(){
        if(!this.sessionObject.isLogged() || this.sessionObject.getLoggedUser().getRole() != User.Role.Pacjent) {
            return "redirect:/login";
        }
        return "redirect:/patient_doctors";
    }
}
