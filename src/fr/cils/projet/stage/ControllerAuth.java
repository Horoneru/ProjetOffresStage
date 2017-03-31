﻿package fr.cils.projet.stage;

import fr.cils.projet.stage.dao.UtilisateurDao;
import fr.cils.projet.stage.entity.Utilisateur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;
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

    UtilisateurDao dao;

    public ControllerAuth()
    {
        this.dao = new UtilisateurDao();
    }

    public void gestionBoutonsAuthInscr(ActionEvent actionEvent) throws IOException
    {
        Stage stage = null;
        Parent root = null;

        if(actionEvent.getSource() == auth) // tentative de connexion
        {
            // on crée un utilisateur temporaire

            Utilisateur u = new Utilisateur(identifiant.getText(), mdp.getText());
            Utilisateur uDatabase = dao.find(identifiant.getText());

            if(uDatabase != null)
            {
                if(u.pass == uDatabase.pass)
                {
                    stage = (Stage) auth.getScene().getWindow();
                    root = FXMLLoader.load(getClass().getResource("ui/main.fxml"));
                }
            }else
            {
                // echec connexion
            }

            stage = (Stage) auth.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("ui/main.fxml"));
            stage.setMaxWidth(1280);
            stage.setMaxHeight(600);
            stage.setMinWidth(623);
            stage.setMinHeight(426);
            stage.setResizable(true);

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
                    Boolean estEntreprise = false; // par défaut étudiant est sélectionné
                    if((RadioButton)t == typeEntreprise)
                    {
                        estEntreprise = true;
                    }

                    // etat false: étudiant    true: entreprise

                    this.dao.create(new Utilisateur(idInscr.getText(),mdpInscr.getText(), estEntreprise));

                    // retour au menu de connexion
                    stage = (Stage) inscr2.getScene().getWindow();
                    root = FXMLLoader.load(getClass().getResource("ui/auth.fxml"));
                }
            }
        }

        Controller.changerMenuPrincipal(stage, root);
    }
}
