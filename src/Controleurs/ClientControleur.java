package Controleurs;

import Model.UserPackage.ClassClient;
import View.Client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClientControleur implements ActionListener
{
    private Client client;
    private String filepath;
    private int nbClient;
    private PersonneEnPlus listener;
    private UnAffichage listener1;
    public ClientControleur(Client cl, String fp, int nb)
    {
        client = cl;
        filepath = fp + "\\Client.properties";
        nbClient = nb;
    }
    public void setUnePersonneEnPlusListener(PersonneEnPlus listener) {
        this.listener = listener;
    }
    public void genererUnePersonne()
    {
        if (listener != null) {
            listener.UnClientAjoute();
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
        if (e.getSource() == client.getTerminerButton())
        {
            boolean licencepresente = false;
            String nom = client.getNom().getText();
            String prenom = client.getPrenom().getText();
            String login = client.getLogin().getText();
            String email = client.getEmail().getText();
            String licence = "";
            if(client.getLicenceDetenueCheckBox().isSelected())
            {
                licence = client.getLicence().getText();
                licencepresente = true;
            }
            char[] mdp = client.getPassword().getPassword();
            String password = new String(mdp);
            boolean ok = true;
            String errormessage = "";
            if(licencepresente == true && licence.length() == 0) //test si le client a dis qu'il avait une licence mais n'a rien encodé
            {
                ok = false;
            }
            if(nom.length() == 0 || prenom.length() == 0 || login.length() == 0 || email.length() == 0 || password.length() == 0 || ok == false)
            {
                ok = false;
                errormessage = "Donnée(s) manquante(s)";
            }
            else
            {
                if(password.length() < 10)
                {
                    ok = false;
                    errormessage = "Le mot de passe est trop court";
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
            }
            boolean ecriture = true;
            JOptionPane jop = new JOptionPane();
            if(ok == false) {
                //affichage du message d'erreur
                jop.showMessageDialog(null, errormessage, "Erreur", jop.ERROR_MESSAGE);
                jop.setVisible(true);
            }
            else
            {
                //affichage que la creation de compte est bien effectuée
                // + écriture des données
                Properties properties = new Properties();

                try
                {
                    // Charger le fichier s'il existe déjà
                    // Enregistrer les propriétés dans le fichier
                    properties.load(new BufferedReader(new FileReader(filepath)));

                    OutputStream output = new FileOutputStream(filepath, false);

                    boolean present = false;

                    for (int i = 0; i<nbClient;i++)
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
                            }
                        }
                    }

                    if(present == false)
                    {
                        // Ajouter ou mettre à jour des propriétés
                        for(int i=0;i<nbClient; i++)
                        {
                            properties.setProperty("Client" + i, ""); //substitution des commentaires
                        }
                        properties.setProperty("nom"+nbClient, nom);
                        properties.setProperty("prenom"+nbClient, prenom);
                        properties.setProperty("email"+nbClient, email);
                        properties.setProperty("licence"+nbClient, licence);
                        properties.setProperty("identifiant"+nbClient, login + "||" + password);

                        properties.store(output, "Client"+nbClient);

                        ClassClient cl = new ClassClient(nbClient, nom, prenom, login, password, licence,false, null, email);
                        ClassSingleton singleton = ClassSingleton.getInstance();
                        singleton.ConnexionClient(cl);
                        modifierlhote();
                        jop.showMessageDialog(null, "Creation de compte effectué", "Information", jop.INFORMATION_MESSAGE);
                        jop.setVisible(true);
                        genererUnePersonne();
                        client.dispose();
                    }
                    else
                    {
                        //écriture du properties récupérer dans le fichier
                        for(int i=0;i<nbClient; i++)
                        {
                            properties.setProperty("Client" + i, ""); //substitution des commentaires
                        }

                        properties.store(output, "Client"+nbClient);
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
        else if(e.getSource() == client.getLicenceDetenueCheckBox())
        {
            if(client.getLicenceDetenueCheckBox().isSelected())
            {
                client.getTextField4().setEnabled(true);
            }
            else
            {
                client.getTextField4().setEnabled(false);
            }
        }
    }
}
