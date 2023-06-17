// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.

import Controleurs.ClassSingleton;
import Controleurs.ClassemainControleur;
import View.Classemain;

public class Main {
    public static void main(String[] args)
    {
        Classemain classemain = new Classemain();
        classemain.setSize(800, 600);
        classemain.setVisible(true);

        ClassSingleton singleton = ClassSingleton.getInstance();
        ClassemainControleur classemainControleur = new ClassemainControleur(classemain);
        classemain.setControleurMain(classemainControleur);
    }
}