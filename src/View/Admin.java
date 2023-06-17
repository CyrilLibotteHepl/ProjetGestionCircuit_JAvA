package View;

import Controleurs.AdminControleur;

import javax.swing.*;

public class Admin extends JDialog{
    private JButton terminerButton;
    private JPanel fieldMDP;
    private JLabel labelTitre;
    private JPasswordField passwordField;

    public JButton getTerminerButton() {
        return terminerButton;
    }

    public JPasswordField getpasswordField() {
        return passwordField;
    }

    public static void main(String[] args)
    {
        Admin adm = new Admin();
        adm.setVisible(true);
    }
    public Admin()
    {
        setTitle("ConnexionClient admin");
        setSize(300, 150);
        setModal(true);
        setContentPane(fieldMDP);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
    }
    public void setAdminControleur(AdminControleur adminControleur)
    {
        terminerButton.addActionListener(adminControleur);
    }

}
