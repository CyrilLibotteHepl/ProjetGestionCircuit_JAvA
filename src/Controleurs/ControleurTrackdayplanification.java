package Controleurs;

import Model.ActivitePackage.ClassJour;
import View.PlanificationTrackday;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class ControleurTrackdayplanification implements ActionListener {


    private PlanificationTrackday trackday;
    private String filepathplanning;
    private ClassSingleton singleton;
    private LocalDate DateCourante;
    public ControleurTrackdayplanification(PlanificationTrackday planificationTrackday)
    {
        trackday = planificationTrackday;
        singleton = ClassSingleton.getInstance();
        filepathplanning = singleton.getFilepathPlanning() + "\\Planning.csv"; //"C:\\Users\\cycro\\Desktop\\JAVApj\\Planning\\Planning.csv"

        DateCourante = LocalDate.now(); // Obtention de la date courante
    }
    public void EcritureDesJours(int jourdumoi, int nbjour, int moi, int annee)
    {
        LocalDate date;
        ArrayList<String> listeJour = new ArrayList<>();
        DefaultListModel<String> list = new DefaultListModel<>();

        for(int i = jourdumoi; i<=nbjour; i++)
        {
            date = LocalDate.of(annee, moi, i);
            ClassJour jour = new ClassJour(date, false, false, false); //écriture par défault

            for(ClassJour jourbis : singleton.getListJour()) //test de si quelque chose est prévu
            {
                if(jourbis.getJour().equals(jour.getJour()))
                {
                    jour = jourbis;
                }
            }

            listeJour.add(jour.toString());
        }
        for (String unjour : listeJour)
        {
            list.addElement(unjour);
        }
        trackday.getListJour().setModel(list);
    }

    public int ConvertionMoiEnInt(String moistr)
    {
        int moi = 0;

        switch (moistr)
        {
            case "Janvier": moi = 1;
                break;
            case "Février": moi = 2;
                break;
            case "Mars": moi = 3;
                break;
            case "Avril": moi = 4;
                break;
            case "Mai": moi = 5;
                break;
            case "Juin": moi = 6;
                break;
            case "Juillet": moi = 7;
                break;
            case "Août": moi = 8;
                break;
            case "Septembre": moi = 9;
                break;
            case "Octobre": moi = 10;
                break;
            case "Novembre": moi = 11;
                break;
            case "Décembre": moi = 12;
        }

        return moi;
    }
    public String ConvertionMoiEnFR (int nummoi)
    {
        String moi = "";
        switch(nummoi)
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
        return moi;
    }
    public int CalculeNbJour(String lemoi)
    {
        int nbJour = 0;
        switch (lemoi)
        {
            case "Janvier":
            case "Mars":
            case "Mai":
            case "Juillet":
            case "Août":
            case "Octobre":
            case "Décembre":
                nbJour = 31;
                break;
            case "Avril":
            case "Juin":
            case "Septembre":
            case "Novembre":
                nbJour = 30;
                break;
            case "Février":
                String strinanntmp = trackday.getLabelAnnee().getText();
                int annee = Integer.parseInt(strinanntmp);

                if((annee % 4 == 0 && annee % 100 != 0) || (annee % 400 == 0))
                {
                    //année bissextil
                    nbJour = 29;
                }
                else
                {
                    nbJour = 28;
                }
                break;
        }
        return nbJour;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == trackday.getButtonPrevius())
        {
            int moi = DateCourante.getMonthValue();
            String moistr = trackday.getLabelMoi().getText();
            int moiaffiche = ConvertionMoiEnInt(moistr);
            int anneeaffiche = Integer.parseInt(trackday.getLabelAnnee().getText());

            JOptionPane jop = new JOptionPane();
            if(moi == moiaffiche && anneeaffiche == DateCourante.getYear()) //on ne peu pas faire de moi précédant (impossible car on ne peu réservé quelque chose entérieur a maintentant)
            {
                jop.showMessageDialog(null, "Impossible de prévoir quelque chose pour une date antérieur", "Erreur", jop.ERROR_MESSAGE);
                jop.setVisible(true);
            }
            else //ok
            {
                if(ConvertionMoiEnInt(trackday.getLabelMoi().getText()) == 1) //retour a l'année précédante
                {
                    String tmp = trackday.getLabelAnnee().getText();
                    int annee = Integer.parseInt(tmp);
                    annee--;

                    trackday.getLabelAnnee().setText("" + annee);
                    trackday.getLabelMoi().setText("Décembre");

                    int jourdumoi = 1;
                    if(DateCourante.getMonthValue() == 12 && annee == DateCourante.getYear()) // on est en décembre, donc on n'affiche pas tout les jour (seulement a partir d'aujourd'hui)
                    {
                        jourdumoi = DateCourante.getDayOfMonth();
                    }

                    EcritureDesJours(jourdumoi, 31, 12, annee);
                }
                else //normal
                {
                    //le moi est trouvé
                    int nummoi = ConvertionMoiEnInt(trackday.getLabelMoi().getText());
                    nummoi--;
                    trackday.getLabelMoi().setText(ConvertionMoiEnFR(nummoi));

                    int jourdumoi = 1;
                    if(nummoi == DateCourante.getMonthValue() && Integer.parseInt(trackday.getLabelAnnee().getText()) == DateCourante.getYear())
                    {
                        jourdumoi = DateCourante.getDayOfMonth();
                    }

                    int nbmaxjour = CalculeNbJour(trackday.getLabelMoi().getText());

                    EcritureDesJours(jourdumoi, nbmaxjour, ConvertionMoiEnInt(trackday.getLabelMoi().getText()), Integer.parseInt(trackday.getLabelAnnee().getText()));
                }
            }
        }
        else if(e.getSource() == trackday.getButtonNext())
        {

            if(ConvertionMoiEnInt(trackday.getLabelMoi().getText()) == 12) //année suivante
            {
                String tmp = trackday.getLabelAnnee().getText();
                int annee = Integer.parseInt(tmp);
                annee++;

                trackday.getLabelAnnee().setText("" + annee);
                trackday.getLabelMoi().setText("Janvier");

                int jourdumoi = 1;

                EcritureDesJours(jourdumoi, 31, 1, annee);
            }
            else //normal
            {
                //le moi est trouvé
                int nummoi = ConvertionMoiEnInt(trackday.getLabelMoi().getText());
                nummoi++;
                trackday.getLabelMoi().setText(ConvertionMoiEnFR(nummoi));

                int jourdumoi = 1;

                int nbmaxjour = CalculeNbJour(trackday.getLabelMoi().getText());

                EcritureDesJours(jourdumoi, nbmaxjour, ConvertionMoiEnInt(trackday.getLabelMoi().getText()), Integer.parseInt(trackday.getLabelAnnee().getText()));
            }
        }
        else if(e.getSource() == trackday.getButtonTerminer())
        {
            String valeuractuelle = (String) trackday.getListJour().getSelectedValue();

            JOptionPane jop = new JOptionPane();
            if(valeuractuelle != null)
            {
                if(!valeuractuelle.contains("trackday")) //ok on peu prévoir
                {
                    String uniquementleschiffre = valeuractuelle.replaceAll("\\D", "");
                    int jour;
                    if(uniquementleschiffre.length() < 6)
                        jour = Integer.parseInt(uniquementleschiffre.substring(0, 1));
                    else
                        jour = Integer.parseInt(uniquementleschiffre.substring(0, 2));

                    LocalDate date = LocalDate.of(Integer.parseInt(trackday.getLabelAnnee().getText()), ConvertionMoiEnInt(trackday.getLabelMoi().getText()), jour);

                    //recherche de l'élément s'il existe déjà
                    int index = 0;
                    boolean SuppressionDsFichier = false;
                    ClassJour lejour = new ClassJour(date, false, true, false);
                    for(ClassJour unjour : singleton.getListJour())
                    {
                        if(unjour.getJour().equals(date))
                        {
                            lejour = unjour;
                            singleton.getListJour().remove(index); //suppression de l'élément car il existait déjà
                            lejour.setTrackdayprevu(true);
                            SuppressionDsFichier = true;
                            break;
                        }
                        index++;
                    }
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filepathplanning, true))) { //creation du fichier ici
                        File file = new File(filepathplanning);
                        if(file.length() == 0) //fichier vide, écriture de l'entète
                        {
                            writer.write("Date,Bathème,Trackday,Course");
                        }

                        // Écrire les lignes de données
                        singleton.getListJour().add(lejour);
                        if(SuppressionDsFichier) //suppression de la ligne et nouvelle écriture a la place
                        {
                            //réécritrure de tout les fichiers
                            try (BufferedWriter writerall = new BufferedWriter(new FileWriter(filepathplanning)))
                            {
                                index = 0;
                                for (ClassJour unjour : singleton.getListJour())
                                {
                                    if(index == 0)
                                    {
                                        writerall.write("Date,Bathème,Trackday,Course");
                                    }
                                    writerall.newLine();
                                    writerall.write(unjour.getJour() + "," + unjour.isBathemeprevu() + "," + unjour.isTrackdayprevu() + "," + unjour.isCourseprevue());
                                    index++;
                                }
                            } catch (IOException exc) {
                                exc.printStackTrace();
                            }
                        }
                        else
                        {
                            writer.newLine();
                            writer.write("" + lejour.getJour() + "," + lejour.isBathemeprevu() + "," + lejour.isTrackdayprevu() + "," + lejour.isCourseprevue());
                        }

                        jop.showMessageDialog(null, "Trackday enregistré", "Information", jop.INFORMATION_MESSAGE);
                        jop.setVisible(true);
                        trackday.dispose();

                    } catch (IOException exc) {
                        exc.printStackTrace();
                    }
                }
                else
                {
                    jop.showMessageDialog(null, "Veuillé sélectionner une autre date, un trackday est déjà prévu", "Erreur", jop.ERROR_MESSAGE);
                    jop.setVisible(true);
                }
            }
            else
            {
                jop.showMessageDialog(null, "Veuillé sélectionner un date", "Erreur", jop.ERROR_MESSAGE);
                jop.setVisible(true);
            }
        }
    }
}
