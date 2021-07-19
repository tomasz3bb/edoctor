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

    @Autowired
    IOfficeService officeService;


    @RequestMapping(value = "/patients", method = RequestMethod.GET)
    public String showAllPatientsByDoctor(Model model){
        if (!this.sessionObject.isLogged() || this.sessionObject.getLoggedUser().getRole() != User.Role.Lekarz){
            return "redirect:/login";
        }
        Doctor loggedDoctor = this.doctorService.getDoctorByUserId(this.sessionObject.getLoggedUser().getUserId());
        model.addAttribute("patientsList", this.doctorListService.getPatientsByDoctor(loggedDoctor));
        model.addAttribute("role", this.sessionObject.isLogged() ? this.sessionObject.getLoggedUser().getRole().toString() : null);
        model.addAttribute("isLogged", this.sessionObject.isLogged());
        model.addAttribute("info", this.sessionObject.getInfo());
        return "doctor/patients";
    }

    @RequestMapping(value = "/editdata", method = RequestMethod.GET)
    public String editDoctorData(Model model){
        if(!this.sessionObject.isLogged() || this.sessionObject.getLoggedUser().getRole() != User.Role.Lekarz) {
            return "redirect:/login";
        }
        Doctor loggedDoctor = this.doctorService.getDoctorByUserId(this.sessionObject.getLoggedUser().getUserId());
        model.addAttribute("doctor", loggedDoctor);
        model.addAttribute("isLogged", this.sessionObject.isLogged());
        model.addAttribute("role", this.sessionObject.isLogged() ? this.sessionObject.getLoggedUser().getRole().toString() : null);
        model.addAttribute("info", this.sessionObject.getInfo());
        return "doctor/editdata";
    }
    @RequestMapping(value = "/editdata", method = RequestMethod.POST)
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

    @RequestMapping(value = "/assignOffice/{doctorScheduleId}", method = RequestMethod.GET)
    public String assignOffice(@PathVariable int doctorScheduleId, Model model){
        if(!this.sessionObject.isLogged() || this.sessionObject.getLoggedUser().getRole() != User.Role.Lekarz) {
            return "redirect:/login";
        }
        DoctorSchedule doctorSchedule = this.doctorScheduleService.getDoctorScheduleById(doctorScheduleId);
        List<Office> officeList = this.officeService.getAllAvailable();
        model.addAttribute("doctorDS", doctorSchedule);
        model.addAttribute("officeAvailable", officeList);
        model.addAttribute("isLogged", this.sessionObject.isLogged());
        model.addAttribute("role", this.sessionObject.isLogged() ? this.sessionObject.getLoggedUser().getRole().toString() : null);
        model.addAttribute("info", this.sessionObject.getInfo());
        return "doctor/assignOffice";
    }
    @RequestMapping(value = "/assignOffice/{doctorScheduleId}", method = RequestMethod.POST)
    public String assignOffice(@ModelAttribute DoctorSchedule doctorSchedule){
        if(!this.sessionObject.isLogged() || this.sessionObject.getLoggedUser().getRole() != User.Role.Lekarz) {
            return "redirect:/login";
        }
        if(!this.officeService.assignOffice(doctorSchedule)){
            this.sessionObject.setInfo("Błąd");
            return "redirect:/doctor/myschedule";
        }
        this.officeService.assignOffice(doctorSchedule);
        this.sessionObject.setInfo("Zapisano zmiany");
        return "redirect:/doctor/myschedule";
    }
}
