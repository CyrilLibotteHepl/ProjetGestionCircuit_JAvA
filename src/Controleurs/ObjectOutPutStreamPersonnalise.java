package Controleurs;

import java.io.*;

public class ObjectOutPutStreamPersonnalise extends ObjectOutputStream //cette classe permet d'écrire un objet dans un fichier sans devoir écrire l'entête ce que permet de reconnaître les fichier lors de la lecture
{                                                                //si on ne le fait pas on écrit plusieurs fois l'entête et lors de la lecture le lecteur ne reconnait pas le type d'objet, un entête ne trop
    public ObjectOutPutStreamPersonnalise(OutputStream out) throws IOException {
        super(out);
    }

    protected ObjectOutPutStreamPersonnalise() throws IOException, SecurityException
    {
        super();
    }
    // Method of this class
    public void writeStreamHeader() throws IOException
    {
        return;
    }
}
