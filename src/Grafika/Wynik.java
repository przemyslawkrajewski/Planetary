package Grafika;


public class Wynik
{
    String imie;
    int wynik;

    public Wynik()
    {
        imie="";
        wynik=0;
    }

    public Wynik(String nimie,int nwynik)
    {
        imie=nimie;
        wynik=nwynik;
    }

    public void wstawDane(String nimie,int nwynik)
    {
        imie=nimie;
        wynik=nwynik;
    }
    public void wstawWynik(int nwynik)
    {
        wynik=nwynik;
    }
    public void wstawImie(String nimie)
    {
        imie=nimie;
    }
    public void kopiujDane(Wynik wzór)
    {
        imie=wzór.pobierzImie();
        wynik=wzór.pobierzWynik();
    }
    public String pobierzImie()
    {
        return imie;
    }
    public int pobierzWynik()
    {
        return wynik;
    }
}
