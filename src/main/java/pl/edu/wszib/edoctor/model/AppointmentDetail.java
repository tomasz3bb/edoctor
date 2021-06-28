package pl.edu.wszib.edoctor.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "tappdetail")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentDetail {
    @Id
    private int appDetailId;
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private Appointment appointment;
    private String description;
}
