package fr.cils.projet.stage;

import fr.cils.projet.stage.dao.UtilisateurDao;
import fr.cils.projet.stage.entity.Role;
import fr.cils.projet.stage.entity.Utilisateur;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

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
    private Button goModifierUtilisateur;
    @FXML
    private Button modifierUtilisateur;
    @FXML
    private Button supprimerUtilisateur;

    static Utilisateur utilisateur;
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
            loginModif.setText(utilisateur.login);

            typeEtudiant.setSelected(false);
            typeEntreprise.setSelected(false);
            typeAdmin.setSelected(false);
            if(utilisateur.role == Role.Utilisateur) typeEtudiant.setSelected(true);
            if(utilisateur.role == Role.Entreprise) typeEntreprise.setSelected(true);
            if(utilisateur.role == Role.Admin) typeAdmin.setSelected(true);
        }
    }
    
    public void modifierUtilisateur()
    {
        String log = loginModif.getText();
        String motdepasse = codeModif.getText();

        if(motdepasse.isEmpty())
            motdepasse = utilisateur.pass;
        else
            motdepasse = Controller.chiffrementSHA1(motdepasse);

        Toggle t = groupeRadioB.getSelectedToggle();
        Role r = Role.Utilisateur; // Par défaut, on est un utilisateur
        if(t == typeEntreprise)
        {
            r = Role.Entreprise;
        }else if(t == typeAdmin)
        {
            r = Role.Admin;
        }

        utilisateur.login = log;
        utilisateur.pass = motdepasse;
        utilisateur.role = r;

        dao.update(utilisateur);
        try
        {
            Controller.instance.switchApparence(new Event(modifierUtilisateur, null, null));
        }catch(IOException e){e.printStackTrace();}
    }

    public void goModifierUtilisateur()
    {
        int id = (int) this.groupeRadioListe.getSelectedToggle().getUserData();
        utilisateur = dao.find(id);

        try
        {
            Controller.instance.switchApparence(new Event(goModifierUtilisateur, null, null));
        }catch(IOException e){e.printStackTrace();}
    }

    public void supprimerUtilisateur()
    {
        int id = (int) this.groupeRadioListe.getSelectedToggle().getUserData();
        dao.delete(dao.find(id));
        try
        {
            Controller.instance.switchApparence(new Event(supprimerUtilisateur, null, null));
        }catch (IOException e) {e.printStackTrace();}
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
