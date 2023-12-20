package fr.elpine.astre.ihm.stage;

import fr.elpine.astre.Controleur;
import fr.elpine.astre.ihm.stage.PopUp.StagePopUp;
import fr.elpine.astre.metier.objet.CategorieIntervenant;
import fr.elpine.astre.metier.objet.Intervenant;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class StageAjoutIntervenant
{
    @FXML
    private TextField txtfRatio;
    @FXML
    private Label lblErreur;
    private Stage stage;
    @FXML
    private TextField txtNom;
    @FXML
    private TextField txtPrenom;
    @FXML
    private ComboBox  cpbContrat;
    @FXML
    private TextField txtService;
    @FXML
    private TextField txtComplementaire;
    @FXML
    private TextField txtEmail;


    private static StageIntervenant parent;

    public static Stage creer( StageIntervenant parent) throws IOException
    {
        Stage stage = new Stage();
        StageAjoutIntervenant.parent = parent;

        FXMLLoader fxmlLoader = new FXMLLoader(StageAjoutIntervenant.class.getResource("saisieIntervenant.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 450);

        StageAjoutIntervenant stagectrl = fxmlLoader.getController();

        if(stagectrl != null) stagectrl.setStage(stage);

        stage.setTitle("Ajout Intervenant");
        stage.setScene(scene);
        stagectrl.setCpbContrat();

        stage.setOnCloseRequest(e -> {
            parent.activer();
        });

        return stage;
    }

    private void setStage(Stage stage) { this.stage = stage; }

    public void setCpbContrat()
    {
        ObservableList<CategorieIntervenant> enscatInter = FXCollections.observableList(Controleur.get().getDb().getAllCategorieIntervenant());
        cpbContrat.setItems(enscatInter);
    }

    public void onBtnValider(ActionEvent actionEvent)
    {
            String nom                  = txtNom   .getText();
            String prenom               = txtPrenom.getText();
            String email                = txtEmail .getText();


            CategorieIntervenant statut = null;
            try {
                statut = (CategorieIntervenant) cpbContrat.getValue();
                if ( statut == null ) { throw new Exception(); }
            } catch (Exception e) {
                StagePopUp.PopUpErreur("Catégorie Intervenant", "Une erreurs est survenue avec la sélection de \"Catégorie Intervenant\".");
                return;
            }

            int heureService = -1;
            try {
                heureService = Integer.parseInt(txtService.getText());
            } catch (Exception e) {
                StagePopUp.PopUpErreur("Heures Services", "Une erreurs est survenue avec les \"Heures de Services\".");
                return;
            }

            int total = -1;
            try {
                total = Integer.parseInt(txtComplementaire.getText()) + heureService;
            } catch (Exception e) {
                StagePopUp.PopUpErreur("Heures Totales", "Une erreurs est survenue avec le nombre \"Heures totales\".");
                return;
            }

            String ratio = "-1";
            try {
                ratio                   = txtfRatio.getText();
                if ( !ratio.matches("^(0*(0(\\.\\d+)?|0\\.[0-9]*[1-9]+)|0*([1-9]\\d*|0)\\/[1-9]\\d*)$") ) { throw new Exception(); }
            } catch (Exception e) {
                StagePopUp.PopUpErreur("Ratio", "Une erreurs est survenue avec le \"Ratio\".");
                return;
            }

            Intervenant inter = Intervenant.creerIntervenant(nom,prenom,email,statut,heureService,total,ratio);
            if (inter == null)
            {
                StagePopUp.PopUpErreur("Email", "Une erreurs est survenue avec l'\" Adresse Mail\".");
                return;
            }
            StageIntervenant.interAAjouter.add(inter);

            parent.refresh();
            this.stage.close();
            parent.activer();
    }

    public void btnAnnuler(ActionEvent actionEvent) throws IOException
    {
        this.stage.close();
        parent.activer();
    }
}
