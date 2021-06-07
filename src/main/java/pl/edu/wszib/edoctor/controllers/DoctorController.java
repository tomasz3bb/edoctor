package pl.edu.wszib.edoctor.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.edu.wszib.edoctor.model.*;
import pl.edu.wszib.edoctor.services.*;
import pl.edu.wszib.edoctor.session.SessionObject;

import javax.annotation.Resource;
import java.util.List;

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

    @Autowired
    IDoctorScheduleService doctorScheduleService;

    @Autowired
    IDoctorListService doctorListService;

    @Autowired
    IAppointmentService appointmentService;


    @RequestMapping(value = "/doctors", method = RequestMethod.GET)
    public String showAllDoctors(Model model){
        if (!this.sessionObject.isLogged()){
            return "redirect:/login";
        }
        model.addAttribute("allDoctors", this.doctorService.getAll());
        model.addAttribute("isLogged", this.sessionObject.isLogged());
        model.addAttribute("role", this.sessionObject.isLogged() ? this.sessionObject.getLoggedUser().getRole().toString() : null);
        return "doctors";
    }

    @RequestMapping(value = "/doctorschedule/{doctorId}", method = RequestMethod.GET)
    public String doctorSchedule(Model model, @PathVariable int doctorId){
        if(!this.sessionObject.isLogged()) {
            return "redirect:/login";
        }
        model.addAttribute("isLogged", this.sessionObject.isLogged());
        model.addAttribute("role", this.sessionObject.isLogged() ? this.sessionObject.getLoggedUser().getRole().toString() : null);
        model.addAttribute("currentDS", this.doctorScheduleService.getAllByDoctorId(doctorId));
        return "doctorschedule";
    }

    @RequestMapping(value = "/doctor_myschedule", method = RequestMethod.GET)
    public String currentDoctorSchedule(Model model){
        if(!this.sessionObject.isLogged()) {
            return "redirect:/login";
        }
        Doctor doctor = this.doctorService.getDoctorByUserId(this.sessionObject.getLoggedUser().getUserId());
        model.addAttribute("isLogged", this.sessionObject.isLogged());
        model.addAttribute("role", this.sessionObject.isLogged() ? this.sessionObject.getLoggedUser().getRole().toString() : null);
        model.addAttribute("myschedule", this.doctorScheduleService.getAllByDoctor(doctor));
        return "doctor_myschedule";
    }

    @RequestMapping(value = "/doctor_patients", method = RequestMethod.GET)
    public String showAllPatientsByDoctor(Model model){
        if (!this.sessionObject.isLogged() || this.sessionObject.getLoggedUser().getRole() != User.Role.Lekarz){
            return "redirect:/login";
        }
        Doctor loggedDoctor = this.doctorService.getDoctorByUserId(this.sessionObject.getLoggedUser().getUserId());
        model.addAttribute("patientsList", this.doctorListService.getPatientsByDoctor(loggedDoctor));
        model.addAttribute("role", this.sessionObject.isLogged() ? this.sessionObject.getLoggedUser().getRole().toString() : null);
        model.addAttribute("isLogged", this.sessionObject.isLogged());
        return "doctor_patients";
    }

    @RequestMapping(value = "/doctor_patienthist/{patientId}", method = RequestMethod.GET)
    public String showAllPatientAppointmentBy(Model model, @PathVariable int patientId){
        if (!this.sessionObject.isLogged() || this.sessionObject.getLoggedUser().getRole() != User.Role.Lekarz){
            return "redirect:/login";
        }
        model.addAttribute("role", this.sessionObject.isLogged() ? this.sessionObject.getLoggedUser().getRole().toString() : null);
        model.addAttribute("isLogged", this.sessionObject.isLogged());
        Doctor doctor = this.doctorService.getDoctorByUserId(this.sessionObject.getLoggedUser().getUserId());
        model.addAttribute("appList", this.appointmentService.getAllPatientAppointmentByDoctor(doctor, patientId));
        return "doctor_patienthist";
    }

    @RequestMapping(value = "/doctor_addday", method = RequestMethod.GET)
    public String addDoctorSchedule(Model model) {
        if(!this.sessionObject.isLogged() || this.sessionObject.getLoggedUser().getRole() != User.Role.Lekarz) {
            return "redirect:/login";
        }
        model.addAttribute("doctorSchedule", new DoctorSchedule());
        model.addAttribute("isLogged", this.sessionObject.isLogged());
        model.addAttribute("role", this.sessionObject.isLogged() ? this.sessionObject.getLoggedUser().getRole().toString() : null);
        return "doctor_addday";
    }
    @RequestMapping(value = "/doctor_addday", method = RequestMethod.POST)
    public String addDoctorSchedule(@ModelAttribute DoctorSchedule doctorSchedule) {
        if(!this.sessionObject.isLogged() || this.sessionObject.getLoggedUser().getRole() != User.Role.Lekarz) {
            return "redirect:/login";
        }
        this.doctorScheduleService.save(doctorSchedule);
        return "redirect:/doctor_myschedule";
    }

    @RequestMapping(value = "/doctor_editDS/{doctorScheduleId}", method = RequestMethod.GET)
    public String editDoctorSchedule(@PathVariable int doctorScheduleId, Model model){
        if(!this.sessionObject.isLogged() || this.sessionObject.getLoggedUser().getRole() != User.Role.Lekarz) {
            return "redirect:/login";
        }
        DoctorSchedule doctorSchedule = this.doctorScheduleService.getDoctorScheduleById(doctorScheduleId);
        model.addAttribute("doctorDS", doctorSchedule);
        model.addAttribute("isLogged", this.sessionObject.isLogged());
        model.addAttribute("role", this.sessionObject.isLogged() ? this.sessionObject.getLoggedUser().getRole().toString() : null);
        return "doctor_editDS";
    }
    @RequestMapping(value = "/doctor_editDS/{doctorScheduleId}", method = RequestMethod.POST)
    public String editDoctorSchedule(@ModelAttribute DoctorSchedule doctorSchedule){
        if(!this.sessionObject.isLogged() || this.sessionObject.getLoggedUser().getRole() != User.Role.Lekarz) {
            return "redirect:/login";
        }
        this.doctorScheduleService.update(doctorSchedule);
        return "redirect:/doctor_myschedule";
    }

    @RequestMapping(value = "/doctor_deleteDS/{doctorScheduleId}", method = RequestMethod.GET)
    public String deleteDoctorSchedule(@PathVariable int doctorScheduleId, Model model){
        if(!this.sessionObject.isLogged() || this.sessionObject.getLoggedUser().getRole() != User.Role.Lekarz) {
            return "redirect:/login";
        }
        DoctorSchedule doctorSchedule = this.doctorScheduleService.getDoctorScheduleById(doctorScheduleId);
        model.addAttribute("doctorDS", doctorSchedule);
        model.addAttribute("isLogged", this.sessionObject.isLogged());
        model.addAttribute("role", this.sessionObject.isLogged() ? this.sessionObject.getLoggedUser().getRole().toString() : null);
        return "doctor_deleteDS";
    }
    @RequestMapping(value = "/doctor_deleteDS/{doctorScheduleId}", method = RequestMethod.POST)
    public String deleteDoctorSchedule(@ModelAttribute DoctorSchedule doctorSchedule){
        if(!this.sessionObject.isLogged() || this.sessionObject.getLoggedUser().getRole() != User.Role.Lekarz) {
            return "redirect:/login";
        }
        this.doctorScheduleService.delete(doctorSchedule);
        return "redirect:/doctor_myschedule";
    }
}
