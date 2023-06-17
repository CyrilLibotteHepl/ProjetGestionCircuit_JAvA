package View;

import Controleurs.NewMdpControleur;

import javax.swing.*;


public class ResetMDP extends JDialog
{
    private JLabel labelmdpactu;
    private JPasswordField passwordField;
    private JLabel labelnouveaumdp;
    private JTextField textFieldnmdp1;
    private JLabel labelrptmdp;
    private JTextField textFieldnmdp2;
    private JButton buttonTerminer;
    private JLabel labeltitremdp;
    private JPanel MdpPanel;

    public JPasswordField getPasswordField() {
        return passwordField;
    }
    public JTextField getTextFieldnmdp1() {
        return textFieldnmdp1;
    }
    public JTextField getTextFieldnmdp2() {
        return textFieldnmdp2;
    }
    public JButton getButtonTerminer() {
        return buttonTerminer;
    }

    public static void main(String[] args)
    {
        ResetMDP nwmdp = new ResetMDP();
        nwmdp.setVisible(true);
    }
    public ResetMDP()
    {
        setTitle("ConnexionClient admin");
        setSize(500, 200);
        setModal(true);
        setContentPane(MdpPanel);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
    }
    public void setMotDePasseControleur(NewMdpControleur mdpControleur)
    {
        buttonTerminer.addActionListener(mdpControleur);
    }
}
