package Controleurs;

import Model.UserPackage.ClassVoiture;
import View.VoitureSelect;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class VoitureSelectControleur implements ActionListener {
    private VoitureSelect voiture;
    private ClassVoiture donneevoiture;
    private String oldValue;
    private String newValue;
    protected PropertyChangeSupport pcs;
    public VoitureSelectControleur(VoitureSelect Voiture, ClassVoiture data)
    {
        this.voiture = Voiture;
        this.donneevoiture = data;

        pcs = new PropertyChangeSupport(this);
    }

    public void ModificationAffichage()
    {
        oldValue = voiture.getLabelMarque().getText();
        newValue = donneevoiture.getMarque();
        pcs.firePropertyChange("Marque",oldValue,newValue);

        oldValue = voiture.getLabelModele().getText();
        newValue = donneevoiture.getModele();
        pcs.firePropertyChange("Modele",oldValue,newValue);

        oldValue = voiture.getLabelCategorie().getText();
        newValue = donneevoiture.getCategorie();
        gestionImage();
        pcs.firePropertyChange("Categorie",oldValue,newValue);

        oldValue = voiture.getLabelPuissance().getText();
        newValue = "" + donneevoiture.getPuissance();
        pcs.firePropertyChange("Puissance",oldValue,newValue);

        oldValue = voiture.getLabelCylindree().getText();
        newValue = "" + donneevoiture.getCylindree();
        pcs.firePropertyChange("Cylindree",oldValue,newValue);

        oldValue = voiture.getLabelCarburant().getText();
        newValue = "" + donneevoiture.getCarburant();
        pcs.firePropertyChange("Carburant",oldValue,newValue);

        oldValue = voiture.getLabelPoids().getText();
        newValue = "" + donneevoiture.getPoids();
        pcs.firePropertyChange("Poids",oldValue,newValue);

        oldValue = voiture.getLabelEtat().getText();
        int status = donneevoiture.getEtat();
        switch(status)
        {
            case 1: newValue = "Circuit";
                break;
            case 2: newValue = "Stand";
                break;
            case 3: newValue = "Réparation";
                break;
            case 4: newValue = "Autre";
                break;
        }
        pcs.firePropertyChange("Etat",oldValue,newValue);
    }
    public void gestionImage()
    {
        String categorie = donneevoiture.getCategorie();
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

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == voiture.getButtonRecuperer())
        {
            ClassSingleton singleton = ClassSingleton.getInstance();

            //écriture dans le fichier
            if(donneevoiture.isAppartientaucircuit())
            {
                try {
                    File file = new File(singleton.getFilepathConnexion() + "\\Voiture.dat");
                    FileOutputStream fileOut = new FileOutputStream(singleton.getFilepathConnexion() + "\\Voiture.dat");
                    ObjectOutputStream objectOut = null;
                    ObjectOutPutStreamPersonnalise objectOut1 = null;
                    for(ClassVoiture unevoiture : singleton.getListVoiture())
                    {
                        if(unevoiture.equals(donneevoiture))
                        {
                            unevoiture.setEstutilise(false);
                        }
                        if(file.length() == 0) //écriture avec entête
                        {
                            objectOut = new ObjectOutputStream(fileOut);

                            objectOut.writeObject(unevoiture);


                        }
                        else //écriture sans entête
                        {
                            objectOut1 = new ObjectOutPutStreamPersonnalise(fileOut);

                            objectOut1.writeObject(unevoiture);

                        }
                    }
                    fileOut.close();
                    objectOut.close();
                    objectOut1.close();
                    JOptionPane.showMessageDialog(null, "Récupération réussie", "Information", JOptionPane.INFORMATION_MESSAGE);

                } catch (IOException exc) {
                    JOptionPane.showMessageDialog(null, "Une erreur s'est produite lors de la sérialisation de l'objet", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Ne peu pas être récupérer car la voiture n'appartient pas au circuit", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }

    }

    //gestion des proprièter liée
    public void addPropertyChangeListener(PropertyChangeListener l)
    {
        pcs.addPropertyChangeListener(l);
    }
    public void removePropertyChangeListener(PropertyChangeListener l)
    {
        pcs.removePropertyChangeListener(l);
    }

}


