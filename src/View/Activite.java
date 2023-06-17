package View;

import javax.swing.*;

public class Activite extends JDialog{

    private JPanel mainPanel;
    private JTextField textField1;
    private JTextField textField2;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JTextPane textPane1;
    private JTextField textField3;
    private JRadioButton bathemeRadioButton;
    private JRadioButton trackdayRadioButton;
    private JComboBox comboBox3;
    private JRadioButton ouiRadioButton;
    private JRadioButton nonRadioButton;
    private JTextField textField4;
    private JTextField textField5;
    private JButton terminerButton;

    public static void main(String[] args)
    {
        Activite actv = new Activite();
        actv.setVisible(true);
    }
    public Activite()
    {
        setTitle("Nouvelle activite");
        setSize(350, 500);
        setContentPane(mainPanel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setModal(true);
    }

}
