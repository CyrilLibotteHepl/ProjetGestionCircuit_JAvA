package Controleurs;

import Model.UserPackage.ClassClient;
import Model.UserPackage.ClassPilote;
import View.ConnexionClient;
import Model.UserPackage.ClassVoiture;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Properties;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConnexionControleur implements ActionListener
{
    private ConnexionClient connexion;
    private String filepathClient;
    private String filepathPilote;
    public int nbClient;
    public int nbPilote;
    private UnAffichage listener;
    private ClassSingleton singleton;
    public ConnexionControleur(ConnexionClient connex, String fp, int NbClient, int NbPilote)
    {
        connexion = connex;
        filepathClient = fp + "\\Client.properties";
        filepathPilote = fp + "\\Pilote.properties";
        nbClient = NbClient;
        nbPilote = NbPilote;
        singleton = ClassSingleton.getInstance();
    }
    public void setAfficheData(UnAffichage listener) {
        this.listener = listener;
    }
    public void modifierlhote()
    {
        if (listener != null)
        {
            listener.ModifierHote();
        }
    }
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == connexion.getConnexionButton())
        {
            if(connexion.getRadioButtonClient().isSelected()) //client
            {
                String login = connexion.getTextLogin().getText();
                char[] mdp = connexion.getPasswordFieldMotDePasse().getPassword();
                String password = new String(mdp);
                boolean ok = true;
                String errormassage = "";
                if (login.length() == 0 || password.length() == 0) {
                    ok = false;
                    errormassage = "Donnée(s) manquante(s)";
                }
                JOptionPane jop = new JOptionPane();
                if (ok == false) {
                    //affichage du message d'erreur
                    jop.showMessageDialog(null, errormassage, "Erreur", jop.ERROR_MESSAGE);
                    jop.setVisible(true);
                } else 
                {
                    if (password.contains("||"))
                    {
                        password = password.replace("||", "DEUXBARRES");
                    }
                    if(password.contains("#"))
                    {
                        password = password.replace("#", "SHARP");
                    }

                    Properties properties = new Properties();
                    boolean lectureok = true;
                    boolean conx = false;
                    int identifiant = -1;
                    try {
                        properties.load(new FileInputStream(filepathClient));
                        
                        for (int i = 0; i < nbClient; i++) {
                            for (String cle : properties.stringPropertyNames()) {
                                String valeur = properties.getProperty(cle);
                                if (cle.equals("identifiant" + i)) {
                                    String[] identifiants = valeur.split("\\|\\|");
                                    String log = identifiants[0];
                                    String modepa = identifiants[1];
                                    if (log != null && modepa != null) {
                                        if (log.equals(login) && modepa.equals(password)) {
                                            conx = true;
                                            identifiant = i;
                                            break;
                                        }
                                    }
                                }
                            }
                        }

                        if (conx == true && lectureok == true)
                        {
                            //récupération des données
                            String nom = "default", prenom = "default", licence = "default", email = "default";
                            for (int i = 0; i < nbClient; i++) {
                                for (String cle : properties.stringPropertyNames()) {
                                    String valeur = properties.getProperty(cle);
                                    if (cle.startsWith("nom" + identifiant)) {
                                        nom = valeur;
                                    } else if (cle.equals("prenom" + identifiant)) {
                                        prenom = valeur;
                                    } else if (cle.equals("email" + identifiant)) {
                                        email = valeur;
                                    } else if (cle.equals("licence" + identifiant)) {
                                        licence = valeur;
                                    }
                                }
                            }
                            //initialisation du client connecté

                            ClassClient clientconnecte = new ClassClient(identifiant, nom, prenom, login, password, licence,false, null, email);
                            ClassSingleton singleton = ClassSingleton.getInstance();
                            singleton.ConnexionClient(clientconnecte);
                            modifierlhote();
                            jop.showMessageDialog(null, "ConnexionClient effectué", "Information", jop.INFORMATION_MESSAGE);
                            jop.setVisible(true);
                            connexion.dispose();
                        } else
                        {
                            jop.showMessageDialog(null, "Erreur de connexion", "Erreur", jop.ERROR_MESSAGE);
                            jop.setVisible(true);
                        }
                    } catch (IOException exc) {
                        jop.showMessageDialog(null, "Erreur de lecture de fichier", "Erreur", jop.ERROR_MESSAGE);
                        jop.setVisible(true);
                        exc.printStackTrace();
                    }
                }
            }
            else //pilote
            {
                String login = connexion.getTextLogin().getText();
                char[] mdp = connexion.getPasswordFieldMotDePasse().getPassword();
                String password = new String(mdp);
                boolean ok = true;
                String errormassage = "";
                if (login.length() == 0 || password.length() == 0) {
                    ok = false;
                    errormassage = "Donnée(s) manquante(s)";
                }
                JOptionPane jop = new JOptionPane();
                if (ok == false) {
                    //affichage du message d'erreur
                    jop.showMessageDialog(null, errormassage, "Erreur", jop.ERROR_MESSAGE);
                    jop.setVisible(true);
                } else 
                {
                    if (password.contains("||")) {
                        password = password.replace("||", "DEUXBARRES");
                    }
                    if (password.contains("#")) {
                        password = password.replace("#", "SHARP");
                    }
                    Properties properties = new Properties();
                    boolean lectureok = true;
                    boolean conx = false;
                    int identifiant = -1;

                    try {
                        properties.load(new FileInputStream(filepathPilote));

                        // Enregistrer les propriétés dans le fichier
                        for (int i = 0; i < nbPilote; i++) {
                            for (String cle : properties.stringPropertyNames()) {
                                String valeur = properties.getProperty(cle);
                                if (cle.equals("identifiant" + i)) {
                                    String[] identifiants = valeur.split("\\|\\|");
                                    String log = identifiants[0];
                                    String modepa = identifiants[1];
                                    if (log != null && modepa != null) {
                                        if (log.equals(login) && modepa.equals(password)) {
                                            conx = true;
                                            identifiant = i;
                                            break;
                                        }
                                    }
                                }
                            }
                        }

                        if (conx == true && lectureok == true)
                        {
                            //récupération des données
                            String nom = "default", prenom = "default", datestr = "default", licence = "default", categorieprece = "default", telelphone = "default",idvehicule = "default";
                            ClassVoiture voiture = null;
                            char sex = 'N';
                            Date date = new Date();
                            for (int i = 0; i < nbClient; i++) {
                                for (String cle : properties.stringPropertyNames()) {
                                    String valeur = properties.getProperty(cle);
                                    if (cle.startsWith("nom" + identifiant)) {
                                        nom = valeur;
                                    } else if (cle.equals("prenom" + identifiant)) {
                                        prenom = valeur;
                                    } else if (cle.equals("datenaissance" + + identifiant)) {
                                        datestr = valeur;
                                        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                                        try {
                                            date = dateFormat.parse(datestr);
                                            //System.out.println(date);
                                        } catch (ParseException exc) {
                                            System.out.println("Erreur conversion chaîne en date : " + exc.getMessage());
                                        }
                                    } else if (cle.equals("licence" + identifiant)) {
                                        licence = valeur;
                                    } else if (cle.equals("categorie" + identifiant)) {
                                        categorieprece = valeur;
                                    } else if (cle.equals("telephone" + identifiant)) {
                                        telelphone = valeur;
                                    } else if (cle.equals("sex" + identifiant)) {
                                        String sexstr = valeur;
                                        sex = sexstr.charAt(0);
                                        System.out.println(sex);
                                    } else if (cle.equals("idvoiture" + identifiant)) {
                                        idvehicule = valeur;
                                        int id = Integer.parseInt(idvehicule);

                                        //recuperation du la voiture
                                        if(id == -1)
                                        {
                                            //pas de voiture
                                            voiture = new ClassVoiture();
                                        }
                                        else
                                        {
                                            try
                                            {
                                                ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(singleton.getFilepathConnexion() + "\\Voiture.bin"));

                                                Object obj = null;
                                                try
                                                {
                                                    while ((obj = inputStream.readObject()) != null)
                                                    {
                                                        voiture = (ClassVoiture) obj;
                                                        if(voiture.getIdentifiant() == id)
                                                        {
                                                            break;
                                                        }
                                                    }
                                                }
                                                catch (ClassNotFoundException exc)
                                                {
                                                    exc.printStackTrace();
                                                }
                                                inputStream.close();
                                            } catch (IOException exc)
                                            {
                                                exc.printStackTrace();
                                            }
                                        }
                                    }
                                }
                            }
                            ClassPilote piloteconnecte = new ClassPilote(identifiant, nom, prenom, login, password, licence, false, date, sex, telelphone, categorieprece, voiture);

                            ClassSingleton singleton = ClassSingleton.getInstance();
                            singleton.ConnexionPilote(piloteconnecte);
                            modifierlhote();
                            jop.showMessageDialog(null, "ConnexionClient effectué", "Information", jop.INFORMATION_MESSAGE);
                            jop.setVisible(true);
                            connexion.dispose();
                        } else {
                            jop.showMessageDialog(null, "Erreur de connexion", "Erreur", jop.ERROR_MESSAGE);
                            jop.setVisible(true);
                        }
                    } catch (IOException exc) {
                        jop.showMessageDialog(null, "Erreur de lecture de fichier", "Erreur", jop.ERROR_MESSAGE);
                        jop.setVisible(true);
                        exc.printStackTrace();
                    }
                }    
            }


        }
        else if (e.getSource() == connexion.getRadioButtonClient())
        {
            if(connexion.getRadioButtonClient().isSelected())
            {
                connexion.getRadioButtonClient().setSelected(true);
                connexion.getRadioButtonPilote().setSelected(false);
            }
        }
        else if (e.getSource() == connexion.getRadioButtonPilote())
        {
            if(connexion.getRadioButtonPilote().isSelected())
            {
                connexion.getRadioButtonPilote().setSelected(true);
                connexion.getRadioButtonClient().setSelected(false);
            }
        }
    }
}


