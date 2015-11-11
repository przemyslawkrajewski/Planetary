package planetarydefence;

import MVK.*;
import javax.swing.*;



public class PlanetaryDefence
{
    private
    Timer t;
    Video wideo;
    Kontroler kontroler;
    Model model;
    JFrame okno;
    public void Start()
    {
        okno = new JFrame("Planetary Defence");
        model = new Model();
        wideo= new Video(model);
        model.ustawWideo(wideo);
        kontroler= new Kontroler(model,wideo);
        okno.add(wideo);
        okno.addMouseListener(kontroler);
        okno.addKeyListener(kontroler);
        wideo.init(okno);
    }

}