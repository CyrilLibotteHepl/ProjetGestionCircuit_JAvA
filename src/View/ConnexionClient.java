package View;

import Controleurs.ConnexionControleur;

import javax.swing.*;

public class ConnexionClient extends JDialog {
    private JPanel PanelConnexion;
    private JTextField textLogin;
    private JLabel Login;
    private JLabel MotDePasse;
    private JTextField textMotDePasse;
    private JButton connexionButton;
    private JPasswordField passwordFieldMotDePasse;
    private JRadioButton radioButtonClient;
    private JRadioButton radioButtonPilote;


    public static void main(String[] args) {
        ConnexionClient conx = new ConnexionClient();
        conx.setVisible(true);
    }

    public ConnexionClient() {
        setTitle("Connexion");
        setSize(400, 200);
        setModal(true);
        setContentPane(PanelConnexion);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        radioButtonClient.setSelected(true);
    }

    public void setControleurConnexionClient(ConnexionControleur uneconnexion)
    {
        connexionButton.addActionListener(uneconnexion);
        radioButtonClient.addActionListener(uneconnexion);
        radioButtonPilote.addActionListener(uneconnexion);
    }
    public JTextField getTextLogin() {
        return textLogin;
    }
    public JPasswordField getPasswordFieldMotDePasse() {
        return passwordFieldMotDePasse;
    }
    public JButton getConnexionButton() {
        return connexionButton;
    }
    public JRadioButton getRadioButtonClient() {
        return radioButtonClient;
    }
    public JRadioButton getRadioButtonPilote() {
        return radioButtonPilote;
    }
    public void setTextLogin(JTextField textLogin) {
        this.textLogin = textLogin;
    }
    public void setTextMotDePasse(JTextField textMotDePasse) {
        this.textMotDePasse = textMotDePasse;
    }
    public void setPasswordFieldMotDePasse(JPasswordField passwordFieldMotDePasse) {
        this.passwordFieldMotDePasse = passwordFieldMotDePasse;
    }

}