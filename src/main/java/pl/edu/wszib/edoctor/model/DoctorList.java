package pl.edu.wszib.edoctor.model;

import javax.persistence.*;

@Entity(name = "tdoctorlist")
public class DoctorList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int doctorListId;
    @ManyToOne
    private Doctor doctor;
    @ManyToOne
    private Patient patient;

    public DoctorList(){

    }

    public DoctorList(int doctorListId, Doctor doctor, Patient patient) {
        this.doctorListId = doctorListId;
        this.doctor = doctor;
        this.patient = patient;
    }

    public int getDoctorListId() {
        return doctorListId;
    }

    public void setDoctorListId(int doctorListId) {
        this.doctorListId = doctorListId;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    @Override
    public String toString() {
        return " " + doctor;
    }
}
