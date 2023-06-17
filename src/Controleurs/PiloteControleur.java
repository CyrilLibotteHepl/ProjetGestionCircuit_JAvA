package Controleurs;

import Model.UserPackage.ClassPilote;
import Model.UserPackage.ClassVoiture;
import View.Pilote;
import View.Voiture;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class PiloteControleur implements ActionListener, SignalToPilote
{
    private Pilote pilote;
    private String filepathpilote;
    private String filepathvoiture;
    private int nbPilote;
    private boolean voitureencode;
    private ClassSingleton singleton;
    private PersonneEnPlus listener;
    private UnAffichage listener1;
    private MajListVoiture listener3;
    public PiloteControleur (Pilote plt, String fp,int nb)
    {
        pilote = plt;
        singleton = ClassSingleton.getInstance();
        filepathvoiture = singleton.getFilepathConnexion(); //"C:\\Users\\cycro\\Desktop\\JAVApj\\Connexion\\Voiture"
        filepathpilote = fp + "\\Pilote.properties"; //"C:\\Users\\cycro\\Desktop\\JAVApj\\Connexion\Pilote.properties"
        nbPilote = nb;
        voitureencode = false;
    }

    public void setEnvoieModifactionListVoiture(MajListVoiture unmajlistvoiture)
    {
        this.listener3 = unmajlistvoiture;
    }
    public void EnvoieModifactionListVoiture()
    {
        this.listener3.MiseAJourListVoiture();
    } //il dit a la classe main d'effectuer la méthode
    public void setUnePersonneEnPlusListener(PersonneEnPlus listener) {
        this.listener = listener;
    }
    public void genererUnePersonne()
    {
        if (listener != null) {
            listener.UnPiloteAjoute();
        }
    }
    public void setAfficheData(UnAffichage listener) {
        this.listener1 = listener;
    }
    public void modifierlhote()
    {
        if (listener1 != null) {
            listener1.ModifierHote();
        }
    }
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == pilote.getTerminerButton())
        {
            boolean ok = true;
            String errormessage = "";

            String nom = pilote.getTextFieldNom().getText();
            String prenom = pilote.getTextFieldPrenom().getText();
            String login = pilote.getTextFieldLogin().getText();
            char[] mdp = pilote.getPasswordField().getPassword();
            String password = new String(mdp);

            //recuperation de la date en sous forme de int

            int jour = -1, moi = -1, annee = -1;
            String jourstr = (String) pilote.getComboBoxJour().getSelectedItem();
            jour = Integer.parseInt(jourstr);
            String moistr = (String) pilote.getComboBoxMoi().getSelectedItem();
            switch (moistr)
            {
                case "Janvier": moi = 0; //dans la classe calendar janvier commence a 0
                    break;
                case "Février": moi = 1;
                    break;
                case "Mars": moi = 2;
                    break;
                case "Avril": moi = 3;
                    break;
                case "Mai": moi = 4;
                    break;
                case "Juin": moi = 5;
                    break;
                case "Juillet": moi = 6;
                    break;
                case "Août": moi = 7;
                    break;
                case "Septembre": moi = 8;
                    break;
                case "Octobre": moi = 9;
                    break;
                case "Novembre": moi = 10;
                    break;
                case "Décembre": moi = 11;
            }
            String anneestr = (String) pilote.getComboBoxAnnee().getSelectedItem();
            annee = Integer.parseInt(anneestr);

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, annee);
            calendar.set(Calendar.MONTH, moi);
            calendar.set(Calendar.DAY_OF_MONTH, jour);
            Date date = calendar.getTime();

            String licence = pilote.getTextFieldLicence().getText();
            String categoriePrece = pilote.getTextFieldCategorie().getText();
            if(pilote.getExperianceCheckBox().isSelected() && categoriePrece.length() == 0) //check si le pilote a dis qu'il avait de l'expérience, mais n'a pas reseigné de catégorie
            {
                ok = false;
            }

            String numerostr = pilote.getTextFieldtel().getText();
            int identfiantvoiture = -1; //signifie que le pilote n'a pas de voiture
            if(voitureencode) //check si une voiture a bien été encodée
            {
                identfiantvoiture = singleton.getNbVoiture();
            }

            String sexstr = (String) pilote.getComboBoxSex().getSelectedItem();
            char sex = 'N';
            switch (sexstr)
            {
                case "Homme": sex = 'M';
                    break;
                case "Femme": sex = 'F';
                    break;
                case "Autre": sex = 'O';
            }

            JOptionPane jop = new JOptionPane();
            if(nom.length() == 0 || prenom.length() == 0 || login.length() == 0 || password.length() == 0 || licence.length() == 0 || numerostr.length() == 0 || ok == false)
            {
                ok = false;
                errormessage = "Donnée(s) manquante(s)";
            }
            else
            {
                int numero = -1;
                boolean numerodeteltropgrand = false;
                try{
                    numero = Integer.parseInt(numerostr);
                } catch (NumberFormatException exception) {
                    if(numerostr.length() > 10) //je peu le mettre ici car passé 10 chiffre ça ne rentre plus dans un int
                    {
                        numerodeteltropgrand = true;
                    }
                    ok = false;
                }

                if(password.length() < 10 || ok == false || numero < 0)
                {
                    if(!ok)
                    {
                        if(numerodeteltropgrand)
                        {
                            errormessage = "Le numero de téléphone ne peu comporté au maximum que 10 chiffres";
                        }
                        else
                        {
                            errormessage = "Le numero de téléphone ne doit comporter que des chiffres";
                        }
                    }
                    else
                    {
                        if (password.length() < 10)
                        {
                            ok = false;
                            errormessage = "Le mot de passe est trop court";
                        }
                        else
                        {
                            ok = false;
                            errormessage = "Un numero de télephone négaif ?";
                        }
                    }
                }
                else
                {
                    int nbmaj = 0;
                    for(int i = 0; i<password.length();i++)
                    {
                        char c = password.charAt(i);
                        if(Character.isUpperCase(c))
                        {
                            nbmaj++;
                        }
                    }
                    if(nbmaj < 2)
                    {
                        ok = false;
                        errormessage = "Pas assez de majuscule dans le mdp";
                    }
                    else
                    {
                        int nbchiffre = 0;
                        for(int i = 0; i<password.length();i++)
                        {
                            char c = password.charAt(i);
                            if(Character.isDigit(c))
                            {
                                nbchiffre++;
                            }
                        }
                        if(nbchiffre < 2)
                        {
                            ok =false;
                            errormessage = "Pas assez de chiffre dans le mdp";
                        }
                        else
                        {
                            Pattern pattern = Pattern.compile("[^a-zA-Z0-9]"); //compilation du string dans un objet parterne dans lequel on peu faire des recherche
                            Matcher matcher = pattern.matcher(password); // permet d'effectuer des recherche dans une objet parterne
                            boolean caracspeciaux = matcher.find();
                            if(caracspeciaux == false)
                            {
                                ok = false;
                                errormessage = "Un caractère spécial minimal requis";
                            }
                            if(password.contains("||")) //je met a la fin pour qu'il n'y a pas de problème avec la gesiton des majuscules
                            {
                                password = password.replace("||", "DEUXBARRES");
                            }
                            if(password.contains("#"))
                            {
                                password = password.replace("#", "SHARP");
                            }
                        }
                    }
                }

                if(ok)
                {
                    //écriture dans le fichier
                    //affichage que la creation de compte est bien effectuée
                    // + écriture des données
                    Properties properties = new Properties();

                    try
                    {
                        // Charger le fichier s'il existe déjà
                        properties.load(new BufferedReader(new FileReader(filepathpilote)));

                        OutputStream output = new FileOutputStream(filepathpilote, false);

                        boolean present = false;
                        // Enregistrer les propriétés dans le fichier
                        for (int i = 0; i<nbPilote;i++)
                        {
                            for (String cle : properties.stringPropertyNames())
                            {
                                String valeur = properties.getProperty(cle);
                                if(cle.equals("identifiant"+i))
                                {
                                    String[] identifiants = valeur.split("\\|\\|");
                                    String log = identifiants[0];
                                    String modepa = identifiants[1];
                                    if (log.equals(login)) { present = true; }
                                    if(modepa.equals(password)) { present = true; }
                                    break;
                                }
                            }
                        }

                        if(present == false)
                        {
                            // Ajouter ou mettre à jour des propriétés
                            for(int i=0;i<nbPilote; i++)
                            {
                                properties.setProperty("Pilote" + i, ""); //substitution des commentaires
                            }
                            properties.setProperty("nom"+nbPilote, nom);
                            properties.setProperty("prenom"+nbPilote, prenom);
                            properties.setProperty("identifiant"+nbPilote, login + "||" + password);
                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                            String dateString = dateFormat.format(date);
                            properties.setProperty("datenaissance"+nbPilote, dateString);
                            properties.setProperty("licence"+nbPilote, licence);
                            properties.setProperty("categorie"+nbPilote, categoriePrece);
                            properties.setProperty("telephone"+nbPilote, numerostr);
                            properties.setProperty("sex"+nbPilote, String.valueOf(sex));
                            properties.setProperty("idvoiture"+nbPilote, String.valueOf(identfiantvoiture)); //on ne stock que des string

                            properties.store(output, "Pilote"+nbPilote);

                            //récupération de la voiture associé au pilote
                            ClassVoiture voiture = null;
                            if(identfiantvoiture == -1)
                            {
                                //pas de voiture
                                voiture = new ClassVoiture();
                            }
                            else
                            {
                                try
                                {
                                    ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filepathvoiture + "\\Voiture.dat"));

                                    Object obj = null;
                                    try
                                    {
                                        while ((obj = inputStream.readObject()) != null)
                                        {
                                            voiture = (ClassVoiture) obj;
                                            if(voiture.getIdentifiant() == identfiantvoiture)
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

                            ClassPilote pl = new ClassPilote(nbPilote, nom, prenom, login, password, licence, false, date, sex, numerostr, categoriePrece, voiture);
                            ClassSingleton singleton = ClassSingleton.getInstance();
                            singleton.ConnexionPilote(pl);
                            modifierlhote();
                            jop.showMessageDialog(null, "Creation de compte effectué", "Information", jop.INFORMATION_MESSAGE);
                            jop.setVisible(true);
                            genererUnePersonne();
                            pilote.dispose();
                        }
                        else
                        {
                            //écriture du properties récupérer dans le fichier
                            for(int i=0;i<nbPilote; i++)
                            {
                                properties.setProperty("Pilote" + i, ""); //substitution des commentaires
                            }

                            properties.store(output, "Pilote"+nbPilote);
                            jop.showMessageDialog(null, "Erreur veuillez choisir un autre mdp ou login", "Erreur", jop.ERROR_MESSAGE);
                            jop.setVisible(true);
                        }
                    } catch (IOException exc)
                    {
                        jop.showMessageDialog(null, "Erreur dans la creation du compte", "Erreur", jop.ERROR_MESSAGE);
                        jop.setVisible(true);
                        exc.printStackTrace();
                    }
                }
            }

            if(!ok)
            {
                jop.showMessageDialog(null, errormessage, "Erreur", jop.ERROR_MESSAGE);
                jop.setVisible(true);
            }
        }
        else if (e.getSource() == pilote.getEncoderUneVoitureButton())
        {
            voitureencode = true;
            boolean creation = true;
            File directory = new File(filepathvoiture);
            if(!directory.exists())
            {
                creation = directory.mkdirs();
            }
            if(creation)
            {
                File file = new File(filepathvoiture + "\\Voiture.dat");
                Voiture vt = new Voiture();
                if(file.exists())
                {
                    VoitureControleur voitureControleur = new VoitureControleur(vt, filepathvoiture + "\\Voiture.dat");
                    voitureControleur.setMajListVoiture(this); //abonnement a l'évenement de pilote
                    vt.setVoitureControleur(voitureControleur);
                    vt.setResizable(false);
                    vt.setVisible(true);
                }
                else
                {
                    try
                    {
                        file.createNewFile(); // creation du fichier
                        VoitureControleur voitureControleur = new VoitureControleur(vt, filepathvoiture + "\\Voiture.dat");
                        voitureControleur.setMajListVoiture(this);
                        vt.setVoitureControleur(voitureControleur);
                        vt.setResizable(false);
                        vt.setVisible(true);
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
        else if (e.getSource() == pilote.getComboBoxMoi())
        {
            //gestion du nombre de jour dans le moi
            String moiselectionner = (String) pilote.getComboBoxMoi().getSelectedItem();
            int nbjours = -1;
            switch (moiselectionner)
            {
                case "Janvier":
                case "Mars":
                case "Mai":
                case "Juillet":
                case "Août":
                case "Octobre":
                case "Décembre":
                    nbjours = 31;
                    break;
                case "Avril":
                case "Juin":
                case "Septembre":
                case "Novembre":
                    nbjours = 30;
                    break;
                case "Février":
                    String strinanntmp = (String) pilote.getComboBoxAnnee().getSelectedItem();

                    int anneeselect = Integer.parseInt(strinanntmp);
                    if((anneeselect % 4 == 0 && anneeselect % 100 != 0) || (anneeselect % 400 == 0))
                    {
                        //année bissextil
                        nbjours = 29;
                    }
                    else
                    {
                        nbjours = 28;
                    }
                    break;
            }
            pilote.getComboBoxJour().removeAllItems();
            String istr = "";
            for(int i = 1; i<=nbjours;i++)
            {
                istr = String.valueOf(i);
                pilote.getComboBoxJour().addItem(istr);
            }
        }
        else if (e.getSource() == pilote.getExperianceCheckBox())
        {
            if(pilote.getExperianceCheckBox().isSelected())
            {
                pilote.getTextFieldCategorie().setEnabled(true);
            }
            else
            {
                pilote.getTextFieldCategorie().setEnabled(false);
            }
        }
    }
}
