package Controleurs;

import Model.ActivitePackage.ClassTrackday;
import Model.UserPackage.ClassVoiture;
import View.ReservationTrackday;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ControleurReservationTrackday implements ActionListener {

    private ReservationTrackday reservation;
    private ClassSingleton singleton;
    private LocalDate ladate;
    private String filepathreservationtrackday;
    private InterEventTrackDay listener;

    public ControleurReservationTrackday(ReservationTrackday reservationTrackday, LocalDate date)
    {
        reservation = reservationTrackday;
        singleton = ClassSingleton.getInstance();
        ladate = date;

        filepathreservationtrackday =  singleton.getFilepathReservation() + "\\ReservationTrackDay.txt";
    }

    public void setInterNotificationTrackDay(InterEventTrackDay interEventTrackDay)
    {
        this.listener = interEventTrackDay;
    }
    public void EnvoieNotifiaction()
    {
        if(listener != null)
        {
            listener.InterNotificationModificationTrackDay();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == reservation.getButtonReservation())
        {
            boolean creation = true;
            String errormessage = "";
            ClassVoiture lavoiture = new ClassVoiture();
            boolean louervoiture = false;
            if(reservation.getLouerUneVoitureCheckBox().isSelected())
            {
                louervoiture = true;
                lavoiture = (ClassVoiture) reservation.getListVoiture().getSelectedValue();
                //lavoiture.setEstutilise(true);

                //écriture dans le fichier

                try {
                    File file = new File(singleton.getFilepathConnexion() + "\\Voiture.dat");
                    FileOutputStream fileOut = new FileOutputStream(singleton.getFilepathConnexion() + "\\Voiture.dat");
                    ObjectOutputStream objectOut = null;
                    ObjectOutPutStreamPersonnalise objectOut1 = null;
                    for(ClassVoiture unevoiture : singleton.getListVoiture())
                    {
                        if(unevoiture.getIdentifiant() == lavoiture.getIdentifiant())
                        {
                            unevoiture.setEstutilise(true);
                            lavoiture = unevoiture;
                        }
                        if(file.length() == 0) //écriture avec entête
                        {
                            objectOut = new ObjectOutputStream(fileOut);

                            objectOut.writeObject(unevoiture);


                        }
                        else //écriture sans entête
                        {
                            objectOut1 = new ObjectOutPutStreamPersonnalise(fileOut);
                            objectOut1.writeObject(unevoiture);
                        }
                    }
                    fileOut.close();
                    objectOut1.close();
                    objectOut.close();
                } catch (IOException exc) {
                    JOptionPane.showMessageDialog(null, "Une erreur s'est produite lors de la sérialisation de l'objet", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }

            if(lavoiture == null)
            {
                creation = false;
                errormessage = "Données manquantes";
            }
            else
            {
                int identifiantPersconnecter = -1;
                if (singleton.TestClient()) {
                    identifiantPersconnecter = singleton.getClientconnecte().getId();
                } else if (singleton.TestPilote()) {
                    identifiantPersconnecter = singleton.getPiloteconnecte().getId();
                }

                String lheure = (String) reservation.getComboBoxHeure().getSelectedItem();
                String[] tmps = lheure.split("h");
                DateTimeFormatter formatter;
                if(tmps[0].length() > 1)
                {
                    formatter = DateTimeFormatter.ofPattern("HH'h'mm");
                }
                else
                {
                    formatter = DateTimeFormatter.ofPattern("H'h'mm");
                }
                LocalTime heure = LocalTime.parse(lheure, formatter);

                LocalDate currentdate = LocalDate.now();
                if (ladate.isEqual(currentdate)) {
                    LocalTime currentTime = LocalTime.now();
                    if (heure.isBefore(currentTime)) {
                        creation = false;
                        errormessage = "Impossible de prendre réservation pour une heure qui est déjà passée";
                    }
                }
                if(creation)
                {
                    //écrit la réservation dans le fichier binaire
                    String description = "Réservation du client " + identifiantPersconnecter + " pour le trackday la " + ladate + " avec une " + lavoiture.getMarque() + " " + lavoiture.getModele();

                    //    public ClassTrackday(int lidentifiant, String letype, LocalDate ladate, LocalTime lheure, String ladescription, double letarif, boolean vehiculeLoue, ClassVoiture voit)
                    ClassTrackday letrackdayreserve = new ClassTrackday(identifiantPersconnecter, "TrackDay", ladate, heure, description, 20, louervoiture, lavoiture);

                    try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filepathreservationtrackday, true))) {
                        bufferedWriter.write(letrackdayreserve.getIdentifiant() + ") " + letrackdayreserve.getDate().toString() + " à " + letrackdayreserve.getHeure().toString());
                        bufferedWriter.newLine();

                        bufferedWriter.close();
                        JOptionPane.showMessageDialog(null, "Réservation pour le trackday effectuée", "Information", JOptionPane.INFORMATION_MESSAGE);
                        EnvoieNotifiaction();
                        reservation.dispose();
                    } catch (FileNotFoundException ex) {
                        throw new RuntimeException(ex);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
            if(!creation)
            {
                JOptionPane.showMessageDialog(null, errormessage, "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
        else if(e.getSource() == reservation.getLouerUneVoitureCheckBox())
        {
            if(reservation.getLouerUneVoitureCheckBox().isSelected())
            {
                reservation.getListVoiture().setEnabled(true);
            }
            else
            {
                reservation.getListVoiture().setEnabled(false);
            }
        }
    }
}
