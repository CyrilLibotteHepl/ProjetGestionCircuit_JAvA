package View;

import Controleurs.Controleurlancementcourses;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class LanCourse extends JDialog implements PropertyChangeListener{
    private JLabel JLabelNomCourse;
    private JList listClassement;
    private JPanel CoursePanel;
    private JLabel JLabelitineraire;

    public JLabel getJLabelNomCourse() {
       return JLabelNomCourse; }

    public JLabel getJLabelitineraire() {
        return JLabelitineraire; }

    public void setJLabelNomCourse(String text) {
        JLabelNomCourse.setText(text);
    }

    public void addJLabelNomCourseListener(PropertyChangeListener listener) {
        JLabelNomCourse.addPropertyChangeListener(listener);
    }

    public void setJLabelitineraire(String text) {
        JLabelitineraire.setText(text);
    }

    public void addJLabelitineraireListener(PropertyChangeListener listener) {
        JLabelitineraire.addPropertyChangeListener(listener);
    }

    public LanCourse()
    {
        setTitle("Course Terminer");
        setModal(true);
        setSize(200, 300);
        setContentPane(CoursePanel);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
    }

    public void afficherTotal(String total) {
        DefaultListModel<String> model = new DefaultListModel<>();
        model.addElement(total);
        listClassement.setModel(model);
    }


    public void setControleurlancementcourses(Controleurlancementcourses controleurlancementcourses)
    {
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Controleurlancementcourses controleurlancementcourses = (Controleurlancementcourses) evt.getSource();

        if ("Nom".equals(evt.getPropertyName())) {
            getJLabelNomCourse().setText((String) evt.getNewValue());
        }

        if ("Itineraire".equals(evt.getPropertyName())) {
            getJLabelitineraire().setText((String) evt.getNewValue());
        }
    }
}



