package fr.elpine.astre.ihm.stage;

import fr.elpine.astre.Controleur;
import fr.elpine.astre.ihm.PopUp;
import fr.elpine.astre.metier.objet.Annee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StageAnnee extends Stage implements Initializable
{
    public ComboBox<Annee> cbbAnnee;
    public Button btnConsulter;
    public Button btnDupliquer;
    public Button btnSupprimer;


    public StageAnnee() // fxml -> "saisieAnnee"
    {
        this.setTitle("Choix de l'annee");
        this.setMinWidth(500);
        this.setMinHeight(150);
        this.setResizable(false);
    }

    public void setCpbContrat()
    {
        ArrayList<Annee>      lstAnnee     = Controleur.get().getMetier().getAnnees();
        ObservableList<Annee> oLstNonAnnee = FXCollections.observableList( new ArrayList<>() );
        for (Annee a : lstAnnee) if (!a.isSupprime()) oLstNonAnnee.add(a);

        this.cbbAnnee.setItems(oLstNonAnnee);

        if (Controleur.get().getMetier().getAnneeActuelle() != null)
            this.cbbAnnee.setValue(Controleur.get().getMetier().getAnneeActuelle());

        boolean disabled = this.cbbAnnee.getItems().isEmpty();

        this.cbbAnnee.setDisable( disabled );
        this.btnConsulter.setDisable( disabled );
        this.btnDupliquer.setDisable( disabled );
        this.btnSupprimer.setDisable( disabled );
    }

    public void onBtnAjouter() {
        //StageAjouterAnnee.creer( this ).show();
        Stage stage = Manager.creer("ajouterAnnee", this);

        stage.showAndWait();
        this.setCpbContrat();
    }

    public void onBtnConsulter() {
        Annee an = this.cbbAnnee.getValue();

        Controleur.get().getMetier().changerAnneeActuelle(an);

        this.close();
    }

    public void onBtnDupliquer() {
        Annee an = this.cbbAnnee.getValue();

        LocalDate dateDebDef = an.getDateDeb().toLocalDate();
        LocalDate dateFinDef = an.getDateFin().toLocalDate();

        PopUp.textInputDialog(
                this.cbbAnnee.getValue().toString(),
                "Dupliquer",
                String.format("Copie de l'année : %s", this.cbbAnnee.getValue().toString()),
                "Nouveau nom :"
        ).showAndWait().ifPresent(nom -> {
            Pattern pattern = Pattern.compile("^(\\d{4})-(\\d{4}).*$");
            Matcher matcher = pattern.matcher(nom);

            if ( matcher.find() && !Controleur.get().getMetier().existeAnnee(nom) && Integer.parseInt(matcher.group(2)) - Integer.parseInt(matcher.group(1)) == 1 ) {
                Date debut = Date.valueOf(String.format("%s-%s-%s", matcher.group(1), dateDebDef.getDayOfMonth(), dateDebDef.getMonthValue()));
                Date fin   = Date.valueOf(String.format("%s-%s-%s", matcher.group(2), dateFinDef.getDayOfMonth(), dateFinDef.getMonthValue()));

                an.dupliquer(nom, debut, fin);
            }
            else
                PopUp.warning("Nom incorrect", null, "Le nom '%s' n'est pas valide.".formatted(nom)).showAndWait();
        });

        this.setCpbContrat();
    }

    public void onBtnSupprimer()
    {
        Annee an = this.cbbAnnee.getValue();

        Optional<ButtonType> result = PopUp.confirmation("Confirmation", null, String.format("Voulez vous supprimer l'année \"%s\" ?", an.getNom())).showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK)
        {
            boolean good = an.supprimer( false );

            if (!good)
            {
                Optional<ButtonType> resultSecond = PopUp.confirmation(
                        "Confirmation",
                        "La suppression est impossible, il y a encore des données présente dans cette année !",
                        String.format("Supprimer l'année \"%s\" et tout son contenu ?", an.getNom())
                ).showAndWait();

                if (resultSecond.isPresent() && resultSecond.get() == ButtonType.OK) {
                    an.supprimer(true);

                    Controleur.get().getMetier().enregistrer();
                }
            }
        }

        this.setCpbContrat();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.setWidth( this.getMinWidth() );
        this.setHeight( this.getMinHeight() );

        this.setCpbContrat();
    }

    public void onBtnFermer()
    {
        this.close();
    }
}
