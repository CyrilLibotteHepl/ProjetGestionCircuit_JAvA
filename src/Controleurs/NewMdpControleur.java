package Controleurs;

import View.ResetMDP;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NewMdpControleur implements ActionListener {
    private ResetMDP resetMDP;
    private String filepathClient;
    private String filepathPilote;
    public int nbClient;
    public int nbPilote;
    private ClassSingleton singleton;
    public NewMdpControleur(ResetMDP resetMDP1, String fp, int NbClient, int NbPilote)
    {
        resetMDP = resetMDP1;
        filepathClient = fp + "\\Client.properties";
        filepathPilote = fp + "\\Pilote.properties";
        nbClient = NbClient;
        nbPilote = NbPilote;
        singleton = ClassSingleton.getInstance();
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == resetMDP.getButtonTerminer())
        {
            if(singleton.getIfClientPiloteCo()) //client co
            {
                //récupération des variable du client connecté
                String passwordCourant = singleton.getMDP();
                String login = singleton.getLOG();

                //récupération du mots de passe entrée
                char[] mdp = resetMDP.getPasswordField().getPassword();
                String passwordEntre = new String(mdp);
                if(passwordEntre.contains("||"))
                {
                    passwordEntre = passwordEntre.replace("||", "DEUXBARRES");
                }
                if(passwordEntre.contains("#"))
                {
                    passwordEntre = passwordEntre.replace("#", "SHARP");
                }

                //ouverture du fichier et lecture des données pour voir si le mot de passe entré est correct
                String testVeracite = login + "||" + passwordEntre;
                JOptionPane jop = new JOptionPane();
                Properties properties = new Properties();
                int identifiant = -1;
                try
                {
                    boolean donnecorrecte = false;
                    properties.load(new FileInputStream(filepathClient));

                    for (int i = 0; i < nbClient; i++)
                    {
                        for (String cle : properties.stringPropertyNames())
                        {
                            String valeur = properties.getProperty(cle);

                            if (cle.equals("identifiant" + i)) {
                                if(valeur.equals(testVeracite))
                                {
                                    donnecorrecte = true;
                                    identifiant = i;
                                }
                            }
                        }
                    }

                    //récupération du nouveau mot de passe
                    if(donnecorrecte == true)
                    {
                        String motdepasse1 = resetMDP.getTextFieldnmdp1().getText();
                        String motdepasse2 = resetMDP.getTextFieldnmdp2().getText();
                        String errormassage = "";
                        boolean ok = true;
                        if(motdepasse1.equals(motdepasse2))
                        {
                            if(motdepasse2.length() < 10)
                            {
                                ok = false;
                                errormassage = "Le mot de passe est trop court";
                            }
                            else
                            {
                                int nbmaj = 0;
                                for(int i = 0; i<motdepasse2.length();i++)
                                {
                                    char c = motdepasse2.charAt(i);
                                    if(Character.isUpperCase(c))
                                    {
                                        nbmaj++;
                                    }
                                }
                                if(nbmaj < 2)
                                {
                                    ok = false;
                                    errormassage = "Pas assez de majuscule dans le mdp";
                                }
                                else
                                {
                                    int nbchiffre = 0;
                                    for(int i = 0; i<motdepasse2.length();i++)
                                    {
                                        char c = motdepasse2.charAt(i);
                                        if(Character.isDigit(c))
                                        {
                                            nbchiffre++;
                                        }
                                    }
                                    if(nbchiffre < 2)
                                    {
                                        ok =false;
                                        errormassage = "Pas assez de chiffre dans le mdp";
                                    }
                                    else
                                    {
                                        Pattern pattern = Pattern.compile("[^a-zA-Z0-9]"); //compilation du string dans un objet parterne dans lequel on peu faire des recherche
                                        Matcher matcher = pattern.matcher(motdepasse2); // permet d'effectuer des recherche dans une objet parterne
                                        boolean caracspeciaux = matcher.find();
                                        if(caracspeciaux == false)
                                        {
                                            ok = false;
                                            errormassage = "Un caractère spécial minimal requis";
                                        }
                                        if(motdepasse2.contains("||")) //je met a la fin pour qu'il n'y a pas de problème avec la gesiton des majuscules
                                        {
                                            motdepasse2 = motdepasse2.replace("||", "DEUXBARRES");
                                        }
                                        if(motdepasse2.contains("#"))
                                        {
                                            motdepasse2 = motdepasse2.replace("#", "SHARP");
                                        }
                                    }
                                }
                            }

                            //mot de passe correcte
                            if(ok == true)
                            {
                                //écriture dans le fichier
                                properties.setProperty("identifiant" + identifiant, login + "||" + motdepasse2);

                                try (OutputStream outputStream = new FileOutputStream(filepathClient))
                                {
                                    // Sauvegarder les modifications dans le fichier
                                    properties.store(outputStream, null);
                                    jop.showMessageDialog(null, "Mot de passe modifié", "Information", jop.INFORMATION_MESSAGE);
                                    jop.setVisible(true);
                                    resetMDP.dispose();
                                } catch (IOException exc)
                                {
                                    jop.showMessageDialog(null, "Erreur dans l'écriture du fichier", "Erreur", jop.ERROR_MESSAGE);
                                    jop.setVisible(true);
                                    exc.printStackTrace();
                                }
                            }
                            else
                            {
                                jop.showMessageDialog(null, errormassage, "Erreur", jop.ERROR_MESSAGE);
                                jop.setVisible(true);
                            }
                        }
                        else
                        {
                            jop.showMessageDialog(null, "Erreur dans la saisie du mot de passe", "Erreur", jop.ERROR_MESSAGE);
                            jop.setVisible(true);
                        }
                    }
                    else
                    {
                        jop.showMessageDialog(null, "Erreur, mot de passe obsolète", "Erreur", jop.ERROR_MESSAGE);
                        jop.setVisible(true);
                    }

                } catch (IOException exception)
                {
                    jop.showMessageDialog(null, "Erreur de lecture de fichier", "Erreur", jop.ERROR_MESSAGE);
                    jop.setVisible(true);
                    exception.printStackTrace();
                }
            }
            else
            {
                //récupération des variable du pilote connecté
                String passwordCourant = singleton.getMDP();
                String login = singleton.getLOG();

                //récupération du mots de passe entrée
                char[] mdp = resetMDP.getPasswordField().getPassword();
                String passwordEntre = new String(mdp);
                if(passwordEntre.contains("||"))
                {
                    passwordEntre = passwordEntre.replace("||", "DEUXBARRES");
                }

                //ouverture du fichier et lecture des données pour voir si le mot de passe entré est correct
                String testVeracite = login + "||" + passwordEntre;
                JOptionPane jop = new JOptionPane();
                Properties properties = new Properties();
                int identifiant = -1;
                try
                {
                    boolean donnecorrecte = false;
                    properties.load(new FileInputStream(filepathPilote));

                    for (int i = 0; i < nbPilote; i++)
                    {
                        for (String cle : properties.stringPropertyNames())
                        {
                            String valeur = properties.getProperty(cle);

                            if (cle.equals("identifiant" + i)) {
                                if(valeur.equals(testVeracite))
                                {
                                    donnecorrecte = true;
                                    identifiant = i;
                                }
                            }
                        }
                    }

                    //récupération du nouveau mot de passe
                    if(donnecorrecte == true)
                    {
                        String motdepasse1 = resetMDP.getTextFieldnmdp1().getText();
                        String motdepasse2 = resetMDP.getTextFieldnmdp2().getText();
                        String errormassage = "";
                        boolean ok = true;
                        if(motdepasse1.equals(motdepasse2))
                        {
                            if(motdepasse2.length() < 10)
                            {
                                ok = false;
                                errormassage = "Le mot de passe est trop court";
                            }
                            else
                            {
                                int nbmaj = 0;
                                for(int i = 0; i<motdepasse2.length();i++)
                                {
                                    char c = motdepasse2.charAt(i);
                                    if(Character.isUpperCase(c))
                                    {
                                        nbmaj++;
                                    }
                                }
                                if(nbmaj < 2)
                                {
                                    ok = false;
                                    errormassage = "Pas assez de majuscule dans le mdp";
                                }
                                else
                                {
                                    int nbchiffre = 0;
                                    for(int i = 0; i<motdepasse2.length();i++)
                                    {
                                        char c = motdepasse2.charAt(i);
                                        if(Character.isDigit(c))
                                        {
                                            nbchiffre++;
                                        }
                                    }
                                    if(nbchiffre < 2)
                                    {
                                        ok =false;
                                        errormassage = "Pas assez de chiffre dans le mdp";
                                    }
                                    else
                                    {
                                        Pattern pattern = Pattern.compile("[^a-zA-Z0-9]"); //compilation du string dans un objet parterne dans lequel on peu faire des recherche
                                        Matcher matcher = pattern.matcher(motdepasse2); // permet d'effectuer des recherche dans une objet parterne
                                        boolean caracspeciaux = matcher.find();
                                        if(caracspeciaux == false)
                                        {
                                            ok = false;
                                            errormassage = "Un caractère spécial minimal requis";
                                        }
                                        if(motdepasse2.contains("||")) //je met a la fin pour qu'il n'y a pas de problème avec la gesiton des majuscules
                                        {
                                            motdepasse2 = motdepasse2.replace("||", "DEUXBARRES");
                                        }
                                        if(motdepasse2.contains("#"))
                                        {
                                            motdepasse2 = motdepasse2.replace("#", "SHARP");
                                        }
                                    }
                                }
                            }

                            //mot de passe correcte
                            if(ok == true)
                            {
                                //écriture dans le fichier
                                properties.setProperty("identifiant" + identifiant, login + "||" + motdepasse2);

                                try (OutputStream outputStream = new FileOutputStream(filepathPilote))
                                {
                                    // Sauvegarder les modifications dans le fichier
                                    properties.store(outputStream, null);
                                    jop.showMessageDialog(null, "Mot de passe modifié", "Information", jop.INFORMATION_MESSAGE);
                                    jop.setVisible(true);
                                    resetMDP.dispose();
                                } catch (IOException exc)
                                {
                                    jop.showMessageDialog(null, "Erreur dans l'écriture du fichier", "Erreur", jop.ERROR_MESSAGE);
                                    jop.setVisible(true);
                                    exc.printStackTrace();
                                }
                            }
                            else
                            {
                                jop.showMessageDialog(null, errormassage, "Erreur", jop.ERROR_MESSAGE);
                                jop.setVisible(true);
                            }
                        }
                        else
                        {
                            jop.showMessageDialog(null, "Erreur dans la saisie du mot de passe", "Erreur", jop.ERROR_MESSAGE);
                            jop.setVisible(true);
                        }
                    }
                    else
                    {
                        jop.showMessageDialog(null, "Erreur, mot de passe obsolète", "Erreur", jop.ERROR_MESSAGE);
                        jop.setVisible(true);
                    }

                } catch (IOException exception)
                {
                    jop.showMessageDialog(null, "Erreur de lecture de fichier", "Erreur", jop.ERROR_MESSAGE);
                    jop.setVisible(true);
                    exception.printStackTrace();
                }
            }
        }
    }
}
