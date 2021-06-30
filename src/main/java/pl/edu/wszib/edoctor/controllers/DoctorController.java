package pl.edu.wszib.edoctor.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.edu.wszib.edoctor.model.*;
import pl.edu.wszib.edoctor.services.*;
import pl.edu.wszib.edoctor.session.SessionObject;

import javax.annotation.Resource;

@Controller
@RequestMapping("/doctor")
public class DoctorController {
    @Resource
    SessionObject sessionObject;

    @Autowired
    IUserService userService;

    @Autowired
    IDoctorService doctorService;

    @Autowired
    IDoctorScheduleService doctorScheduleService;

    @Autowired
    IDoctorListService doctorListService;

    @Autowired
    IAppointmentService appointmentService;

    @RequestMapping(value = "/schedule/{doctorId}", method = RequestMethod.GET)
    public String doctorSchedule(Model model, @PathVariable int doctorId){
        if(!this.sessionObject.isLogged()) {
            return "redirect:/login";
        }
        model.addAttribute("isLogged", this.sessionObject.isLogged());
        model.addAttribute("role", this.sessionObject.isLogged() ? this.sessionObject.getLoggedUser().getRole().toString() : null);
        model.addAttribute("currentDS", this.doctorScheduleService.getAllByDoctorId(doctorId));
        return "doctor/schedule";
    }

    @RequestMapping(value = "/myschedule", method = RequestMethod.GET)
    public String currentDoctorSchedule(Model model){
        if(!this.sessionObject.isLogged()) {
            return "redirect:/login";
        }
        Doctor doctor = this.doctorService.getDoctorByUserId(this.sessionObject.getLoggedUser().getUserId());
        model.addAttribute("isLogged", this.sessionObject.isLogged());
        model.addAttribute("role", this.sessionObject.isLogged() ? this.sessionObject.getLoggedUser().getRole().toString() : null);
        model.addAttribute("myschedule", this.doctorScheduleService.getAllByDoctor(doctor));
        return "doctor/myschedule";
    }

    @RequestMapping(value = "/patients", method = RequestMethod.GET)
    public String showAllPatientsByDoctor(Model model){
        if (!this.sessionObject.isLogged() || this.sessionObject.getLoggedUser().getRole() != User.Role.Lekarz){
            return "redirect:/login";
        }
        Doctor loggedDoctor = this.doctorService.getDoctorByUserId(this.sessionObject.getLoggedUser().getUserId());
        model.addAttribute("patientsList", this.doctorListService.getPatientsByDoctor(loggedDoctor));
        model.addAttribute("role", this.sessionObject.isLogged() ? this.sessionObject.getLoggedUser().getRole().toString() : null);
        model.addAttribute("isLogged", this.sessionObject.isLogged());
        return "doctor/patients";
    }

    @RequestMapping(value = "/addday", method = RequestMethod.GET)
    public String addDoctorSchedule(Model model) {
        if(!this.sessionObject.isLogged() || this.sessionObject.getLoggedUser().getRole() != User.Role.Lekarz) {
            return "redirect:/login";
        }
        model.addAttribute("doctorSchedule", new DoctorSchedule());
        model.addAttribute("isLogged", this.sessionObject.isLogged());
        model.addAttribute("role", this.sessionObject.isLogged() ? this.sessionObject.getLoggedUser().getRole().toString() : null);
        model.addAttribute("info", this.sessionObject.getInfo());
        return "doctor/addday";
    }
    @RequestMapping(value = "/addday", method = RequestMethod.POST)
    public String addDoctorSchedule(@ModelAttribute DoctorSchedule doctorSchedule) {
        if(!this.sessionObject.isLogged() || this.sessionObject.getLoggedUser().getRole() != User.Role.Lekarz) {
            return "redirect:/login";
        }
        if(this.doctorScheduleService.save(doctorSchedule)){
            this.sessionObject.setInfo("Dodano dzień do harmonogramu.");
        }else {
            this.sessionObject.setInfo("Błąd");
        }
        return "redirect:/doctor/myschedule";
    }

    @RequestMapping(value = "/editDS/{doctorScheduleId}", method = RequestMethod.GET)
    public String editDoctorSchedule(@PathVariable int doctorScheduleId, Model model){
        if(!this.sessionObject.isLogged() || this.sessionObject.getLoggedUser().getRole() != User.Role.Lekarz) {
            return "redirect:/login";
        }
        DoctorSchedule doctorSchedule = this.doctorScheduleService.getDoctorScheduleById(doctorScheduleId);
        model.addAttribute("doctorDS", doctorSchedule);
        model.addAttribute("isLogged", this.sessionObject.isLogged());
        model.addAttribute("role", this.sessionObject.isLogged() ? this.sessionObject.getLoggedUser().getRole().toString() : null);
        model.addAttribute("info", this.sessionObject.getInfo());
        return "doctor/editDS";
    }
    @RequestMapping(value = "/editDS/{doctorScheduleId}", method = RequestMethod.POST)
    public String editDoctorSchedule(@ModelAttribute DoctorSchedule doctorSchedule){
        if(!this.sessionObject.isLogged() || this.sessionObject.getLoggedUser().getRole() != User.Role.Lekarz) {
            return "redirect:/login";
        }
        if(this.doctorScheduleService.update(doctorSchedule)){
            this.sessionObject.setInfo("Zapisano zmiany");
        }else {
            this.sessionObject.setInfo("Błąd");
        }
        return "redirect:/doctor/myschedule";
    }

    @RequestMapping(value = "/deleteDS/{doctorScheduleId}", method = RequestMethod.GET)
    public String deleteDoctorSchedule(@PathVariable int doctorScheduleId, Model model){
        if(!this.sessionObject.isLogged() || this.sessionObject.getLoggedUser().getRole() != User.Role.Lekarz) {
            return "redirect:/login";
        }
        DoctorSchedule doctorSchedule = this.doctorScheduleService.getDoctorScheduleById(doctorScheduleId);
        model.addAttribute("doctorDS", doctorSchedule);
        model.addAttribute("isLogged", this.sessionObject.isLogged());
        model.addAttribute("role", this.sessionObject.isLogged() ? this.sessionObject.getLoggedUser().getRole().toString() : null);
        model.addAttribute("info", this.sessionObject.getInfo());
        return "doctor/deleteDS";
    }
    @RequestMapping(value = "/deleteDS/{doctorScheduleId}", method = RequestMethod.POST)
    public String deleteDoctorSchedule(@ModelAttribute DoctorSchedule doctorSchedule){
        if(!this.sessionObject.isLogged() || this.sessionObject.getLoggedUser().getRole() != User.Role.Lekarz) {
            return "redirect:/login";
        }
        if(this.doctorScheduleService.delete(doctorSchedule)){
            this.sessionObject.setInfo("Usunięto dzień z harmonogramu.");
        }else {
        this.sessionObject.setInfo("Błąd");
        }
        return "redirect:/doctor/myschedule";
    }

    @RequestMapping(value = "/editdata/{doctorId}", method = RequestMethod.GET)
    public String editDoctorData(Model model, @PathVariable int doctorId){
        if(!this.sessionObject.isLogged() || this.sessionObject.getLoggedUser().getRole() != User.Role.Lekarz) {
            return "redirect:/login";
        }
        Doctor loggedDoctor = this.doctorService.getDoctorByDoctorId(doctorId);
        model.addAttribute("doctor", loggedDoctor);
        model.addAttribute("isLogged", this.sessionObject.isLogged());
        model.addAttribute("role", this.sessionObject.isLogged() ? this.sessionObject.getLoggedUser().getRole().toString() : null);
        model.addAttribute("info", this.sessionObject.getInfo());
        return "doctor/editdata";
    }
    @RequestMapping(value = "/editdata/{doctorId}", method = RequestMethod.POST)
    public String editDoctorData(@ModelAttribute Doctor doctor){
        if(!this.sessionObject.isLogged() || this.sessionObject.getLoggedUser().getRole() != User.Role.Lekarz) {
            return "redirect:/login";
        }
        if (!this.doctorService.update(doctor)){
            this.sessionObject.setInfo("Wystąpił błąd.");
            return "redirect:/doctor/account";
        }
        this.doctorService.update(doctor);
        this.sessionObject.setInfo("Zmiany zapisane.");
        return "redirect:/doctor/account";
    }
}
