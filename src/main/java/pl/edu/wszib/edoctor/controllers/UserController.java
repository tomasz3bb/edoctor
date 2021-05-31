package pl.edu.wszib.edoctor.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.edu.wszib.edoctor.model.Doctor;
import pl.edu.wszib.edoctor.model.User;
import pl.edu.wszib.edoctor.services.IDoctorScheduleService;
import pl.edu.wszib.edoctor.services.IDoctorService;
import pl.edu.wszib.edoctor.services.IPatientService;
import pl.edu.wszib.edoctor.services.IUserService;
import pl.edu.wszib.edoctor.session.SessionObject;

import javax.annotation.Resource;

@Controller
public class UserController {

    @Autowired
    IUserService userService;

    @Autowired
    IDoctorService doctorService;

    @Autowired
    IPatientService patientService;

    @Autowired
    IDoctorScheduleService doctorScheduleService;

    @Resource
    SessionObject sessionObject;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginForm(Model model) {
        if(this.sessionObject.isLogged()) {
            return "redirect:/main";
        }
        model.addAttribute("userModel", new User());
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@ModelAttribute User user) {
        this.userService.authenticate(user);
        if(this.sessionObject.isLogged()) {
            return "redirect:/main";
        }
        return "redirect:/login";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout() {
        this.userService.logout();
        return "redirect:/login";
    }

    @RequestMapping(value = "/patient_account", method = RequestMethod.GET)
    public String patientInfo(Model model){
        if(!this.sessionObject.isLogged()) {
            return "redirect:/login";
        }
        model.addAttribute("user", this.sessionObject.getLoggedUser());
        model.addAttribute("patient", this.patientService.getPatientByUserId(this.sessionObject.getLoggedUser().getUserId()));
        model.addAttribute("isLogged", this.sessionObject.isLogged());
        model.addAttribute("role", this.sessionObject.isLogged() ? this.sessionObject.getLoggedUser().getRole().toString() : null);
        return "patient_account";
    }

    @RequestMapping(value = "/doctor_account", method = RequestMethod.GET)
    public String doctorInfo(Model model){
        if(!this.sessionObject.isLogged()) {
            return "redirect:/login";
        }
        model.addAttribute("user", this.sessionObject.getLoggedUser());
        model.addAttribute("doctor", this.doctorService.getDoctorByUserId(this.sessionObject.getLoggedUser().getUserId()));
        model.addAttribute("doctorSchedule", this.doctorScheduleService.getDoctorScheduleByDoctor
                        (this.doctorService.getDoctorByUserId(this.sessionObject.getLoggedUser().getUserId())));
        model.addAttribute("isLogged", this.sessionObject.isLogged());
        model.addAttribute("role", this.sessionObject.isLogged() ? this.sessionObject.getLoggedUser().getRole().toString() : null);
        return "doctor_account";
    }
}
