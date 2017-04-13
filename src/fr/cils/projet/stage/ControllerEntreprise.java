package fr.cils.projet.stage;

import fr.cils.projet.stage.dao.EntrepriseDao;
import fr.cils.projet.stage.entity.Entreprise;
import fr.cils.projet.stage.entity.Role;
import fr.cils.projet.stage.entity.Utilisateur;
import fr.cils.projet.stage.ui.SuccessAlert;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

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
    @FXML
    private GridPane tableauEntreprises;
    @FXML
    private ToggleGroup groupeRadioListe;
    @FXML
    private Button goModifier;
    @FXML
    private Button modifierEntreprise;
    @FXML
    private Button supprimerEntreprise;

    private Entreprise entreprise;
    EntrepriseDao dao;

    public ControllerEntreprise()
    {
        this.dao = new EntrepriseDao();
    }

    public void initialize()
    {
        Platform.runLater(() -> nomEntr.requestFocus());
        if (tableauEntreprises != null) afficherListeEntreprises();
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

    public void modifierEntreprise()
    {
        this.entreprise.raisonSociale = nomEntr.getText();
        this.entreprise.rue = adresse.getText();
        this.entreprise.codePostal = codePostal.getText();
        this.entreprise.ville = ville.getText();
        this.entreprise.mail = mail.getText();
        this.entreprise.tel = tel.getText();
        this.entreprise.secteurActivite = secteur.getText();

        dao.update(this.entreprise);

        Stage stage = null;
        Parent root = null;
        try
        {
            stage = (Stage) modifierEntreprise.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("ui/liste-entreprise.fxml"));
        }catch(IOException e){e.printStackTrace();}

        Controller.changerMenuPrincipal(stage, root);
    }

    public void supprimerEntreprise()
    {
        int id = (int) groupeRadioListe.getSelectedToggle().getUserData();
        dao.delete(dao.find(id));

        Stage stage = null;
        Parent root = null;
        try
        {
            stage = (Stage) supprimerEntreprise.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("ui/liste-entreprise.fxml"));
        }catch(IOException e){e.printStackTrace();}

        Controller.changerMenuPrincipal(stage, root);
    }

    public void goModifierEntreprise()
    {
        int id = (int) this.groupeRadioListe.getSelectedToggle().getUserData();
        this.entreprise = dao.find(id);

        Stage stage = null;
        Parent root = null;
        try
        {
            stage = (Stage) goModifier.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("ui/modifier-entreprise.fxml"));
        }catch(IOException e){e.printStackTrace();}

        Controller.changerMenuPrincipal(stage, root);

        nomEntr.setText(this.entreprise.raisonSociale);
        adresse.setText(this.entreprise.rue);
        codePostal.setText(this.entreprise.codePostal);
        ville.setText(this.entreprise.ville);
        mail.setText(this.entreprise.mail);
        tel.setText(this.entreprise.tel);
        secteur.setText(this.entreprise.secteurActivite);
    }

    public void afficherListeEntreprises()
    {
        ArrayList<Entreprise> listeEntreprises = dao.findAll();

        int ligne = 1;
        for (Entreprise e : listeEntreprises)
        {
            tableauEntreprises.add(new Label(e.raisonSociale), 0, ligne);

            tableauEntreprises.add(new Label(e.secteurActivite), 1, ligne);

            RadioButton r = new RadioButton();
            if(ligne == 1) r.setSelected(true);
            r.setToggleGroup(groupeRadioListe);
            r.setUserData(e.id); // ID pour une ligne
            tableauEntreprises.add(r, 2, ligne);

            ligne++;
        }
    }
}
