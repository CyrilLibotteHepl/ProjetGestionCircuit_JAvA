package Model.ActivitePackage;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class ClassCourse extends ClassActivite implements Serializable {
    private int itineraire;
    private String nom;
    private double record;
    private String categorie;
    private String discipline;
    private boolean courseterminer;

    // Constructeur de la classe
    public ClassCourse()
    {
        super();
        this.itineraire = 0;
        this.record = 0;
        this.categorie = "";
        this.nom = "";
        this.discipline = "";
        courseterminer = false;
    }
    public ClassCourse(int lidentifiant,String letype, LocalDate ladate, LocalTime lheure, String ladescription, double letarif, int itineraire, String Nom,double record, String categorie, String discipline, boolean CourseTerminer)
    {
        super(lidentifiant, letype, ladate, lheure, ladescription, letarif);
        this.itineraire = itineraire;
        this.nom = Nom;
        this.record = record;
        this.categorie = categorie;
        this.discipline = discipline;
        this.courseterminer = CourseTerminer;
    }

    public int getItineraire() {
        return itineraire;
    }
    public double getRecord() {
        return record;
    }
    public String getCategorie() {
        return categorie;
    }
    public String getDiscipline() {
        return discipline;
    }
    public void setItineraire(int itineraire)
    {
        this.itineraire = itineraire;
    }
    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }
    public void setDiscipline(String discipline) {
        this.discipline = discipline;
    }
    public void setRecord(double record) {
        this.record = record;
    }
    public boolean isCourseterminer() {
        return courseterminer;
    }
    public void setCourseterminer(boolean courseterminer) {
        this.courseterminer = courseterminer;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public String toString() {
        return "ClassCourse{" +
                "itineraire=" + itineraire +
                ", record=" + record +
                ", categorie='" + categorie + '\'' +
                ", discipline='" + discipline + '\'' +
                ", type='" + type + '\'' +
                ", date=" + date +
                ", heure=" + heure +
                ", description='" + description + '\'' +
                ", tarif=" + tarif +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClassCourse that)) return false;
        return itineraire == that.itineraire && Double.compare(that.record, record) == 0 && Objects.equals(categorie, that.categorie) && Objects.equals(discipline, that.discipline);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itineraire, record, categorie, discipline);
    }

    public static void main(String[] args)
    {
        //ClassCourse ccrs = new ClassCourse("Course", LocalDate.of(2023, 5, 6), LocalTime.of(9, 30, 0), "Course de championa Gt4", 200000, 200, 1, 0, 9.36, "Joujoux de circuit", "Course Circuit");
        //System.out.println(ccrs.toString());
    }
}
