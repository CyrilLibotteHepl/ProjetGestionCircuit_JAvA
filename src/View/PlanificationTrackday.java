package View;

import Controleurs.ClassSingleton;
import Controleurs.ControleurTrackdayplanification;
import Model.ActivitePackage.ClassJour;

import javax.swing.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class PlanificationTrackday extends JDialog{
    private JList listJour;
    private JButton buttonTerminer;
    private JButton buttonPrevius;
    private JButton buttonNext;
    private JLabel labelMoi;
    private JLabel labelAnnee;
    private JPanel mainPanel;

    //getter et setter
    public JList getListJour() {
        return listJour;
    }

    public JButton getButtonTerminer() {
        return buttonTerminer;
    }

    public JButton getButtonPrevius() {
        return buttonPrevius;
    }

    public JButton getButtonNext() {
        return buttonNext;
    }

    public JLabel getLabelMoi() {
        return labelMoi;
    }

    public JLabel getLabelAnnee() {
        return labelAnnee;
    }

    public void setListJour(JList listJour) {
        this.listJour = listJour;
    }

    public void setButtonTerminer(JButton buttonTerminer) {
        this.buttonTerminer = buttonTerminer;
    }

    public void setButtonPrevius(JButton buttonPrevius) {
        this.buttonPrevius = buttonPrevius;
    }

    public void setButtonNext(JButton buttonNext) {
        this.buttonNext = buttonNext;
    }

    public void setLabelMoi(JLabel labelMoi) {
        this.labelMoi = labelMoi;
    }

    public void setLabelAnnee(JLabel labelAnnee) {
        this.labelAnnee = labelAnnee;
    }

    public PlanificationTrackday()
    {
        setTitle("Nouveau trackday");
        setModal(true);
        setSize(400, 500);
        setContentPane(mainPanel);
        setDefaultCloseOperation(HIDE_ON_CLOSE);

        //gestion de la date en fonction de la date courrante

        LocalDate dateCourante = LocalDate.now(); // Obtention de la date courante

        int jourdumoi = dateCourante.getDayOfMonth();
        int mois = dateCourante.getMonthValue(); // Récupération du mois
        int annee = dateCourante.getYear(); // Récupération de l'année


        getLabelAnnee().setText("" + annee);
        String moi = "";
        switch(mois)
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

        getLabelMoi().setText(moi);

        int nbmaxjour = -1;
        switch (mois)
        {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                nbmaxjour = 31;
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                nbmaxjour = 30;
                break;
            case 2:
                int anneeselect = Integer.parseInt(getLabelAnnee().getText());
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
        LocalDate date;
        ArrayList<String> listeJour = new ArrayList<>();
        DefaultListModel<String> list = new DefaultListModel<>();

        ClassSingleton singleton = ClassSingleton.getInstance();

        for(int i = jourdumoi; i<=nbmaxjour; i++)
        {
            date = LocalDate.of(annee, mois, i);
            ClassJour jour = new ClassJour(date, false, false, false); //écriture par défault

            if(singleton.getListJour() != null)
            {
                for(ClassJour jourbis : singleton.getListJour()) //test de si quelque chose est prévu
                {
                    if(jourbis.getJour().equals(jour.getJour()))
                    {
                        jour = jourbis;
                    }
                }
            }

            listeJour.add(jour.toString());
        }
        for (String unjour : listeJour)
        {
            list.addElement(unjour);
        }
        listJour.setModel(list);
    }

    public void setTrackdayControleur(ControleurTrackdayplanification controleurTrackdayplanification)
    {
        buttonPrevius.addActionListener(controleurTrackdayplanification);
        buttonNext.addActionListener(controleurTrackdayplanification);
        buttonTerminer.addActionListener(controleurTrackdayplanification);
    }

    public static void main(String[] args)
    {
        PlanificationTrackday planificationTrackday = new PlanificationTrackday();
        planificationTrackday.setVisible(true);
    }
}
