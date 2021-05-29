package pl.edu.wszib.edoctor.model;

import javax.persistence.*;
import java.sql.Date;

@Entity(name = "tcalendar")
public class Calendar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int calendarId;
    private java.sql.Date date;
    @ManyToOne(fetch = FetchType.EAGER)
    private Day dayOfWeek;

    public Calendar() {
    }

    public Calendar(int calendarId, java.sql.Date date, Day dayOfWeek) {
        this.calendarId = calendarId;
        this.date = date;
        this.dayOfWeek = dayOfWeek;
    }

    public void setDate(java.sql.Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public int getCalendarId() {
        return calendarId;
    }

    public void setCalendarId(int calendarId) {
        this.calendarId = calendarId;
    }

    public Day getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(Day dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

}
