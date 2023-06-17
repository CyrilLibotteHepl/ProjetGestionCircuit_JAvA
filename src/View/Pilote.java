package View;

import Controleurs.PiloteControleur;

import javax.swing.*;

public class Pilote extends JDialog {

    private JPanel mainPanel;
    private JComboBox comboBoxSex;
    private JButton encoderUneVoitureButton;
    private JButton terminerButton;
    private JTextField textFieldNom;
    private JTextField textFieldPrenom;
    private JTextField textFieldLogin;
    private JTextField textFieldCategorie;
    private JTextField textFieldLicence;
    private JTextField textFieldtel;
    private JCheckBox experianceCheckBox;
    private JPasswordField passwordField;
    private JComboBox comboBoxJour;
    private JComboBox comboBoxMoi;
    private JComboBox comboBoxAnnee;


    public JComboBox getComboBoxSex() {
        return comboBoxSex;
    }

    public JButton getEncoderUneVoitureButton() {
        return encoderUneVoitureButton;
    }

    public JButton getTerminerButton() {
        return terminerButton;
    }

    public JTextField getTextFieldNom() {
        return textFieldNom;
    }

    public JTextField getTextFieldPrenom() {
        return textFieldPrenom;
    }

    public JTextField getTextFieldLogin() {
        return textFieldLogin;
    }

    public JTextField getTextFieldCategorie() {
        return textFieldCategorie;
    }

    public JTextField getTextFieldLicence() {
        return textFieldLicence;
    }

    public JTextField getTextFieldtel() {
        return textFieldtel;
    }

    public JCheckBox getExperianceCheckBox() {
        return experianceCheckBox;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public JComboBox getComboBoxJour() {
        return comboBoxJour;
    }

    public JComboBox getComboBoxMoi() {
        return comboBoxMoi;
    }

    public JComboBox getComboBoxAnnee() {
        return comboBoxAnnee;
    }

    public static void main(String[] args)
    {
        Pilote plt = new Pilote();
        plt.setVisible(true);
    }
    public Pilote()
    {
        setTitle("Nouveau pilote");
        setModal(true);
        setSize(600, 500);
        setContentPane(mainPanel);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        textFieldCategorie.setEnabled(false);
        this.getComboBoxAnnee().setSelectedItem(1);
    }
    public void setPiloteControleur(PiloteControleur piloteControleur)
    {
        encoderUneVoitureButton.addActionListener(piloteControleur);
        terminerButton.addActionListener(piloteControleur);
        experianceCheckBox.addActionListener(piloteControleur);
        comboBoxMoi.addActionListener(piloteControleur);
    }

}
