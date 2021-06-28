package pl.edu.wszib.edoctor.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name = "tdoctorschedule")
public class DoctorSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int doctorScheduleId;
    @OneToOne(fetch = FetchType.EAGER)
    private Doctor doctor;
    @Enumerated
    private DayOfWeek dayOfWeek;
    private String startOfWork;
    private String endOfWork;

    private enum DayOfWeek {
        Niedziela,
        Poniedziałek,
        Wtorek,
        Środa,
        Czwartek,
        Piątek,
        Sobota;
    }
}
