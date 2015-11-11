package Obiekt;

import static java.lang.Math.*;


public class Pocisk extends Obiekt
{
    int xd;
    int yd;
    double a;
    double vx;
    double vy;
    int rodzaj;

    public Pocisk()
    {

    }
    public void stwórz(int x1, int y1, int x2, int y2, int v,int nrodzaj)
    {//Tworzymy wektor o długości v po którym będzie przesuwany pocisk
        zbroja=1;
        rodzaj=nrodzaj;
        ustawWymiary("Bullet.png");
        double c =(double) sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
        x=x1;
        y=y1;
        vx=(double)(v*(x2-x1))/c;
        vy=(double)(v*(y2-y1))/c;
        //x+=vx*3;
        //y+=vy*3;
    }
    public void przelicz()
    {   //przesuwanie po wspomnianym wektorze
        if(czyIstnieje()==0) return;
        x+=vx;
        y+=vy;
        if(y<minimalneY() || x<minimalneX() || x> maksymalneX() || y > maksymalneY()) zniszcz();
    }
    public double pobierzKąt()
    {
        return java.lang.Math.asin(vy/sqrt(vy*vy+ vx*vx));
    }
    public int czyPrawo()
    {
        if(vx>0) return 1;
        return 0;
    }
    public int pobierzRodzaj()
    {
        return rodzaj;
    }
}
