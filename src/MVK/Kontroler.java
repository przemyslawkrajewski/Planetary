package MVK;


import java.awt.event.*;
import javax.swing.*;

public class Kontroler  implements MouseListener,ActionListener,KeyListener
{
    private
    Model model;
    Video wideo;
    Timer t;
    int xm,ym;
    int n;
    public Kontroler(Model nmodel,Video nwideo)
    {
        wideo=nwideo;
        model=nmodel;
        t = new Timer(14, this);
        t.start();
    }

    public void mouseClicked(MouseEvent e)  {}
    public void mouseEntered(MouseEvent e)  {}
    public void mouseExited(MouseEvent e)   {}
    public void mouseReleased(MouseEvent e) {n=0;}  //Do dalszego rozwoju
    public void mousePressed(MouseEvent e)//Tworzymy se nowy pocisk który ma leciec w kierunku myszki
    {
        n=1;
        ym=e.getY();
        xm=e.getX();
        model.stwórzStrzał(xm, ym, model.pobierzGracza());
        /*if(model.pobierzGracza().czyIstnieje() ==1 && model.pobierzStanPrzeładowania()==0) 
        {    
            if (model.pobierzJakaBroń()==3) model.stwórzPocisk(xm , ym-25, model.pobierzGracza(),3,1);
            else if (model.pobierzJakaBroń()==1 ||model.pobierzJakaBroń()==2) model.stwórzPocisk(e.getX(), e.getY()-25, model.pobierzGracza(),1,2);
            else model.stwórzPocisk(xm , ym-25, model.pobierzGracza(),1,0);
            if(model.pobierzJakaBroń()==2)
            {
                  model.stwórzPocisk(e.getX()-model.pobierzRozrzut(), e.getY()-25-model.pobierzRozrzut(), model.pobierzGracza(),1,2);
                  model.stwórzPocisk(e.getX()+model.pobierzRozrzut(), e.getY()-25-model.pobierzRozrzut(), model.pobierzGracza(),1,2);
                  model.stwórzPocisk(e.getX()-model.pobierzRozrzut(), e.getY()-25+model.pobierzRozrzut(), model.pobierzGracza(),1,2);
                  model.stwórzPocisk(e.getX()+model.pobierzRozrzut(), e.getY()-25+model.pobierzRozrzut(), model.pobierzGracza(),1,2);
            }

            
            
            model.podajStanPrzeładowania(model.pobierzCzasPrzeładowania());
        }*/
    }

    //Timer!
    public void actionPerformed(ActionEvent e)
    {
        if(n==1) 
        {
            model.stwórzStrzał(xm, ym, model.pobierzGracza());
        }
         
        //if(n==1) model.stwórzPocisk(xm, ym-25, model.pobierzGracza());
        model.obsłużKolizje();
        model.przelicz();   //przelicza w grze obiekty i w menu
        model.generujPrzeciwnika(); //Gdy gra się nie odbywa to nie ma sensu tworzyć przeciwników
        wideo.repaint();
    }
    public void keyReleased(KeyEvent e){}
    public void keyPressed(KeyEvent e)
    {
        int i=0;
        //any key w najlepszych
        if(model.czyJestHall()==1) {model.przejdźDoMenu();i=1;}
        //Obsługa menu
        if(e.getKeyCode() == e.VK_UP) {model.menuDół();}
        if(e.getKeyCode() == e.VK_DOWN) {model.menuGóra();}
        if(e.getKeyCode() == e.VK_ENTER)
        {
            if(model.czyJestMenu()==0) return;
            if(model.pobierzMenu()==0)  if(i==0)model.startGry();
            if(model.pobierzMenu()==1)  model.najlepsi();
            if(model.pobierzMenu()==2)  System.exit(0);
        }
        //Wybór broni
        if(e.getKeyCode() == e.VK_1) model.podajJakaBroń(0);
        if(e.getKeyCode() == e.VK_2) model.podajJakaBroń(1);
        if(e.getKeyCode() == e.VK_3) model.podajJakaBroń(2);
        if(e.getKeyCode() == e.VK_4) model.podajJakaBroń(3);
        //rezygnacja z gry
        if(e.getKeyCode() == e.VK_ESCAPE)
        {
            if(model.czyToczySięGra()==1) model.koniecGry();
        }
    }
    public void keyTyped(KeyEvent e){}
}
