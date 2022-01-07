package application;

import controleur.Controleur;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application
{
    @Override
    public void start(Stage stage) throws Exception
    {
        Controleur controleur = new Controleur(stage);
        controleur.run();
    }
}
