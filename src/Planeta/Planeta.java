

package Planeta;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;

public class Planeta
{
    private
    BufferedImage krater;

    int x,y;
    int maxx,maxy;
    int kształt[][];
    public Planeta()
    {
     BufferedImage obraz;
       try
       {
           krater = ImageIO.read(new File("grafika/Krater25.png"));
           obraz = ImageIO.read(new File("grafika/PlanetaIdioty.png"));
           maxx=obraz.getWidth();
           maxy=obraz.getHeight();
           kształt = new int[maxx][maxy];
       } catch (IOException e) {System.out.println("Znowu coś źle z rysunkiem!");}

    }
    public void stwórz()
    {
        x=400;
        y=300;
    BufferedImage obraz;
       try
       {
           obraz = ImageIO.read(new File("grafika/Planeta.png"));  //Wczytujemy obrazek Planety tylko po to żeby na jej podstawie stworzyć kształt w tablicy intów
           int i,j,c;
           for(i=0;i<maxx;i++)
           {
               for(j=0;j<maxy;j++)
               {
                   c = obraz.getRGB(i, j);
                   if(c!=-16777216) kształt[i][j]=1;
                   else kształt[i][j]=0;
               }
           }
       } catch (IOException e) {System.out.println("Znowu coś źle z rysunkiem!");}


    }
    public int czyKolizja(int x1, int y1)       //sprawdzamy czy w danym pixelu w tabliy jest zapalony czy nie`
    {
        if((maxx/2)+x1-x<1 || (maxx/2)+x1-x>maxx-1 || (maxy/2)+y1-y<1 || (maxy/2)+y1-y>maxy-1) return 0;
        if(kształt[(maxx/2)+x1-x][(maxy/2)+y1-y]==1) return 1;
        //System.out.println(c+"\n");
        return 0;
    }
    public void zróbKrater(int x1, int y1)
    {
        int i,j,c;
        int psk=krater.getWidth()/2;
        int pwk=krater.getHeight()/2;
        for(i=0;i<krater.getWidth();i++) //Na podstawie obrazku krater rzeźbimy w tablicy intów
        {
            for(j=0;j<krater.getHeight();j++)
            {
                c = krater.getRGB(i, j);
                if((maxx/2)+x1-x+i-psk<1 || (maxx/2)+x1-x+i-psk >maxx-1 ||(maxy/2)+y1-y+j-pwk<1 || (maxy/2)+y1-y+j-pwk>maxy-1) continue;
                if(c!=-16777216)kształt[(maxx/2)+x1-x+i-psk][(maxy/2)+y1-y+j-pwk]=0;


            }

        }
    }

    public int pobierzXPoPoprawce()
    {
        return x-maxx/2;
    }
    public int pobierzYPoPoprawce()
    {
        return y-maxy/2;
    }
    public int pobierzX()
    {
        return x;
    }
    public int pobierzY()
    {
        return y;
    }
    public int podajRozmiarWybuchu()
    {
        return (krater.getHeight()/2)+5;
    }
    public int czyPlanetaŻyje() //Czy środek jest nietknięty
    {
        return kształt[(int)maxx/2][(int)maxy/2];
    }
}

