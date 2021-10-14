package pl.edu.wszib.edoctor.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name = "tappslot")
public class AppSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int appSlotId;
    @OneToOne(fetch = FetchType.EAGER)
    private Doctor doctor;
    private Date appointmentDate;
    private String dayOfWeek;
    private LocalTime appointmentTimeStart;
    private boolean isAvailable;
}
