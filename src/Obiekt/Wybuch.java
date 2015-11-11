
package Obiekt;


public class Wybuch extends Obiekt
{
    private
    int zagrożenie; //czy stanowi zagrożenie 
    int czasŻycia;
    int faza; //która klatka animacji

     public void stwórz(int nx, int ny, int czas,int nzagrożenie)
     {
         zagrożenie=nzagrożenie;
         zbroja=1;
         x=nx;
         y=ny;
         czasŻycia=czas;
         ustawWymiary("Boom3100.png");
     }
     public void przelicz()
     {
         if(czyIstnieje()==0) return;
         czasŻycia--;
         if(czasŻycia<1) {zniszcz();return;}
         if(czasŻycia<20) faza=0;
         if(czasŻycia<12) faza=1;
         if(czasŻycia<6) faza=2;
         //if(czasŻycia%2==1) ustawObrazek("Wybuch.png");
         //if(czasŻycia%2==0) ustawObrazek("Wybuch2.png");
     }
     public int pobierzFaze()
     {
         return faza;
     }
     public int czyGroźny()
     {
         return zagrożenie;
     }
}
