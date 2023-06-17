package Controleurs;

import Model.ActivitePackage.ClassCourse;
import Model.ActivitePackage.ClassJour;
import Model.ActivitePackage.Reservation;
import Model.UserPackage.ClassClient;
import Model.UserPackage.ClassPilote;
import Model.UserPackage.ClassVoiture;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ClassSingleton {
    private static ClassSingleton instance = null;
    private ClassClient clientconnecte;
    private ClassPilote piloteconnecte;
    private int nbVoiture;
    private int nbCourse;
    private int nbClient;
    private int nbPilote;
    private String filepathConnexion = "C:\\Users\\cycro\\Desktop\\JAVApj\\Connexion\\Voiture";
    private String filepathCourse = "C:\\Users\\cycro\\Desktop\\JAVApj\\Course";
    private String filepathPlanning = "C:\\Users\\cycro\\Desktop\\JAVApj\\Planning";
    private String filepathReservation = "C:\\Users\\cycro\\Desktop\\JAVApj\\Reservation";
    private String filepathConnexionIntervenant = "C:\\Users\\cycro\\Desktop\\JAVApj\\Connexion";
    private String filepathRecord = "C:\\Users\\cycro\\Desktop\\JAVApj\\Course"; //pour le fichier record
    private ArrayList<ClassJour> listJour;
    private ArrayList<ClassVoiture> listVoiture;
    private ArrayList<ClassCourse> listCourse;
    private ArrayList<Reservation> listReservation;

    private ClassSingleton() {
        clientconnecte = new ClassClient();

        nbVoiture = 0;
        listVoiture = new ArrayList<>();
        File filevoiture = new File(filepathConnexion + "\\Voiture.dat");  //récupérer le nb de voiture dans le fichier
        if(filevoiture.exists()) //récupération du nombre de pilote enregistré
        {
            try (ObjectInputStream inputStreamVoiture = new ObjectInputStream(new FileInputStream(filepathConnexion + "\\Voiture.dat")))
            {

                Object obj = null;
                try
                {
                    while ((obj = inputStreamVoiture.readObject()) != null)
                    {
                        nbVoiture++;
                        listVoiture.add((ClassVoiture) obj);
                    }
                }
                catch (ClassNotFoundException e)
                {
                    e.printStackTrace();
                }

            } catch (IOException exc)
            {
                exc.printStackTrace();
            }
        }

        nbCourse = 0;
        listCourse = new ArrayList<>();
        File filecourse = new File(filepathCourse + "\\Course.dat");  //récupérer le nb de voiture dans le fichier

        if(filecourse.exists()) //récupération du nombre de pilote enregistré
        {
            try (ObjectInputStream inputStreamCourse = new ObjectInputStream(new FileInputStream(filepathCourse + "\\Course.dat"))){

                Object obj = null;
                try {
                    while ((obj = inputStreamCourse.readObject()) != null) {
                        nbCourse++;
                        listCourse.add((ClassCourse) obj);
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } catch (IOException exc) {
                exc.printStackTrace();
            }
        }


        listJour = new ArrayList<>();
        File fileplanning = new File(filepathPlanning + "\\Planning.csv");
        if(fileplanning.exists())
        {
            try (BufferedReader reader = new BufferedReader(new FileReader(filepathPlanning + "\\Planning.csv"))) {
                String ligne;
                boolean premiereLigne = true;

                while ((ligne = reader.readLine()) != null)
                {
                    if (premiereLigne) // Ignorer la première ligne de commentaire
                    {
                        premiereLigne = false;
                        continue;
                    }
                    String[] part = ligne.split(",");
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate date = LocalDate.parse(part[0], formatter);
                    boolean batheme = Boolean.parseBoolean(part[1]);
                    boolean trackay = Boolean.parseBoolean(part[2]);
                    boolean course = Boolean.parseBoolean(part[3]);
                    ClassJour classJour = new ClassJour(date, batheme, trackay, course);

                    listJour.add(classJour);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    public static ClassSingleton getInstance() {
        if (instance == null) {
            instance = new ClassSingleton();
        }
        return instance;
    }

    public String getFilepathConnexion() {
        return filepathConnexion;
    }
    public String getFilepathCourse() {
        return filepathCourse;
    }
    public String getFilepathPlanning() {
        return filepathPlanning;
    }
    public String getFilepathReservation() {
        return filepathReservation;
    }
    public int getNbVoiture() {
        return nbVoiture;
    }
    public int getNbCourse(){ return nbCourse; }
    public void setNbVoiture(int nbVoiture) {
        this.nbVoiture = nbVoiture;
    }
    public void setNbCourse(int NbCourse) { this.nbCourse = NbCourse; }
    public ClassClient getClientconnecte()
    {
        return clientconnecte;
    }
    public ClassPilote getPiloteconnecte()
    {
        return piloteconnecte;
    }
    public String getFilepathConnexionIntervenant() {
        return filepathConnexionIntervenant;
    }
    public String getFilepathRecord() {
        return filepathRecord;
    }

    public int getNbClient() {
        return nbClient;
    }
    public int getNbPilote() {
        return nbPilote;
    }

    public void setNbClient(int nbClient) {
        this.nbClient = nbClient;
    }
    public void setNbPilote(int nbPilote) {
        this.nbPilote = nbPilote;
    }

    public boolean getIfClientPiloteCo()
    {
        if(!clientconnecte.getLogin().equals("")) //client connecté
        {
            return true;
        }
        else //pilote connecté
        {
            return false;
        }
    }
    public ArrayList<ClassJour> getListJour()
    {
        return listJour;
    }
    public ArrayList<ClassVoiture> getListVoiture() {
        return listVoiture;
    }
    public ArrayList<ClassCourse> getListCourse() {
        return listCourse;
    }
    public ArrayList<Reservation> getListReservation(){
        return listReservation;
    }
    public void Deconnexion()
    {
        clientconnecte = new ClassClient();
        piloteconnecte = new ClassPilote();
    }
    public boolean TestClient()
    {
        if( clientconnecte != null && !clientconnecte.getLogin().equals("")) //client connecté
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    public boolean TestPilote()
    {
        if(piloteconnecte != null && !piloteconnecte.getLogin().equals("")) //pilote connecté
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    public void ConnexionClient(ClassClient leclient)
    {
        clientconnecte = leclient;
        piloteconnecte = new ClassPilote();
    }
    public void ConnexionPilote(ClassPilote lepilote)
    {
        clientconnecte = new ClassClient();
        piloteconnecte = lepilote;
    }
    public boolean testAdmin()
    {
        if (clientconnecte != null && !clientconnecte.getLogin().equals("")) //client connecté
        {
            if(clientconnecte.getadmin())
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        else if (piloteconnecte != null && !piloteconnecte.getLogin().equals("")) //pilote connecté
        {
            if(piloteconnecte.getadmin())
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            return false;
        }
    }
    public void setAdmin()
    {
        if( clientconnecte != null && !clientconnecte.getLogin().equals("")) //client connecté
        {
            clientconnecte.setAdmin(true);
        }
        else if (piloteconnecte != null && !piloteconnecte.getLogin().equals("")) //pilote connecté
        {
            piloteconnecte.setAdmin(true);
        }
    }
    public String getMDP()
    {
        if(clientconnecte != null && !clientconnecte.getLogin().equals("")) //client connecté
        {
            return clientconnecte.getMotDePasse();
        }
        else if (piloteconnecte != null && !piloteconnecte.getLogin().equals(""))//pilote connecté
        {
            return piloteconnecte.getMotDePasse();
        }
        else
        {
            return clientconnecte.getMotDePasse();
        }
    }
    public String getLOG()
    {
        if(clientconnecte != null && !clientconnecte.getLogin().equals("")) //client connecté
        {
            return clientconnecte.getLogin();
        }
        else if(piloteconnecte != null && !piloteconnecte.getLogin().equals(""))//pilote connecté
        {
            return piloteconnecte.getLogin();
        }
        else //ici ça signifie que personne n'est connecté
        {
            return clientconnecte.getLogin(); //du coup on retourne le client qui est par défaut, ça équivaut à retourné ""
        }
    }
    public void setLOG(String log)
    {
        if(clientconnecte != null && !clientconnecte.getLogin().equals("")) //client connecté
        {
            clientconnecte.setLogin(log);
        }
        else if (piloteconnecte != null && !piloteconnecte.getLogin().equals(""))//pilote connecté
        {
            piloteconnecte.setLogin(log);
        }
    }

    public String getNOM()
    {
        if(clientconnecte != null && !clientconnecte.getLogin().equals("")) //client connecté
        {
            return clientconnecte.getNompersonne();
        }
        else if(piloteconnecte != null && !piloteconnecte.getLogin().equals(""))//pilote connecté
        {
            return piloteconnecte.getNompersonne();
        }
        else
        {
            return clientconnecte.getNompersonne();
        }
    }

    public String getPRENOM()
    {
        if(clientconnecte != null && !clientconnecte.getLogin().equals("")) //client connecté
        {
            return clientconnecte.getPrenomPersonne();
        }
        else if (piloteconnecte != null && !piloteconnecte.getLogin().equals(""))//pilote connecté
        {
            return piloteconnecte.getPrenomPersonne();
        }
        else
        {
            return clientconnecte.getPrenomPersonne();
        }
    }

}
