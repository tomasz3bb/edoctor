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
@Entity(name = "tdoctorlist")
public class DoctorList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int doctorListId;
    @ManyToOne
    private Doctor doctor;
    @ManyToOne
    private Patient patient;

}
