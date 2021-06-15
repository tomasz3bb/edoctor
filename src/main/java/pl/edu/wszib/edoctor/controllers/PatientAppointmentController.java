package pl.edu.wszib.edoctor.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.edu.wszib.edoctor.model.Appointment;
import pl.edu.wszib.edoctor.model.User;
import pl.edu.wszib.edoctor.services.*;
import pl.edu.wszib.edoctor.session.SessionObject;

import javax.annotation.Resource;

@Controller
@RequestMapping("/patient")
public class PatientAppointmentController {
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

    @RequestMapping(value = "/makeapp/{doctorId}", method = RequestMethod.GET)
    public String makeAppointment(Model model, @PathVariable int doctorId){
        if(!this.sessionObject.isLogged() || this.sessionObject.getLoggedUser().getRole() != User.Role.Pacjent) {
            return "redirect:/login";
        }
        model.addAttribute("isLogged", this.sessionObject.isLogged());
        model.addAttribute("role", this.sessionObject.isLogged() ? this.sessionObject.getLoggedUser().getRole().toString() : null);
        model.addAttribute("doctor", this.doctorService.getDoctorByDoctorId(doctorId));
        model.addAttribute("appointment", new Appointment());
        model.addAttribute("info", this.sessionObject.getInfo());
        return "patient/makeapp";
    }
    @RequestMapping(value = "/makeapp/{doctorId}", method = RequestMethod.POST)
    public String makeAppointment(@ModelAttribute Appointment appointment, @PathVariable int doctorId){
        if(!this.sessionObject.isLogged() || this.sessionObject.getLoggedUser().getRole() != User.Role.Pacjent) {
            return "redirect:/login";
        }
        if(!this.appointmentService.addAppointment(appointment, doctorId)) {
            this.sessionObject.setInfo("Błąd, wybrany termin wizyty jest zajęty lub dany lekarz nie pracuje w tym terminie.");
            return "redirect:/patient/app";
        }
        this.appointmentService.addAppointment(appointment, doctorId);
        this.sessionObject.setInfo("Sukces, ustalono nowy termin wizyty.");
        return "redirect:/patient/currentapp";
    }

    @RequestMapping(value = "/deleteapp/{appId}", method = RequestMethod.GET)
    public String deleteAppointment(Model model, @PathVariable int appId){
        if(!this.sessionObject.isLogged() || this.sessionObject.getLoggedUser().getRole() != User.Role.Pacjent) {
            return "redirect:/login";
        }
        model.addAttribute("isLogged", this.sessionObject.isLogged());
        model.addAttribute("role", this.sessionObject.isLogged() ? this.sessionObject.getLoggedUser().getRole().toString() : null);
        model.addAttribute("appointment", this.appointmentService.getById(appId));
        model.addAttribute("info", this.sessionObject.getInfo());
        return "patient/deleteapp";
    }

    @RequestMapping(value = "/deleteapp/{appId}", method = RequestMethod.POST)
    public String deleteAppointment(@ModelAttribute Appointment appointment, @PathVariable int appId){
        if(!this.sessionObject.isLogged() || this.sessionObject.getLoggedUser().getRole() != User.Role.Pacjent) {
            return "redirect:/login";
        }
        if(!this.appointmentService.delete(appId)) {
            this.sessionObject.setInfo("Wystąpił błąd.");
            return "redirect:/patient/currentapp";
        }
        this.appointmentService.delete(appId);
        this.sessionObject.setInfo("Pomyślnie usunięto termin wizyty.");
        return "redirect:/patient/currentapp";
    }

    @RequestMapping(value = "/editapp/{appId}", method = RequestMethod.GET)
    public String editAppointment(Model model, @PathVariable int appId){
        if(!this.sessionObject.isLogged() || this.sessionObject.getLoggedUser().getRole() != User.Role.Pacjent) {
            return "redirect:/login";
        }
        model.addAttribute("isLogged", this.sessionObject.isLogged());
        model.addAttribute("role", this.sessionObject.isLogged() ? this.sessionObject.getLoggedUser().getRole().toString() : null);
        model.addAttribute("appointment", this.appointmentService.getById(appId));
        model.addAttribute("info", this.sessionObject.getInfo());
        return "patient/editapp";
    }

    @RequestMapping(value = "/editapp/{appId}", method = RequestMethod.POST)
    public String editAppointment(@ModelAttribute Appointment appointment, @PathVariable int appId){
        if(!this.sessionObject.isLogged() || this.sessionObject.getLoggedUser().getRole() != User.Role.Pacjent) {
            return "redirect:/login";
        }
        if(!this.appointmentService.update(appId)) {
            this.sessionObject.setInfo("Wystąpił błąd.");
            return "redirect:/patient/currentapp";
        }
        this.appointmentService.update(appId);
        this.sessionObject.setInfo("Zmieniono termin wizyty.");
        return "redirect:/patient/currentapp";
    }
}
