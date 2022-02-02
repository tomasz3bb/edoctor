package pl.edu.wszib.edoctor.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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


}

