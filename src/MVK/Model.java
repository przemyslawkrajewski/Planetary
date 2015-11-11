package MVK;
import javax.swing.*;
import Obiekt.Statek;
import Obiekt.Pocisk;
import Obiekt. Wybuch;
import Planeta.Planeta;
import Grafika.Wynik;
import java.io.*;
import sun.audio.*;



public class Model
{
    private



            
    Video wideo;

    Statek gracz;
    Statek[] wróg;
    Pocisk[] pocisk;
    Wybuch[] wybuch;
    Wynik[] wyniki;
    Planeta planeta;

    //Stałe
    int czasOdrodzeniaGracza; //Ile obiegów do odrodzenia gracza
    int czasNieśmiertelności; //Ile obiegów gracz jest nieśmiertelny
    int czasOdrodzeniaWroga;  //początkowa wartość za ile czasu odrodzi się wróg
    int czasŻyciaWybuchu;   //Ile obiegów trwa wybuch
    int prędkośćPocisku;  //tego nie będę tłumaczył
    int czasKońcaGry;    //Ile obiegów trwa napis koniec gry i trwa gra
    int prędkośćGracza;  //prędkość obrotowa
    
    int[] czasPrzeładowania; //czas "schłodzenia" działka
    int rozrzut;        //rozrzut do spread
    
    int wynik;      //aktyalny wynik
    int bonus;      //ile wynosi bonus
    int combo;      //ile obiegów zostało do zakończenia combo
    
    int menu;       //wybór z menu
    
    
    double kiedyOdrodziSięWróg; //kiedy się odrodzi wróg i ta wartość maleje
    double prędkośćBomb;        //też wzrasta


    int odrodzenieWroga;        //licznik stanu odradzania się wroga jeśli dojdzie do kiedy OdrodziSięWróg to ma wartość znów 0
    int odrodzenieGracza;       //podobnie tyle że z graczem i czasOdrodzeniaGracza
    int nieśmiertelnośćGracza;  //to samo -.-
    int przeładowywanie;        //etap "schładzania" działka
    int broń;                   //która broń normal machinegun spread laser
    
    int gra;                    //co się dzieje w programie 0-menu 1-gra -1-zakończenie gry -2-najlepsi
    int koniecGry;              // za ile obiegów skończy się koniecGry

    public Model()
    {                   //tworzenie wszystkich obiektów i ustawianie podstawowych parametrów
       //x=400;
      // y=400;
        nieśmiertelnośćGracza=0;
        odrodzenieGracza=-1;
        
       wróg = new Statek[20];
       pocisk = new Pocisk[20];
       wyniki = new Wynik[10];
       wybuch = new Wybuch[(wróg.length+pocisk.length)];
       czasPrzeładowania = new int[4];
       int i;
       for (i=0;i<wróg.length;i++) wróg[i]= new Statek();
       for (i=0;i<pocisk.length;i++) pocisk[i]= new Pocisk();
       for (i=0;i<wybuch.length;i++) wybuch[i]= new Wybuch();
       for (i=0;i<wyniki.length;i++) 
       {
           wyniki[i]= new Wynik();
           wyniki[i].wstawDane("",0);
       }
       gracz= new Statek();
       planeta= new Planeta();

       czasPrzeładowania[0]=30;
       czasPrzeładowania[1]=10;
       czasPrzeładowania[2]=50;
       czasPrzeładowania[3]=140;
       broń=0;
       rozrzut=20;
       
       czasOdrodzeniaGracza=500;
       czasNieśmiertelności= 300;
       czasOdrodzeniaWroga=200;
       czasŻyciaWybuchu=20;
       prędkośćPocisku=10;
       czasKońcaGry=200;
       prędkośćGracza=3;
       planszaTytułowa();
       
       
    }
    public void ustawWideo(Video nwideo)
    {
        wideo = nwideo;
    }
    public void startGry()
    {       //niszczenie obiektów resetowanie zmiennych i tworzenie początkowych obietków
       
        try {
        InputStream in = new FileInputStream("dzwiek/Start.wav");
        AudioStream as = new AudioStream(in);
        AudioPlayer.player.start(as); 
        } catch (IOException e) {}
        int i;
       for (i=0;i<wróg.length;i++)
       {
          wróg[i].zniszcz();
       }
       for  (i=0;i<pocisk.length;i++)
       {
           pocisk[i].zniszcz();
       }
       planeta.stwórz();
       gracz.stwórz(0,prędkośćGracza);
       odrodzenieWroga=0;
       odrodzenieGracza=0;
       nieśmiertelnośćGracza=0;
       kiedyOdrodziSięWróg = czasOdrodzeniaWroga;
       prędkośćBomb=1;
       wynik = 0;
       wideo.wczytajObrazki();
       gra=1;

    }

    //przechodzenie do różnych ekranów
    public void koniecGry()
    {
        gra=-1;

        koniecGry=czasKońcaGry;
    }
    public void przejdźDoMenu()
    {
        gra=0;
        menu=0;
    }
    public void najlepsi()
    {
        try{ wczytaj();}
        catch(IOException e){}
        gra=-2;
    }
    public void planszaTytułowa()
    {   //niszczenie wszystkich obietków i przygotowanie menu
       int i;
       for (i=0;i<wróg.length;i++)
       {
          wróg[i].zniszcz();
       }
       for  (i=0;i<pocisk.length;i++)
       {
           pocisk[i].zniszcz();
       }
       gracz.zniszcz();
       odrodzenieWroga=0;
       odrodzenieGracza=0;
       nieśmiertelnośćGracza=0;
       gra=0;
       menu = 0;
    }

    //przeliczanie
    public void obsłużKolizje()
    {//sprawdzanie kolizji niszczenie obiektów kolizyjnych i tworzenie wybucjów
        int i,j;
        if(czyToczySięGra()==0 && czyKończySięGra()==0) return;
        //KOLIZJA GRACZA
        if(nieśmiertelnośćGracza>0)nieśmiertelnośćGracza--;
        if(gracz.czyIstnieje()==1)
        {
            for (j=0;j<wróg.length;j++)
            {
                if(wróg[j].czyIstnieje()!=0)
                if (gracz.sprawdźKolizję(wróg[j])==1)
                    {
                        if(nieśmiertelnośćGracza<1) {gracz.zniszcz();stwórzWybuch(gracz.pobierzX(),gracz.pobierzY(),czasŻyciaWybuchu,1);odrodzenieGracza=czasOdrodzeniaGracza;}
                        {wróg[j].zniszcz();stwórzWybuch(wróg[j].pobierzX(),wróg[j].pobierzY(),czasŻyciaWybuchu,1);}
                        break;
                    }
            }
        }

        //kolizje miedzy pociskami a wrogami
        for (i=0;i<pocisk.length;i++)
        {
            if(pocisk[i].czyIstnieje()!=0)
            for (j=0;j<wróg.length;j++)
            {
                if(wróg[j].czyIstnieje()!=0)
                    if (pocisk[i].sprawdźKolizję(wróg[j])==1)
                        {
                            if(broń!=3) pocisk[i].zniszcz();//stwórzWybuch(pocisk[i].pobierzX(),pocisk[i].pobierzY(),czasŻyciaWybuchu);
                            wróg[j].zniszcz();//stwórzWybuch(wróg[j].pobierzX(),wróg[j].pobierzY(),czasŻyciaWybuchu);
                            if(broń==0 || broń==3) stwórzWybuch((wróg[j].pobierzX()+pocisk[i].pobierzX())/2,(wróg[j].pobierzY()+pocisk[i].pobierzY())/2,czasŻyciaWybuchu,1);
                            else stwórzWybuch((wróg[j].pobierzX()+pocisk[i].pobierzX())/2,(wróg[j].pobierzY()+pocisk[i].pobierzY())/2,czasŻyciaWybuchu,0);
                            if(gra==1) wynik+=50+bonus;
                            bonus+=5;
                            combo = 100;
                            break;
                        }
            }
        }
        //kolizje miedzy wrogami a wybuchami
        for (i=0;i<wróg.length;i++)
        {
            if(wróg[i].czyIstnieje()!=0)
            for (j=0;j<wybuch.length;j++)
            {
                if(wybuch[j].czyIstnieje()!=0 && wybuch[j].czyGroźny()==1)
                    if (wróg[i].sprawdźKolizję(wybuch[j])==1)
                        {
                            wróg[i].zniszcz();stwórzWybuch(wróg[i].pobierzX(),wróg[i].pobierzY(),czasŻyciaWybuchu,1);
                            break;
                        }
            }
        }//*/
        //kolizje miedzy pociskami a wybuchami
        /*for (i=0;i<pocisk.length;i++)
        {
            if(pocisk[i].czyIstnieje()!=0)
            for (j=0;j<wybuch.length;j++)
            {
                if(wybuch[j].czyIstnieje()!=0)
                    if (pocisk[i].sprawdźKolizję(wybuch[j])==1)
                        {
                            pocisk[i].zniszcz();stwórzWybuch(pocisk[i].pobierzX(),pocisk[i].pobierzY(),czasŻyciaWybuchu);
                            break;
                        }
            }
        }//*/
        
        //kolizje pocisków między sobą
        /*
        for (i=0;i<pocisk.length;i++)
        {
            if(pocisk[i].czyIstnieje()!=0)
            for (j=i+1;j<pocisk.length;j++)
            {
                if(pocisk[j].czyIstnieje()!=0)
                    if (pocisk[i].sprawdźKolizję(pocisk[j])==1)
                        {
                            pocisk[i].zniszcz();stwórzWybuch(pocisk[i].pobierzX(),pocisk[i].pobierzY(),czasŻyciaWybuchu);
                            pocisk[j].zniszcz();stwórzWybuch(pocisk[j].pobierzX(),pocisk[j].pobierzY(),czasŻyciaWybuchu);
                            break;
                        }
            }
        }//*/
        //Kolizja gracza z planetą
        if( gracz.czyIstnieje()==1 && planeta.czyKolizja(gracz.pobierzX(), gracz.pobierzY())==1 && nieśmiertelnośćGracza<1)
            {
                gracz.zniszcz();stwórzWybuch(gracz.pobierzX(),gracz.pobierzY(),czasŻyciaWybuchu,1);odrodzenieGracza=czasOdrodzeniaGracza;
                planeta.zróbKrater(gracz.pobierzX(),gracz.pobierzY());
                wideo.zróbKraterWPlanecie(gracz.pobierzX()-planeta.pobierzXPoPoprawce()
                                         ,gracz.pobierzY()-planeta.pobierzYPoPoprawce()
                                         , planeta.podajRozmiarWybuchu());
            }
        //Kolizja wroga z planetą
        for (i=0;i<wróg.length;i++)
        {
            if(wróg[i].czyIstnieje()!=0 && planeta.czyKolizja(wróg[i].pobierzX(), wróg[i].pobierzY())==1)
            {
                wróg[i].zniszcz();stwórzWybuch(wróg[i].pobierzX(),wróg[i].pobierzY(),czasŻyciaWybuchu,1);
                planeta.zróbKrater(wróg[i].pobierzX(),wróg[i].pobierzY());
                wideo.zróbKraterWPlanecie(wróg[i].pobierzX()-planeta.pobierzXPoPoprawce()
                                         ,wróg[i].pobierzY()-planeta.pobierzYPoPoprawce()
                                         , planeta.podajRozmiarWybuchu());            }
        }
        //Kolizja pocisków z planetą
        for (i=0;i<pocisk.length;i++)
        {
            if(pocisk[i].czyIstnieje()!=0 && planeta.czyKolizja(pocisk[i].pobierzX(), pocisk[i].pobierzY())==1)
            {

                if(broń==0)
                {
                pocisk[i].zniszcz();
                stwórzWybuch(pocisk[i].pobierzX(),pocisk[i].pobierzY(),czasŻyciaWybuchu,1);
                planeta.zróbKrater(pocisk[i].pobierzX(),pocisk[i].pobierzY());
                wideo.zróbKraterWPlanecie(pocisk[i].pobierzX()-planeta.pobierzXPoPoprawce()
                                         ,pocisk[i].pobierzY()-planeta.pobierzYPoPoprawce()
                                         , planeta.podajRozmiarWybuchu());
                }
                if(broń==1)
                {
                pocisk[i].zniszcz();
                stwórzWybuch(pocisk[i].pobierzX(),pocisk[i].pobierzY(),czasŻyciaWybuchu,0);
                planeta.zróbKrater(pocisk[i].pobierzX(),pocisk[i].pobierzY());
                wideo.zróbKraterWPlanecie(pocisk[i].pobierzX()-planeta.pobierzXPoPoprawce()
                                         ,pocisk[i].pobierzY()-planeta.pobierzYPoPoprawce()
                                         , planeta.podajRozmiarWybuchu());
                }
                if(broń==2)
                {
                pocisk[i].zniszcz();
                stwórzWybuch(pocisk[i].pobierzX(),pocisk[i].pobierzY(),czasŻyciaWybuchu,0);
                planeta.zróbKrater(pocisk[i].pobierzX(),pocisk[i].pobierzY());
                wideo.zróbKraterWPlanecie(pocisk[i].pobierzX()-planeta.pobierzXPoPoprawce()
                                         ,pocisk[i].pobierzY()-planeta.pobierzYPoPoprawce()
                                         , planeta.podajRozmiarWybuchu());
                }
                if(broń==3)
                {
                //pocisk[i].zniszcz();
                //stwórzWybuch(pocisk[i].pobierzX(),pocisk[i].pobierzY(),czasŻyciaWybuchu,1);
                planeta.zróbKrater(pocisk[i].pobierzX(),pocisk[i].pobierzY());
                wideo.zróbKraterWPlanecie(pocisk[i].pobierzX()-planeta.pobierzXPoPoprawce()
                                         ,pocisk[i].pobierzY()-planeta.pobierzYPoPoprawce()
                                         , planeta.podajRozmiarWybuchu());
                }
            }
        }
        if(czyToczySięGra()==1 && planeta.czyPlanetaŻyje()==0) koniecGry();
    }
    public void generujPrzeciwnika()
    {
        if(czyToczySięGra()==0) return;
        //czekamy aż nadejdzie czas odrodzenia gracza jeśli nie to idziemy dalej
        if(odrodzenieGracza>0)odrodzenieGracza--;
        if(odrodzenieGracza == 0) {gracz.stwórz(0,prędkośćGracza);odrodzenieGracza=-1;nieśmiertelnośćGracza=czasNieśmiertelności;}

        //czekamy aż odrodzi się wróg
        odrodzenieWroga++;
        if(odrodzenieWroga<kiedyOdrodziSięWróg) return;
        odrodzenieWroga=0;
        if(kiedyOdrodziSięWróg >30) kiedyOdrodziSięWróg-=5;

        int i;//szukamy wolnej przestrzeni
        for (i=0; i<wróg.length; i++)
        {
           if(wróg[i].czyIstnieje()==0) break;
        }
        if(i==wróg.length) return ;
        wróg[i].stwórz(1,(int) prędkośćBomb);
        prędkośćBomb+=1;
    }
    public void stwórzStrzał (int x2,int y2, Statek kto)
    {
        if(pobierzGracza().czyIstnieje() ==1 && pobierzStanPrzeładowania()==0 && czyToczySięGra()==1) 
        {    //Laser
            if (pobierzJakaBroń()==3) stwórzPocisk(x2 , y2-25, pobierzGracza(),3,1);
            else if (pobierzJakaBroń()==1 ||pobierzJakaBroń()==2) stwórzPocisk(x2, y2-25,pobierzGracza(),1,2);
            else stwórzPocisk(x2 , y2-25, pobierzGracza(),1,0);
            if(pobierzJakaBroń()==2)
            {
                  stwórzPocisk(x2-pobierzRozrzut(), y2-25-pobierzRozrzut(), pobierzGracza(),1,2);
                  stwórzPocisk(x2+pobierzRozrzut(), y2-25-pobierzRozrzut(), pobierzGracza(),1,2);
                  stwórzPocisk(x2-pobierzRozrzut(), y2-25+pobierzRozrzut(), pobierzGracza(),1,2);
                  stwórzPocisk(x2+pobierzRozrzut(), y2-25+pobierzRozrzut(), pobierzGracza(),1,2);
            }

            
            
            podajStanPrzeładowania(pobierzCzasPrzeładowania());
       }
    }
    public void stwórzPocisk(int x2,int y2, Statek kto,int prędkość,int rodzaj)
    {
        if(rodzaj==0)
        {
            try {
                InputStream in = new FileInputStream("dzwiek/Fire.wav");
                AudioStream as = new AudioStream(in);
                AudioPlayer.player.start(as); 
            } catch (IOException e) {}
        }
        else if(rodzaj==1)
        {
            try {
                InputStream in = new FileInputStream("dzwiek/Laser.wav");
                AudioStream as = new AudioStream(in);
                AudioPlayer.player.start(as); 
            } catch (IOException e) {}
        }
        else
        {
            try {
                InputStream in = new FileInputStream("dzwiek/Srut.wav");
                AudioStream as = new AudioStream(in);
                AudioPlayer.player.start(as); 
            } catch (IOException e) {}
        }
        int i;//gdzie jest wolne miejsce
        for (i=0; i<pocisk.length; i++)
        {
           if(pocisk[i].czyIstnieje()==0) break;
        }

        if(i==pocisk.length) return ; //tworzymy
        pocisk[i].stwórz(kto.pobierzX(),kto.pobierzY(),x2,y2,prędkośćPocisku*prędkość,rodzaj);
    }
    public void stwórzWybuch(int nx, int ny,int czas,int zagrożenie)
    {
        if(zagrożenie==1)
        {
        try {
                InputStream in = new FileInputStream("dzwiek/Wybuch.wav");
                AudioStream as = new AudioStream(in);
                AudioPlayer.player.start(as); 
            } catch (IOException e) {}
        }
        else
        {
        try {
                InputStream in = new FileInputStream("dzwiek/MWybuch.wav");
                AudioStream as = new AudioStream(in);
                AudioPlayer.player.start(as); 
            } catch (IOException e) {}
        }       
        int i;
        for (i=0; i<wybuch.length; i++)
        {           if(wybuch[i].czyIstnieje()==0) break;
        }
        if(i==wybuch.length) return ;
        wybuch[i].stwórz(nx,ny,czas,zagrożenie);
    }

    public void przelicz()
    {
        int i;
        if(przeładowywanie>0) przeładowywanie--;
        //Czy koniec gry
        if(czyToczySięGra()==1 || this.czyKończySięGra()==1)
        {
            if(koniecGry>0) //ile czasu ma wyswietlac KONIEC GRY
            {
                koniecGry--;
                if(koniecGry==0)
                {
                    najlepsi(); //wpisujemy do hall of fame
                    Wynik nowy=new Wynik("Wpisz swe imie",wynik);
                    podmieńWynik(nowy);      
                }

            }//przeliczamy wszystkie obiekty
            for (i=0;i<wróg.length;i++) wróg[i].przelicz();
            for (i=0;i<pocisk.length;i++) pocisk[i].przelicz();
            for (i=0;i<wybuch.length;i++) wybuch[i].przelicz();
            gracz.przelicz();
            if(combo>0) combo--;
            else bonus = 0;
        }
}
    public Statek pobierzWroga(int numer)
    {
        return wróg[numer];
    }
    public Statek pobierzGracza()
    {
        return gracz;
    }
    public Wybuch pobierzWybuch(int numer)
    {
        return wybuch[numer];
    }
    public Pocisk pobierzPocisk(int numer)
    {
        return pocisk[numer];
    }
    public Planeta pobierzPlanete()
    {
            return planeta;
    }
    public int pobierzMaxWrogów()
    {
        return wróg.length;
    }
    public int pobierzMaxPocisków()
    {
        return pocisk.length;
    }
    public int pobierzMaxWybuchów()
    {
        return wybuch.length;
    }
    
    public int czyIstniejeGracz()
    {
        if(gracz!=null) return 1;
        else return 0;
    }
    public int czyIstniejeWróg(int który)
    {
        if(wróg[który]!=null) return 1;
        else return 0;
    }
    public int czyIstniejePocisk(int który)
    {
        if(pocisk[który]!=null) return 1;
        else return 0;
    }
    public int czyToczySięGra()
    {
        if(gra==1) return 1;
        return 0;
    }
    public int czyJestMenu()
    {
        if(gra==0) return 1;
        return 0;
    }
    public int czyKończySięGra()
    {
        if(gra==-1) return 1;
        return 0;
    }
    public int czyJestHall()
    {
        if(gra==-2) return 1;
        return 0;
    }
    public int pobierzWynik()
    {
        return wynik;
    }
    public void menuGóra()
    {
        if(czyJestMenu()==0) return;
        menu= (menu+1);
        if(menu>2) menu = 0;
    }
    public void menuDół()
    {
        if(czyJestMenu()==0) return;
        menu= (menu-1);
        if(menu<0) menu = 2;
    }
    public int pobierzMenu()
    {
        return menu;
    }
    public Wynik pobierzWynik(int który)
    {
        return wyniki[który];
    }
    public void podajStanPrzeładowania(int n)
    {
        przeładowywanie = n;
    }
    public int pobierzStanPrzeładowania()
    {
        return przeładowywanie;
    }
    public void podajJakaBroń(int n)
    {
        broń = n;
    }
    public int pobierzJakaBroń()
    {
        return broń;
    }
    public int pobierzRozrzut()
    {
            return rozrzut;
    }
    public int pobierzCzasPrzeładowania()
    {
        return czasPrzeładowania[broń];
    }
    public int podmieńWynik(Wynik naJaki)
    {
        //try{ zapisz();}
        //catch(IOException e){}
        int i=wyniki.length-1;
        if(wyniki[i].pobierzWynik()>=naJaki.pobierzWynik()) return 0;//jeśli jesteś najgorszy to do widzenia
        String imie;
        try {
        InputStream in = new FileInputStream("dzwiek/Bravo.wav");
        AudioStream as = new AudioStream(in);
        AudioPlayer.player.start(as); 
        } catch (IOException e) {}
        imie = JOptionPane.showInputDialog("Podaj swe imię obrońco!");
        naJaki.wstawImie(imie);
        
        i--;//szukamy naszego miejsca w tablicy
        while(i>-1 && wyniki[i].pobierzWynik() < naJaki.pobierzWynik())
        {
            i--;
        }
        i++;
        Wynik tmp=new Wynik();
        while(i<10) //przesuwamy reszte
        {
            tmp.kopiujDane(wyniki[i]);
            wyniki[i].kopiujDane(naJaki);
            naJaki.kopiujDane(tmp);
            i++;
        }
        try{ zapisz();} //zapis wyników
        catch(IOException e){}
        return 1;
    }
    public void zapisz() throws IOException
    {
        
        FileWriter file = new FileWriter("score.txt");
        int i,j;
        String s;
        for (i=0;i<wyniki.length;i++) //wyniki
        {
            file.write(wyniki[i].pobierzWynik());
        }
        for (i=0;i<wyniki.length;i++) //imiona
        {
            file.write(wyniki[i].pobierzImie()+"\n");
        }
        file.close();
    }
    public void wczytaj() throws IOException
    {
        
        FileReader file = new FileReader("score.txt");
        BufferedReader bufer = new BufferedReader(file);
        int i;
        for (i=0;i<wyniki.length;i++) //wyniki
        {
            wyniki[i].wstawWynik(bufer.read());
        }
        for (i=0;i<wyniki.length;i++)//imiona
        {
            wyniki[i].wstawImie(bufer.readLine());
        }
        file.close();
    }
}
