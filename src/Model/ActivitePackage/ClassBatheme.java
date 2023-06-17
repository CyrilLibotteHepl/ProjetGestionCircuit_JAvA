package Model.ActivitePackage;

import Model.UserPackage.ClassPilote;

import java.time.LocalDate;
import java.time.LocalTime;

public class ClassBatheme extends ClassActivite {
    private int nombredetour;
    private ClassPilote Pilote;

    public ClassBatheme()
    {
        super();
        this.nombredetour = 0;
        this.Pilote = null;
    }
    public ClassBatheme(int lidentifiant, String letype, LocalDate ladate, LocalTime lheure, String ladescription, double letarif, int nombredetour, ClassPilote pilote) {
        super(lidentifiant, letype, ladate, lheure, ladescription, letarif);
        this.nombredetour = nombredetour;
        this.Pilote = pilote;
    }

    public int getNombredetour() {
        return nombredetour;
    }

    public void setNombredetour(int nombredetour) {
        this.nombredetour = nombredetour;
    }

    public ClassPilote getPilote() {
        return Pilote;
    }

    public void setPilote(ClassPilote pilote) {
        this.Pilote = pilote;
    }

    @Override
    public String toString() {
        return "ClassBatheme{" +
                "nombredetour=" + nombredetour +
                ", Pilote=" + Pilote +
                ", identifiant=" + identifiant +
                ", type='" + type + '\'' +
                ", date=" + date +
                ", heure=" + heure +
                ", description='" + description + '\'' +
                ", tarif=" + tarif +
                '}';
    }

    public static void main(String[] args)
    {
//        ClassPilote plt = new ClassPilote();
//        ClassBatheme cbtm = new ClassBatheme(0,"Batheme", LocalDate.of(2023, 6, 20), LocalTime.of(11, 45, 0), "Batheme de feu sur circuit avec un pilote proffessionnel", 10, 30, 10, plt);
//        System.out.println(cbtm.toString());
    }
}

