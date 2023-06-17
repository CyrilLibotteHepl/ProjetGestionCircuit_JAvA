package Controleurs;

import Model.ActivitePackage.ClassCourse;
import Model.ActivitePackage.ClassJour;
import Model.ActivitePackage.Reservation;
import View.DeterminerReservation;
import View.ReservationBatheme;
import View.ReservationTrackday;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class ControleurDeterminerReservation implements ActionListener, InterEventBatheme, InterEventTrackDay {

    private DeterminerReservation reservation;
    private String filepathplanning;
    private String filepathreservationcourse;
    private String filepathreservationbatheme;
    private String filepathreservationtrackday;
    private ClassSingleton singleton;
    private LocalDate DateCourante;
    private NouvelleDonneeRCourse listener;
    private NouvelleDonneRBatheme listener1;
    private NouvelleDonneRTrackDay listener2;
    private int laPlace;
    private LocalDate date;
    private LocalTime heure;

    public ControleurDeterminerReservation(DeterminerReservation determinerReservation)
    {
        reservation = determinerReservation;
        singleton = ClassSingleton.getInstance();
        filepathplanning = singleton.getFilepathPlanning() + "\\Planning.csv"; //"C:\\Users\\cycro\\Desktop\\JAVApj\\Planning\\Planning.csv"
        filepathreservationcourse = singleton.getFilepathReservation() + "\\Reservation.txt"; //"C:\\Users\\cycro\\Desktop\\JAVApj\\Reservation\\Reservation.txt"
        filepathreservationbatheme = singleton.getFilepathReservation() + "\\ReservationBatheme.txt"; //"C:\\Users\\cycro\\Desktop\\JAVApj\\Reservation\\ReservationBatheme.txt"
        filepathreservationtrackday = singleton.getFilepathReservation() + "\\ReservationTrackDay.txt"; ////"C:\\Users\\cycro\\Desktop\\JAVApj\\Reservation\\ReservationBatheme.txt"
        DateCourante = LocalDate.now(); // Obtention de la date courante
    }

    public void setEventMajReservCourse(NouvelleDonneeRCourse nouvelleDonneeRCourse)
    {
        this.listener = nouvelleDonneeRCourse;
    }
    public void setEventMagReservBatheme(NouvelleDonneRBatheme nouvelleDonneRBatheme)
    {
        this.listener1 = nouvelleDonneRBatheme;
    }
    public void setEventMagReservTrackDay(NouvelleDonneRTrackDay nouvelleDonneRTrackDay)
    {
        this.listener2 = nouvelleDonneRTrackDay;
    }
    @Override
    public void InterNotificationModificationBatheme() {
        //envoié nbtours a classmain
        if(listener1 != null)
        {
            this.listener1.ModificationDonneeReservationBatheme();
        }
    }

    @Override
    public void InterNotificationModificationTrackDay() {
        if(listener2 != null)
        {
            this.listener2.ModificationDonneeReservationTrackDay();
        }
    }

    public void ModifierVarialbleReservCourse()
    {
        if(listener != null)
        {
            this.listener.ModificationDonneeReservationCourse(laPlace, date, heure);
        }
    }

    public void EcritureDesJours(int jourdumoi, int nbjour, int moi, int annee)
    {
        LocalDate date;
        ArrayList<String> listeJour = new ArrayList<>();
        DefaultListModel<String> list = new DefaultListModel<>();

        for(int i = jourdumoi; i<=nbjour; i++)
        {
            date = LocalDate.of(annee, moi, i);
            ClassJour jour = new ClassJour(date, false, false, false); //écriture par défault

            for(ClassJour jourbis : singleton.getListJour()) //test de si quelque chose est prévu
            {
                if(jourbis.getJour().equals(jour.getJour()))
                {
                    jour = jourbis;
                }
            }

            listeJour.add(jour.toString());
        }
        for (String unjour : listeJour)
        {
            list.addElement(unjour);
        }
        reservation.getListJour().setModel(list);
    }

    public int ConvertionMoiEnInt(String moistr)
    {
        int moi = 0;

        switch (moistr)
        {
            case "Janvier": moi = 1;
                break;
            case "Février": moi = 2;
                break;
            case "Mars": moi = 3;
                break;
            case "Avril": moi = 4;
                break;
            case "Mai": moi = 5;
                break;
            case "Juin": moi = 6;
                break;
            case "Juillet": moi = 7;
                break;
            case "Août": moi = 8;
                break;
            case "Septembre": moi = 9;
                break;
            case "Octobre": moi = 10;
                break;
            case "Novembre": moi = 11;
                break;
            case "Décembre": moi = 12;
        }

        return moi;
    }
    public String ConvertionMoiEnFR (int nummoi)
    {
        String moi = "";
        switch(nummoi)
        {
            case 1 : moi = "Janvier";
                break;
            case 2 : moi = "Février";
                break;
            case 3 : moi = "Mars";
                break;
            case 4 : moi = "Avril";
                break;
            case 5 : moi = "Mai";
                break;
            case 6 : moi = "Juin";
                break;
            case 7 : moi = "Juillet";
                break;
            case 8 : moi = "Août";
                break;
            case 9 : moi = "Septembre";
                break;
            case 10 : moi = "Octobre";
                break;
            case 11 : moi = "Novembre";
                break;
            case 12 : moi = "Décembre";
                break;
        }
        return moi;
    }
    public int CalculeNbJour(String lemoi)
    {
        int nbJour = 0;
        switch (lemoi)
        {
            case "Janvier":
            case "Mars":
            case "Mai":
            case "Juillet":
            case "Août":
            case "Octobre":
            case "Décembre":
                nbJour = 31;
                break;
            case "Avril":
            case "Juin":
            case "Septembre":
            case "Novembre":
                nbJour = 30;
                break;
            case "Février":
                String strinanntmp = reservation.getLabelAnnee().getText();
                int annee = Integer.parseInt(strinanntmp);

                if((annee % 4 == 0 && annee % 100 != 0) || (annee % 400 == 0))
                {
                    //année bissextil
                    nbJour = 29;
                }
                else
                {
                    nbJour = 28;
                }
                break;
        }
        return nbJour;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == reservation.getButtonPrevius())
        {
            int moi = DateCourante.getMonthValue();
            String moistr = reservation.getLabelMoi().getText();
            int moiaffiche = ConvertionMoiEnInt(moistr);
            int anneeaffiche = Integer.parseInt(reservation.getLabelAnnee().getText());

            JOptionPane jop = new JOptionPane();
            if(moi == moiaffiche && anneeaffiche == DateCourante.getYear()) //on ne peu pas faire de moi précédant (impossible car on ne peu réservé quelque chose entérieur a maintentant)
            {
                jop.showMessageDialog(null, "Impossible de prévoir quelque chose pour une date antérieur", "Erreur", jop.ERROR_MESSAGE);
                jop.setVisible(true);
            }
            else //ok
            {
                if(ConvertionMoiEnInt(reservation.getLabelMoi().getText()) == 1) //retour a l'année précédante
                {
                    String tmp = reservation.getLabelAnnee().getText();
                    int annee = Integer.parseInt(tmp);
                    annee--;

                    reservation.getLabelAnnee().setText("" + annee);
                    reservation.getLabelMoi().setText("Décembre");

                    int jourdumoi = 1;
                    if(DateCourante.getMonthValue() == 12 && annee == DateCourante.getYear()) // on est en décembre, donc on n'affiche pas tout les jour (seulement a partir d'aujourd'hui)
                    {
                        jourdumoi = DateCourante.getDayOfMonth();
                    }

                    EcritureDesJours(jourdumoi, 31, 12, annee);
                }
                else //normal
                {
                    //le moi est trouvé
                    int nummoi = ConvertionMoiEnInt(reservation.getLabelMoi().getText());
                    nummoi--;
                    reservation.getLabelMoi().setText(ConvertionMoiEnFR(nummoi));

                    int jourdumoi = 1;
                    if(nummoi == DateCourante.getMonthValue() && Integer.parseInt(reservation.getLabelAnnee().getText()) == DateCourante.getYear())
                    {
                        jourdumoi = DateCourante.getDayOfMonth();
                    }

                    int nbmaxjour = CalculeNbJour(reservation.getLabelMoi().getText());

                    EcritureDesJours(jourdumoi, nbmaxjour, ConvertionMoiEnInt(reservation.getLabelMoi().getText()), Integer.parseInt(reservation.getLabelAnnee().getText()));
                }
            }
        }
        else if(e.getSource() == reservation.getButtonNext())
        {

            if(ConvertionMoiEnInt(reservation.getLabelMoi().getText()) == 12) //année suivante
            {
                String tmp = reservation.getLabelAnnee().getText();
                int annee = Integer.parseInt(tmp);
                annee++;

                reservation.getLabelAnnee().setText("" + annee);
                reservation.getLabelMoi().setText("Janvier");

                int jourdumoi = 1;

                EcritureDesJours(jourdumoi, 31, 1, annee);
            }
            else //normal
            {
                //le moi est trouvé
                int nummoi = ConvertionMoiEnInt(reservation.getLabelMoi().getText());
                nummoi++;
                reservation.getLabelMoi().setText(ConvertionMoiEnFR(nummoi));

                int jourdumoi = 1;

                int nbmaxjour = CalculeNbJour(reservation.getLabelMoi().getText());

                EcritureDesJours(jourdumoi, nbmaxjour, ConvertionMoiEnInt(reservation.getLabelMoi().getText()), Integer.parseInt(reservation.getLabelAnnee().getText()));
            }
        }
        else if(e.getSource() == reservation.getButtonReservation())
        {
            String valeurselectionner = (String) reservation.getListJour().getSelectedValue();
            boolean pourbatheme = false;
            boolean pourcourse = false;
            boolean pourtrackday = false;

            String[] options1 = {"Batheme", "Course"};
            String[] options2 = {"Batheme", "Trackday"};
            String[] options3 = {"Trackday", "Course"};
            String[] options4 = {"Batheme", "Trackday", "Course"};
            int result = -1;
            JOptionPane jop = new JOptionPane();

            if(valeurselectionner.length() != 0)
            {
                if(valeurselectionner.contains("un bathème est prévu"))
                {
                    pourbatheme = true;
                }
                else if (valeurselectionner.contains("un trackday est prévu"))
                {
                    pourtrackday = true;
                }
                else if (valeurselectionner.contains("une course est prévue"))
                {
                    pourcourse = true;
                }
                else if (valeurselectionner.contains("une course et un trackday sont prévu"))
                {
                    // Affichage de la boîte de dialogue avec les boutons radio
                    result = JOptionPane.showOptionDialog(null, "Sélectionnez une option : ", "Options", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,options3, options3[0]);
                    jop.setVisible(true);

                    switch (result)
                    {
                        case 0: pourtrackday = true;
                            break;
                        case 1: pourcourse = true;
                            break;
                    }
                }
                else if (valeurselectionner.contains("un bathème et un trackday sont prévu"))
                {
                    result = JOptionPane.showOptionDialog(null, "Sélectionnez une option : ", "Options", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,options2, options2[0]);
                    jop.setVisible(true);

                    switch (result)
                    {
                        case 0: pourbatheme = true;
                            break;
                        case 1: pourtrackday = true;
                            break;
                    }
                }
                else if(valeurselectionner.contains("un bathème et une course sont prévu"))
                {
                    result = JOptionPane.showOptionDialog(null, "Sélectionnez une option : ", "Options", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,options1, options1[0]);
                    jop.setVisible(true);

                    switch (result)
                    {
                        case 0: pourbatheme = true;
                            break;
                        case 1: pourcourse = true;
                            break;
                    }
                }
                else if (valeurselectionner.contains("un bathème, un trackday et une course sont prévu"))
                {
                    result = JOptionPane.showOptionDialog(null, "Sélectionnez une option : ", "Options"
                            ,JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,options4, options4[0]);
                    jop.setVisible(true);

                    switch (result)
                    {
                        case 0: pourbatheme = true;
                            break;
                        case 1: pourtrackday = true;
                            break;
                        case 2: pourcourse = true;
                            break;
                    }
                }
                else if(valeurselectionner.contains("rien n'est prévu"))
                {
                    jop.showMessageDialog(null, "Rien n'est prévu à cette date", "Information", jop.INFORMATION_MESSAGE);
                    jop.setVisible(true);
                }

                //récupération de la date, pouvoir ensuit allé recherché la bonne activité dans les arraylist
                String uniquementleschiffre = valeurselectionner.replaceAll("\\D", "");
                int jour;
                if(uniquementleschiffre.length() < 6)
                    jour = Integer.parseInt(uniquementleschiffre.substring(0, 1));
                else
                    jour = Integer.parseInt(uniquementleschiffre.substring(0, 2));

                date = LocalDate.of(Integer.parseInt(reservation.getLabelAnnee().getText()), ConvertionMoiEnInt(reservation.getLabelMoi().getText()), jour);

                if(pourbatheme)
                {
                    boolean creation = true;
                    File filereservationbatheme = new File(filepathreservationbatheme); //cration du fichier si nécessaire
                    if(!filereservationbatheme.exists())
                    {
                        try
                        {
                            filereservationbatheme.createNewFile(); // creation du fichier
                            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filepathreservationbatheme, true))) {
                                writer.write("Les bathemes :");
                                writer.newLine();
                                writer.close();

                                System.out.println("Ligne écrite avec succès dans le fichier.");
                            } catch (IOException exc) {
                                exc.printStackTrace();
                            }
                        }
                        catch (IOException exception)
                        {
                            creation = false;
                            JOptionPane.showMessageDialog(null, "Problème de créaiton du ficier...", "Erreur", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    if(creation)
                    {
                        ReservationBatheme reservationBatheme = new ReservationBatheme();

                        ControleurReservationBatheme controleurReservationBatheme = new ControleurReservationBatheme(reservationBatheme, date);
                        controleurReservationBatheme.setInterNotificationBatheme(this);
                        reservationBatheme.setControleurReservationBatheme(controleurReservationBatheme);
                        reservationBatheme.setResizable(true);
                        reservationBatheme.setVisible(true);
                    }
                }
                else if (pourtrackday)
                {
                    boolean creation = true;
                    File filereservationtrackday = new File(filepathreservationtrackday); //cration du fichier si nécessaire
                    if(!filereservationtrackday.exists())
                    {
                        try
                        {
                            filereservationtrackday.createNewFile(); // creation du fichier
                            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filepathreservationtrackday, true))) {
                                writer.write("Les trackdays reserv :");
                                writer.newLine();
                                writer.close();

                                System.out.println("Ligne écrite avec succès dans le fichier.");
                            } catch (IOException exc) {
                                exc.printStackTrace();
                            }
                        }
                        catch (IOException exception)
                        {
                            creation = false;
                            JOptionPane.showMessageDialog(null, "Problème de créaiton du ficier...", "Erreur", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    if(creation)
                    {
                        ReservationTrackday reservationTrackday = new ReservationTrackday();

                        ControleurReservationTrackday controleurReservationTrackday = new ControleurReservationTrackday(reservationTrackday, date);
                        controleurReservationTrackday.setInterNotificationTrackDay(this);
                        reservationTrackday.setControleurReservationTrackday(controleurReservationTrackday);
                        reservationTrackday.setResizable(true);
                        reservationTrackday.setVisible(true);
                    }
                }
                else if(pourcourse)
                {
                    for (ClassCourse unecourse : singleton.getListCourse())
                    {
                        if(unecourse.getDate().isEqual(date))
                        {
                            //on a trouvé la course ou il/elle veux prendre une place
                            ClassCourse lacourse = unecourse;

                            String input = JOptionPane.showInputDialog(null, "Entrez un nombre :", "Entrée de nombre", JOptionPane.QUESTION_MESSAGE);

                            boolean creation = true;
                            if (input.length() != 0)
                            {
                                File filereservationcourse = new File(filepathreservationcourse); //cration du fichier si nécessaire
                                if(!filereservationcourse.exists())
                                {
                                    try
                                    {
                                        filereservationcourse.createNewFile(); // creation du fichier
                                    }
                                    catch (IOException exception)
                                    {
                                        creation = false;
                                        JOptionPane.showMessageDialog(null, "Problème de créaiton du ficier...", "Erreur", JOptionPane.ERROR_MESSAGE);
                                    }
                                }
                                if(creation)
                                {
                                    try {
                                        laPlace = Integer.parseInt(input);

                                        if (laPlace >= 0 && laPlace <= 500)
                                        {
                                            heure = lacourse.getHeure();
                                            Reservation lareservation = new Reservation(date, heure, laPlace);

                                            ////////////sauvegarde de la réservation

                                            //test de si la place est déjà prise ou nn
                                            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filepathreservationcourse)))
                                            {
                                                String unreservation;
                                                boolean PlaceDejaOccupe = false;

                                                // Parcourir les lignes du fichier
                                                while ((unreservation = bufferedReader.readLine()) != null)
                                                {
                                                    if (unreservation.contains("Place : " + laPlace + " "))
                                                    {
                                                        PlaceDejaOccupe = true;
                                                        break;
                                                    }
                                                }
                                                if(PlaceDejaOccupe)
                                                {
                                                    JOptionPane.showMessageDialog(null, "Place déjà réservée", "Erreur", JOptionPane.ERROR_MESSAGE);
                                                }
                                                else
                                                {
                                                    int identifiantdelapersonnequireserve = -1;
                                                    if(singleton.TestPilote())
                                                    {
                                                        identifiantdelapersonnequireserve = singleton.getClientconnecte().getId();
                                                    }
                                                    else if (singleton.TestClient())
                                                    {
                                                        identifiantdelapersonnequireserve = singleton.getPiloteconnecte().getId();
                                                    }
                                                    //écriture a la fin du fichier
                                                    try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filepathreservationcourse, true)))
                                                    {
                                                        bufferedWriter.newLine();
                                                        bufferedWriter.write("La date : " + lareservation.getDate().toString() + "," + " L'heure : " + lareservation.getHeure().toString() + "," + " Place : " + laPlace + " Id : " + identifiantdelapersonnequireserve);

                                                        ModifierVarialbleReservCourse();

                                                        JOptionPane.showMessageDialog(null, "Reservation effectuée", "Reservation", JOptionPane.INFORMATION_MESSAGE);
                                                        reservation.dispose();

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
                                        else
                                        {
                                            JOptionPane.showMessageDialog(null, "Pas de place avec ce numéro : place entre 0 et 500", "Erreur", JOptionPane.ERROR_MESSAGE);
                                        }

                                    } catch (NumberFormatException exc) {
                                        JOptionPane.showMessageDialog(null, "Veuillez entrer un nombre valide.", "Erreur", JOptionPane.ERROR_MESSAGE);
                                    }
                                }
                            }
                            else
                            {
                                JOptionPane.showMessageDialog(null, "Veuillez entrer un nombre valide.", "Erreur", JOptionPane.ERROR_MESSAGE);
                            }
                            break;
                        }
                    }
                    if(singleton.getListCourse() == null)
                    {
                        jop.showMessageDialog(null, "Aucune course enregistireé", "Erreur", jop.ERROR_MESSAGE);
                        jop.setVisible(true);
                    }
                }

            }
            else
            {
                jop.showMessageDialog(null, "Veuillé sélectionner une date", "Erreur", jop.ERROR_MESSAGE);
                jop.setVisible(true);
            }
        }
    }

}
