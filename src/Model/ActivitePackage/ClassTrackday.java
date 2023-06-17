package Model.ActivitePackage;

import Model.UserPackage.ClassVoiture;

import java.time.LocalDate;
import java.time.LocalTime;

public class ClassTrackday extends ClassActivite {
    private boolean vehiculeLoue;
    private ClassVoiture voiture;
    public ClassTrackday()
    {
        super();
        this.vehiculeLoue = false;
        this.voiture = null;
    }
    public ClassTrackday(int lidentifiant, String letype, LocalDate ladate, LocalTime lheure, String ladescription, double letarif, boolean vehiculeLoue, ClassVoiture voit)
    {
        super(lidentifiant, letype, ladate, lheure, ladescription, letarif);
        this.vehiculeLoue = vehiculeLoue;
        this.voiture = voit;
    }

    public boolean getVehiculeLoue() {
        return vehiculeLoue;
    }
    public ClassVoiture getVoiture()
    {
        return voiture;
    }

    public void setVehiculeLoue(boolean vehiculeLoue) {
        this.vehiculeLoue = vehiculeLoue;
    }
    public void setVoiture(ClassVoiture nouvellevoiture)
    {
        voiture = nouvellevoiture;
    }

    public String getNomVehicule()
    {
        // concaténation
        return null;
    }

    @Override
    public String toString() {
        return "ClassTrackday{" +
                "vehiculeLoue=" + vehiculeLoue +
                ", voiture=" + voiture +
                ", type='" + type + '\'' +
                ", date=" + date +
                ", heure=" + heure +
                ", description='" + description + '\'' +
                ", tarif=" + tarif +
                '}';
    }

    public static void main(String[] args)
    {
//        ClassVoiture clv = new ClassVoiture();
//        ClassTrackday cltr = new ClassTrackday(0, "Trackday", LocalDate.of(2023, 10, 19), LocalTime.of(15, 30, 0), "Un jour complet ou le circuit est ouvert à tous", 100, 0, false, clv);
//        System.out.println(cltr.toString());
    }
}
