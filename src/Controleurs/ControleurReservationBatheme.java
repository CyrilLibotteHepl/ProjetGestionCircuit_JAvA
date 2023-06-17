package Controleurs;

import Model.ActivitePackage.ClassBatheme;
import Model.UserPackage.ClassPilote;
import Model.UserPackage.ClassVoiture;
import View.ReservationBatheme;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Properties;

public class ControleurReservationBatheme implements ActionListener {
    private ReservationBatheme batheme;
    private ClassSingleton singleton;
    private LocalDate ladate;
    private int nbtours;
    private String filepathreservation;
    private InterEventBatheme listener;

    public ControleurReservationBatheme(ReservationBatheme reservationBatheme, LocalDate ladatedelareserv)
    {
        batheme = reservationBatheme;
        singleton = ClassSingleton.getInstance();
        filepathreservation = singleton.getFilepathReservation() + "\\ReservationBatheme.txt";

        ladate = ladatedelareserv;
    }
    public void setInterNotificationBatheme(InterEventBatheme interEventBatheme)
    {
        this.listener = interEventBatheme;
    }
    public void EnvoieNotifiaction()
    {
        if(listener != null)
        {
            listener.InterNotificationModificationBatheme();
        }
    }
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == batheme.getButtonReserver())
        {
            boolean creation = true;
            String errormessage = "";
            String nbtoursstr = batheme.getTextFieldNbTour().getText();
            int nbtours = -1;

            String lepilote = (String) batheme.getListPilote().getSelectedValue();
            if(nbtoursstr.length() == 0 || lepilote == null)
            {
                creation = false;
                errormessage = "Données manquantes";
            }
            else {
                nbtours = Integer.parseInt(nbtoursstr);
                if (nbtours <= 0 && nbtours > 15) {
                    creation = false;
                    errormessage = "Il n'est pas possible de faire plus de 15 tours (ou moins de 0)";
                } else {
                    String filepath = singleton.getFilepathConnexionIntervenant() + "\\Pilote.properties";
                    Properties properties = new Properties();

                    try (FileInputStream fileInputStream = new FileInputStream(filepath)) {
                        properties.load(fileInputStream);

                        String nom = "";
                        String prenom = "";
                        String login = "";
                        String password = "";
                        String datestr = "";
                        Date date = null;
                        String licence = "";
                        String categorieprece = "";
                        String telelphone = "";
                        char sex = 'O';
                        String idvehicule = "";
                        ClassVoiture voiture = new ClassVoiture();

                        String identifiant = lepilote.replaceAll("[^\\d].*$", ""); //supression de tout sauf de l'id au début
                        int id = Integer.parseInt(identifiant);

                        for (String key : properties.stringPropertyNames()) {
                            String valeur = properties.getProperty(key);
                            if (key.startsWith("nom" + id)) {
                                nom = valeur;
                            } else if (key.equals("prenom" + id)) {
                                prenom = valeur;
                            } else if (key.equals("identifiant" + id)) {
                                String[] identifiants = valeur.split("\\|\\|");
                                login = identifiants[0];
                                password = identifiants[1];
                            } else if (key.equals("datenaissance" + id)) {
                                datestr = valeur;
                                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                                try {
                                    date = dateFormat.parse(datestr);
                                    //System.out.println(date);
                                } catch (ParseException exc) {
                                    System.out.println("Erreur conversion chaîne en date : " + exc.getMessage());
                                }
                            } else if (key.equals("licence" + id)) {
                                licence = valeur;
                            } else if (key.equals("categorie" + id)) {
                                categorieprece = valeur;
                            } else if (key.equals("telephone" + id)) {
                                telelphone = valeur;
                            } else if (key.equals("sex" + id)) {
                                String sexstr = valeur;
                                sex = sexstr.charAt(0);
                                System.out.println(sex);
                            } else if (key.equals("idvoiture" + id)) {
                                idvehicule = valeur;
                                int idvoit = Integer.parseInt(idvehicule);

                                //recuperation du la voiture
                                if (idvoit == -1) {
                                    //pas de voiture
                                    voiture = new ClassVoiture();
                                } else {
                                    try {
                                        ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(singleton.getFilepathConnexion() + "\\Voiture.bin"));

                                        Object obj = null;
                                        try {
                                            while ((obj = inputStream.readObject()) != null) {
                                                voiture = (ClassVoiture) obj;
                                                if (voiture.getIdentifiant() == idvoit) {
                                                    break;
                                                }
                                            }
                                        } catch (ClassNotFoundException exc) {
                                            exc.printStackTrace();
                                        }
                                        inputStream.close();
                                    } catch (IOException exc) {
                                        exc.printStackTrace();
                                    }
                                }
                            }

                        }
                        ClassPilote pilote = new ClassPilote(id, nom, prenom, login, password, licence, false, date, sex, telelphone, categorieprece, voiture); //récupération du pilote


                        int identifiantPersconnecter = -1;
                        if (singleton.TestClient()) {
                            identifiantPersconnecter = singleton.getClientconnecte().getId();
                        } else if (singleton.TestPilote()) {
                            identifiantPersconnecter = singleton.getPiloteconnecte().getId();
                        }
                        String lheure = (String) batheme.getComboBoxHeure().getSelectedItem();
                        String[] tmps = lheure.split("h");
                        DateTimeFormatter formatter;
                        if(tmps[0].length() > 1)
                        {
                            formatter = DateTimeFormatter.ofPattern("HH'h'mm");
                        }
                        else
                        {
                            formatter = DateTimeFormatter.ofPattern("H'h'mm");
                        }
                        LocalTime heure = LocalTime.parse(lheure, formatter);

                        LocalDate currentdate = LocalDate.now();
                        if (ladate.isEqual(currentdate)) {
                            LocalTime currentTime = LocalTime.now();
                            if (heure.isBefore(currentTime)) {
                                creation = false;
                                errormessage = "Impossible de prendre réservation pour une heure qui est déjà passée";
                            }
                        }
                        if (creation) {
                            //public ClassBatheme(int lidentifiant, String letype, LocalDate ladate, LocalTime lheure, String ladescription, double letarif, int nombredetour, ClassPilote pilote) {
                            String description = "Bahtème du client " + identifiantPersconnecter + " pour " + nbtours + " tours avec le pilote " + pilote.getLogin();
                            ClassBatheme unbatheme = new ClassBatheme(identifiantPersconnecter, "Bathème", ladate, heure, description, nbtours * 15, nbtours, pilote);

                            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filepathreservation, true))) {
                                bufferedWriter.write(unbatheme.getIdentifiant() + ") " + unbatheme.getDate().toString() + " à " + unbatheme.getHeure().toString());
                                bufferedWriter.newLine();

                                bufferedWriter.close();
                                JOptionPane.showMessageDialog(null, "Réservation de bathème effectuée", "Information", JOptionPane.INFORMATION_MESSAGE);
                                EnvoieNotifiaction();
                                batheme.dispose();
                            } catch (FileNotFoundException ex) {
                                throw new RuntimeException(ex);
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                    } catch (FileNotFoundException ex) {
                        throw new RuntimeException(ex);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
            if(!creation)
            {
                JOptionPane.showMessageDialog(null, errormessage, "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
        else if (e.getSource() == batheme.getTextFieldNbTour())
        {
            String nbtourstr = batheme.getTextFieldNbTour().getText();

            if(nbtourstr.length() != 0)
            {
                try
                {

                    nbtours = Integer.parseInt(nbtourstr);
                    if(nbtours > 0 && nbtours <= 15)
                    {
                        nbtourstr = Integer.toString(nbtours*15);
                    }
                    else
                    {
                        nbtourstr = "---";
                    }
                    batheme.getLabelTarif().setText(nbtourstr);
                } catch (NumberFormatException exception) {
                    JOptionPane.showMessageDialog(null, "Veuillez entrez un chiffre valides", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
}
