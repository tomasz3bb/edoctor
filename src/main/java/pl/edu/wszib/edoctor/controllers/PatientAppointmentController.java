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
    IDoctorScheduleService doctorScheduleService;

    @Autowired
    IAppointmentService appointmentService;

    @Autowired
    IAppointmentDetailService appointmentDetailService;

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
        model.addAttribute("currentDS", this.doctorScheduleService.getAllByDoctorId(doctorId));
        return "patient/makeapp";
    }
    @RequestMapping(value = "/makeapp/{doctorId}", method = RequestMethod.POST)
    public String makeAppointment(@ModelAttribute Appointment appointment, @PathVariable int doctorId){
        if(!this.sessionObject.isLogged() || this.sessionObject.getLoggedUser().getRole() != User.Role.Pacjent) {
            return "redirect:/login";
        }
        if(this.appointmentService.addAppointment(appointment, doctorId)){
            this.sessionObject.setInfo("Sukces, ustalono nowy termin wizyty.");
        }else {
            this.sessionObject.setInfo("Błąd, temin zajęty lub dany lekarz nie pracuje o tej porze.");
        }
        return "redirect:/patient/currentapp";
    }

    @RequestMapping(value = "/deleteapp/{appointmentId}", method = RequestMethod.GET)
    public String deleteAppointment(Model model, @PathVariable int appointmentId){
        if(!this.sessionObject.isLogged() || this.sessionObject.getLoggedUser().getRole() != User.Role.Pacjent) {
            return "redirect:/login";
        }
        Appointment appointment = this.appointmentService.getById(appointmentId);
        model.addAttribute("isLogged", this.sessionObject.isLogged());
        model.addAttribute("role", this.sessionObject.isLogged() ? this.sessionObject.getLoggedUser().getRole().toString() : null);
        model.addAttribute("appointment", appointment);
        model.addAttribute("info", this.sessionObject.getInfo());
        return "patient/deleteapp";
    }

    @RequestMapping(value = "/deleteapp/{appointmentId}", method = RequestMethod.POST)
    public String deleteAppointment(@ModelAttribute Appointment appointment){
        if(!this.sessionObject.isLogged() || this.sessionObject.getLoggedUser().getRole() != User.Role.Pacjent) {
            return "redirect:/login";
        }
        if (this.appointmentService.delete(appointment)){
            this.sessionObject.setInfo("Pomyślnie usunięto termin wizyty.");
        }else {
            this.sessionObject.setInfo("Błąd");
        }
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
    public String editAppointment(@ModelAttribute Appointment appointment){
        if(!this.sessionObject.isLogged() || this.sessionObject.getLoggedUser().getRole() != User.Role.Pacjent) {
            return "redirect:/login";
        }
        if (!this.appointmentService.update(appointment)){
            this.sessionObject.setInfo("Wystąpił błąd!");
            return "redirect:/patient/currentapp";
        }
        this.appointmentService.update(appointment);
        this.sessionObject.setInfo("Zmieniono termin wizyty.");
        return "redirect:/patient/currentapp";
    }
    @RequestMapping(value = "/appdetail/{appId}", method = RequestMethod.GET)
    public String showAppDetail(@PathVariable int appId, Model model){
        if(!this.sessionObject.isLogged() || this.sessionObject.getLoggedUser().getRole() != User.Role.Pacjent) {
            return "redirect:/login";
        }
        model.addAttribute("appdetail", this.appointmentDetailService.getById(appId));
        model.addAttribute("role", this.sessionObject.isLogged() ? this.sessionObject.getLoggedUser().getRole().toString() : null);
        model.addAttribute("isLogged", this.sessionObject.isLogged());
        model.addAttribute("info", this.sessionObject.getInfo());
        return "patient/appdetail";
    }
}
