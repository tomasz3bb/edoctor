package pl.edu.wszib.edoctor.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.edu.wszib.edoctor.model.Appointment;
import pl.edu.wszib.edoctor.model.Doctor;
import pl.edu.wszib.edoctor.model.User;
import pl.edu.wszib.edoctor.services.*;
import pl.edu.wszib.edoctor.session.SessionObject;

import javax.annotation.Resource;

@Controller
public class AppointmentController {
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

    @RequestMapping(value = "/patient_makeapp/{doctorId}", method = RequestMethod.GET)
    public String makeAppointment(Model model, @PathVariable int doctorId){
        if(!this.sessionObject.isLogged() || this.sessionObject.getLoggedUser().getRole() != User.Role.Pacjent) {
            return "redirect:/login";
        }
        model.addAttribute("isLogged", this.sessionObject.isLogged());
        model.addAttribute("role", this.sessionObject.isLogged() ? this.sessionObject.getLoggedUser().getRole().toString() : null);
        model.addAttribute("doctor", this.doctorService.getDoctorByDoctorId(doctorId));
        model.addAttribute("appointment", new Appointment());
        return "patient_makeapp";
    }
    @RequestMapping(value = "/patient_makeapp/{doctorId}", method = RequestMethod.POST)
    public String makeAppointment(@ModelAttribute Appointment appointment, @PathVariable int doctorId){
        if(!this.sessionObject.isLogged() || this.sessionObject.getLoggedUser().getRole() != User.Role.Pacjent) {
            return "redirect:/login";
        }
        if(this.appointmentService.addAppointment(appointment, doctorId)) {
            return "redirect:/patient_app";
        } else {
            return "redirect:/patient_app";
        }
    }
}
