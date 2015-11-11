
package Obiekt;

import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

public class Obiekt
{
    private
    static double maxx,maxy,minx,miny;
    double x,y;
    int w,s; //wysokość,szerokość
    int zbroja; // ogranicza się do określania czy jest czy nie ma obiektu w przyszłości będzie mieć szersze zastosowanie
    
    //BufferedImage obrazek;
    public Obiekt()
    {  //określenie granic planszy
        maxx=1000;
        maxy=800;
        miny=-100;
        minx=-100;
    }
    public void ustawWymiary(String nazwa)
    {

        try
       {
         BufferedImage obrazek;
         obrazek = ImageIO.read(new File("grafika/" + nazwa));
         s=obrazek.getWidth(null)/2;
         w=obrazek.getHeight(null)/2;
       } catch (IOException e) {} 
       //
      //   System.out.println(nazwa + "  " + s);
    }
    public void zniszcz()
    {
        zbroja = 0;
        s=w=0;
    }
    public int sprawdźKolizję(Obiekt obiekt)
    { //sprawdza kolizję z innym obiektem
        int x2 = obiekt.pobierzX();
        int y2 = obiekt.pobierzY();
        int s2 = obiekt.pobierzSzerokość();
        int w2 = obiekt.pobierzWysokość();
        if( ( (y-y2<w2+w && y>y2)||(y2-y<w2+w && y<=y2)) && ((x-x2<s2+s && x>=x2)||(x2-x<s2+s && x<x2)) ) return 1;
        return 0;
    }
    public int pobierzXPoPoprawce()
    {
        return (int) x-s;
    }
    public int pobierzYPoPoprawce()
    {
        return (int) y-w;
    }
    public int pobierzX()
    {
        return (int) x;
    }
    public int pobierzY()
    {
        return (int) y;
    }
    public int pobierzSzerokość()
    {
        return (int) s;
    }
    public int pobierzWysokość()
    {
        return (int) w;
    }
    public int czyIstnieje()
    {
        if(zbroja<1) return 0;
        return 1;
    }
    public double minimalneX(){return minx;}
    public double minimalneY(){return miny;}
    public double maksymalneX(){return maxx;}
    public double maksymalneY(){return maxy;}
}
