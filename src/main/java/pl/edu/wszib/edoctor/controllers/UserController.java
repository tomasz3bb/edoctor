package pl.edu.wszib.edoctor.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import pl.edu.wszib.edoctor.model.Patient;
import pl.edu.wszib.edoctor.model.User;
import pl.edu.wszib.edoctor.model.view.ChangePasswordModel;
import pl.edu.wszib.edoctor.model.view.RegistrationModel;
import pl.edu.wszib.edoctor.services.IDoctorScheduleService;
import pl.edu.wszib.edoctor.services.IDoctorService;
import pl.edu.wszib.edoctor.services.IPatientService;
import pl.edu.wszib.edoctor.services.IUserService;
import pl.edu.wszib.edoctor.session.SessionObject;

import javax.annotation.Resource;
import java.io.IOException;
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
            return "redirect:/login";
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

    @RequestMapping(value = "/patient/account", method = RequestMethod.GET)
    public String patientInfo(Model model){
        if(!this.sessionObject.isLogged()) {
            return "redirect:/login";
        }
        model.addAttribute("user", this.sessionObject.getLoggedUser());
        model.addAttribute("patient", this.patientService.getPatientByUserId(this.sessionObject.getLoggedUser().getUserId()));
        model.addAttribute("isLogged", this.sessionObject.isLogged());
        model.addAttribute("role", this.sessionObject.isLogged() ? this.sessionObject.getLoggedUser().getRole().toString() : null);
        model.addAttribute("info", this.sessionObject.getInfo());
        model.addAttribute("changePasswordModel", new ChangePasswordModel());
        return "patient/account";
    }

    @RequestMapping(value = "/patient/account", method = RequestMethod.POST)
    public String changePatientPass(@ModelAttribute User user, @ModelAttribute ChangePasswordModel changePasswordModel){
        if(!this.sessionObject.isLogged()) {
            return "redirect:/login";
        }
        if (!this.userService.updatePassword(this.sessionObject.getLoggedUser(), changePasswordModel)){
            this.sessionObject.setInfo("Wystąpił błąd.");
            return "redirect:/patient/account";
        }
        this.userService.updatePassword(this.sessionObject.getLoggedUser(), changePasswordModel);
        this.sessionObject.setInfo("Zmiany zapisane.");
        return "redirect:/patient/account";
    }

    @RequestMapping(value = "/doctor/account", method = RequestMethod.GET)
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
        model.addAttribute("info", this.sessionObject.getInfo());
        model.addAttribute("changePasswordModel", new ChangePasswordModel());
        return "doctor/account";
    }

    @RequestMapping(value = "/doctor/account", method = RequestMethod.POST)
    public String changeDoctorPass(@ModelAttribute User user, @ModelAttribute ChangePasswordModel changePasswordModel){
        if(!this.sessionObject.isLogged()) {
            return "redirect:/login";
        }
        if (!this.userService.updatePassword(this.sessionObject.getLoggedUser(), changePasswordModel)){
            this.sessionObject.setInfo("Wystąpił błąd.");
            return "redirect:/doctor/account";
        }
        this.userService.updatePassword(this.sessionObject.getLoggedUser(), changePasswordModel);
        this.sessionObject.setInfo("Zmiany zapisane.");
        return "redirect:/doctor/account";
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

    @RequestMapping(value = "/changephoto", method = RequestMethod.GET)
    public String changePhoto(Model model){
        if(!this.sessionObject.isLogged()){
            return "redirect:/login";
        }
        User user = this.userService.getUserById(this.sessionObject.getLoggedUser().getUserId());
        model.addAttribute("user", user);
        model.addAttribute("isLogged", this.sessionObject.isLogged());
        model.addAttribute("role", this.sessionObject.isLogged() ? this.sessionObject.getLoggedUser().getRole().toString() : null);
        model.addAttribute("info", this.sessionObject.getInfo());
        return "changephoto";
    }
    @RequestMapping(value = "/changephoto", method = RequestMethod.POST)
    public String changePhoto(@ModelAttribute User user, @RequestParam("image") MultipartFile image){
        if(!this.sessionObject.isLogged()) {
            return "redirect:/login";
        }
        if (!this.userService.uploadPhoto(user, image)){
            this.sessionObject.setInfo("Wystąpił błąd podczas uploadu.");
            return "redirect:/doctor/account";
        }
        this.userService.uploadPhoto(user, image);
        this.sessionObject.setInfo("Zmieniono zdjęcie profilowe.");

        if (this.sessionObject.getLoggedUser().getRole().equals(User.Role.Lekarz)){
            return "redirect:/doctor/account";
        }
        else {
            return "redirect:/patient/account";
        }
    }
}
