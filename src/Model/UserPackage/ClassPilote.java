package Model.UserPackage;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class ClassPilote extends ClassPersonne {
    private Date naissance;
    private char sex;
    private String numtel;
    private String catergorieprece;
    private ClassVoiture voiture;

    // Constructeur
    public ClassPilote()
    {
        super();
        this.naissance = Calendar.getInstance().getTime();
        this.sex = 'N';
        this.numtel = "";
        this.catergorieprece = "";
        this.voiture = null;
    }
    public ClassPilote(int Id, String Nom, String Prenom, String Login, String Mdp, String Licence,Boolean Admin, Date Naissance, char Sex, String Numtel, String Catergorieprece,ClassVoiture Voiture) {
        super(Id, Nom, Prenom, Login, Mdp, Licence, Admin);
        this.naissance = Naissance;
        this.sex = Sex;
        this.numtel = Numtel;
        this.catergorieprece = Catergorieprece;
        this.voiture = Voiture;
    }

    // Getter et setter pour chaque attribut


    public Date getNaissance() {
        return naissance;
    }
    public void setNaissance(Date naissance) {
        this.naissance = naissance;
    }
    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public String getNumtel() {
        return numtel;
    }

    public void setNumtel(String numtel) {
        this.numtel = numtel;
    }

    public ClassVoiture getVoiture() {
        return voiture;
    }

    public void setVoiture(ClassVoiture voiture) {
        this.voiture = voiture;
    }

    public String getCatergorieprece() {
        return catergorieprece;
    }

    public void setCatergorieprece(String catergorieprece) {
        this.catergorieprece = catergorieprece;
    }
    // Méthode pour calculer l'âge du pilote
//    public int Age()
//    {
//        LocalDate currentDate = LocalDate.now();
//        int currentYear = currentDate.getYear();
//        int birthYear = Integer.parseInt(Licence.substring(0, 4));
//        return currentYear - birthYear;
//    }

    // Méthode pour tester si le pilote est conforme aux normes
    public boolean TestNorme()
    {
        return false;
    }

    @Override
    public String toString() {
        return "ClassPilote{" +
                "naissance=" + naissance +
                ", sex=" + sex +
                ", numtel='" + numtel + '\'' +
                ", catergorieprece='" + catergorieprece + '\'' +
                ", voiture=" + voiture +
                ", identifiantP=" + identifiant +
                ", Nom='" + Nom + '\'' +
                ", Prenom='" + Prenom + '\'' +
                ", Login='" + Login + '\'' +
                ", MotDePasse='" + MotDePasse + '\'' +
                ", Licence='" + Licence + '\'' +
                ", Admin=" + Admin +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClassPilote that)) return false;
        return sex == that.sex && Objects.equals(naissance, that.naissance) && Objects.equals(numtel, that.numtel) && Objects.equals(catergorieprece, that.catergorieprece) && Objects.equals(voiture, that.voiture);
    }

    @Override
    public int hashCode() {
        return Objects.hash(naissance, sex, numtel, catergorieprece, voiture);
    }

    public static void main(String[] args)
    {
        //ClassVoiture cvt = new ClassVoiture();
        //ClassPilote cplt = new ClassPilote(1, "Potty", "Antoine", "Pota", "123", false, "RWD Course GT4", 12.25, 'M', "0456 23 55 78, ", cvt);
        //System.out.println(cplt.toString());
    }
}
