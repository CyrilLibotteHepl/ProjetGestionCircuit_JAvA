package View;

import Controleurs.ClassSingleton;
import Controleurs.ControleurReservationTrackday;
import Model.UserPackage.ClassVoiture;

import javax.swing.*;
import java.util.ArrayList;

public class ReservationTrackday extends JDialog{
    private JCheckBox louerUneVoitureCheckBox;
    private JLabel labelListVoiture;
    private JList listVoiture;
    private JButton buttonReservation;
    private JPanel mainPanel;
    private JScrollPane LeScrollPanel;
    private JLabel labelHeure;
    private JComboBox comboBoxHeure;
    private JLabel labelPrix;

    public JCheckBox getLouerUneVoitureCheckBox() {
        return louerUneVoitureCheckBox;
    }
    public JLabel getLabelListVoiture() {
        return labelListVoiture;
    }
    public JList getListVoiture() {
        return listVoiture;
    }
    public JButton getButtonReservation() {
        return buttonReservation;
    }
    public JScrollPane getLeScrollPanel() {
        return LeScrollPanel;
    }
    public JComboBox getComboBoxHeure() {
        return comboBoxHeure;
    }


    public ReservationTrackday()
    {
        setTitle("Nouvelle réservation batheme");
        setModal(true);
        setSize(400, 500);
        setContentPane(mainPanel);
        setDefaultCloseOperation(HIDE_ON_CLOSE);

        ClassSingleton singleton = ClassSingleton.getInstance();
        ArrayList<ClassVoiture> voitreArrayList = new ArrayList<>();
        for(ClassVoiture unevoiture : singleton.getListVoiture())
        {
            if(unevoiture.isAppartientaucircuit() && !unevoiture.isEstutilise()) //quel appartient au circuit et qu'elle n'est pas utilisé
            {
                voitreArrayList.add(unevoiture);
            }
        }

        listVoiture.setListData(voitreArrayList.toArray());

        comboBoxHeure.setSelectedIndex(0);
        listVoiture.setEnabled(false);
    }

    public void setControleurReservationTrackday(ControleurReservationTrackday controleurReservationTrackday)
    {
        buttonReservation.addActionListener(controleurReservationTrackday);
        louerUneVoitureCheckBox.addActionListener(controleurReservationTrackday);
    }

    public static void main(String[] args)
    {
        ReservationTrackday reservationTrackday = new ReservationTrackday();
        reservationTrackday.setVisible(true);
    }
}
