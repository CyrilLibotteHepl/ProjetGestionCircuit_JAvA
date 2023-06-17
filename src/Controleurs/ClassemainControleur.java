package Controleurs;

import Model.ActivitePackage.ClassCourse;
import Model.UserPackage.ClassVoiture;
import View.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Properties;

public class ClassemainControleur implements ActionListener, PersonneEnPlus, UnAffichage, AdminInterface, Desenableinterface, MajListVoiture, NouvelleDonneeRCourse, NouvelleDonneRBatheme, NouvelleDonneRTrackDay
{
    private Classemain Lafenetre;
    private String filepathConnexion;
    private String filepathRecord; //pour le fichier record
    private static int nbClient;
    private static int nbPilote;
    private ClassSingleton singleton;
    protected PropertyChangeSupport pcs;
    public ClassemainControleur(Classemain classemainwindow)
    {
        Lafenetre = classemainwindow;
        singleton = ClassSingleton.getInstance();

        filepathConnexion = singleton.getFilepathConnexionIntervenant();
        filepathRecord = singleton.getFilepathRecord();

        pcs = new PropertyChangeSupport(this);

        File file = new File(filepathConnexion + "\\Client.properties");
        if(file.exists()) //récupération du nombre de client enregistré
        {
            try
            {
                BufferedReader reader = new BufferedReader(new FileReader(filepathConnexion + "\\Client.properties"));
                nbClient = 0;
                String unligne;
                while ((unligne = reader.readLine()) != null)
                {
                    if (unligne.startsWith("#Client") || unligne.startsWith("Client"))
                    {
                        nbClient++;
                    }
                }

            }catch (Exception exc)
            {
                exc.printStackTrace();
            }

        }
        else
        {
            nbClient = 0;
        }
        singleton.setNbClient(nbClient); //maj pour singleton et ainsi avoir un bon chiffre pour l'interface réservationbatheme

        File file1 = new File(filepathConnexion + "\\Pilote.properties");
        if(file1.exists()) //récupération du nombre de pilote enregistré
        {
            try
            {
                BufferedReader reader = new BufferedReader(new FileReader(filepathConnexion + "\\Pilote.properties"));
                nbPilote = 0;
                String unligne;
                while ((unligne = reader.readLine()) != null)
                {
                    if (unligne.startsWith("#Pilote") || unligne.startsWith("Pilote"))
                    {
                        nbPilote++;
                    }
                }

            }catch (Exception exc)
            {
                exc.printStackTrace();
            }

        }
        else
        {
            nbPilote = 0;
        }
        singleton.setNbPilote(nbPilote);

        //gestion des répertoires des records

        boolean creationrepertoircourse = true;
        boolean creationrepertoirerecord = true;


        File directory = new File(filepathRecord);
        if(!directory.exists()) //creation du répertoire si nécessaire
        {
            creationrepertoircourse = directory.mkdirs();
        }

//        DefaultListModel<String> list = new DefaultListModel<>();
//        for (ClassVoiture Voiture : singleton.getListVoiture())
//        {
//            String tmp = Voiture.toString();
//            list.addElement(tmp);
//        }
        //version encore plus simplifié sans defaultlistmodel
        Lafenetre.getListVoiture().setListData(singleton.getListVoiture().toArray());

        if(creationrepertoircourse)
        {
            directory = new File(filepathRecord + "\\record");
            if(!directory.exists())
            {
                creationrepertoirerecord = directory.mkdirs();
            }
            if(creationrepertoirerecord)
            {
                //les 2 répertoire sont créer

                File filerecord = new File(filepathRecord + "\\record\\record.properties");
                if(!filerecord.exists())
                {
                    //creation du fichier record avec valeur de base
                    Properties properties = new Properties();
                    properties.setProperty("1", "00.00");
                    properties.setProperty("2", "00.00");
                    properties.setProperty("3", "00.00");
                    properties.setProperty("4", "00.00");
                    properties.setProperty("5", "00.00");

                    try {
                        FileOutputStream outputStream = new FileOutputStream(filepathRecord + "\\record\\record.properties");

                        properties.store(outputStream, "Les records");

                        outputStream.close();
                    } catch (FileNotFoundException ex) {
                        throw new RuntimeException(ex);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    //fin de l'initialisation du fichier
                }
            }
        }
        if (!creationrepertoircourse || !creationrepertoirerecord)
        {
            JOptionPane jop1 = new JOptionPane();
            jop1.showMessageDialog(null, "Erreur dans la création des répertoires", "Repertoire", jop1.ERROR_MESSAGE);
        }

        ModificationDonneeReservationBatheme();
        ModificationDonneeReservationTrackDay();
    }

    public void MiseAJourListVoiture()
    {
        Lafenetre.getListVoiture().setListData(singleton.getListVoiture().toArray());
    } //mise a jour de la liste car on a ajouté une voiture

    @Override
    public void ModificationDonneeReservationBatheme() {
        //lecture du fichier////////////////////////////////////////////////////////
        //vidé la comboBox
        Lafenetre.getComboBoxReservationBtm().removeAllItems();

        try (BufferedReader reader = new BufferedReader(new FileReader(singleton.getFilepathReservation() + "\\ReservationBatheme.txt")))
        {
            String unligne;
            while ((unligne = reader.readLine()) != null)
            {
                Lafenetre.getComboBoxReservationBtm().addItem(unligne);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void ModificationDonneeReservationTrackDay() {
        //////gestion de l'affichage de la combobox des trackdays
        Lafenetre.getComboBoxReservTrackDay().removeAllItems();

        try (BufferedReader reader = new BufferedReader(new FileReader(singleton.getFilepathReservation() + "\\ReservationTrackDay.txt")))
        {
            String unligne;
            while ((unligne = reader.readLine()) != null)
            {
                Lafenetre.getComboBoxReservTrackDay().addItem(unligne);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void UnClientAjoute()
    {
        nbClient++;
        singleton.setNbPilote(nbClient);
    }
    public void UnPiloteAjoute()
    {
        nbPilote++;
        singleton.setNbPilote(nbPilote);
    }
    @Override
    public void CourseDesable()
    {
        Lafenetre.getJMenu3().setEnabled(true);
    }
    @Override
    public void ModifierHote()
    {
        //modifiaction de l'affichage de l'hote connecté

        if(singleton.getLOG() == "" || singleton.getLOG() == null)
        {
            singleton.setLOG("No host");
            Lafenetre.getChamphote().setForeground(Color.BLACK);
            Lafenetre.getChamphote().setText(singleton.getLOG());
        }
        else
        {
            if(singleton.getIfClientPiloteCo()) //client connecté
            {
                Lafenetre.getLabelhost().setText("Client " + singleton.getClientconnecte().getId() + ":");
            }
            else //pilote connecté
            {
                Lafenetre.getLabelhost().setText("Pilote " + singleton.getPiloteconnecte().getId() + ":");
            }
            String affichage = singleton.getLOG() + ", " + singleton.getNOM() + " " + singleton.getPRENOM();
            Lafenetre.getChamphote().setText(affichage);
        }
    }

    @Override
    public void setAdminIndisponible()
    {
        Lafenetre.getJMenuItem3().setEnabled(false);
    }
    public void setAdminDisponible()
    {
        Lafenetre.getChamphote().setForeground(Color.RED);
        Lafenetre.getButtonLanceCourse().setEnabled(true);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == Lafenetre.getJMenuItem1())
        {
            //initialisation du controleur plus de ça fenêtre
            boolean creation = true;
            File directory = new File(filepathConnexion);
            if(!directory.exists()) //creation du répertoire si nécessaire
            {
                creation = directory.mkdirs();
            }
            if(creation)
            {
                File file = new File(filepathConnexion + "\\Client.properties");
                if(file.exists())
                {
                    ConnexionClient c = new ConnexionClient();
                    ConnexionControleur connexionControleur = new ConnexionControleur(c, filepathConnexion, nbClient, nbPilote);
                    connexionControleur.setAfficheData(this);
                    c.setControleurConnexionClient(connexionControleur);
                    c.setResizable(false);
                    c.setVisible(true);
                }
                else
                {
                    JOptionPane jop1 = new JOptionPane();
                    jop1.showMessageDialog(null, "Aucun client encode, veuillé créer une compte", "Compte", jop1.INFORMATION_MESSAGE);
                }
            }
            if(!creation)
            {
                JOptionPane jop = new JOptionPane();
                jop.showMessageDialog(null, "Erreur dans la gestion des fichiers", "Erreur", jop.ERROR_MESSAGE);
            }
        }
        else if(e.getSource() == Lafenetre.getJMenuItem2())
        {
            if(singleton.TestPilote() || singleton.TestClient())
            {
                singleton.Deconnexion();
                Lafenetre.getJMenu3().setEnabled(false);
                Lafenetre.getButtonLanceCourse().setEnabled(false);
                ModifierHote();
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Veuillez vous connectez pour vous déconnecter", "Erreur", JOptionPane.INFORMATION_MESSAGE);
            }

        }
        else if(e.getSource() == Lafenetre.getJMenuItem3())
        {

            if(singleton.getLOG() == "" || singleton.getLOG() == null)
            {
                JOptionPane jop = new JOptionPane();
                jop.showMessageDialog(null, "Veuillez d'abord vous connecter", "Erreur", jop.ERROR_MESSAGE);
            }
            else
            {
                Admin admin = new Admin();
                AdminControleur adminControleur = new AdminControleur(admin);
                adminControleur.setEventDisponibiliteAdmin(this);
                adminControleur.setEventCourseAdmin(this);
                admin.setAdminControleur(adminControleur);
                admin.setResizable(false);
                admin.setVisible(true);
            }
        }
        else if(e.getSource() == Lafenetre.getJMenuItem4())
        {
            if(singleton.getLOG() == "" || singleton.getLOG() == null)
            {
                JOptionPane jop = new JOptionPane();
                jop.showMessageDialog(null, "Veuillez d'abord vous connecter", "Erreur", jop.ERROR_MESSAGE);
            }
            else
            {
                ResetMDP resetMDP = new ResetMDP();
                NewMdpControleur newMdpControleur = new NewMdpControleur(resetMDP, filepathConnexion, nbClient, nbPilote);
                resetMDP.setMotDePasseControleur(newMdpControleur);
                resetMDP.setResizable(false);
                resetMDP.setVisible(true);
            }
        }
        else if(e.getSource() == Lafenetre.getJMenuItem5())
        {
            boolean creation = true;
            File directory = new File(filepathConnexion);
            if(!directory.exists())
            {
                creation = directory.mkdirs();
            }
            if(creation)
            {
                File file = new File(filepathConnexion + "\\Client.properties");
                Client cl = new Client();
                if(file.exists())
                {
                    ClientControleur clientControleur = new ClientControleur(cl, filepathConnexion, nbClient);
                    clientControleur.setAfficheData(this);
                    clientControleur.setUnePersonneEnPlusListener(this);
                    cl.setControleurClient(clientControleur);
                    cl.setResizable(false);
                    cl.setVisible(true);
                }
                else
                {
                    try
                    {
                        file.createNewFile(); // creation du fichier
                        ClientControleur clientControleur = new ClientControleur(cl, filepathConnexion, nbClient);
                        clientControleur.setAfficheData(this);
                        clientControleur.setUnePersonneEnPlusListener(this);
                        cl.setControleurClient(clientControleur);
                        cl.setResizable(false);
                        cl.setVisible(true);
                    }
                    catch (IOException exception)
                    {
                        creation = false;
                    }
                }
            }
            if(!creation)
            {
                JOptionPane jop = new JOptionPane();
                jop.showMessageDialog(null, "Erreur dans la gestion des fichiers", "Erreur", jop.ERROR_MESSAGE);
            }
        }
        else if(e.getSource() == Lafenetre.getJMenuItem6())
        {
            boolean creation = true;
            File directory = new File(filepathConnexion);
            if(!directory.exists())
            {
                creation = directory.mkdirs();
            }
            if(creation)
            {
                File file = new File(filepathConnexion + "\\Pilote.properties");
                Pilote plt = new Pilote();
                if(file.exists())
                {
                    PiloteControleur piloteControleur = new PiloteControleur(plt, filepathConnexion, nbPilote);
                    piloteControleur.setAfficheData(this);
                    piloteControleur.setUnePersonneEnPlusListener(this);
                    piloteControleur.setEnvoieModifactionListVoiture(this);
                    plt.setPiloteControleur(piloteControleur);
                    plt.setResizable(false);
                    plt.setVisible(true);
                }
                else
                {
                    try
                    {
                        file.createNewFile(); // creation du fichier
                        PiloteControleur piloteControleur = new PiloteControleur(plt, filepathConnexion, nbPilote);
                        piloteControleur.setAfficheData(this);
                        piloteControleur.setUnePersonneEnPlusListener(this);
                        piloteControleur.setEnvoieModifactionListVoiture(this);
                        plt.setPiloteControleur(piloteControleur);
                        plt.setResizable(false);
                        plt.setVisible(true);
                    }
                    catch (IOException exception)
                    {
                        creation = false;
                    }
                }
            }
            if(!creation)
            {
                JOptionPane jop = new JOptionPane();
                jop.showMessageDialog(null, "Erreur dans la gestion des fichiers", "Erreur", jop.ERROR_MESSAGE);
            }
        }
        else if (e.getSource() == Lafenetre.getJMenuItem7())
        {
            boolean creation = true;
            File directory = new File(singleton.getFilepathCourse());
            if(!directory.exists())
            {
                creation = directory.mkdirs();
            }
            if(creation)
            {
                File filerecord = new File(filepathRecord + "\\record\\record.properties");
                Course course = new Course();
                if(filerecord.exists())
                {
                    File file = new File(singleton.getFilepathCourse() + "\\Course.dat");
                    Voiture vt = new Voiture();
                    if(file.exists())
                    {
                        CourseControleur courseControleur = new CourseControleur(course, filepathRecord + "\\record\\record.properties");
                        course.setControleurCourse(courseControleur);
                        course.setResizable(false);
                        course.setVisible(true);
                    }
                    else
                    {
                        try
                        {
                            file.createNewFile(); // creation du fichier
                            CourseControleur courseControleur = new CourseControleur(course, filepathRecord + "\\record\\record.properties");
                            course.setControleurCourse(courseControleur);
                            course.setResizable(false);
                            course.setVisible(true);
                        }
                        catch (IOException exception)
                        {
                            creation = false;
                        }
                    }
                }
                if(!creation)
                {
                    JOptionPane jop = new JOptionPane();
                    jop.showMessageDialog(null, "Erreur dans la gestion des fichiers", "Erreur", jop.ERROR_MESSAGE);
                }
            }
        }
        else if (e.getSource() == Lafenetre.getJMenuItem8())
        {
            boolean creation = true;
            File directory = new File(singleton.getFilepathPlanning());
            if(!directory.exists())
            {
                creation = directory.mkdirs();
            }
            if(creation)
            {
                PlanificationBatheme planificationBatheme = new PlanificationBatheme(); //on crée le fichier dans le contrôleur ici

                ControleurBathemeplanification controleurBathemeplanification = new ControleurBathemeplanification(planificationBatheme);
                planificationBatheme.setBathemePlanControleur(controleurBathemeplanification);
                planificationBatheme.setResizable(false);
                planificationBatheme.setVisible(true);

            }
            else
            {
                JOptionPane jop = new JOptionPane();
                jop.showMessageDialog(null, "Erreur dans la gestion des fichiers", "Erreur", jop.ERROR_MESSAGE);
            }
        }
        else if (e.getSource() == Lafenetre.getJMenuItem9())
        {
            boolean creation = true;
            File directory = new File(singleton.getFilepathPlanning());
            if(!directory.exists())
            {
                creation = directory.mkdirs();
            }
            if(creation)
            {
                PlanificationTrackday planificationTrackday = new PlanificationTrackday(); //on crée le fichier dans le contrôleur ici

                ControleurTrackdayplanification controleurTrackdayplanification = new ControleurTrackdayplanification(planificationTrackday);
                planificationTrackday.setTrackdayControleur(controleurTrackdayplanification);
                planificationTrackday.setResizable(false);
                planificationTrackday.setVisible(true);

            }
            else
            {
                JOptionPane jop = new JOptionPane();
                jop.showMessageDialog(null, "Erreur dans la gestion des fichiers", "Erreur", jop.ERROR_MESSAGE);
            }
        }
        else if(e.getSource() == Lafenetre.getButtonSelectionner())
        {
            //gestion de l'affichage des donnée d'une voiture
            ClassVoiture tostringdelavoiture = (ClassVoiture) Lafenetre.getListVoiture().getSelectedValue();
            JOptionPane jop = new JOptionPane();
            if(tostringdelavoiture != null)
            {
                ClassVoiture lavoiture = new ClassVoiture();
                for(ClassVoiture unevoiture : singleton.getListVoiture())
                {
                    if(unevoiture.toString().equals(tostringdelavoiture.toString())) // to string identique donc c'est le bon objet
                    {
                        lavoiture = unevoiture;
                        break;
                    }
                }

                VoitureSelect voitureSelect = new VoitureSelect();
                VoitureSelectControleur voitureSelectControleur = new VoitureSelectControleur(voitureSelect, lavoiture);
                voitureSelect.setVoitureSelectControleur(voitureSelectControleur);
                voitureSelectControleur.addPropertyChangeListener(voitureSelect);
                voitureSelectControleur.ModificationAffichage();
                voitureSelect.setResizable(false);
                voitureSelect.setVisible(true);
            }
            else
            {
                jop.showMessageDialog(null, "Erreur, veuillé sélectionner une voiture", "Erreur", jop.ERROR_MESSAGE);
            }

        }
        else if (e.getSource() == Lafenetre.getButtonReservation())
        {
            if(singleton.TestClient() || singleton.TestPilote())
            {
                boolean creation = true;
                File file = new File(singleton.getFilepathPlanning() + "\\Planning.csv");
                JOptionPane jop = new JOptionPane();
                if(file.exists())
                {
                    File directoryreservation = new File(singleton.getFilepathReservation());
                    if(!directoryreservation.exists())
                    {
                        creation = directoryreservation.mkdirs();
                    }
                    else
                    {

                        DeterminerReservation determinerReservation = new DeterminerReservation(); //on crée le fichier dans le contrôleur ici

                        ControleurDeterminerReservation controleurDeterminerReservation= new ControleurDeterminerReservation(determinerReservation);
                        controleurDeterminerReservation.setEventMajReservCourse(this);
                        controleurDeterminerReservation.setEventMagReservBatheme(this);
                        controleurDeterminerReservation.setEventMagReservTrackDay(this);
                        addPropertyChangeListener(Lafenetre);
                        determinerReservation.setDeterminerReservationControleur(controleurDeterminerReservation);
                        determinerReservation.setResizable(false);
                        determinerReservation.setVisible(true);


                    }
                    if(!creation)
                    {
                        jop.showMessageDialog(null, "Erreur dans la gestion des fichiers", "Erreur", jop.ERROR_MESSAGE);
                    }
                }
                else
                {
                    jop.showMessageDialog(null, "Erreur fichier planning introuvable", "Erreur", jop.ERROR_MESSAGE);
                }
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Erreur, veuillez d'abord vous connectez", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
        else if (e.getSource() == Lafenetre.getButtonLanceCourse())
        {
            //gestion de l'affichage des donnée d'une course terminé
            JOptionPane jop = new JOptionPane();
            LocalDate datecur = null;
            LocalTime time = null;
            LocalDate date = null;
            LocalDate datenow = LocalDate.now();
            LocalTime timenow = LocalTime.now();
            ClassCourse lacourse = new ClassCourse();

            for (ClassCourse jCourse : singleton.getListCourse()) {
                date = jCourse.getDate(); // Date à comparer
                time = jCourse.getHeure(); // Heure à comparer
                if (date.isEqual(datenow))
                {
                    //date egale date actuelle
                    datecur = date;
                    lacourse = jCourse;
                }
            }
            if(datecur == null)
                jop.showMessageDialog(null, "Erreur, Pas de course aujourd'hui.", "Erreur", jop.ERROR_MESSAGE);
            else if (time.isAfter(timenow) && datecur != null && !lacourse.isCourseterminer())
            {
                LanCourse lancourse= new LanCourse();
                Controleurlancementcourses controleurlancementcourses = null;
                try {
                    controleurlancementcourses = new Controleurlancementcourses(lancourse, lacourse);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                controleurlancementcourses.addPropertyChangeListener(lancourse);
                controleurlancementcourses.ModifierAffichage();
                lancourse.setResizable(false);
                lancourse.setVisible(true);
                lacourse.setCourseterminer(true);
            }
            else if (time.isBefore(timenow) || lacourse.isCourseterminer() == true)
            {
                jop.showMessageDialog(null, "Erreur, La course d'aujourd'hui est deja terminé.", "Erreur", jop.ERROR_MESSAGE);
            }
        }
    }

    @Override
    public void ModificationDonneeReservationCourse(int place, LocalDate date, LocalTime heure)
    {
        String oldValue = "";
        String newValue = "";
        oldValue = Lafenetre.getLabelPgradin().getText();
        newValue = "" + place;
        pcs.firePropertyChange("Place",oldValue,newValue);

        oldValue = Lafenetre.getLabelDateReserv().getText();
        newValue = date.toString() + " à " + heure.toString();
        pcs.firePropertyChange("DateReserv",oldValue,newValue);
    }

    public void addPropertyChangeListener(PropertyChangeListener l)
    {
        pcs.addPropertyChangeListener(l);
    }
    public void removePropertyChangeListener(PropertyChangeListener l)
    {
        pcs.removePropertyChangeListener(l);
    }
}
