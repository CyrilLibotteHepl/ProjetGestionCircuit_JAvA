package View;

import Controleurs.ClientControleur;

import javax.swing.*;

public class Client extends JDialog {
    public static void main(String[] args)
    {
        Client prs = new Client();
        prs.setVisible(true);
    }
    public Client()
    {
        setTitle("Nouveau client");
        setSize(400, 350);
        setContentPane(mainPanel);
        setModal(true);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        textField4.setEnabled(false);
    }
    public void setControleurClient(ClientControleur controleurClient)
    {
        terminerButton.addActionListener(controleurClient);
        licenceDetenueCheckBox.addActionListener(controleurClient);
    }
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField6;
    private JPasswordField passwordField1;
    private JButton terminerButton;
    private JPanel mainPanel;
    private JCheckBox licenceDetenueCheckBox;
    public JTextField getNom() {
        return textField1;
    }

    public JTextField getPrenom() {
        return textField2;
    }

    public JTextField getLogin() {
        return textField3;
    }

    public JTextField getEmail() {
        return textField6;
    }
    public JTextField getLicence() {return textField4;}
    public JPasswordField getPassword() {
        return passwordField1;
    }

    public JButton getTerminerButton() {
        return terminerButton;
    }
    public JCheckBox getLicenceDetenueCheckBox() { return licenceDetenueCheckBox; }

    public JTextField getTextField4() { return textField4; }
}
