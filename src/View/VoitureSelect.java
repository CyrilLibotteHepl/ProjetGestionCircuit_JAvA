package View;

import Controleurs.ClassSingleton;
import Controleurs.VoitureSelectControleur;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;


public class VoitureSelect extends JDialog implements PropertyChangeListener{

    private JLabel labelMarque;
    private JLabel labelModele;
    private JLabel labelPuissance;
    private JLabel labelCarburant;
    private JLabel labelCategorie;
    private JLabel labelPoids;
    private JLabel labelEtat;
    private JPanel imagePanel;
    private JLabel labelImage;
    private JPanel mainpanel;
    private JPanel Image;
    private JLabel labelCylindree;
    private JPanel mainpanelvs;
    private JButton buttonRecuperer;


    public JLabel getLabelMarque() {
        return labelMarque;
    }

    public JLabel getLabelModele() {
        return labelModele;
    }

    public JLabel getLabelPuissance() {
        return labelPuissance;
    }

    public JLabel getLabelCarburant() {
        return labelCarburant;
    }

    public JLabel getLabelCategorie() {
        return labelCategorie;
    }

    public JLabel getLabelCylindree() {
        return labelCylindree;
    }

    public JLabel getLabelPoids() {
        return labelPoids;
    }

    public JLabel getLabelEtat() {
        return labelEtat;
    }

    public void setImage(JPanel image) {
        Image = image;
    }

    public JLabel getLabelImage() {
        return labelImage;
    }
    public void setLabelImage(JLabel labelImage) {
        this.labelImage = labelImage;
    }
    public JPanel getImage() {
        return Image;
    }
    public JButton getButtonRecuperer() {
        return buttonRecuperer;
    }

    public void setLabelMarque(String text) {
        labelMarque.setText(text);
    }

    public void addLabelMarqueListener(PropertyChangeListener listener) {
        labelMarque.addPropertyChangeListener(listener);
    }

    public void setLabelModele(String text) {
        labelModele.setText(text);
    }

    public void addLabelModeleListener(PropertyChangeListener listener) {
        labelModele.addPropertyChangeListener(listener);
    }

    public void setLabelCategorie(String text) {
        labelCategorie.setText(text);
    }

    public void addLabelCategoryListener(PropertyChangeListener listener) {
        labelCategorie.addPropertyChangeListener(listener);
    }

    public void setLabelPuissance(String text) {
        labelPuissance.setText(text);
    }

    public void addLabelPuissanceListener(PropertyChangeListener listener) {
        labelPuissance.addPropertyChangeListener(listener);
    }

    public void setLabelCylindree(String text) {
        labelCylindree.setText(text);
    }

    public void addLabelCylindreeListener(PropertyChangeListener listener) {
        labelCylindree.addPropertyChangeListener(listener);
    }
    public void setLabelCarburant(String text) {
        labelCarburant.setText(text);
    }

    public void addLabelCarburantListener(PropertyChangeListener listener) {
        labelCarburant.addPropertyChangeListener(listener);
    }

    public void setLabelPoids(String text) {
        labelPoids.setText(text);
    }

    public void addLabelPoidsListener(PropertyChangeListener listener) {
        labelPoids.addPropertyChangeListener(listener);
    }

    public void setLabelEtat(String text) {
        labelEtat.setText(text);
    }

    public void addLabelEtatListener(PropertyChangeListener listener) {
        labelEtat.addPropertyChangeListener(listener);
    }



    public static void main(String[] args)
    {
        VoitureSelect vtst = new VoitureSelect();
        vtst.setVisible(true);
    }

    public VoitureSelect()
    {
        setTitle("Voiture sélectionnée");
        setModal(true);
        setSize(350, 450);
        setContentPane(mainpanelvs);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        ImageIcon imageIcon = new ImageIcon("C:\\Users\\cycro\\Desktop\\JAVApj\\Image\\Voiture\\rien.jpg");
        labelImage = new JLabel(imageIcon);
        Image.setLayout(new BorderLayout());
        Image.add(labelImage);

        ClassSingleton singleton = ClassSingleton.getInstance();

        buttonRecuperer.setEnabled(false);
        if(singleton.TestClient())
        {
            if(singleton.getClientconnecte().getadmin())
            {
                buttonRecuperer.setEnabled(true);
            }
        }
        else if (singleton.TestPilote())
        {
            if(singleton.getPiloteconnecte().getadmin())
            {
                buttonRecuperer.setEnabled(true);
            }
        }
    }
    public void setVoitureSelectControleur(VoitureSelectControleur voitureSelectControleur)
    {
        buttonRecuperer.addActionListener(voitureSelectControleur);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt)
    {
        //gestion de l'affichage
        //VoitureSelectControleur voitureSelectControleur = (VoitureSelectControleur) evt.getSource();

        if("Marque".equals(evt.getPropertyName()))
        {
            getLabelMarque().setText((String) evt.getNewValue());
        }

        if("Modele".equals(evt.getPropertyName()))
        {
            getLabelModele().setText((String) evt.getNewValue());
        }

        if("Categorie".equals(evt.getPropertyName()))
        {
            getLabelCategorie().setText((String) evt.getNewValue());
        }

        if("Puissance".equals(evt.getPropertyName()))
        {
            getLabelPuissance().setText((String) evt.getNewValue());
        }

        if("Cylindree".equals(evt.getPropertyName()))
        {
            getLabelCylindree().setText((String) evt.getNewValue());
        }

        if("Carburant".equals(evt.getPropertyName()))
        {
            getLabelCarburant().setText((String) evt.getNewValue());
        }

        if("Poids".equals(evt.getPropertyName()))
        {
            getLabelPoids().setText((String) evt.getNewValue());
        }

        if("Etat".equals(evt.getPropertyName()))
        {
            getLabelEtat().setText((String) evt.getNewValue());
        }
    }
}
