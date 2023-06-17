package View;

import Controleurs.ClassemainControleur;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class Classemain extends JFrame implements PropertyChangeListener {
    private JPanel MainPannel;
    private JLabel labelPlaceGradin;
    private JLabel labelDR;
    private JLabel labelbatheme;
    private JLabel labelreservation;
    private JLabel labeltrackday;
    private JLabel labelvehiculeloue;
    private JLabel labelReserveBtm;
    private JLabel labelhost;
    private JLabel labelhostcourant;
    private JList listVoiture;
    private JLabel labelListeVoiture;
    private JButton ButtonSelectionner;
    private JButton ButtonReservation;
    private JLabel labelVehiculeLoue;
    private JLabel labelDateReserv;
    private JLabel labelPgradin;
    private JComboBox comboBoxReservationBtm;
    private JComboBox comboBoxReservTrackDay;
    private JButton buttonLanceCourse;
    private JLabel labelNbTour;

    private JMenuBar JMenuBar1;

    private JMenu JMenu1;
    private JMenu JMenu2;
    private JMenu JMenu3;
    private JMenuItem JMenuItem1;
    private JMenuItem JMenuItem2;
    private JMenuItem JMenuItem3;
    private JMenuItem JMenuItem4;
    private JMenuItem JMenuItem5;
    private JMenuItem JMenuItem6;
    private JMenuItem JMenuItem7;
    private JMenuItem JMenuItem8;
    private JMenuItem JMenuItem9;


    public JMenuItem getJMenuItem1() {
        return JMenuItem1;
    }
    public JMenuItem getJMenuItem2()
    {
        return JMenuItem2;
    }
    public JMenuItem getJMenuItem3()
    {
        return JMenuItem3;
    }
    public JMenuItem getJMenuItem4()
    {
        return JMenuItem4;
    }
    public JMenuItem getJMenuItem5()
    {
        return JMenuItem5;
    }
    public JMenuItem getJMenuItem6()
    {
        return JMenuItem6;
    }
    public JMenuItem getJMenuItem7(){
        return JMenuItem7;
    }
    public JMenuItem getJMenuItem8()
    {
        return JMenuItem8;
    }
    public JMenuItem getJMenuItem9()
    {
        return JMenuItem9;
    }
    public JMenu getJMenu3() {
        return JMenu3;
    }
    public JList getListVoiture() {
        return listVoiture;
    }
    public JButton getButtonSelectionner(){
        return ButtonSelectionner;
    }
    public JButton getButtonReservation() {
        return ButtonReservation;
    }
    public JLabel getLabelPgradin() {
        return labelPgradin;
    }
    public JLabel getLabelDateReserv() {
        return labelDateReserv;
    }
    public JLabel getLabelVehiculeLoue() {
        return labelVehiculeLoue;
    }
    public JLabel getLabelNbTour() {
        return labelNbTour;
    }
    public JComboBox getComboBoxReservationBtm() {
        return comboBoxReservationBtm;
    }
    public JComboBox getComboBoxReservTrackDay()
    {
        return comboBoxReservTrackDay;
    }

    public JButton getButtonLanceCourse() {return buttonLanceCourse;}

    public Classemain ()
    {
        setContentPane(MainPannel);

        JMenuBar1 = new JMenuBar();
        setJMenuBar(JMenuBar1);
        JMenu1 = new JMenu();
        JMenu2 = new JMenu();
        JMenu3 = new JMenu();

        JMenuItem1 = new JMenuItem();
        JMenuItem2 = new JMenuItem();
        JMenuItem3 = new JMenuItem();
        JMenuItem4 = new JMenuItem();
        JMenuItem5 = new JMenuItem();
        JMenuItem6 = new JMenuItem();
        JMenuItem7 = new JMenuItem();
        JMenuItem8 = new JMenuItem();
        JMenuItem9 = new JMenuItem();

        JMenuBar1.add(JMenu1);
        JMenuBar1.add(JMenu2);
        JMenuBar1.add(JMenu3);

        JMenu1.setText("Utilisateur");
        JMenuItem1.setText("Se connecter");
        JMenuItem2.setText("Se déconnecter");
        JMenuItem3.setText("Admin");
        JMenuItem4.setText("Reset mot de passe");

        JMenu2.setText("Créer un compte");
        JMenuItem5.setText("Client");
        JMenuItem6.setText("Pilote");

        JMenu3.setText("Activite");
        JMenuItem7.setText("Course");
        JMenuItem8.setText("Batheme");
        JMenuItem9.setText("Trackday");


        JMenu1.add(JMenuItem1);
        JMenu1.addSeparator();
        JMenu1.add(JMenuItem2);
        JMenu1.addSeparator();
        JMenu1.add(JMenuItem3);
        JMenu1.addSeparator();
        JMenu1.add(JMenuItem4);

        JMenu2.add(JMenuItem5);
        JMenu2.addSeparator();
        JMenu2.add(JMenuItem6);

        JMenu3.add(JMenuItem7);
        JMenu3.addSeparator();
        JMenu3.add(JMenuItem8);
        JMenu3.addSeparator();
        JMenu3.add(JMenuItem9);

        JMenu3.setEnabled(false);
        buttonLanceCourse.setEnabled(false);
    }
    public static void main(String[] args)
    {
        Classemain cm = new Classemain();
        cm.setSize(600, 400);
        cm.setVisible(true);
        cm.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    public JLabel getChamphote() {return labelhostcourant;}
    public JLabel getLabelhost() { return labelhost; }

    public void setControleurMain(ClassemainControleur classemainControleur)
    {
        JMenuItem1.addActionListener(classemainControleur);
        JMenuItem2.addActionListener(classemainControleur);
        JMenuItem3.addActionListener(classemainControleur);
        JMenuItem4.addActionListener(classemainControleur);
        JMenuItem5.addActionListener(classemainControleur);
        JMenuItem6.addActionListener(classemainControleur);
        this.addWindowListener(new WindowAdapter() { //je ne sais pas si c'est possible de faire ça en mvc ?
                                   @Override
                                   public void windowClosing(WindowEvent e) {
                                       System.exit(0);
                                   }
                               }
        );
        JMenuItem7.addActionListener(classemainControleur);
        JMenuItem8.addActionListener(classemainControleur);
        JMenuItem9.addActionListener(classemainControleur);
        ButtonSelectionner.addActionListener(classemainControleur);
        ButtonReservation.addActionListener(classemainControleur);
        buttonLanceCourse.addActionListener(classemainControleur);
    }

    public void propertyChange(PropertyChangeEvent evt)
    {
        //gestion de l'affichage

        if("Place".equals(evt.getPropertyName()))
        {
            getLabelPgradin().setText((String) evt.getNewValue());
        }
        if("DateReserv".equals(evt.getPropertyName()))
        {
            getLabelDateReserv().setText((String) evt.getNewValue());
        }
    }
    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}

