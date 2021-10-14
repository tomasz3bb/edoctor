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
@Entity(name = "tappointment")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int appointmentId;
    @OneToOne(fetch = FetchType.EAGER)
    private Patient patient;
    @OneToOne(fetch = FetchType.EAGER)
    private AppSlot appSlot;
    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status{
        Zaplanowana,
        Zakończona
    }

}
