package Controleurs;

import View.Admin;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminControleur implements ActionListener
{
    private Admin admin;
    private String mdpAdmin = "123abc";
    private static int nbinteration = 3;
    private AdminInterface listener;
    private Desenableinterface listener1;
    public AdminControleur(Admin adm)
    {
        admin = adm;
    }

    public void setEventDisponibiliteAdmin(AdminInterface listener) {
        this.listener = listener;
    }
    public void setEventCourseAdmin(Desenableinterface listener) {this.listener1 = listener;}
    public void RetirerAdmin()
    {
        if (listener != null) {
            listener.setAdminIndisponible();
        }
    }
    public void AfficherAdmin()
    {
        if (listener != null) {
            listener.setAdminDisponible();
        }
    }
    public void CourseEnable()
    {
        if(listener1 != null)
        {
            listener1.CourseDesable();
        }
    }
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == admin.getTerminerButton())
        {
            boolean ok = false;
            if(admin.getpasswordField().getPassword() != null)
            {
                char[] mdp = admin.getpasswordField().getPassword();
                String motdepasse = new String(mdp);
                if(motdepasse.equals(mdpAdmin))
                {
                    ClassSingleton singleton = ClassSingleton.getInstance();
                    singleton.setAdmin();
                    CourseEnable();
                    AfficherAdmin();
                    ok = true;
                    JOptionPane jop = new JOptionPane();
                    jop.showMessageDialog(null, "Vous passez en mode admin", "Information", jop.INFORMATION_MESSAGE);
                    admin.dispose();
                }
                else
                {
                    JOptionPane jop = new JOptionPane();
                    jop.showMessageDialog(null, "Erreur mot de passe", "Erreur", jop.ERROR_MESSAGE);
                    nbinteration--;
                }
            }
            if(ok == false && nbinteration == 0)
            {
                RetirerAdmin();
                JOptionPane jop = new JOptionPane();
                jop.showMessageDialog(null, "Erreur mdp : plus de chance disponible", "Erreur", jop.ERROR_MESSAGE);
                admin.dispose();
            }
        }
    }
}
