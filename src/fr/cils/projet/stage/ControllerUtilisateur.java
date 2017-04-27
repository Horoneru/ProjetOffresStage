package fr.cils.projet.stage;

import fr.cils.projet.stage.dao.UtilisateurDao;
import fr.cils.projet.stage.entity.OffreStage;
import fr.cils.projet.stage.entity.Role;
import fr.cils.projet.stage.entity.Utilisateur;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import javax.rmi.CORBA.Util;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ControllerUtilisateur
{

    @FXML
    private TextField loginModif;
    @FXML
    private TextField codeModif;
    @FXML
    private ToggleGroup groupeRadioB;
    @FXML
    private RadioButton typeEtudiant;
    @FXML
    private RadioButton typeEntreprise;
    @FXML
    private RadioButton typeAdmin;
    @FXML
    private TableView tableauUtilisateurs;
    @FXML
    private TableColumn colonneId;
    @FXML
    private TableColumn colonneRole;
    @FXML
    private TableColumn colonneSelection;
    @FXML
    private Button goModifier;
    @FXML
    private Button modifierUtilisateur;

    private Utilisateur utilisateur;
    private UtilisateurDao dao;
    private ToggleGroup groupeRadioListe;

    public ControllerUtilisateur()
    {
        dao = new UtilisateurDao();
    }

    @FXML
    public void initialize()
    {
        if(tableauUtilisateurs != null) afficherListeUtilisateurs();
        if(loginModif != null)
        {
            loginModif.setText(this.utilisateur.login);
            codeModif.setText(this.utilisateur.pass);

            typeEtudiant.setSelected(false);
            typeEntreprise.setSelected(false);
            typeAdmin.setSelected(false);
            if(this.utilisateur.role == Role.Utilisateur) typeEtudiant.setSelected(true);
            if(this.utilisateur.role == Role.Entreprise) typeEntreprise.setSelected(true);
            if(this.utilisateur.role == Role.Admin) typeAdmin.setSelected(true);
        }
    }
    
    public void modifierUtilisateur()
    {
        String log = loginModif.getText();
        String motdepasse = codeModif.getText();
        Toggle t = groupeRadioB.getSelectedToggle();
        Role r = Role.Utilisateur; // Par défaut, on est un utilisateur
        if(t == typeEntreprise)
        {
            r = Role.Entreprise;
        }else if(t == typeAdmin)
        {
            r = Role.Admin;
        }

        this.utilisateur.login = log;
        this.utilisateur.pass = motdepasse;
        this.utilisateur.role = r;

        dao.update(this.utilisateur);

        Stage stage = null;
        Parent root = null;
        try
        {
            stage = (Stage) modifierUtilisateur.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("ui/liste-utilisateurs.fxml"));
        }catch(IOException e){e.printStackTrace();}

        Controller.changerMenuPrincipal(stage, root);
    }

    public void goModifierUtilisateur()
    {
        int id = (int) this.groupeRadioListe.getSelectedToggle().getUserData();
        this.utilisateur = dao.find(id);

        Stage stage = null;
        Parent root = null;
        try
        {
            stage = (Stage) goModifier.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("ui/modifier-utilisateur.fxml"));
        }catch(IOException e){e.printStackTrace();}

        Controller.changerMenuPrincipal(stage, root);
    }

    public void supprimerUtilisateur()
    {
        int id = (int) this.groupeRadioListe.getSelectedToggle().getUserData();
        dao.delete(dao.find(id));
    }

    public void afficherListeUtilisateurs()
    {
        ArrayList<Utilisateur> listeUtilisateurs = dao.findAll();
        this.groupeRadioListe = new ToggleGroup();

        int ligne = 1;
        for(Utilisateur u : listeUtilisateurs)
        {
            if(ligne == 1) u.selecteur.setSelected(true);
            u.selecteur.setToggleGroup(this.groupeRadioListe);
            ligne++;
        }

        ObservableList<Utilisateur> data = FXCollections.observableArrayList(listeUtilisateurs);

        colonneId.setCellValueFactory(new PropertyValueFactory<>("login"));
        colonneRole.setCellValueFactory(new PropertyValueFactory<>("role"));
        colonneSelection.setCellValueFactory(new PropertyValueFactory<>("selecteur"));

        tableauUtilisateurs.setItems(data);

    }
}
