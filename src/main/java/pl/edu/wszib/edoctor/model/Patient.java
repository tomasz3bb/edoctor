package pl.edu.wszib.edoctor.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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
}
