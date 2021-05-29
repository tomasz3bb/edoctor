package pl.edu.wszib.edoctor.model;

import javax.persistence.*;
import java.sql.Date;

@Entity(name = "tpatient")
public class Patient{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int patientId;
    @OneToOne(fetch = FetchType.EAGER)
    private User user;
    private String name;
    private String surname;
    private String phone;
    private Date dateOfBirth;
    private String PESEL;

    public Patient() {
    }

    public Patient(int patientId, User user, String name, String surname, String phone, Date dateOfBirth, String PESEL) {
        this.patientId = patientId;
        this.user = user;
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.dateOfBirth = dateOfBirth;
        this.PESEL = PESEL;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
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

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPESEL() {
        return PESEL;
    }

    public void setPESEL(String PESEL) {
        this.PESEL = PESEL;
    }
}
