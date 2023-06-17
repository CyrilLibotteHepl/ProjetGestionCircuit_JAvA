package View;

import Controleurs.CourseControleur;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class Course extends JDialog{

    private JLabel labelItineraire;
    private JComboBox comboBoxItineraire;
    private JTextField textFieldNom;
    private JLabel LibelNom;
    private JPanel mainPanel;
    private JLabel libelCetegorie;
    private JComboBox comboBoxDiscipline;
    private JComboBox comboBoxCategorie;
    private JTextField textFieldDate;
    private JComboBox comboBoxHeure;
    private JButton terminerButton;
    private JTextField textfieldDescription;
    private JLabel labelDescription;
    private JTextField textFieldPrix;
    private JLabel textfieldTarif;
    private JComboBox comboBoxJour;
    private JComboBox comboBoxAnnee;
    private JComboBox comboBoxMoi;
    private JPanel panelImage;
    private JLabel labelImage;
    private boolean Coursefinie;

    public JComboBox getComboBoxItineraire() {
        return comboBoxItineraire;
    }

    public JTextField getTextFieldNom() {
        return textFieldNom;
    }

    public JComboBox getComboBoxDiscipline() {
        return comboBoxDiscipline;
    }

    public JComboBox getComboBoxCategorie() {
        return comboBoxCategorie;
    }

    public JTextField getTextFieldDate() {
        return textFieldDate;
    }

    public JComboBox getComboBoxHeure() {
        return comboBoxHeure;
    }

    public JButton getTerminerButton() {
        return terminerButton;
    }

    public JTextField getTextfieldDescription() {
        return textfieldDescription;
    }

    public JTextField getTextFieldPrix() {
        return textFieldPrix;
    }

    public JComboBox getComboBoxAnnee() {
        return comboBoxAnnee;
    }

    public JComboBox getComboBoxJour() {
        return comboBoxJour;
    }

    public JComboBox getComboBoxMoi() {
        return comboBoxMoi;
    }

    public JLabel getlabelImage() {
        return labelImage;
    }

    public JPanel getPanelImage() {
        return panelImage;
    }

    public void setComboBoxItineraire(JComboBox comboBoxItineraire) {
        this.comboBoxItineraire = comboBoxItineraire;
    }

    public void setTextFieldNom(JTextField textFieldNom) {
        this.textFieldNom = textFieldNom;
    }

    public void setMainPanel(JPanel mainPanel) {
        this.mainPanel = mainPanel;
    }

    public void setComboBoxDiscipline(JComboBox comboBox2) {
        this.comboBoxDiscipline = comboBox2;
    }

    public void setComboBoxCategorie(JComboBox comboBoxCategorie) {
        this.comboBoxCategorie = comboBoxCategorie;
    }

    public void setTextFieldDate(JTextField textField3) {
        this.textFieldDate = textField3;
    }

    public void setComboBoxHeure(JComboBox comboBox4) {
        this.comboBoxHeure = comboBox4;
    }

    public void setTerminerButton(JButton terminerButton) {
        this.terminerButton = terminerButton;
    }

    public void setTextfieldDescription(JTextField textfieldDescription) {
        this.textfieldDescription = textfieldDescription;
    }

    public void setTextFieldPrix(JTextField textFieldPrix) {
        this.textFieldPrix = textFieldPrix;
    }

    public void setComboBoxAnnee(JComboBox comboBoxAnnee) {
        this.comboBoxAnnee = comboBoxAnnee;
    }

    public void setComboBoxJour(JComboBox comboBoxJour) {
        this.comboBoxJour = comboBoxJour;
    }
    public void setComboBoxMoi(JComboBox comboBoxMoi) {
        this.comboBoxMoi = comboBoxMoi;
    }

    public void setPanelImage(JPanel panelImage) {
        this.panelImage = panelImage;
    }

    public void setlabelImage(JLabel labelImage) {
        this.labelImage = labelImage;
    }

    public static void main(String[] args)
    {
        Course crs = new Course();
        crs.setVisible(true);
    }
    public Course()
    {
        setTitle("Nouvelle course");
        setModal(true);
        setSize(400, 500);
        setContentPane(mainPanel);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        ImageIcon imageIcon = new ImageIcon("C:\\Users\\cycro\\Desktop\\JAVApj\\Image\\Circuit\\default.jpg");
        labelImage = new JLabel(imageIcon);
        panelImage.setLayout(new BorderLayout());
        panelImage.add(labelImage);

        //gestion de la date en fonction de la date courrante

        LocalDate dateCourante = LocalDate.now(); // Obtention de la date courante

        int jour = dateCourante.getDayOfMonth(); // Récupération du jour du mois
        int mois = dateCourante.getMonthValue(); // Récupération du mois
        int annee = dateCourante.getYear(); // Récupération de l'année

        for(int i = annee; i<=annee+20; i++)
        {
            getComboBoxAnnee().addItem(Integer.toString(i));
        }
        getComboBoxAnnee().setSelectedIndex(0);

        String moi = "";
        for(int i = mois; i<=12; i++)
        {
            switch(i)
            {
                case 1 : moi = "Janvier";
                        break;
                case 2 : moi = "Février";
                        break;
                case 3 : moi = "Mars";
                    break;
                case 4 : moi = "Avril";
                    break;
                case 5 : moi = "Mai";
                    break;
                case 6 : moi = "Juin";
                    break;
                case 7 : moi = "Juillet";
                    break;
                case 8 : moi = "Août";
                    break;
                case 9 : moi = "Septembre";
                    break;
                case 10 : moi = "Octobre";
                    break;
                case 11 : moi = "Novembre";
                    break;
                case 12 : moi = "Décembre";
                    break;
            }

            getComboBoxMoi().addItem(moi);
        }
        getComboBoxMoi().setSelectedIndex(0);
        String moiselectionner = (String) getComboBoxMoi().getSelectedItem();
        int nbmaxjour = -1;
        switch (moiselectionner)
        {
            case "Janvier":
            case "Mars":
            case "Mai":
            case "Juillet":
            case "Août":
            case "Octobre":
            case "Décembre":
                nbmaxjour = 31;
                break;
            case "Avril":
            case "Juin":
            case "Septembre":
            case "Novembre":
                nbmaxjour = 30;
                break;
            case "Février":
                String strinanntmp = (String) getComboBoxAnnee().getSelectedItem();

                int anneeselect = Integer.parseInt(strinanntmp);
                if((anneeselect % 4 == 0 && anneeselect % 100 != 0) || (anneeselect % 400 == 0))
                {
                    //année bissextil
                    nbmaxjour = 29;
                }
                else
                {
                    nbmaxjour = 28;
                }
                break;
        }
        String jourstr = "";
        for(int i = jour; i<=nbmaxjour; i++)
        {
            jourstr = Integer.toString(i);
            getComboBoxJour().addItem(jourstr);
        }
        getComboBoxJour().setSelectedIndex(0);
    }
    public void setControleurCourse(CourseControleur courseControleur)
    {
        terminerButton.addActionListener(courseControleur);
        comboBoxAnnee.addActionListener(courseControleur);
        comboBoxMoi.addActionListener(courseControleur);
        comboBoxItineraire.addActionListener(courseControleur);
    }
}
