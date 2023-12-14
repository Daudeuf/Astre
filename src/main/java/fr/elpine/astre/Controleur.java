package fr.elpine.astre;

import fr.elpine.astre.ihm.AstreApplication;
import fr.elpine.astre.ihm.stage.StageInitBd;
import fr.elpine.astre.metier.Astre;
import fr.elpine.astre.metier.DB;
import javafx.application.Application;

import java.io.IOException;

public class Controleur
{
    private static Controleur ctrl;

    private DB    db;
    private Astre astre;

    private Controleur()
    {
        this.db         = new DB();
        this.astre      = new Astre( this );
    }

    public static Controleur get()
    {
        if (Controleur.ctrl == null) Controleur.ctrl = new Controleur();

        return Controleur.ctrl;
    }

    public static void lancementInterface(int code) {
        AstreApplication.init(code);
        Application.launch(AstreApplication.class);
    }

    public DB getDb() { return this.db; }

    public static void main(String[] args)
    {
        Controleur.get();
    }
}
