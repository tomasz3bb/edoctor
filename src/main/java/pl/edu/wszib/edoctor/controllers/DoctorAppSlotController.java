package pl.edu.wszib.edoctor.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.edu.wszib.edoctor.model.AppSlot;
import pl.edu.wszib.edoctor.model.Doctor;
import pl.edu.wszib.edoctor.model.DoctorSchedule;
import pl.edu.wszib.edoctor.model.User;
import pl.edu.wszib.edoctor.services.*;
import pl.edu.wszib.edoctor.session.SessionObject;

import javax.annotation.Resource;
import java.sql.Date;

@Controller
@RequestMapping("/doctor")
public class DoctorAppSlotController {
    @Resource
    SessionObject sessionObject;

    @Autowired
    IUserService userService;

    @Autowired
    IDoctorService doctorService;

    @Autowired
    IDoctorScheduleService doctorScheduleService;

    @Autowired
    IAppSlotService appSlotService;

    @Autowired
    IAppointmentService appointmentService;


    @RequestMapping(value = "/myappslot", method = RequestMethod.GET)
    public String currentDoctorAppSlot(Model model){
        if(!this.sessionObject.isLogged()) {
            return "redirect:/login";
        }
        Doctor doctor = this.doctorService.getDoctorByUserId(this.sessionObject.getLoggedUser().getUserId());
        model.addAttribute("isLogged", this.sessionObject.isLogged());
        model.addAttribute("role", this.sessionObject.isLogged() ? this.sessionObject.getLoggedUser().getRole().toString() : null);
        model.addAttribute("myappslot", this.appSlotService.getAllByDoctor(doctor));
        model.addAttribute("info", this.sessionObject.getInfo());
        return "doctor/myappslot";
    }

    @RequestMapping(value = "/addappslot", method = RequestMethod.GET)
    public String addDoctorAppSlot(Model model) {
        if(!this.sessionObject.isLogged() || this.sessionObject.getLoggedUser().getRole() != User.Role.Lekarz) {
            return "redirect:/login";
        }
        model.addAttribute("doctorAppSlot", new AppSlot());
        model.addAttribute("isLogged", this.sessionObject.isLogged());
        model.addAttribute("role", this.sessionObject.isLogged() ? this.sessionObject.getLoggedUser().getRole().toString() : null);
        model.addAttribute("info", this.sessionObject.getInfo());
        return "doctor/addappslot";
    }
    @RequestMapping(value = "/addappslot", method = RequestMethod.POST)
    public String addDoctorAppSlot(@ModelAttribute AppSlot appSlot) {
        if(!this.sessionObject.isLogged() || this.sessionObject.getLoggedUser().getRole() != User.Role.Lekarz) {
            return "redirect:/login";
        }
        if(this.appSlotService.addAppSlot(appSlot)){
            this.sessionObject.setInfo("Dodano nowy slot.");
        }else {
            this.sessionObject.setInfo("Błąd");
        }
        return "redirect:/doctor/myappslot";
    }

}
