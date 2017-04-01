package fr.cils.projet.stage;

import fr.cils.projet.stage.dao.EntrepriseDao;
import fr.cils.projet.stage.entity.Entreprise;
import fr.cils.projet.stage.ui.SuccessAlert;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Created by infol3-70 on 09/03/17.
 */
public class ControllerEntreprise
{

    @FXML
    private TextField nomEntr;
    @FXML
    private TextField adresse;
    @FXML
    private TextField codePostal;
    @FXML
    private TextField ville;
    @FXML
    private TextField mail;
    @FXML
    private TextField tel;
    @FXML
    private TextField secteur;

    EntrepriseDao dao;

    public ControllerEntreprise()
    {
        this.dao = new EntrepriseDao();
    }

    public void initialize()
    {
        Platform.runLater(() -> nomEntr.requestFocus());
    }

    public void checkIfEnterPressed(KeyEvent e)
    {
        if(e.getCode() == KeyCode.ENTER)
            creerEntreprise(null);
    }

    public void clear(ActionEvent actionEvent)
    {
        nomEntr.clear();
        adresse.clear();
        codePostal.clear();
        ville.clear();
        mail.clear();
        tel.clear();
        secteur.clear();
    }

    public void creerEntreprise(ActionEvent actionEvent)
    {
        final Boolean requiredFieldEmpty =
                nomEntr.getText().isEmpty() || adresse.getText().isEmpty() ||
                        codePostal.getText().isEmpty() || ville.getText().isEmpty() ||
                        tel.getText().isEmpty() || secteur.getText().isEmpty();
        if(requiredFieldEmpty)
        {
            Alert missingFieldAlert = new Alert(Alert.AlertType.WARNING,
                    "Certains champs obligatoires n'ont pas été remplis ! ");
            missingFieldAlert.showAndWait();
            return;
        }

        Entreprise entreprise = new Entreprise(nomEntr.getText(), mail.getText(), ville.getText(), adresse.getText(),
                codePostal.getText(), tel.getText(), secteur.getText());

        if(dao.create(entreprise) != null)
        {
            SuccessAlert successPopup = new SuccessAlert(
                    "L'entreprise a bien été créée !");
            successPopup.showAndWait();
            clear(null);
        }
        else
        {
            Alert errorPopup = new Alert(Alert.AlertType.ERROR,
                    "Une erreur est survenue lors de l'ajout de cette entreprise...");
            errorPopup.showAndWait();
        }
    }
}
