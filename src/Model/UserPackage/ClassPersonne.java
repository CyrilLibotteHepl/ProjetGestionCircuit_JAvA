package Model.UserPackage;

public abstract class ClassPersonne implements IdInterface
{
    protected int identifiant;
    protected String Nom;
    protected String  Prenom;
    protected String Login;
    protected String MotDePasse;
    protected String Licence;
    protected boolean Admin;
    public ClassPersonne()
    {
        identifiant = -1;
        Nom = "";
        Prenom = "";
        Login = "";
        Licence = "";
        MotDePasse = null;
        Admin = false;
    }
    public ClassPersonne(int id, String nompersonne, String prenomPersonne , String  login , String motDePasse, String Licence, Boolean admin)
    {
        identifiant = id;
        Nom = nompersonne;
        Prenom = prenomPersonne;
        Login = login;
        MotDePasse = motDePasse;
        Licence = Licence;
        Admin = admin;
    }
    //getter
    public int getId()
    {
        return identifiant;
    }
    public String getNompersonne()
    {
        return Nom;
    }
    public String getPrenomPersonne()
    {
        return Prenom;
    }
    public String getLogin()
    {
        return Login;
    }
    public String getMotDePasse()
    {
        return MotDePasse;
    }
    public boolean getadmin()
    {
        return Admin;
    }
    public String getLicence() {
        return Licence;
    }
    public void setLicence(String licence) {
        Licence = licence;
    }

    //setter
    public void setId(int id)
    {
        identifiant = id;
    }
    public void setNompersonne(String nompersonne) {
        Prenom = nompersonne;
    }
    public void setPrenomPersonne(String prenomPersonne)
    {
        Prenom = prenomPersonne;
    }
    public void setLogin(String login) {
        Login = login;
    }
    public void setMotDePasse(String motDePasse)
    {
        MotDePasse = motDePasse;
    }
    public void setAdmin (boolean admin)
    {
        Admin = admin;
    }

}
