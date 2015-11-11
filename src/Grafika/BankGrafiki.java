package Grafika;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;

public class BankGrafiki
{
        BufferedImage planeta;
        BufferedImage satelita;
        BufferedImage bomba1,bomba2;
        BufferedImage pocisk;
        BufferedImage laser;
        BufferedImage śrut;
        BufferedImage []wybuch;
        BufferedImage []mwybuch;
        BufferedImage []numer;
        
        
        public void wcztajObrazki()
        {
        try
        {
            numer = new BufferedImage[10];
            wybuch = new BufferedImage[3];
            mwybuch = new BufferedImage[3];
            satelita = ImageIO.read(new File("grafika/" + "Satelite.png"));
            bomba1 = ImageIO.read(new File("grafika/" + "Bomb.png"));
            bomba2 = ImageIO.read(new File("grafika/" + "Bomb.png"));
            pocisk = ImageIO.read(new File("grafika/" + "Bullet.png"));
            laser = ImageIO.read(new File("grafika/" + "Laser.png"));  
            śrut = ImageIO.read(new File("grafika/" + "Srut.png"));  
            wybuch[0] = ImageIO.read(new File("grafika/" + "Boom3100.png"));
            wybuch[1] = ImageIO.read(new File("grafika/" + "Boom2100.png"));
            wybuch[2] = ImageIO.read(new File("grafika/" + "Boom1100.png"));
            mwybuch[0] = ImageIO.read(new File("grafika/" + "Boom3.png"));
            mwybuch[1] = ImageIO.read(new File("grafika/" + "Boom2.png"));
            mwybuch[2] = ImageIO.read(new File("grafika/" + "Boom1.png"));
            planeta = ImageIO.read(new File("grafika/" + "PlanetaIdioty.png"));      
            numer[0] = ImageIO.read(new File("grafika/" + "0.bmp"));
            numer[1] = ImageIO.read(new File("grafika/" + "1.bmp"));
            numer[2] = ImageIO.read(new File("grafika/" + "2.bmp"));
            numer[3] = ImageIO.read(new File("grafika/" + "3.bmp"));
            numer[4] = ImageIO.read(new File("grafika/" + "4.bmp"));
            numer[5] = ImageIO.read(new File("grafika/" + "5.bmp"));
            numer[6] = ImageIO.read(new File("grafika/" + "6.bmp"));
            numer[7] = ImageIO.read(new File("grafika/" + "7.bmp"));
            numer[8] = ImageIO.read(new File("grafika/" + "8.bmp"));
            numer[9] = ImageIO.read(new File("grafika/" + "9.bmp"));

        }
        catch (IOException e) {System.out.println("Grafa się skopciła!");}
        
        }
        public BufferedImage pobierzObrazekPlanety()
        {
            return planeta;
        }
        public BufferedImage pobierzObrazekGracza()
        {
            return satelita;
        }
        public BufferedImage pobierzObrazekPocisku()
        {
            return pocisk;
        }
        public BufferedImage pobierzObrazekLasera()
        {
            return laser;
        }
        public BufferedImage pobierzObrazekŚrutu()
        {
            return śrut;
        }
        public BufferedImage pobierzObrazekWroga(int który)
        {
            if(który==0) return bomba1;
            return bomba1;
        }
        public BufferedImage pobierzObrazekWybuchu(int klatka)
        {
            if(klatka >=0&& klatka<3) return wybuch[klatka];
            return null;
        }
        public BufferedImage pobierzObrazekMWybuchu(int klatka)
        {
            if(klatka >=0&& klatka<3) return mwybuch[klatka];
            return null;
        }
        public BufferedImage pobierzObrazeknumeru(int który)
        {
            return numer[który];
        }

        public void zróbKraterWPlanecie(int x, int y, int r)
        {
        Graphics g;         //Malujemy czarne koło w planecie
        g=planeta.createGraphics();
        g.setColor(Color.BLACK);
        g.fillOval(x-r, y-r, 2*r, 2*r);
        }
}
