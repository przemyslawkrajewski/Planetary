package MVK;

import Obiekt.Statek;
import Obiekt.Pocisk;
import Obiekt.Wybuch;
import Planeta.Planeta;
import Grafika.BankGrafiki;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;
import java.awt.geom.AffineTransform;


public class Video extends JPanel
{
    private
    Font czcionka;
    BankGrafiki bank;;
    BufferedImage tytułowaStrona;
    BufferedImage tło;
    BufferedImage koniecGry;
    BufferedImage nowaGra;
    BufferedImage najlepsi;
    BufferedImage koniec;
    BufferedImage hall;
    BufferedImage bufor;
    int x;
    Model model;
    public Video(Model nmodel)
    {
       
       czcionka= new Font("sansserif",Font.BOLD,32);
       model = nmodel;
       bank = new BankGrafiki();
       bank.wcztajObrazki();
       try
       {
           koniecGry = ImageIO.read(new File("grafika/KoniecGry.png"));
           tło = ImageIO.read(new File("grafika/Plansza.bmp"));
           tytułowaStrona = ImageIO.read(new File("grafika/Title.bmp"));
           nowaGra= ImageIO.read(new File("grafika/nowagra.jpg"));
           koniec= ImageIO.read(new File("grafika/koniec.jpg"));
           najlepsi= ImageIO.read(new File("grafika/najlepsi.jpg"));
           hall = ImageIO.read(new File("grafika/Hall.bmp"));
           bufor = ImageIO.read(new File("grafika/Plansza.bmp"));
       } catch (IOException e) {System.out.println("Nie ma obrazkow");}
    }
    public void init( JFrame f)
    {
      
       f.pack();
       f.setVisible(true);
       f.setResizable(false);
       f.addWindowListener(new WindowAdapter(){public void windowClosing(WindowEvent e) {System.exit(0);}});
    }
    public void paint(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
              RenderingHints rh =
            new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                               RenderingHints.VALUE_ANTIALIAS_ON);

      rh.put(RenderingHints.KEY_RENDERING,
             RenderingHints.VALUE_RENDER_QUALITY);

      g2.setRenderingHints(rh);
     // Graphics g =  bufor.getGraphics();
       if(model.czyJestMenu()==1) //Menu
       {
         g2.drawImage(tytułowaStrona, 0, 0, this);
         g2.drawImage(nowaGra,70,300,this);
         g2.drawImage(najlepsi,70,400,this);
         g2.drawImage(koniec,70,500,this);
         g2.drawImage(bank.pobierzObrazekGracza(), 20, 290+model.pobierzMenu()*100, this);
         g2.setColor(Color.white);
         g2.setFont(czcionka);
       }
       else if(model.czyJestHall()==1) //Hall of shame
       { //tło & wyniki
           int i;
           String napis;
            g.setColor(Color.MAGENTA);
            g.setFont(czcionka);
           g2.drawImage(hall, 0, 0, this);
           for (i=0;i<10;i++)
           {
               g2.drawString(model.pobierzWynik(i).pobierzImie(), 100, 200+i*30);
               napis = ""+model.pobierzWynik(i).pobierzWynik();
               g2.drawString(napis, 400, 200+i*30);
           }

       }
       else//Gra
       {//najpierw tło potem poszczególne obiekty a na koniec wynik aktualny i ew napis KONIEC GRY
         int i;
         g.drawImage(tło, 0, 0, this);
         Planeta planeta = model.pobierzPlanete();
         g.drawImage(bank.pobierzObrazekPlanety(),planeta.pobierzXPoPoprawce(),planeta.pobierzYPoPoprawce(), this);

         Statek obiekt;
         BufferedImage obrazek;
         Graphics2D wzór;
         for(i=0;i<model.pobierzMaxWrogów();i++)
         {
            obiekt=model.pobierzWroga(i);
            if(obiekt.czyIstnieje()==0) continue;
            //wzór = bank.pobierzObrazekWroga(0).createGraphics();
            AffineTransform affineTransform = new AffineTransform();
            affineTransform.setToTranslation(obiekt.pobierzXPoPoprawce(), obiekt.pobierzYPoPoprawce() );
            affineTransform.rotate(1.57-obiekt.pobierzKąt(),obiekt.pobierzSzerokość(),obiekt.pobierzWysokość() );
            //g2.drawImage(bank.pobierzObrazekWroga(0),obiekt.pobierzXPoPoprawce(), obiekt.pobierzYPoPoprawce(), this);
            g2.drawImage(bank.pobierzObrazekWroga(0), affineTransform, this);
            
         }
         obiekt=model.pobierzGracza();
         if(obiekt.czyIstnieje()==1) 
         {
             AffineTransform affineTransform = new AffineTransform();
             affineTransform.setToTranslation(obiekt.pobierzXPoPoprawce(), obiekt.pobierzYPoPoprawce() );
             affineTransform.rotate(1.57-obiekt.pobierzKąt(),obiekt.pobierzSzerokość(),obiekt.pobierzWysokość() );
             g2.drawImage(bank.pobierzObrazekGracza(), affineTransform, this);
         }
         Pocisk pocisk;
         for(i=0;i<model.pobierzMaxPocisków();i++)
         {
            pocisk=model.pobierzPocisk(i);
            if(pocisk.czyIstnieje()==0) continue;
            AffineTransform affineTransform = new AffineTransform();
            affineTransform.setToTranslation(pocisk.pobierzXPoPoprawce(), pocisk.pobierzYPoPoprawce() );
            if(pocisk.czyPrawo()==1) affineTransform.rotate(pocisk.pobierzKąt()+3.14,pocisk.pobierzSzerokość(),pocisk.pobierzWysokość() );
            else affineTransform.rotate(-pocisk.pobierzKąt(),pocisk.pobierzSzerokość(),pocisk.pobierzWysokość() );
            if(pocisk.pobierzRodzaj()==0) g2.drawImage(bank.pobierzObrazekPocisku(), affineTransform, this);            
            else if(pocisk.pobierzRodzaj()==1) g2.drawImage(bank.pobierzObrazekLasera(), affineTransform, this);
            else g2.drawImage(bank.pobierzObrazekŚrutu(), affineTransform, this);
            //g2.drawImage(bank.pobierzObrazekPocisku(),pocisk.pobierzXPoPoprawce(), pocisk.pobierzYPoPoprawce(), this);
         }
         Wybuch wybuch;
         for(i=0;i<model.pobierzMaxWybuchów();i++)
         {
            wybuch=model.pobierzWybuch(i);
            if(wybuch.czyIstnieje()==0) continue;
            if(wybuch.czyGroźny()==1)g2.drawImage(bank.pobierzObrazekWybuchu(wybuch.pobierzFaze()),wybuch.pobierzXPoPoprawce(), wybuch.pobierzYPoPoprawce(), this);
            else g2.drawImage(bank.pobierzObrazekMWybuchu(wybuch.pobierzFaze()),wybuch.pobierzXPoPoprawce()+35, wybuch.pobierzYPoPoprawce()+35, this);
         }
         {
             int x=520;
             int y=530;
             int numer=model.pobierzWynik();
             for (i=0;i<8;i++)
             {
                 g2.drawImage(bank.pobierzObrazeknumeru(numer%10),x,y,this);
                 numer = numer/10;
                 x=x-40;
             }
         }
         if(model.czyKończySięGra()==1)
         {
             g2.drawImage(koniecGry, 295, 250, this);
         }
       }
       //Image p = bufor.getScaledInstance(this.getWidth(), this.getHeight(), java.awt.Image.SCALE_DEFAULT);
      // grafika.drawImage(p, 0, 0, this);
    }

    public Dimension getPreferredSize()
    {
        if (tytułowaStrona == null) {return new Dimension(100,100);}
        else {return new Dimension(tytułowaStrona.getWidth(null)-10, tytułowaStrona.getHeight(null)-10);}
    }
    public void zróbKraterWPlanecie(int x,int y,int r)
    {
        bank.zróbKraterWPlanecie(x, y, r);
    }
    public void wczytajObrazki()
    {
        bank.wcztajObrazki();
    }
}