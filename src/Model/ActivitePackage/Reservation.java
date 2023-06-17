package Model.ActivitePackage;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {
    private LocalDate date;
    private LocalTime heure;
    private int numPlace;

    //private ClassClient cl; //a gérer
    //private ClassActivite ac;

    public Reservation()
    {
        this.date = null;
        this.heure = null;
        this.numPlace = 0;
    }
    public Reservation(LocalDate dateresrv, LocalTime heurersrv, int numdelaplace) {
        this.date = dateresrv;
        this.heure = heurersrv;
        this.numPlace = numdelaplace;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getHeure() {
        return heure;
    }

    public void setHeure(LocalTime heure) {
        this.heure = heure;
    }

    public int getNumPlace() {
        return numPlace;
    }

    public void setNumPlace(int numPlace) {
        this.numPlace = numPlace;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "date=" + date +
                ", heure=" + heure +
                ", numPlace=" + numPlace +
                '}';
    }

    public static void main(String[] args)
    {
        Reservation rsv = new Reservation(LocalDate.of(2023, 8, 15), LocalTime.of(10, 30, 0), 350);
        System.out.println(rsv.toString());
    }
}
