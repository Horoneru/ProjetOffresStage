package fr.cils.projet.stage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import javax.xml.soap.Text;
import java.io.IOException;

/**
 * Created by infol3-70 on 23/02/17.
 */
public class ControllerAuth
{
    @FXML
    private Button inscr1;
    @FXML
    private Button inscr2;
    @FXML
    private Button auth;
    @FXML
    private TextField identifiant;
    @FXML
    private PasswordField mdp;
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

    public void gestionBoutonsAuthInscr(ActionEvent actionEvent) throws IOException
    {
        Stage stage = null;
        Parent root = null;

        if(actionEvent.getSource() == auth) // tentative de connexion
        {
            // on récupère les données relatives à l'inscription
            String id = identifiant.getText();
            String motdepasse = mdp.getText();


            /*

                    connexion via base de données

            */


            stage = (Stage) auth.getScene().getWindow();
            stage.setMaxWidth(1280);
            stage.setMaxHeight(600);
            stage.setMinWidth(623);
            stage.setMinHeight(426);
            root = FXMLLoader.load(getClass().getResource("ui/main.fxml"));

        }else
        {
            if(actionEvent.getSource() == inscr1) // on veut s'inscrire
            {
                stage = (Stage) inscr1.getScene().getWindow();
                root = FXMLLoader.load(getClass().getResource("ui/inscription.fxml"));

            }else
            {
                if(actionEvent.getSource() == inscr2) // fin d'inscription
                {
                    // on récupère les données relatives à l'inscription
                    String id = idInscr.getText();
                    String motdepasse = mdpInscr.getText();

                    Toggle t = groupeRadioB.getSelectedToggle();
                    Boolean etat = false; // par défaut étudiant est sélectionné
                    if((RadioButton)t == typeEntreprise)
                    {
                        etat = true;
                    }

                    // etat false: étudiant    true: entreprise

                    /*

                    inscription dans la base de données

                     */


                    // retour au menu de connexion
                    stage = (Stage) inscr2.getScene().getWindow();
                    root = FXMLLoader.load(getClass().getResource("ui/auth.fxml"));
                }
            }
        }



        Scene scene = new Scene(root); // on affiche la nouvelle fenêtre
        stage.setScene(scene);
        stage.show();

    }
}
