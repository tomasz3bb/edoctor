package pl.edu.wszib.edoctor.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.edu.wszib.edoctor.model.Doctor;
import pl.edu.wszib.edoctor.model.Patient;
import pl.edu.wszib.edoctor.model.User;
import pl.edu.wszib.edoctor.model.view.RegistrationModel;
import pl.edu.wszib.edoctor.services.IDoctorScheduleService;
import pl.edu.wszib.edoctor.services.IDoctorService;
import pl.edu.wszib.edoctor.services.IPatientService;
import pl.edu.wszib.edoctor.services.IUserService;
import pl.edu.wszib.edoctor.session.SessionObject;

import javax.annotation.Resource;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        model.addAttribute("info", this.sessionObject.getInfo());
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@ModelAttribute User user) {
        if (!this.userService.authenticate(user)){
            this.sessionObject.setInfo("Błąd, niepoprawny login lub hasło!");
        }
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
        model.addAttribute("doctorSchedule", this.doctorScheduleService.getAllByDoctor
                        (this.doctorService.getDoctorByUserId(this.sessionObject.getLoggedUser().getUserId())));
        model.addAttribute("isLogged", this.sessionObject.isLogged());
        model.addAttribute("role", this.sessionObject.isLogged() ? this.sessionObject.getLoggedUser().getRole().toString() : null);
        return "doctor_account";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String registerForm(Model model) {
        model.addAttribute("registrationModel", new RegistrationModel());
        model.addAttribute("patient", new Patient());
        model.addAttribute("info", this.sessionObject.getInfo());
        return "register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@ModelAttribute RegistrationModel registrationModel, @ModelAttribute Patient patient) {
        Pattern regexp = Pattern.compile("[A-Za-z0-9]{5}.*");
        Matcher loginMatcher = regexp.matcher(registrationModel.getLogin());
        Matcher passMatcher = regexp.matcher(registrationModel.getPass());
        Matcher pass2Matcher = regexp.matcher(registrationModel.getPass2());

        if(!loginMatcher.matches() || !passMatcher.matches() || !pass2Matcher.matches() || !registrationModel.getPass().equals(registrationModel.getPass2())) {
            this.sessionObject.setInfo("Błąd, niepoprawny login lub hasło!");
            return "redirect:/register";
        }

        if(this.userService.register(registrationModel, patient)) {
            this.sessionObject.setInfo("Konto zostało pomyślnie założone.");
            return "redirect:/login";
        } else {
            this.sessionObject.setInfo("Login zajęty.");
            return "redirect:/register";
        }
    }
}
