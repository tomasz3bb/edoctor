package pl.edu.wszib.edoctor.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name = "tday")
public class Day {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int dayOfWeek;
    private String nameOfWeek;

}
