package fr.cils.projet.stage;

import fr.cils.projet.stage.dao.UtilisateurDao;
import fr.cils.projet.stage.entity.Role;
import fr.cils.projet.stage.entity.Utilisateur;
import fr.cils.projet.stage.ui.SuccessAlert;
import javafx.application.Platform;
import javafx.event.ActionEvent;
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

/**
 * Created by infol3-70 on 23/02/17.
 */
public class ControllerAuth
{
    //Authentification
    @FXML
    private GridPane authForm;
    @FXML
    private Button inscr1;
    @FXML
    private Button auth;
    @FXML
    private TextField identifiant;
    @FXML
    private PasswordField mdp;

    //Inscription
    @FXML
    private GridPane inscrForm;
    @FXML
    private TextField idInscr;
    @FXML
    private PasswordField mdpInscr;
    @FXML
    private ToggleGroup groupeRadioB;
    @FXML
    private RadioButton typeEtudiant;
    @FXML
    private RadioButton typeEntreprise;
    @FXML
    private Button inscr2;

    UtilisateurDao dao;

    public ControllerAuth()
    {
        this.dao = new UtilisateurDao();
    }

    @FXML
    public void initialize()
    {
        if(authForm != null) //On est sur l'interface de login
        {
            //Auto-focus le TextField lorsque la Scene est chargée et visible
            Platform.runLater(() -> identifiant.requestFocus());
        }
        else //On est sur l'interface d'inscription
        {
            Platform.runLater(() -> idInscr.requestFocus());
        }
    }

    public void checkIfEnterPressed(KeyEvent e)
    {
        if(e.getCode() == KeyCode.ENTER)
        {
            try
            {
                if (e.getSource() == authForm)
                    gestionBoutonsAuthInscr(new ActionEvent(auth, null));
                else
                    gestionBoutonsAuthInscr(new ActionEvent(inscr2, null));
            } catch (IOException e1)
            {
                e1.printStackTrace();
            }
        }

    }

    public void gestionBoutonsAuthInscr(ActionEvent actionEvent) throws IOException
    {
        Stage stage = null;
        Parent root = null;
        Alert badIdsPopup = new Alert(Alert.AlertType.ERROR,
                "Identifiants invalides ! ");
        SuccessAlert registrationSuccessPopup = new SuccessAlert(
                "Vous avez bien été enregistré !");
        Alert missingFieldAlert = new Alert(Alert.AlertType.WARNING,
                "Certains champs n'ont pas été remplis ! ");
        if(actionEvent.getSource() == auth) // tentative de connexion
        {
            if(identifiant.getText().isEmpty() || mdp.getText().isEmpty())
            {
                missingFieldAlert.showAndWait();
                return;
            }

            // on crée un utilisateur temporaire
            Utilisateur u = new Utilisateur(identifiant.getText(), mdp.getText());
            Utilisateur uDatabase = dao.find(identifiant.getText());

            if(uDatabase != null)
            {
                if(Controller.chiffrementSHA1(u.pass).equals(uDatabase.pass))
                {
                    //Prépare le main
                    Controller.currentUser = uDatabase;
                    stage = (Stage) auth.getScene().getWindow();
                    root = FXMLLoader.load(getClass().getResource("ui/main.fxml"));
                    stage.setTitle(stage.getTitle() + " - " + uDatabase.login +
                            " (" + uDatabase.role + ")");
                    stage.setMaxWidth(1280);
                    stage.setMaxHeight(600);
                    stage.setMinWidth(875);
                    stage.setMinHeight(550);
                    stage.setResizable(true);
                }
                else
                {
                    badIdsPopup.showAndWait();
                    return;
                }
            }
            //Échec connexion
            else
            {
                badIdsPopup.showAndWait();
                return;
            }

        }else // tentative d'inscription, on change juste d'interface
        {
            if(actionEvent.getSource() == inscr1)
            {
                stage = (Stage) inscr1.getScene().getWindow();
                root = FXMLLoader.load(getClass().getResource("ui/inscription.fxml"));


            }else // sortie d'inscription --> interaction avec la base de données
            {
                if(actionEvent.getSource() == inscr2)
                {
                    // on récupère les données relatives à l'inscription
                    String id = idInscr.getText();
                    String motdepasse = mdpInscr.getText();

                    Toggle t = groupeRadioB.getSelectedToggle();

                    Role role = Role.Utilisateur; // Par défaut, on est un utilisateur
                    if(t == typeEntreprise)
                    {
                        role = Role.Entreprise;
                    }

                    //On return à l'inscription sinon sans rien envoyer
                    if(!id.isEmpty() && !motdepasse.isEmpty())
                    {
                        if(this.dao.create(new Utilisateur(idInscr.getText(),
                                mdpInscr.getText(), role)) != null)
                        {
                            registrationSuccessPopup.showAndWait();
                        }
                        else
                        {
                            Alert errorPopup = new Alert(Alert.AlertType.ERROR,
                                    "Une erreur est survenue lors de votre inscription...");
                            errorPopup.showAndWait();
                        }
                    }

                    // retour au menu de connexion
                    stage = (Stage) inscr2.getScene().getWindow();
                    root = FXMLLoader.load(getClass().getResource("ui/auth.fxml"));
                }
            }
        }

        Controller.changerMenuPrincipal(stage, root);
    }
}
