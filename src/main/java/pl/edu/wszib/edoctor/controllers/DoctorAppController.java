package pl.edu.wszib.edoctor.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.edu.wszib.edoctor.model.*;
import pl.edu.wszib.edoctor.services.*;
import pl.edu.wszib.edoctor.session.SessionObject;

import javax.annotation.Resource;

@Controller
@RequestMapping("/doctor")
public class DoctorAppController {
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

    @Autowired
    IAppointmentDetailService appointmentDetailService;

    @RequestMapping(value = "/patienthist/{patientId}", method = RequestMethod.GET)
    public String showAllPatientAppointmentBy(Model model, @PathVariable int patientId){
        if (!this.sessionObject.isLogged() || this.sessionObject.getLoggedUser().getRole() != User.Role.Lekarz){
            return "redirect:/login";
        }
        Patient patient = this.patientService.getPatientByPatientId(patientId);
        model.addAttribute("role", this.sessionObject.isLogged() ? this.sessionObject.getLoggedUser().getRole().toString() : null);
        model.addAttribute("isLogged", this.sessionObject.isLogged());
        model.addAttribute("appList", this.appointmentService.getHistAppByPatient(patient.getUser().getUserId(), Appointment.Status.Zakończona));
        model.addAttribute("info", this.sessionObject.getInfo());
        return "doctor/patienthist";
    }

    @RequestMapping(value = "/appdetail/{appId}", method = RequestMethod.GET)
    public String showAppDetailDoctor(@PathVariable int appId, Model model){
        if(!this.sessionObject.isLogged() || this.sessionObject.getLoggedUser().getRole() != User.Role.Lekarz) {
            return "redirect:/login";
        }
        model.addAttribute("appdetail", this.appointmentDetailService.getById(appId));
        model.addAttribute("role", this.sessionObject.isLogged() ? this.sessionObject.getLoggedUser().getRole().toString() : null);
        model.addAttribute("isLogged", this.sessionObject.isLogged());
        model.addAttribute("info", this.sessionObject.getInfo());
        return "doctor/appdetail";
    }

    @RequestMapping(value = "/patientcurrent/{patientId}", method = RequestMethod.GET)
    public String showCurrentPatientAppByDoctor(Model model, @PathVariable int patientId){
        if (!this.sessionObject.isLogged()){
            return "redirect:/login";
        }
        Patient patient = this.patientService.getPatientByPatientId(patientId);
        model.addAttribute("currentapp", this.appointmentService.getCurrentAppByPatient(patient.getUser().getUserId(), Appointment.Status.Zaplanowana));
        model.addAttribute("role", this.sessionObject.isLogged() ? this.sessionObject.getLoggedUser().getRole().toString() : null);
        model.addAttribute("isLogged", this.sessionObject.isLogged());
        model.addAttribute("info", this.sessionObject.getInfo());
        return "doctor/patientcurrent";
    }
    @RequestMapping(value = "/endapp/{appId}", method = RequestMethod.GET)
    public String endApp(Model model, @PathVariable int appId) {
        if(!this.sessionObject.isLogged() || this.sessionObject.getLoggedUser().getRole() != User.Role.Lekarz) {
            return "redirect:/login";
        }
        model.addAttribute("appDetail", new AppointmentDetail());
        model.addAttribute("isLogged", this.sessionObject.isLogged());
        model.addAttribute("role", this.sessionObject.isLogged() ? this.sessionObject.getLoggedUser().getRole().toString() : null);
        model.addAttribute("info", this.sessionObject.getInfo());
        return "doctor/endapp";
    }
    @RequestMapping(value = "/endapp/{appId}", method = RequestMethod.POST)
    public String endApp(@ModelAttribute AppointmentDetail appointmentDetail, @PathVariable int appId) {
        if(!this.sessionObject.isLogged() || this.sessionObject.getLoggedUser().getRole() != User.Role.Lekarz) {
            return "redirect:/login";
        }
        Appointment appointment = this.appointmentService.getById(appId);
        if(this.appointmentDetailService.add(appointment, appointmentDetail)){
            this.sessionObject.setInfo("Zakończono wizytę i dodano szczegóły wizyty.");
        }else {
            this.sessionObject.setInfo("Błąd");
        }
        return "redirect:/main";
    }
}
