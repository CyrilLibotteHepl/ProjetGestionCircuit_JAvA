package Controleurs;


import Model.UserPackage.ClassVoiture;
import View.Voiture;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class VoitureControleur implements ActionListener
{
    private Voiture voiture;
    private String filepath;
    private int nbVoiture;
    private ClassSingleton singleton;
    private SignalToPilote listener;
    public VoitureControleur(Voiture voit, String fp)
    {
        voiture = voit;
        filepath = fp; //"C:\\Users\\cycro\\Desktop\\JAVApj\\Connexion\\Voiture\\Voiture.dat"
        singleton = ClassSingleton.getInstance();
        nbVoiture = singleton.getNbVoiture();
    }

    //gestion événement
    public void setMajListVoiture(SignalToPilote unmajdelaliste)
    {
        this.listener = unmajdelaliste;
    } //méthode qui permet de s'abonné a l'event

    public void MettreAJourListVoiture()
    {
        if(listener != null)
            this.listener.EnvoieModifactionListVoiture();
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == voiture.getTerminerButton())
        {
            boolean ok = true;
            String errormessage = "";
            String marque = voiture.getTextFieldMarque().getText();
            String modele = voiture.getTextFieldModele().getText();
            String categorie = (String)voiture.getComboBoxCategorie().getSelectedItem();
            String puissancestr = voiture.getTextFieldPuissance().getText();
            String cylindreestr = voiture.getTextFieldCylindree().getText();
            String carburant = voiture.getTextFieldCarburant().getText();
            String poidsstr = voiture.getTextFieldPoids().getText();
            int puissance = 0, cylindree = 0, poids = 0;
            int etat = 0;
            if (voiture.getCircuitRadioButton().isSelected())
            {
                etat = 1;
            }
            else if (voiture.getStandRadioButton().isSelected())
            {
                etat = 2;
            }
            else if(voiture.getReparationRadioButton().isSelected())
            {
                etat = 3;
            }
            else if (voiture.getAutreRadioButton().isSelected())
            {
                etat = 4;
            }

            JOptionPane jop = new JOptionPane();
            if(marque.length() == 0 || modele.length() == 0 || categorie.length() == 0 || puissancestr.length() == 0 || cylindreestr.length() == 0 || carburant.length() == 0 || poidsstr.length() == 0 || etat == 0)
            {
                ok = false;
                errormessage = "Donnée(s) manquante(s)";
            }
            else
            {
                try {
                    puissance = Integer.parseInt(puissancestr);
                } catch (NumberFormatException exception) {
                    ok = false;
                    errormessage = "La puissance ne doit comporter que des chiffres";
                }
                try {
                    cylindree = Integer.parseInt(cylindreestr);
                } catch (NumberFormatException exception) {
                    ok = false;
                    errormessage = "La cylindrée ne doit comporter que des chiffres (cc)";
                }
                try {
                    poids = Integer.parseInt(poidsstr);
                } catch (NumberFormatException exception)
                {
                    ok = false;
                    errormessage = "Le poids ne doit comporter que des chiffres";
                }

                if(ok)
                {
                    //int id, String Marque, String Modele, String Categorie, int P, int Cylindree, String Carburant, int Poids, int Etat
                    nbVoiture++;

                    // testAdmin : déterminer si la personne qui encode la voiture est un admin ou non => pour savoir si la voiture appartient au circuit ou nn
                    ClassVoiture nouvellevoiture = new ClassVoiture(nbVoiture, marque, modele, categorie, puissance, cylindree, carburant, poids, etat, singleton.testAdmin(), false);
                    //écriture dans le fichier
                    try {
                        File file = new File(filepath);
                        FileOutputStream fileOut = new FileOutputStream(filepath, true);
                        if(file.length() == 0) //écriture avec entête
                        {
                            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);

                            objectOut.writeObject(nouvellevoiture);

                            objectOut.close();
                        }
                        else //écriture sans entête
                        {
                            ObjectOutPutStreamPersonnalise objectOut1 = new ObjectOutPutStreamPersonnalise(fileOut);

                            objectOut1.writeObject(nouvellevoiture);

                            objectOut1.close();
                        }

                        fileOut.close();

                        jop.showMessageDialog(null, "Sauvegarde la voiture réussie", "Information", jop.INFORMATION_MESSAGE);
                        jop.setVisible(true);
                        singleton.setNbVoiture(nbVoiture);
                        singleton.getListVoiture().add(nouvellevoiture);
                        MettreAJourListVoiture();
                        voiture.dispose();

                    } catch (IOException exc) {
                        jop.showMessageDialog(null, "Une erreur s'est produite lors de la sérialisation de l'objet", "Erreur", jop.ERROR_MESSAGE);
                        jop.setVisible(true);
                    }
                }
            }
            if(!ok)
            {
                jop.showMessageDialog(null, errormessage, "Erreur", jop.ERROR_MESSAGE);
                jop.setVisible(true);
            }
        }
        else if (e.getSource() == voiture.getCircuitRadioButton())
        {
            voiture.getCircuitRadioButton().setSelected(true);
            voiture.getReparationRadioButton().setSelected(false);
            voiture.getStandRadioButton().setSelected(false);
            voiture.getAutreRadioButton().setSelected(false);
        }
        else if (e.getSource() == voiture.getReparationRadioButton())
        {
            voiture.getCircuitRadioButton().setSelected(false);
            voiture.getReparationRadioButton().setSelected(true);
            voiture.getStandRadioButton().setSelected(false);
            voiture.getAutreRadioButton().setSelected(false);
        }
        else if (e.getSource() == voiture.getStandRadioButton())
        {
            voiture.getCircuitRadioButton().setSelected(false);
            voiture.getReparationRadioButton().setSelected(false);
            voiture.getStandRadioButton().setSelected(true);
            voiture.getAutreRadioButton().setSelected(false);
        }
        else if (e.getSource() == voiture.getAutreRadioButton())
        {
            voiture.getCircuitRadioButton().setSelected(false);
            voiture.getReparationRadioButton().setSelected(false);
            voiture.getStandRadioButton().setSelected(false);
            voiture.getAutreRadioButton().setSelected(true);
        }
        else if(e.getSource() == voiture.getComboBoxCategorie())
        {

            String categorie = (String) voiture.getComboBoxCategorie().getSelectedItem();
            ImageIcon imageIcon;
            switch (categorie)
            {
                case "Classique":
                    imageIcon = new ImageIcon("C:\\Users\\cycro\\Desktop\\JAVApj\\Image\\Voiture\\classique.jpg");
                    voiture.getLabelImage().setIcon(imageIcon);
                    voiture.getImage().add(voiture.getLabelImage());
                    voiture.getImage().revalidate();
                    voiture.getImage().repaint();
                    break;
                case "Super berlines":
                    imageIcon = new ImageIcon("C:\\Users\\cycro\\Desktop\\JAVApj\\Image\\Voiture\\Super_berlines.jpg");
                    voiture.getLabelImage().setIcon(imageIcon);
                    voiture.getImage().add(voiture.getLabelImage());
                    voiture.getImage().revalidate();
                    voiture.getImage().repaint();
                    break;
                case "Joujoux de circuit":
                    imageIcon = new ImageIcon("C:\\Users\\cycro\\Desktop\\JAVApj\\Image\\Voiture\\Joujoux_de_circuit.jpg");
                    voiture.getLabelImage().setIcon(imageIcon);
                    voiture.getImage().add(voiture.getLabelImage());
                    voiture.getImage().revalidate();
                    voiture.getImage().repaint();
                    break;
                case "Monstres de rallye":
                    imageIcon = new ImageIcon("C:\\Users\\cycro\\Desktop\\JAVApj\\Image\\Voiture\\Monstres_de_rallye.jpg");
                    voiture.getLabelImage().setIcon(imageIcon);
                    voiture.getImage().add(voiture.getLabelImage());
                    voiture.getImage().revalidate();
                    voiture.getImage().repaint();
                    break;
                case "Supercars":
                    imageIcon = new ImageIcon("C:\\Users\\cycro\\Desktop\\JAVApj\\Image\\Voiture\\Supercars.jpg");
                    voiture.getLabelImage().setIcon(imageIcon);
                    voiture.getImage().add(voiture.getLabelImage());
                    voiture.getImage().revalidate();
                    voiture.getImage().repaint();
                    break;
                case "GT":
                    imageIcon = new ImageIcon("C:\\Users\\cycro\\Desktop\\JAVApj\\Image\\Voiture\\GT.jpg");
                    voiture.getLabelImage().setIcon(imageIcon);
                    voiture.getImage().add(voiture.getLabelImage());
                    voiture.getImage().revalidate();
                    voiture.getImage().repaint();
                    break;
                case "Off-road":
                    imageIcon = new ImageIcon("C:\\Users\\cycro\\Desktop\\JAVApj\\Image\\Voiture\\Off-road.jpg");
                    voiture.getLabelImage().setIcon(imageIcon);
                    voiture.getImage().add(voiture.getLabelImage());
                    voiture.getImage().revalidate();
                    voiture.getImage().repaint();
                    break;
                case "Véhicules tout-terrain":
                    imageIcon = new ImageIcon("C:\\Users\\cycro\\Desktop\\JAVApj\\Image\\Voiture\\Véhicules_tout-terrain.jpg");
                    voiture.getLabelImage().setIcon(imageIcon);
                    voiture.getImage().add(voiture.getLabelImage());
                    voiture.getImage().revalidate();
                    voiture.getImage().repaint();
                    break;
                case "Muscle cars":
                    imageIcon = new ImageIcon("C:\\Users\\cycro\\Desktop\\JAVApj\\Image\\Voiture\\Muscle_cars.jpg");
                    voiture.getLabelImage().setIcon(imageIcon);
                    voiture.getImage().add(voiture.getLabelImage());
                    voiture.getImage().revalidate();
                    voiture.getImage().repaint();
                    break;
                case "Hot rods":
                    imageIcon = new ImageIcon("C:\\Users\\cycro\\Desktop\\JAVApj\\Image\\Voiture\\Hot_rods.jpg");
                    voiture.getLabelImage().setIcon(imageIcon);
                    voiture.getImage().add(voiture.getLabelImage());
                    voiture.getImage().revalidate();
                    voiture.getImage().repaint();
                    break;
                case "Hypercars":
                    imageIcon = new ImageIcon("C:\\Users\\cycro\\Desktop\\JAVApj\\Image\\Voiture\\Hypercars.jpg");
                    voiture.getLabelImage().setIcon(imageIcon);
                    voiture.getImage().add(voiture.getLabelImage());
                    voiture.getImage().revalidate();
                    voiture.getImage().repaint();
                    break;
                case "Voitures électriques":
                    imageIcon = new ImageIcon("C:\\Users\\cycro\\Desktop\\JAVApj\\Image\\Voiture\\Voitures_electriques.jpg");
                    voiture.getLabelImage().setIcon(imageIcon);
                    voiture.getImage().add(voiture.getLabelImage());
                    voiture.getImage().revalidate();
                    voiture.getImage().repaint();
                    break;
                case "Supersaloon":
                    imageIcon = new ImageIcon("C:\\Users\\cycro\\Desktop\\JAVApj\\Image\\Voiture\\Supersaloon.jpg");
                    voiture.getLabelImage().setIcon(imageIcon);
                    voiture.getImage().add(voiture.getLabelImage());
                    voiture.getImage().revalidate();
                    voiture.getImage().repaint();
                    break;
                case "SUV":
                    imageIcon = new ImageIcon("C:\\Users\\cycro\\Desktop\\JAVApj\\Image\\Voiture\\SUV.jpg");
                    voiture.getLabelImage().setIcon(imageIcon);
                    voiture.getImage().add(voiture.getLabelImage());
                    voiture.getImage().revalidate();
                    voiture.getImage().repaint();
                    break;
                case "Camion":
                    imageIcon = new ImageIcon("C:\\Users\\cycro\\Desktop\\JAVApj\\Image\\Voiture\\Camion.jpg");
                    voiture.getLabelImage().setIcon(imageIcon);
                    voiture.getImage().add(voiture.getLabelImage());
                    voiture.getImage().revalidate();
                    voiture.getImage().repaint();
                    break;
                case "Kart":
                    imageIcon = new ImageIcon("C:\\Users\\cycro\\Desktop\\JAVApj\\Image\\Voiture\\Kart.jpg");
                    voiture.getLabelImage().setIcon(imageIcon);
                    voiture.getImage().add(voiture.getLabelImage());
                    voiture.getImage().revalidate();
                    voiture.getImage().repaint();
                    break;
                case "Autre":
                    imageIcon = new ImageIcon("C:\\Users\\cycro\\Desktop\\JAVApj\\Image\\Voiture\\Autre.jpg");
                    voiture.getLabelImage().setIcon(imageIcon);
                    voiture.getImage().add(voiture.getLabelImage());
                    voiture.getImage().revalidate();
                    voiture.getImage().repaint();
                    break;
                default:
                    imageIcon = new ImageIcon("C:\\Users\\cycro\\Desktop\\JAVApj\\Image\\Voiture\\rien.jpg");
                    voiture.getLabelImage().setIcon(imageIcon);
                    voiture.getImage().add(voiture.getLabelImage());
                    voiture.getImage().revalidate();
                    voiture.getImage().repaint();
                    break;
            }
        }
    }
}
