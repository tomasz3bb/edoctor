package pl.edu.wszib.edoctor.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.edu.wszib.edoctor.model.*;
import pl.edu.wszib.edoctor.services.*;
import pl.edu.wszib.edoctor.session.SessionObject;

import javax.annotation.Resource;

@Controller
@RequestMapping("/patient")
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

    @RequestMapping(value = "/app", method = RequestMethod.GET)
    public String showPatientApp(Model model){
        if (!this.sessionObject.isLogged()){
            return "redirect:/login";
        }
        User loggedUser = this.sessionObject.getLoggedUser();
        model.addAttribute("loggedPatientApp", this.appointmentService.getAllAppointmentByPatient(loggedUser.getUserId()));
        model.addAttribute("role", this.sessionObject.isLogged() ? this.sessionObject.getLoggedUser().getRole().toString() : null);
        model.addAttribute("isLogged", this.sessionObject.isLogged());
        model.addAttribute("info", this.sessionObject.getInfo());
        return "patient/app";
    }
    @RequestMapping(value = "/currentapp", method = RequestMethod.GET)
    public String showCurrentPatientApp(Model model){
        if (!this.sessionObject.isLogged()){
            return "redirect:/login";
        }
        User loggedUser = this.sessionObject.getLoggedUser();
        model.addAttribute("currentapp", this.appointmentService.getCurrentAppByPatient(loggedUser.getUserId(), Appointment.Status.Zaplanowana));
        model.addAttribute("role", this.sessionObject.isLogged() ? this.sessionObject.getLoggedUser().getRole().toString() : null);
        model.addAttribute("isLogged", this.sessionObject.isLogged());
        model.addAttribute("info", this.sessionObject.getInfo());
        return "patient/currentapp";
    }

    @RequestMapping(value = "/histapp", method = RequestMethod.GET)
    public String showHistPatientApp(Model model){
        if (!this.sessionObject.isLogged()){
            return "redirect:/login";
        }
        User loggedUser = this.sessionObject.getLoggedUser();
        model.addAttribute("currentapp", this.appointmentService.getHistAppByPatient(loggedUser.getUserId(), Appointment.Status.Zakończona));
        model.addAttribute("role", this.sessionObject.isLogged() ? this.sessionObject.getLoggedUser().getRole().toString() : null);
        model.addAttribute("isLogged", this.sessionObject.isLogged());
        model.addAttribute("info", this.sessionObject.getInfo());
        return "patient/histapp";
    }

    @RequestMapping(value = "/doctors", method = RequestMethod.GET)
    public String showAllDoctorsByPatient(Model model){
        if (!this.sessionObject.isLogged()){
            return "redirect:/login";
        }
        Patient loggedPatient = this.patientService.getPatientByUserId(this.sessionObject.getLoggedUser().getUserId());
        model.addAttribute("doctorsList", this.doctorListService.getDoctorsByPatient(loggedPatient));
        model.addAttribute("role", this.sessionObject.isLogged() ? this.sessionObject.getLoggedUser().getRole().toString() : null);
        model.addAttribute("isLogged", this.sessionObject.isLogged());
        model.addAttribute("info", this.sessionObject.getInfo());
        return "patient/doctors";
    }

    @RequestMapping(value = "/editdata/{patientId}", method = RequestMethod.GET)
    public String editPatientData(Model model, @PathVariable int patientId){
        if(!this.sessionObject.isLogged() || this.sessionObject.getLoggedUser().getRole() != User.Role.Pacjent) {
            return "redirect:/login";
        }
        Patient loggedPatient = this.patientService.getPatientByPatientId(patientId);
        model.addAttribute("patient", loggedPatient);
        model.addAttribute("isLogged", this.sessionObject.isLogged());
        model.addAttribute("role", this.sessionObject.isLogged() ? this.sessionObject.getLoggedUser().getRole().toString() : null);
        model.addAttribute("info", this.sessionObject.getInfo());
        return "patient/editdata";
    }
    @RequestMapping(value = "/editdata/{patientId}", method = RequestMethod.POST)
    public String editPatientData(@ModelAttribute Patient patient){
        if(!this.sessionObject.isLogged() || this.sessionObject.getLoggedUser().getRole() != User.Role.Pacjent) {
            return "redirect:/login";
        }
        if (!this.patientService.update(patient)){
            this.sessionObject.setInfo("Wystąpił błąd.");
            return "redirect:/patient/account";
        }
        this.patientService.update(patient);
        this.sessionObject.setInfo("Zmiany zapisane.");
        return "redirect:/patient/account";
    }

    @RequestMapping(value = "/doctorConfirm/{doctorId}", method = RequestMethod.GET)
    public String savePatientToDoctor(Model model, @PathVariable int doctorId){
        if(!this.sessionObject.isLogged() || this.sessionObject.getLoggedUser().getRole() != User.Role.Pacjent) {
            return "redirect:/login";
        }
        model.addAttribute("isLogged", this.sessionObject.isLogged());
        model.addAttribute("role", this.sessionObject.isLogged() ? this.sessionObject.getLoggedUser().getRole().toString() : null);
        model.addAttribute("doctor", this.doctorService.getDoctorByDoctorId(doctorId));
        model.addAttribute("doctorList", new DoctorList());
        model.addAttribute("info", this.sessionObject.getInfo());
        return "patient/doctorConfirm";
    }

    @RequestMapping(value = "/doctorConfirm/{doctorId}", method = RequestMethod.POST)
    public String savePatientToDoctor(@ModelAttribute DoctorList doctorList, @PathVariable int doctorId) {
        if (!this.sessionObject.isLogged() || this.sessionObject.getLoggedUser().getRole() != User.Role.Pacjent) {
            return "redirect:/login";
        }
        if (!this.doctorListService.savePatientToDoctor(doctorList, doctorId)) {
            this.sessionObject.setInfo("Błąd, jesteś już zapisany u tego doktora.");
            return "redirect:/patient/doctors";
        }
        this.doctorListService.savePatientToDoctor(doctorList, doctorId);
        return "redirect:/patient/doctors";
    }
}
