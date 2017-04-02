package fr.cils.projet.stage;

import fr.cils.projet.stage.dao.Dao;
import fr.cils.projet.stage.dao.EntrepriseDao;
import fr.cils.projet.stage.dao.OffreStageDao;
import fr.cils.projet.stage.entity.Entreprise;
import fr.cils.projet.stage.entity.OffreStage;
import fr.cils.projet.stage.ui.SuccessAlert;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.time.LocalDate;

/**
 * Created by infol3-70 on 09/03/17.
 */
public class ControllerOffre
{
    @FXML
    private Button envoyerOffre;
    @FXML
    private Button annulerOffre;
    @FXML
    private ComboBox<String> nomEntr;
    @FXML
    private TextField domOffre;
    @FXML
    private TextField intitule;
    @FXML
    private DatePicker dateDeb;
    @FXML
    private TextField duree;
    @FXML
    private TextField descr;

    OffreStageDao dao;

    public ControllerOffre()
    {
        this.dao = new OffreStageDao();
    }

    @FXML
    public void initialize()
    {
        Platform.runLater(() -> nomEntr.requestFocus());
    }

    public void checkIfEnterPressed(KeyEvent e)
    {
        if(e.getCode() == KeyCode.ENTER)
            creerOffre(null);
    }

    public void clear(ActionEvent actionEvent)
    {
        nomEntr.getEditor().clear();
        domOffre.clear();
        intitule.clear();
        dateDeb.getEditor().clear();
        duree.clear();
        descr.clear();
    }

    public void creerOffre(ActionEvent actionEvent)
    {
        final Boolean requiredFieldEmpty =
                nomEntr.getValue() == null || domOffre.getText().isEmpty() ||
                        domOffre.getText().isEmpty() || intitule.getText().isEmpty() ||
                        dateDeb.getEditor().getText().isEmpty() || duree.getText().isEmpty() ||
                        descr.getText().isEmpty();
        if(requiredFieldEmpty)
        {
            Alert missingFieldAlert = new Alert(Alert.AlertType.WARNING,
                    "Certains champs n'ont pas été remplis ! ");
            missingFieldAlert.showAndWait();
            return;
        }

        Dao<Entreprise> entrepriseDao = new EntrepriseDao();
        Boolean estValide = true;
        String nomEntreprise = nomEntr.getValue();
        String domaineOffre = domOffre.getText();
        String titre = intitule.getText();
        LocalDate dateDebut = dateDeb.getValue();
        int temps = Integer.parseInt(duree.getText());
        String description = descr.getText();

        if(dateDebut.compareTo(dateDebut.minusDays(1)) > 0) estValide = false;
        
        OffreStage offre = new OffreStage(titre, description, domaineOffre,
                dateDebut, temps, estValide);
        if(this.dao.create(offre) != null)
        {
            SuccessAlert successPopup = new SuccessAlert(
                    "L'offre a bien été créée !");
            successPopup.showAndWait();
            clear(null);
        }
        else
        {
            Alert errorPopup = new Alert(Alert.AlertType.ERROR,
                    "Une erreur est survenue lors de l'ajout de cette offre...");
            errorPopup.showAndWait();
        }

    }
}
