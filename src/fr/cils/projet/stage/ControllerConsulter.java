package fr.cils.projet.stage;

import fr.cils.projet.stage.dao.OffreStageDao;
import fr.cils.projet.stage.dao.UtilisateurDao;
import fr.cils.projet.stage.entity.Entreprise;
import fr.cils.projet.stage.entity.OffreStage;
import fr.cils.projet.stage.entity.Role;
import fr.cils.projet.stage.entity.Utilisateur;
import fr.cils.projet.stage.ui.SuccessAlert;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by infol3-70 on 09/03/17.
 */
public class ControllerConsulter
{
    @FXML
    private GridPane affichage;
    @FXML
    private TextField nomEntr;
    @FXML
    private TextField ville;
    @FXML
    private TextField mail;
    @FXML
    private TextField domaine;
    @FXML
    private TextField intitule;
    @FXML
    private DatePicker dateDebut;
    @FXML
    private TextField duree;
    @FXML
    private TextField descr;
    @FXML
    private Button boutonPostuler;
    @FXML
    private Button boutonModifier;
    @FXML
    private Button boutonSupprimer;
    @FXML
    private Button boutonPrecedent;
    @FXML
    private Button boutonSuivant;
    @FXML
    private Button fermer;

    private OffreStageDao dao;
    private UtilisateurDao dao_utilisateur;
    private int idOffre;
    private OffreStage offre;
    private ArrayList<OffreStage> liste_offres;

    public ControllerConsulter()
    {
        this.dao = new OffreStageDao();
        this.dao_utilisateur = new UtilisateurDao();
        this.liste_offres = new ArrayList<OffreStage>();

        if(Controller.currentUser.role == Role.Entreprise)
        {
            for(Entreprise e : Controller.currentUser.entreprisesCrees)
            {
                this.liste_offres.addAll(e.offresStage);
            }

        }else
        {
            this.liste_offres = this.dao.findAll();
        }


        //this.idOffre = 1;
    }

    @FXML
    public void initialize()
    {
        afficherOffre();
        if(Controller.currentUser.role == Role.Utilisateur)
        {
            boutonSupprimer.setVisible(false);
            boutonModifier.setVisible(false);
            affichage.setDisable(true);
        }
        else
            boutonPostuler.setVisible(false);
    }

    public void afficherOffre()
    {
        this.offre = liste_offres.get(this.idOffre);
        if(offre == null)
        {
            Alert errorPopup = new Alert(Alert.AlertType.ERROR,
                    "Une erreur est survenue lors de la récupération de l'offre demandée...");
            errorPopup.showAndWait();
            return;
        }
        nomEntr.setText(offre.entrepriseAssociee.raisonSociale);
        ville.setText(offre.entrepriseAssociee.ville);
        mail.setText(offre.entrepriseAssociee.mail);
        domaine.setText(offre.domaine);
        intitule.setText(offre.libelle);
        dateDebut.setValue(offre.dateDebut);
        duree.setText(Integer.toString(offre.duree));
        descr.setText(offre.description);

        if(Controller.currentUser.offreStagesPostulees.contains(offre))
            boutonPostuler.setDisable(true);
    }

    public void changerOffreAffichee(ActionEvent e) // id precedent, suivant
    {
        int modif=0;
        Button boutonClique = (Button) e.getSource();
        if(boutonClique.getId().equals("boutonPrecedent"))
        {
            modif = -1;

        }else
        {
            modif = 1;
        }

        if((this.idOffre + modif) >=0 ) // on veut un ID positif pour chercher dans le tableau[]
            this.idOffre = this.idOffre + modif;

        this.afficherOffre();
    }

    public void fermerConsultation()
    {
        Stage stage = (Stage) fermer.getScene().getWindow();
        Parent root = null;
        try
        {
            root = FXMLLoader.load(getClass().getResource("ui/main.fxml"));

        }catch(IOException e){e.printStackTrace();}

        if(root != null) Controller.changerMenuPrincipal(stage, root);
    }

    public void postulerOffre()
    {
        Controller.currentUser.offreStagesPostulees.add(offre);
        if(dao_utilisateur.postuler(Controller.currentUser, offre))
        {
            SuccessAlert successAlert = new SuccessAlert("" +
                    "Votre candidature a bien été enregistrée");
            successAlert.showAndWait();
            boutonPostuler.setDisable(true);
        }
        else
        {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR, "" +
                    "Une erreur est survenue lors de l'enregistrement de votre candidature");
            errorAlert.showAndWait();
        }
    }

    public void modifierOffre(ActionEvent actionEvent)
    {
        offre.dateDebut = dateDebut.getValue();
        offre.description = descr.getText();
        offre.domaine = domaine.getText();
        offre.duree = Integer.parseInt(duree.getText());
        offre.libelle = intitule.getText();

        if(dao.update(offre))
        {
            SuccessAlert successAlert = new SuccessAlert(
                    "L'offre a bien été mise à jour ! ");
            successAlert.showAndWait();
        }
        else
        {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR,
                    "Impossible de mettre à jour l'offre");
            errorAlert.showAndWait();
        }
    }

    public void supprimerOffre(ActionEvent actionEvent)
    {
        //TODO : renvoyer un booléen lors de delete.
        // En fait, ça n'a aucun sens d'avoir un void
        if(dao.delete(offre))
        {
            SuccessAlert successAlert = new SuccessAlert(
                    "L'offre a bien été supprimée");
            successAlert.showAndWait();
            //TODO : afficher la prochaine offre
        }
        else
        {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR,
                    "Impossible de supprimer l'offre");
            errorAlert.showAndWait();
        }
    }
}
