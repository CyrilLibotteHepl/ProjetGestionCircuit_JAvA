package Controleurs;

import Model.ActivitePackage.ClassCourse;
import View.LanCourse;

import javax.swing.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.util.Random;


public class Controleurlancementcourses
{
    private LanCourse lcourse;
    private ClassCourse donneecourse;
    private String oldvalue;
    private String newvalue;

    String total=null;

    //private ArrayList<String> listvt;
    protected PropertyChangeSupport pcs;
    //tirage des pilote

    public Controleurlancementcourses(LanCourse lanCourse, ClassCourse data) throws IOException {
        this.lcourse = lanCourse;
        this.donneecourse=data;

        pcs = new PropertyChangeSupport(this);

        Random random = new Random();
        int dureeMin = 5000; // 5 secondes en millisecondes
        int dureeMax = 10000; // 10 secondes en millisecondes

        int dureeAleatoire = random.nextInt(dureeMax - dureeMin + 1) + dureeMin;

        try {
            Thread.sleep(dureeAleatoire);
        } catch (InterruptedException exc) {
            exc.printStackTrace();
        }

        JOptionPane.showMessageDialog(null, "La course c'est terminée correctement.", "Infomation", JOptionPane.INFORMATION_MESSAGE);
    }

    public void ModifierAffichage()
    {
        oldvalue = lcourse.getJLabelNomCourse().getText();
        newvalue = donneecourse.getNom();
        pcs.firePropertyChange("Nom",oldvalue,newvalue);

        oldvalue = lcourse.getJLabelitineraire().getText();
        newvalue = ""+donneecourse.getItineraire();
        pcs.firePropertyChange("Itineraire",oldvalue,newvalue);

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

