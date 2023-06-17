package Model.ActivitePackage;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
public abstract class ClassActivite implements Type, Serializable {

    protected int identifiant;

    protected String type;

    protected LocalDate date;
    protected LocalTime heure;
    protected String description;
    protected double tarif;


    // Getters and Setters


    public int getIdentifiant() {
        return identifiant;
    }
    public String getType()
    {
        return type;
    }

    public void setType(String letype)
    {
        this.type = type;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getTarif() {
        return tarif;
    }

    public void setTarif(double tarif) {
        this.tarif = tarif;
    }

    public void setIdentifiant(int identifiant) {
        this.identifiant = identifiant;
    }

    public ClassActivite()
    {
        this.identifiant = 0;
        this.type = "";
        this.date = null;
        this.heure = null;
        this.description = "";
        this.tarif = 0;
    }
    public ClassActivite(int lidentifiant,String letype, LocalDate ladate, LocalTime lheure, String ladescription, double letarif)
    {
        this.identifiant = lidentifiant;
        this.type = letype;
        this.date = ladate;
        this.heure = lheure;
        this.description = ladescription;
        this.tarif = letarif;
    }


}
