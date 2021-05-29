package pl.edu.wszib.edoctor.model;

import javax.persistence.*;

@Entity(name = "tdoctor")
public class Doctor{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int doctorId;
    @OneToOne(fetch = FetchType.EAGER)
    private User user;
    private String name;
    private String surname;
    private String phone;
    @ManyToOne(fetch = FetchType.EAGER)
    private Speciality speciality;
    private String PWZNumber;

    public Doctor() {
    }

    public Doctor(int doctorId, User user, String name, String surname, String phone, Speciality speciality, String PWZNumber) {
        this.doctorId = doctorId;
        this.user = user;
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.speciality = speciality;
        this.PWZNumber = PWZNumber;
    }

    public Speciality getSpeciality() {
        return speciality;
    }

    public void setSpeciality(Speciality speciality) {
        this.speciality = speciality;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPWZNumber() {
        return PWZNumber;
    }

    public void setPWZNumber(String PWZNumber) {
        this.PWZNumber = PWZNumber;
    }

    @Override
    public String toString() {
        return name + " " + surname + " " + phone + " " + speciality;
    }
}

