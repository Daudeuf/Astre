package fr.elpine.astre.ihm.stage;

import fr.elpine.astre.Controleur;
import fr.elpine.astre.metier.objet.CategorieIntervenant;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class StageAjouterCategories
{
    private Stage stage;

    private TextField txtfCodeCatInter;
    private TextField txtfNomCatInter;
    private TextField txtfRatioCatInter;
    private TextField txtfNbHMCatInter;
    private TextField txtfNbHServCatInter;


    static StageAccueilConfig parent;



    public static Stage creer( StageAccueilConfig parent ) throws IOException
    {
        Stage stage = new Stage();
        StageAjouterCategories.parent = parent;

        FXMLLoader fxmlLoader = new FXMLLoader(StagePrincipal.class.getResource("ajouterCategories.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 600, 400);

        StageAjouterCategories stageCtrl = fxmlLoader.getController();
        if (stageCtrl != null) stageCtrl.setStage(stage);

        stage.setTitle("Accueil Config");
        stage.setScene(scene);

        stage.setOnCloseRequest(e -> {
            parent.activer();
        });

        return stage;
    }

    private void setStage(Stage stage) { this.stage = stage; }

    public void onBtnEnregistrerCatInter(ActionEvent actionEvent)
    {
        String code    =                     txtfCodeCatInter   .getText();
        String nom     =                     txtfNomCatInter    .getText();
        double ratioTD = Double .parseDouble(txtfRatioCatInter  .getText());
        int    nbHM    = Integer.parseInt   (txtfNbHMCatInter   .getText());
        int    nbHServ = Integer.parseInt   (txtfNbHServCatInter.getText());

        if(estValide(code,nom))
            Controleur.get().getDb().ajouterCategorieIntervenant(new CategorieIntervenant(code, nom,nbHM,nbHServ,ratioTD));

        parent.activer();
        stage.close();
    }


    public boolean estValide(String code, String nom)
    {
        if(code == null  && nom == null)
            return false;
        return true;
    }

    public void onBtnAnnulerCatInter(ActionEvent actionEvent) {
        parent.activer();
        stage.close();
    }

    public void onBtnEnregistrerCatH(ActionEvent actionEvent) {
        parent.activer();
        stage.close();
    }

    public void onBtnAnnulerCatH(ActionEvent actionEvent) {
        parent.activer();
        stage.close();
    }
}