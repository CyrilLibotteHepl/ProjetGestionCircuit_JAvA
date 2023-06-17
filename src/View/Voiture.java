package View;

import Controleurs.VoitureControleur;

import javax.swing.*;
import java.awt.*;

public class Voiture extends JDialog{

    private JTextField textFieldMarque;
    private JTextField textFieldModele;
    private JTextField textFieldPuissance;
    private JTextField textFieldCarburant;
    private JComboBox comboBoxCategorie;
    private JTextField textFieldPoids;
    private JRadioButton circuitRadioButton;
    private JRadioButton standRadioButton;
    private JRadioButton reparationRadioButton;
    private JRadioButton autreRadioButton;
    private JButton terminerButton;
    private JPanel Image;
    private JPanel mainPanel;
    private JTextField textFieldCylindree;
    private JLabel labelImage;


    public JTextField getTextFieldMarque() {
        return textFieldMarque;
    }

    public JTextField getTextFieldModele() {
        return textFieldModele;
    }

    public JTextField getTextFieldPuissance() {
        return textFieldPuissance;
    }

    public JTextField getTextFieldCarburant() {
        return textFieldCarburant;
    }

    public JComboBox getComboBoxCategorie() {
        return comboBoxCategorie;
    }

    public JTextField getTextFieldPoids() {
        return textFieldPoids;
    }

    public JRadioButton getCircuitRadioButton() {
        return circuitRadioButton;
    }

    public JRadioButton getStandRadioButton() {
        return standRadioButton;
    }

    public JRadioButton getReparationRadioButton() {
        return reparationRadioButton;
    }

    public JButton getTerminerButton() {
        return terminerButton;
    }

    public JTextField getTextFieldCylindree() {
        return textFieldCylindree;
    }

    public void setImage(JPanel image) {
        Image = image;
    }

    public JRadioButton getAutreRadioButton() {
        return autreRadioButton;
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

    public static void main(String[] args)
    {
        Voiture vt = new Voiture();
        vt.setVisible(true);
    }

    public Voiture()
    {
        setTitle("Nouvelle voiture");
        setModal(true);
        setSize(550, 500);
        setContentPane(mainPanel);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        ImageIcon imageIcon = new ImageIcon("C:\\Users\\cycro\\Desktop\\JAVApj\\Image\\Voiture\\rien.jpg");
        labelImage = new JLabel(imageIcon);
        Image.setLayout(new BorderLayout());
        Image.add(labelImage);
    }
    public void setVoitureControleur(VoitureControleur voitureControleur)
    {
        terminerButton.addActionListener(voitureControleur);
        circuitRadioButton.addActionListener(voitureControleur);
        standRadioButton.addActionListener(voitureControleur);
        reparationRadioButton.addActionListener(voitureControleur);
        autreRadioButton.addActionListener(voitureControleur);
        comboBoxCategorie.addActionListener(voitureControleur);
    }
}
