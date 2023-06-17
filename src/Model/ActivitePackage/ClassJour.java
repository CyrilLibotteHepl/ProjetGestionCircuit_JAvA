package Model.ActivitePackage;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Objects;

public class ClassJour
{
    private LocalDate Jour;
    private boolean bathemeprevu;
    private boolean trackdayprevu;
    private boolean courseprevue;

    public LocalDate getJour() {
        return Jour;
    }

    public boolean isBathemeprevu() {
        return bathemeprevu;
    }

    public boolean isTrackdayprevu() {
        return trackdayprevu;
    }

    public void setJour(LocalDate jour) {
        Jour = jour;
    }

    public void setBathemeprevu(boolean bathemeprevu) {
        this.bathemeprevu = bathemeprevu;
    }

    public void setTrackdayprevu(boolean trackdayprevu) {
        this.trackdayprevu = trackdayprevu;
    }

    public boolean isCourseprevue() {
        return courseprevue;
    }

    public void setCourseprevue(boolean courseprevue) {
        this.courseprevue = courseprevue;
    }

    public ClassJour()
    {
        Jour = LocalDate.now();
        bathemeprevu = false;
        trackdayprevu = false;
        courseprevue = false;
    }
    public ClassJour(LocalDate ledate, boolean batheme, boolean trackday, boolean course)
    {
        Jour = ledate;
        bathemeprevu = batheme;
        trackdayprevu = trackday;
        courseprevue = course;
    }

    @Override
    public String toString() {
        String lemoi = ConvertionMoi(Jour.getMonthValue());
        String lejour = ConverstionJour(Jour.getDayOfWeek());
        if(bathemeprevu && !trackdayprevu && !courseprevue)
        {
            return lejour + " " + Jour.getDayOfMonth() + " " + lemoi + " " + Jour.getYear() +" un bathème est prévu";
        }
        else if (trackdayprevu && !bathemeprevu && !courseprevue)
        {
            return lejour + " " + Jour.getDayOfMonth() + " " + lemoi + " " + Jour.getYear() +" un trackday est prévu";
        }
        else if(courseprevue && !trackdayprevu && !bathemeprevu)
        {
            return lejour + " " + Jour.getDayOfMonth() + " " + lemoi + " " + Jour.getYear() +" une course est prévue";
        }
        else if(trackdayprevu && !bathemeprevu && courseprevue)
        {
            return lejour + " " + Jour.getDayOfMonth() + " " + lemoi + " " + Jour.getYear() +" une course et un trackday sont prévu";
        }
        else if(trackdayprevu && bathemeprevu && !courseprevue)
        {
            return lejour + " " + Jour.getDayOfMonth() + " " + lemoi + " " + Jour.getYear() +" un bathème et un trackday sont prévu";
        }
        else if(!trackdayprevu && bathemeprevu && courseprevue)
        {
            return lejour + " " + Jour.getDayOfMonth() + " " + lemoi + " " + Jour.getYear() +" un bathème et une course sont prévu";
        }
        else if(trackdayprevu && bathemeprevu && courseprevue)
        {
            return lejour + " " + Jour.getDayOfMonth() + " " + lemoi + " " + Jour.getYear() +" un bathème, un trackday et une course sont prévu";
        }
        else
        {
            return lejour + " " + Jour.getDayOfMonth() + " " + lemoi + " " + Jour.getYear() +" rien n'est prévu";
        }
    }

    public String ConverstionJour(DayOfWeek joursemaine)
    {
        String lejour = "";
        switch(joursemaine.ordinal())
        {
            case 0 : lejour = "Lundi";
                break;
            case 1 : lejour = "Mardi";
                break;
            case 2 : lejour = "Mercredi";
                break;
            case 3 : lejour = "Jeudi";
                break;
            case 4 : lejour = "Vendredi";
                break;
            case 5 : lejour = "Samedi";
                break;
            case 6 : lejour = "Dimanche";
        }
        return lejour;
    }
    public String ConvertionMoi(int lemoi)
    {
        String moi = "";
        switch(lemoi)
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
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClassJour classJour)) return false;
        return bathemeprevu == classJour.bathemeprevu && trackdayprevu == classJour.trackdayprevu && Objects.equals(Jour, classJour.Jour);
    }
}
