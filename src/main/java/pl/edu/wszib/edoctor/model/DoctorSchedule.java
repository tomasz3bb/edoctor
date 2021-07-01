package pl.edu.wszib.edoctor.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

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
    private String dayOfWeek;
    private LocalTime startOfWork;
    private LocalTime endOfWork;
    @OneToOne(fetch = FetchType.EAGER)
    private Office office;
}
