package Obiekt;

import java.util.Random;

import static java.lang.Math.*;

public class Statek extends Obiekt
{
    private
    static double ox,oy;  //współrzędne okręgu
    double r;      //odległość od środka okręgu
    double a;      //kąt

    double vr;      //prędkość wzdłuż prostej
    double va;  //prędkość obrotowa


    public Statek()
    {
            ox= x=400;
            oy= y=300;
    }

    public void stwórz(int numer,int parametr)
    {
       if(numer==0)  //Nasz bohater
       {
            zbroja=1;
            r=170;
            a=0;
            va=0.01*parametr;
            x= (r*sin(a));
            x= x+ox;
            y= (r*cos(a));
            y= y+oy;
            ustawWymiary("Satelite.png");
       }
       if(numer==1)   //Klasyczne bomby
       {
           Random generator = new Random();
            zbroja=1;
            r=400;
            a=generator.nextDouble()*6.28;
            va=0;
            vr=-0.5-0.01*parametr;
            x= (r*sin(a));
            x= x+ox;
            y= (r*cos(a));
            y= y+oy;
            ustawWymiary("Bomb.png");
       }
    }
    public void przelicz()
    {
        if(czyIstnieje()==0) return;
        if(y<minimalneY() || x<minimalneX() || x> maksymalneX() || y > maksymalneY()) zniszcz();
        x=(r*sin(a));
        x= x+ox;
        y=(r*cos(a));
        y= y+oy;
        a+=va;
        r+=vr;
    }
    public double pobierzKąt()
    {
            return a;
    }
    public void wLewo()
    {
        //va-=0.002;
    }
    public void wPrawo()
    {
        //va+=0.002;
    }
    public void doŚrodka()
    {
        //r-=1;
    }
    public void odŚrodka()
    {
        //r+=1;
    }
}
