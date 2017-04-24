package fr.cils.projet.stage;

import fr.cils.projet.stage.dao.UtilisateurDao;
import fr.cils.projet.stage.entity.OffreStage;
import fr.cils.projet.stage.entity.Role;
import fr.cils.projet.stage.entity.Utilisateur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

public class ControllerUtilisateur
{

    @FXML
    private TextField login;
    @FXML
    private TextField code;
    @FXML
    private ToggleGroup groupeRadioB;
    @FXML
    private ToggleGroup groupeRadioListe;
    @FXML
    private RadioButton typeEtudiant;
    @FXML
    private RadioButton typeEntreprise;
    @FXML
    private RadioButton typeAdmin;
    @FXML
    private GridPane tableauUtilisateurs;

    private Utilisateur utilisateur;
    private UtilisateurDao dao;

    public ControllerUtilisateur()
    {
        dao = new UtilisateurDao();
    }
    
    public void initialize()
    {
        if(tableauUtilisateurs != null) afficherListeUtilisateurs();
    }
    
    public void modifierUtilisateur()
    {
        String log = login.getText();
        String motdepasse = code.getText();
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
    }

    public void supprimerUtilisateur()
    {
        int id = (int) groupeRadioListe.getSelectedToggle().getUserData();
        dao.delete(dao.find(id));
    }

    public void afficherListeUtilisateurs()
    {
        ArrayList<Utilisateur> listeUtilisateurs = dao.findAll();

        int ligne = 1;
        for (Utilisateur u : listeUtilisateurs)
        {
            tableauUtilisateurs.add(new Label(u.login), 0, ligne);

            tableauUtilisateurs.add(new Label(u.role.name()), 1, ligne);

            RadioButton r = new RadioButton();
            if(ligne == 1) r.setSelected(true);
            r.setUserData(u.id); // ID pour une ligne
            r.setToggleGroup(groupeRadioListe);
            tableauUtilisateurs.add(r, 2, ligne);

            ligne++;
        }
    }
}
