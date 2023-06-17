package Controleurs;


import Model.ActivitePackage.ClassCourse;
import Model.ActivitePackage.ClassJour;
import View.Course;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

public class CourseControleur implements ActionListener
{
    private Course course;
    private String filepathrecord;
    private String filepathcourse;
    private String filepathplanning;
    private int nbCourse;
    private ClassSingleton singleton;
    public CourseControleur(Course crs, String fp)
    {
        course = crs;
        filepathrecord = fp; //"C:\\Users\\cycro\\Desktop\\JAVApj\\Course\\record.properties"
        singleton = ClassSingleton.getInstance();
        filepathcourse = singleton.getFilepathCourse(); //"C:\Users\cycro\Desktop\JAVApj\Course"
        nbCourse = singleton.getNbCourse();
        filepathplanning = singleton.getFilepathPlanning() + "\\Planning.csv"; //"C:\\Users\\cycro\\Desktop\\JAVApj\\Planning\\Planning.csv"
    }
    public int MonthConvertToNumber(String lemoi)
    {
        int lenumcorrespondant = -1;

        switch (lemoi)
        {
            case "Janvier" : lenumcorrespondant = 1;
                break;
            case "Février" : lenumcorrespondant = 2;
                break;
            case "Mars" : lenumcorrespondant = 3;
                break;
            case "Avril" : lenumcorrespondant = 4;
                break;
            case "Mai" : lenumcorrespondant = 5;
                break;
            case "Juin" : lenumcorrespondant = 6;
                break;
            case "Juillet" : lenumcorrespondant = 7;
                break;
            case "Août" : lenumcorrespondant = 8;
                break;
            case "Septembre" : lenumcorrespondant = 9;
                break;
            case "Octobre" : lenumcorrespondant = 10;
                break;
            case "Novembre" : lenumcorrespondant = 11;
                break;
            case "Décembre" : lenumcorrespondant = 12;
                break;
        }

        return lenumcorrespondant;
    }
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == course.getTerminerButton())
        {
            int itineraire = -1;
            boolean ok = true;
            String errormessage = "default";
            String nom = course.getTextFieldNom().getText();
            String itinerairestr = (String) course.getComboBoxItineraire().getSelectedItem(); //
            if(itinerairestr.length() != 0)
            {
                itineraire = Integer.parseInt(itinerairestr);
            }
            String categorie = (String) course.getComboBoxCategorie().getSelectedItem(); //
            String description = course.getTextfieldDescription().getText();
            String discpline = (String) course.getComboBoxDiscipline().getSelectedItem(); //
            int jour, moi, annee;

            String jourstr = (String) course.getComboBoxJour().getSelectedItem();
            String moistr = (String) course.getComboBoxMoi().getSelectedItem();
            String anneestr = (String) course.getComboBoxAnnee().getSelectedItem();

            jour = Integer.parseInt(jourstr);
            moi = MonthConvertToNumber(moistr);
            annee = Integer.parseInt(anneestr);

            String heurestr = (String) course.getComboBoxHeure().getSelectedItem(); //
            String tarifstr = course.getTextFieldPrix().getText();


            JOptionPane jop = new JOptionPane();
            if(nom.length() == 0 || description.length() == 0 || tarifstr.length() == 0 || itinerairestr.length() == 0)
            {
                ok = false;
                errormessage = "Donnée(s) manquante(s)";
            }
            else
            {
                // tarif
                double tarif = -1;
                try{
                    tarif = Double.parseDouble(tarifstr);

                    if(tarif < 0)
                    {
                        ok = false;
                        errormessage = "Le tarif ne peu être négatif";
                    }
                } catch (NumberFormatException exception)
                {
                    ok = false;
                    errormessage = "Le tarif ne peux comporté que des chiffres";
                }

                //formatage de la date

                ////////////////formatage de l'heure

                String[] tmps = heurestr.split("h");
                DateTimeFormatter formatter;
                if(tmps[0].length() > 1)
                {
                    formatter = DateTimeFormatter.ofPattern("HH'h'mm");
                }
                else
                {
                    formatter = DateTimeFormatter.ofPattern("H'h'mm");
                }
                LocalTime heure = LocalTime.parse(heurestr, formatter);

                LocalDate date = LocalDate.of(annee, moi, jour);
                LocalDate currentdate = LocalDate.now();
                if (date.isEqual(currentdate)) {
                    LocalTime currentTime = LocalTime.now();
                    if (heure.isBefore(currentTime)) {
                        ok = false;
                        errormessage = "Impossible de créer une course pour une heure qui est déjà passée";
                    }
                }

                //écriture dans le fichier
                if(ok)
                {
                    nbCourse++;

                    //////////////////recherche du record en fonction de la course
                    Properties properties = new Properties();

                    try (FileInputStream fileInputStream = new FileInputStream(filepathrecord)) {
                        properties.load(fileInputStream);

                        Double record = 00.00;
                        for (String cle : properties.stringPropertyNames()) {
                            String valeur = properties.getProperty(cle);
                            if (cle.equals("" + itineraire))
                            {
                                record = Double.parseDouble(valeur);
                                break;
                            }
                        }

                        ClassCourse classCourse = new ClassCourse(nbCourse,"Course", date, heure, description, tarif, itineraire, nom,record, categorie, discpline, false);
                        //écriture dans le fichier
                        try {
                            //sauvegarde d'un jour dans le fichier des jours
                            //test si le jour dans lequel on prévoit la course est déjà existant
                            int index = 0;
                            boolean SuppressionDsFichier = false;
                            boolean CourseDejaPrevue = false;
                            ClassJour lejour = new ClassJour(date, false, false, true);
                            for(ClassJour unjour : singleton.getListJour())
                            {
                                if(unjour.getJour().equals(date))
                                {
                                    lejour = unjour;
                                    singleton.getListJour().remove(index);
                                    if(unjour.isCourseprevue())
                                    {
                                        CourseDejaPrevue = true;
                                    }
                                    lejour.setCourseprevue(true);
                                    SuppressionDsFichier = true;
                                    break;
                                }
                                index++;
                            }
                            singleton.getListJour().add(lejour);

                            if(!CourseDejaPrevue)
                            {
                                File file = new File(filepathcourse + "\\Course.dat");
                                FileOutputStream fileOut = new FileOutputStream(filepathcourse + "\\Course.dat", true);
                                if(file.length() == 0) //écriture avec entête
                                {
                                    ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);

                                    objectOut.writeObject(classCourse);

                                    objectOut.close();
                                }
                                else //écriture sans entête
                                {
                                    ObjectOutPutStreamPersonnalise objectOut1 = new ObjectOutPutStreamPersonnalise(fileOut);

                                    objectOut1.writeObject(classCourse);

                                    objectOut1.close();
                                }

                                fileOut.close();
                                singleton.getListCourse().add(classCourse);

                                try (BufferedWriter writer = new BufferedWriter(new FileWriter(filepathplanning, true))) { //creation du fichier ici
                                    File fileplanning = new File(filepathplanning);
                                    if(fileplanning.length() == 0) //fichier vide, écriture de l'entète
                                    {
                                        writer.write("Date,Bathème,Trackday,Course");
                                    }

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
                                                writerall.write(lejour.getJour() + "," + unjour.isBathemeprevu() + "," + unjour.isTrackdayprevu() + "," + unjour.isCourseprevue());
                                                index++;
                                            }
                                        } catch (IOException exc) {
                                            exc.printStackTrace();
                                        }
                                    }
                                    else
                                    {
                                        // Écrire les lignes de données
                                        writer.newLine();
                                        writer.write("" + lejour.getJour() + "," + lejour.isBathemeprevu() + "," + lejour.isTrackdayprevu() + "," + lejour.isCourseprevue()); //ici le CourseDejaPrevue contient l'état du boolean de course avant de prévoir celle-ci (pour concordé au maximum)
                                    }



                                } catch (IOException exc) {
                                    exc.printStackTrace();
                                }

                                jop.showMessageDialog(null, "Sauvegarde la course réussie", "Information", jop.INFORMATION_MESSAGE);
                                jop.setVisible(true);
                                singleton.setNbVoiture(nbCourse);
                                course.dispose();
                            }
                            else
                            {
                                jop.showMessageDialog(null, "Impossiblité de sauvegardé, une course est déjà prévue", "Erreur", jop.ERROR_MESSAGE);
                                jop.setVisible(true);
                            }
                        } catch (IOException exc) {
                            jop.showMessageDialog(null, "Une erreur s'est produite lors de la sérialisation de l'objet", "Erreur", jop.ERROR_MESSAGE);
                            jop.setVisible(true);
                        }

                    } catch (IOException exc) {
                        exc.printStackTrace();
                        ok = false;
                        errormessage = "Erreur dans la gestion du fichier properties des records";
                    }
                }
            }

            if(!ok)
            {
                jop.showMessageDialog(null, errormessage, "Erreur", jop.ERROR_MESSAGE);
                jop.setVisible(true);
            }
        }
        else if(e.getSource() == course.getComboBoxAnnee())
        {
            LocalDate dateCourante = LocalDate.now(); // Obtention de la date courante

            int jour = dateCourante.getDayOfMonth(); // Récupération du jour du mois
            int mois = dateCourante.getMonthValue(); // Récupération du mois
            int annee = dateCourante.getYear(); // Récupération de l'année

            String anneestr = (String) course.getComboBoxAnnee().getSelectedItem();
            int anneechoisi = Integer.parseInt(anneestr);

            int moidebut = 1;

            if(anneechoisi == annee) //permet de déterminer si on affiche tout les mois ou ceux a partir du moi actuelle
            {
                moidebut = mois;

                //nb je jour en fonction du moi
                int nbjours = -1;
                String moi = "";
                switch (mois)
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

                switch(moi)
                {
                    case "Janvier":
                    case "Mars":
                    case "Mai":
                    case "Juillet":
                    case "Août":
                    case "Octobre":
                    case "Décembre":
                        nbjours = 31;
                        break;
                    case "Avril":
                    case "Juin":
                    case "Septembre":
                    case "Novembre":
                        nbjours = 30;
                        break;
                    case "Février":
                        String strinanntmp = (String) course.getComboBoxAnnee().getSelectedItem();

                        int anneeselect = Integer.parseInt(strinanntmp);
                        if((anneeselect % 4 == 0 && anneeselect % 100 != 0) || (anneeselect % 400 == 0))
                        {
                            //année bissextil
                            nbjours = 29;
                        }
                        else
                        {
                            nbjours = 28;
                        }
                        break;
                }

                course.getComboBoxJour().removeAllItems();

                String jourstr = "";
                for(int i = jour; i<=nbjours; i++)
                {
                    jourstr = Integer.toString(i);
                    course.getComboBoxJour().addItem(jourstr);
                }
            }

            course.getComboBoxMoi().removeAllItems();
            String moi = "";
            for(int i = moidebut; i<=12 ; i++)
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
                }

                course.getComboBoxMoi().addItem(moi);
            }
        }
        else if(e.getSource() == course.getComboBoxMoi())
        {
            //gestion du nombre de jour dans le moi
            String moiselectionner = (String) course.getComboBoxMoi().getSelectedItem();

            LocalDate dateCourante = LocalDate.now(); // Obtention de la date courante

            int jour = dateCourante.getDayOfMonth(); // Récupération du jour du mois
            int mois = dateCourante.getMonthValue(); // Récupération du mois
            int annee = dateCourante.getYear(); // Récupération de l'année

            int nbjours = -1;

            if(moiselectionner != null) //je fait ce if car qd on fait removeAllItem dans else if au dessus ça fait appel a ce else if et ça fait le switch sur moiselectionner qui est null
            {
                switch (moiselectionner)
                {
                    case "Janvier":
                    case "Mars":
                    case "Mai":
                    case "Juillet":
                    case "Août":
                    case "Octobre":
                    case "Décembre":
                        nbjours = 31;
                        break;
                    case "Avril":
                    case "Juin":
                    case "Septembre":
                    case "Novembre":
                        nbjours = 30;
                        break;
                    case "Février":
                        String strinanntmp = (String) course.getComboBoxAnnee().getSelectedItem();

                        int anneeselect = Integer.parseInt(strinanntmp);
                        if((anneeselect % 4 == 0 && anneeselect % 100 != 0) || (anneeselect % 400 == 0))
                        {
                            //année bissextil
                            nbjours = 29;
                        }
                        else
                        {
                            nbjours = 28;
                        }
                        break;
                }
                course.getComboBoxJour().removeAllItems();

                String moichoisistr = (String) course.getComboBoxMoi().getSelectedItem();
                int moichoisi = MonthConvertToNumber(moichoisistr);
                String anneechoisiestr = (String) course.getComboBoxAnnee().getSelectedItem();
                int anneechoisie = Integer.parseInt(anneechoisiestr);

                String istr = "";
                if(anneechoisie == annee && mois == moichoisi)
                {
                    //mise en place des jour a partir d'aujourd'hui
                    for(int i = jour; i<=nbjours; i++)
                    {
                        istr = String.valueOf(i);
                        course.getComboBoxJour().addItem(istr);
                    }
                }
                else
                {
                    //mise en place de tout les jours du moi
                    for(int i = 1; i<=nbjours;i++)
                    {
                        istr = String.valueOf(i);
                        course.getComboBoxJour().addItem(istr);
                    }
                }
            }
        }
        else if(e.getSource() == course.getComboBoxItineraire())
        {
            ImageIcon imageIcon;
            String itinerairestr = (String) course.getComboBoxItineraire().getSelectedItem();
            if (itinerairestr.length() != 0)
            {
                int itineraire = Integer.parseInt(itinerairestr);
                switch (itineraire)
                {
                    case 1:
                        imageIcon = new ImageIcon("C:\\Users\\cycro\\Desktop\\JAVApj\\Image\\Circuit\\circuit1.jpeg"); //je fait tout dans chaque switch parce qu'il faut faire une new imageIcon a chaque fois
                        course.getlabelImage().setIcon(imageIcon);
                        course.getPanelImage().add(course.getlabelImage());
                        course.getPanelImage().revalidate();
                        course.getPanelImage().repaint();
                        break;
                    case 2:
                        imageIcon = new ImageIcon("C:\\Users\\cycro\\Desktop\\JAVApj\\Image\\Circuit\\circuit2.jpeg");
                        course.getlabelImage().setIcon(imageIcon);
                        course.getPanelImage().add(course.getlabelImage());
                        course.getPanelImage().revalidate();
                        course.getPanelImage().repaint();
                        break;
                    case 3:
                        imageIcon = new ImageIcon("C:\\Users\\cycro\\Desktop\\JAVApj\\Image\\Circuit\\circuit3.jpeg");
                        course.getlabelImage().setIcon(imageIcon);
                        course.getPanelImage().add(course.getlabelImage());
                        course.getPanelImage().revalidate();
                        course.getPanelImage().repaint();
                        break;
                    case 4:
                        imageIcon = new ImageIcon("C:\\Users\\cycro\\Desktop\\JAVApj\\Image\\Circuit\\circuit4.jpeg");
                        course.getlabelImage().setIcon(imageIcon);
                        course.getPanelImage().add(course.getlabelImage());
                        course.getPanelImage().revalidate();
                        course.getPanelImage().repaint();
                        break;
                    case 5:
                        imageIcon = new ImageIcon("C:\\Users\\cycro\\Desktop\\JAVApj\\Image\\Circuit\\circuit5.jpg");
                        course.getlabelImage().setIcon(imageIcon);
                        course.getPanelImage().add(course.getlabelImage());
                        course.getPanelImage().revalidate();
                        course.getPanelImage().repaint();
                        break;
                }
            }
            else
            {
                imageIcon = new ImageIcon("C:\\Users\\cycro\\Desktop\\JAVApj\\Image\\Circuit\\default.jpg");
                course.getlabelImage().setIcon(imageIcon);
                course.getPanelImage().add(course.getlabelImage());
                course.getPanelImage().revalidate();
                course.getPanelImage().repaint();
            }
        }
    }
}
