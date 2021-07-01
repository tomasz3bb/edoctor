package pl.edu.wszib.edoctor.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "toffice")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Office {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int officeId;
    private int roomNumber;
    private int floor;
    private String description;
    private boolean isAvailable;
}
