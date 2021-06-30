package pl.edu.wszib.edoctor.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.edu.wszib.edoctor.model.Patient;
import pl.edu.wszib.edoctor.model.User;
import pl.edu.wszib.edoctor.model.view.ChangePasswordModel;
import pl.edu.wszib.edoctor.model.view.ChangePhotoModel;
import pl.edu.wszib.edoctor.model.view.RegistrationModel;
import pl.edu.wszib.edoctor.services.IDoctorScheduleService;
import pl.edu.wszib.edoctor.services.IDoctorService;
import pl.edu.wszib.edoctor.services.IPatientService;
import pl.edu.wszib.edoctor.services.IUserService;
import pl.edu.wszib.edoctor.session.SessionObject;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.security.Principal;
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

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

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

        return "patient/account";
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
        return "doctor/account";
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

    @RequestMapping(value = "/changepass/{userId}", method = RequestMethod.GET)
    public String changePass(Model model, @PathVariable int userId){
        if(!this.sessionObject.isLogged()){
            return "redirect:/login";
        }
        User user = this.userService.getUserById(userId);
        model.addAttribute("user", user);
        model.addAttribute("changePasswordModel", new ChangePasswordModel());
        model.addAttribute("isLogged", this.sessionObject.isLogged());
        model.addAttribute("role", this.sessionObject.isLogged() ? this.sessionObject.getLoggedUser().getRole().toString() : null);
        model.addAttribute("info", this.sessionObject.getInfo());
        return "changepass";
    }
    @RequestMapping(value = "/changepass/{userId}", method = RequestMethod.POST)
    public String changePass(@ModelAttribute User user, @ModelAttribute ChangePasswordModel changePasswordModel){
        if(!this.sessionObject.isLogged()) {
            return "redirect:/login";
        }
        if (!this.userService.updatePassword(user, changePasswordModel)){
            this.sessionObject.setInfo("Wystąpił błąd.");
            return "redirect:/changepass/{userId}";
        }
        this.userService.updatePassword(user, changePasswordModel);
        this.sessionObject.setInfo("Zmiany zapisane.");
        return "redirect:/changepass/{userId}";
    }

    @RequestMapping(value = "/changephoto/{userId}", method = RequestMethod.GET)
    public String changePhoto(Model model, @PathVariable int userId){
        if(!this.sessionObject.isLogged()){
            return "redirect:/login";
        }
        User user = this.userService.getUserById(userId);
        model.addAttribute("user", user);
        model.addAttribute("changePhotoModel", new ChangePhotoModel());
        model.addAttribute("isLogged", this.sessionObject.isLogged());
        model.addAttribute("role", this.sessionObject.isLogged() ? this.sessionObject.getLoggedUser().getRole().toString() : null);
        model.addAttribute("info", this.sessionObject.getInfo());
        return "changephoto";
    }
    @RequestMapping(value = "/changephoto/{userId}", method = RequestMethod.POST)
    public String changePhoto(@ModelAttribute User user, @ModelAttribute ChangePhotoModel changePhotoModel,
                              @RequestParam("file") MultipartFile file, ModelMap modelMap){
        if(!this.sessionObject.isLogged()) {
            return "redirect:/login";
        }
        modelMap.addAttribute("file", file);
        if (!this.userService.updatePhoto(user, changePhotoModel)){
            this.sessionObject.setInfo("Wystąpił błąd.");
            return "redirect:/changephoto/{userId}";
        }
        this.userService.updatePhoto(user, changePhotoModel);
        this.sessionObject.setInfo("Zmiany zapisane.");
        return "redirect:/changephoto/{userId}";
    }
}
