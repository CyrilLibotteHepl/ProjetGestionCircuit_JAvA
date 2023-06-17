package Model.UserPackage;

import java.io.Serializable;
import java.util.Objects;

public class ClassVoiture implements Serializable {
    private int identifiant;
    private String marque;
    private String modele;
    private String categorie;
    private int puissance;
    private int cylindree;
    private String carburant;
    private int poids;
    private int etat;
    private boolean appartientaucircuit;
    private boolean estutilise;

    public int getIdentifiant() {
        return identifiant;
    }

    public String getMarque() {
        return marque;
    }

    public String getModele() {
        return modele;
    }

    public String getCategorie() {
        return categorie;
    }

    public int getPuissance() {
        return puissance;
    }

    public int getCylindree() {
        return cylindree;
    }

    public String getCarburant() {
        return carburant;
    }

    public int getPoids() {
        return poids;
    }

    public int getEtat() {
        return etat;
    }

    public boolean isAppartientaucircuit() {
        return appartientaucircuit;
    }

    public void setIdentifiant(int identifiant) {
        this.identifiant = identifiant;
    }
    public boolean isEstutilise() {
        return estutilise;
    }
    public void setEstutilise(boolean estutilise) {
        this.estutilise = estutilise;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public void setPuissance(int puissance) {
        this.puissance = puissance;
    }

    public void setCylindree(int cylindree) {
        this.cylindree = cylindree;
    }

    public void setCarburant(String carburant) {
        this.carburant = carburant;
    }

    public void setPoids(int poids) {
        this.poids = poids;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

    public ClassVoiture()
    {
        this.identifiant = -1;
        this.marque = "";
        this.modele = "";
        this.categorie = "";
        this.puissance = 0;
        this.cylindree = 0;
        this.carburant = "";
        this.poids = 0;
        this.etat = 4;
        this.appartientaucircuit = false;
        this.estutilise = false;
    }
    public ClassVoiture(int id, String Marque, String Modele, String Categorie, int P, int Cylindree, String Carburant, int Poids, int Etat, boolean AppartientAuCircuit, boolean EstUtilise)
    {
        this.identifiant = id;
        this.marque = Marque;
        this.modele = Modele;
        this.categorie = Categorie;
        this.puissance = P;
        this.cylindree = Cylindree;
        this.carburant = Carburant;
        this.poids = Poids;
        this.etat = Etat;
        this.appartientaucircuit = AppartientAuCircuit;
        this.estutilise = EstUtilise;
    }

    @Override
    public String toString() {
        return "        id : " + identifiant +
                "       marque : " + marque +
                "       modele : " + modele ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClassVoiture that)) return false;
        return identifiant == that.identifiant && puissance == that.puissance && cylindree == that.cylindree && poids == that.poids && etat == that.etat && Objects.equals(marque, that.marque) && Objects.equals(modele, that.modele) && Objects.equals(categorie, that.categorie) && Objects.equals(carburant, that.carburant);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifiant, marque, modele, categorie, puissance, cylindree, carburant, poids, etat);
    }

    public static void main(String[] args)
    {
//        ClassVoiture clv = new ClassVoiture("Toyota", 1, "SupraMK4", 6000, 50, "Slick", 4, 1800, 4);
//        System.out.println(clv.toString());
    }
}
