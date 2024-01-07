package fr.elpine.astre.ihm.stage;

import fr.elpine.astre.Controleur;
import fr.elpine.astre.ihm.AstreApplication;
import fr.elpine.astre.metier.objet.Affectation;
import fr.elpine.astre.metier.objet.CategorieHeure;
import fr.elpine.astre.metier.objet.Intervenant;
import fr.elpine.astre.metier.objet.Module;
import fr.elpine.astre.metier.outil.Fraction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

// TODO : renommer en affectation et non ajout ... ou creationModules
//  et faire en sorte que cette classe gère l'ajout d'affectation pour tous les types de modules

public class StageAjoutRessource extends Stage implements Initializable {
    //private Stage stage;

    private String code;
    private String semestre;
    private static ArrayList<Intervenant> listInter;

    private static ToggleGroup toggleGroup;

    private Module module;

    @FXML
    private RadioButton rbHP;
    @FXML
    private RadioButton rbAutre;
    @FXML
    private ComboBox<Intervenant> cbbInter;
    @FXML
    private TextField txtCommentaire;
    @FXML
    private TextField txtNbHeure;
    @FXML
    private ComboBox<CategorieHeure> cbbCatHeure;
    @FXML
    private TextField txtNbSemaine;
    @FXML
    private TextField txtNbGp;


    public StageAjoutRessource() // fxml -> "creationModules"
    {
        this.setTitle("Affectation");
        this.setMinWidth(600);
        this.setMinHeight(400);
    }

    /*public static Stage creer(Module module, StageSaisieRessource parent) throws IOException
    {
        Stage stage = new Stage();

        AstreApplication.refreshIcon(stage);



        FXMLLoader fxmlLoader = new FXMLLoader(StageSaisieRessource.class.getResource("creationModules.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 1000, 660);

        StageAjoutRessource stageCtrl = fxmlLoader.getController();
        if (stageCtrl != null) stageCtrl.setStage(stage);

        stage.setTitle("Affectation");
        stage.setScene(scene);

        stage.setOnCloseRequest(e -> {
            parent.refresh();
            parent.activer();
        });

        return stage;
    }

    @FXML
    private void setStage(Stage stage) { this.stage = stage; }*/
    @FXML
    public void onBtnAjouter() {
        if(rbAutre.isSelected())
            new Affectation(module, cbbInter.getValue(), cbbCatHeure.getValue(), Integer.parseInt(this.txtNbGp.getText()), Integer.parseInt(this.txtNbSemaine.getText()), this.txtCommentaire.getText());
        if(rbAutre.isSelected())
            new Affectation(module, cbbInter.getValue(), cbbCatHeure.getValue(), Fraction.valueOf(this.txtNbHeure.getText()), this.txtCommentaire.getText());

        this.close();
    }

    public void onBtnAnnuler() {this.close();}

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        this.setWidth( this.getMinWidth() );
        this.setHeight( this.getMinHeight() );

        toggleGroup = new ToggleGroup();
        rbAutre.setToggleGroup(toggleGroup);
        rbHP.setToggleGroup(toggleGroup);
        listInter = new ArrayList<>();
        listInter = Controleur.get().getMetier().getIntervenants();

        ObservableList<Intervenant> observableListInter = FXCollections.observableList(listInter);
        cbbInter.setItems(observableListInter);
        rbHP.setSelected(false);
        rbAutre.setSelected(false);

        txtNbHeure.setDisable(true);
        txtNbSemaine.setDisable(true);
        txtNbGp.setDisable(true);
        cbbCatHeure.setDisable(true);
    }

    @FXML
    public void isHp() {
        txtNbHeure.setDisable(false);

        txtNbHeure.setDisable(true);
    }
    @FXML
    public void isAutreType() {
        cbbCatHeure.setDisable(false);
        txtNbSemaine.setDisable(false);
        txtNbGp.setDisable(false);

        txtNbSemaine.setDisable(true);
        txtNbGp.setDisable(true);
        cbbCatHeure.setDisable(true);
    }

    public void setModule(Module module) {
        this.module = module;
    }
}
