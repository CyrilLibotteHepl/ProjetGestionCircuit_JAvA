package View;

import Controleurs.ClassSingleton;
import Controleurs.ControleurReservationBatheme;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

public class ReservationBatheme extends JDialog{
    private JTextField textFieldNbTour;
    private JList listPilote;
    private JButton buttonReserver;
    private JPanel mainPanel;
    private JLabel labeltarif;
    private JLabel labelTarif;
    private JLabel labelHeure;
    private JComboBox comboBoxHeure;
    private ArrayList<String> list;
    public JTextField getTextFieldNbTour() {
        return textFieldNbTour;
    }
    public JList getListPilote() {
        return listPilote;
    }
    public JButton getButtonReserver() {
        return buttonReserver;
    }
    public ArrayList<String> getArrayListPilote() {
        return list;
    }
    public JLabel getLabelTarif() {
        return labelTarif;
    }
    public JComboBox getComboBoxHeure() {
        return comboBoxHeure;
    }

    public ReservationBatheme()
    {
        setTitle("Nouvelle réservation batheme");
        setModal(true);
        setSize(400, 500);
        setContentPane(mainPanel);
        setDefaultCloseOperation(HIDE_ON_CLOSE);

        ClassSingleton singleton = ClassSingleton.getInstance();

        String filepath = singleton.getFilepathConnexionIntervenant() + "\\Pilote.properties";

        list = new ArrayList<>();
        Properties properties = new Properties();

        try (FileInputStream fileInputStream = new FileInputStream(filepath)) {
            properties.load(fileInputStream);

            String nom = "";
            String prenom = "";
            String licence = "";
            for (int i = 0; i<singleton.getNbPilote() ;i++)
            {
                String unpilote = "";
                for (String key : properties.stringPropertyNames())
                {
                    String value = properties.getProperty(key);
                    if (key.startsWith("nom" + i)) {
                        nom = value;
                    } else if (key.contains("prenom" + i)) {
                        prenom = value;
                    } else if (key.contains("licence" + i)){
                        licence = value;
                    }
                    key = "";

                }
                unpilote = "" + i + "; Nom : " + nom + "; Prenom : " + prenom + "; Licence : " + licence;
                list.add(unpilote);
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        comboBoxHeure.setSelectedIndex(0);
        getListPilote().setListData(list.toArray()); //affichage
    }

    public void setControleurReservationBatheme(ControleurReservationBatheme controleurReservationBatheme)
    {
        buttonReserver.addActionListener(controleurReservationBatheme);
        textFieldNbTour.addActionListener(controleurReservationBatheme);
    }

    public static void main(String[] args)
    {
        ReservationBatheme reservationBatheme = new ReservationBatheme();
        reservationBatheme.setVisible(true);
    }
}
