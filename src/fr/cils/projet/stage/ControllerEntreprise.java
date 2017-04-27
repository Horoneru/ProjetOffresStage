package fr.cils.projet.stage;

import fr.cils.projet.stage.dao.EntrepriseDao;
import fr.cils.projet.stage.entity.Entreprise;
import fr.cils.projet.stage.ui.SuccessAlert;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
    private TableView tableauEntreprises;
    @FXML
    private TableColumn colonneNom;
    @FXML
    private TableColumn colonneSecteur;
    @FXML
    private TableColumn colonneSelection;
    @FXML
    private Button goModifierEntreprise;
    @FXML
    private Button modifierEntreprise;
    @FXML
    private Button supprimerEntreprise;
    @FXML
    private Button envoyerEntreprise;

    static Entreprise entreprise;
    EntrepriseDao dao;
    private ToggleGroup groupeRadioListe;

    public ControllerEntreprise()
    {
        this.dao = new EntrepriseDao();
    }

    @FXML
    public void initialize()
    {
        if (tableauEntreprises != null) afficherListeEntreprises();
        else
        {
            Platform.runLater(() -> nomEntr.requestFocus());
            //On est en train de modifier
            if(modifierEntreprise != null)
            {
                nomEntr.setText(entreprise.raisonSociale);
                adresse.setText(entreprise.rue);
                codePostal.setText(entreprise.codePostal);
                ville.setText(entreprise.ville);
                mail.setText(entreprise.mail);
                tel.setText(entreprise.tel);
                secteur.setText(entreprise.secteurActivite);
            }
        }
    }

    public void checkIfEnterPressed(KeyEvent e)
    {
        if(e.getCode() == KeyCode.ENTER)
        {
            //Si envoyer est présent, on est en train de créer, sinon on modifie
            if(envoyerEntreprise != null)
                creerEntreprise(null);
            else
                modifierEntreprise();
        }
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
                codePostal.getText(), tel.getText(), secteur.getText(), Controller.currentUser);

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
        entreprise.raisonSociale = nomEntr.getText();
        entreprise.rue = adresse.getText();
        entreprise.codePostal = codePostal.getText();
        entreprise.ville = ville.getText();
        entreprise.mail = mail.getText();
        entreprise.tel = tel.getText();
        entreprise.secteurActivite = secteur.getText();

        dao.update(entreprise);
        try
        {
            Controller.instance.switchApparence(new Event(modifierEntreprise, null, null));
        }catch(IOException e){e.printStackTrace();}
    }

    public void supprimerEntreprise()
    {
        int id = (int) groupeRadioListe.getSelectedToggle().getUserData();
        dao.delete(dao.find(id));

        for (Entreprise e : Controller.currentUser.entreprisesCrees)
        {
            if(e.id == id)
                Controller.currentUser.entreprisesCrees.remove(e);
        }
        try
        {
            Controller.instance.switchApparence(new Event(supprimerEntreprise, null, null));
        }catch(IOException e){e.printStackTrace();}
    }

    public void goModifierEntreprise()
    {
        int id = (int) this.groupeRadioListe.getSelectedToggle().getUserData();
        entreprise = dao.find(id);
        try
        {
            Controller.instance.switchApparence(new Event(goModifierEntreprise, null, null));
        }catch(IOException e){e.printStackTrace();}
    }

    public void afficherListeEntreprises()
    {
        ArrayList<Entreprise> listeEntreprises = dao.findAll();
        this.groupeRadioListe = new ToggleGroup();

        int ligne = 1;
        for (Entreprise e : listeEntreprises) {
            if (ligne == 1) e.selecteur.setSelected(true);
            e.selecteur.setToggleGroup(this.groupeRadioListe);
            ligne++;
        }

        ObservableList<Entreprise> data = FXCollections.observableArrayList(listeEntreprises);

        colonneNom.setCellValueFactory(new PropertyValueFactory<>("raisonSociale"));
        colonneSecteur.setCellValueFactory(new PropertyValueFactory<>("secteurActivite"));
        colonneSelection.setCellValueFactory(new PropertyValueFactory<>("selecteur"));

        tableauEntreprises.setItems(data);
    }
}
