package pl.edu.wszib.edoctor.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "tday")
public class Day {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int dayOfWeek;
    private String nameOfWeek;

    public Day() {
    }

    public Day(int dayOfWeek, String nameOfWeek) {
        this.dayOfWeek = dayOfWeek;
        this.nameOfWeek = nameOfWeek;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getNameOfWeek() {
        return nameOfWeek;
    }

    public void setNameOfWeek(String nameOfWeek) {
        this.nameOfWeek = nameOfWeek;
    }

    @Override
    public String toString() {
        return nameOfWeek;
    }
}
