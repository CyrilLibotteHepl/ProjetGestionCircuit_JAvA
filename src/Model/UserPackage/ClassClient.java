package Model.UserPackage;


import Model.ActivitePackage.Reservation;

import java.util.Objects;

public class ClassClient extends ClassPersonne{

    private Reservation reservation;
    private String mail;

    public ClassClient()
    {
        super();
        this.reservation = null;
        this.mail = "";
    }
    public ClassClient(int id, String nom, String prenom, String login, String mdp, String licence,Boolean admin,Reservation reservation, String mail) {
        super(id, nom, prenom, login, mdp, licence,admin);
        this.reservation = reservation;
        this.mail = mail;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }


    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }


    @Override
    public String toString() {
        return "ClassClient{" +
                "reservation=" + reservation +
                ", licence='" + Licence + '\'' +
                ", mail='" + mail + '\'' +
                ", identifiant=" + identifiant +
                ", Nom='" + Nom + '\'' +
                ", Prenom='" + Prenom + '\'' +
                ", Login='" + Login + '\'' +
                ", MotDePasse='" + MotDePasse + '\'' +
                ", Admin=" + Admin +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClassClient that)) return false;
        return Objects.equals(reservation, that.reservation) && Objects.equals(mail, that.mail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reservation, mail);
    }

    public static void main(String[] args)
    {
//        Reservation rsv = new Reservation();
//        ClassClient cl = new ClassClient(1, "Castellani", "Josue", "CasJo", "321", false, rsv, "Non", "Lecastel@gmail.com");
//        System.out.println(cl.toString());
    }
}
