package fr.cils.projet.stage;

import fr.cils.projet.stage.dao.OffreStageDao;
import fr.cils.projet.stage.dao.UtilisateurDao;
import fr.cils.projet.stage.entity.Entreprise;
import fr.cils.projet.stage.entity.OffreStage;
import fr.cils.projet.stage.entity.Role;
import fr.cils.projet.stage.entity.Utilisateur;
import fr.cils.projet.stage.ui.SuccessAlert;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
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
    private TextArea descr;
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
    private Button boutonPostulants;
    @FXML
    private Button fermer;

    private OffreStageDao dao;
    private UtilisateurDao dao_utilisateur;
    private int idOffre;
    private OffreStage offre;
    private ArrayList<OffreStage> liste_offres;

    public ControllerConsulter() {
        this.dao = new OffreStageDao();
        this.dao_utilisateur = new UtilisateurDao();
        this.liste_offres = new ArrayList<OffreStage>();

        if(Controller.currentUser.role == Role.Entreprise)
        {
            for(Entreprise e : Controller.currentUser.entreprisesCrees)
            {
                this.liste_offres.addAll(e.offresStage);
                // normalement pas de doublons, on ne va pas creer deux fois la meme offre dans deux entreprises
            }
        }
        else
        {
            this.liste_offres = this.dao.findAll();
        }

        // L'indice est basé sur les éléments du tableau
        // on commence donc toujours par 0
        this.idOffre = 0;
    }

    @FXML
    public void initialize()
    {
        afficherOffre();
        if(Controller.currentUser.role == Role.Utilisateur)
        {
            boutonSupprimer.setVisible(false);
            boutonModifier.setVisible(false);
            boutonPostulants.setVisible(false);

            //Set all fields to non-editable
            nomEntr.setEditable(false);
            ville.setEditable(false);
            mail.setEditable(false);
            domaine.setEditable(false);
            intitule.setEditable(false);
            dateDebut.setEditable(false);
            duree.setEditable(false);
            descr.setEditable(false);
        }
        else
            boutonPostuler.setVisible(false);

        alreadyCandidateCheckProcess();
    }

    private void alreadyCandidateCheckProcess()
    {
        for(OffreStage o : Controller.currentUser.offreStagesPostulees)
        {
            if(offre == o)
                boutonPostuler.setDisable(true);
        }
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
        else
            boutonPostuler.setDisable(false);
    }

    public void changerOffreAffichee(ActionEvent e) // id precedent, suivant
    {
        int modif=0;
        Button boutonClique = (Button) e.getSource();
        if(boutonClique.getId().equals("boutonPrecedent"))
        {
            modif = -1;

        }
        else
        {
            modif = 1;
        }

        if((this.idOffre + modif) >=0 ) // on veut un ID positif pour chercher dans le tableau[]
        {
            if(this.liste_offres.size() > this.idOffre + modif) //On évite de dépasser la limite
                this.idOffre = this.idOffre + modif;
            else
            {
                Alert outOfBounds = new Alert(Alert.AlertType.INFORMATION, "Il n'y a plus aucune offre après celle-ci");
                outOfBounds.showAndWait();
                return;
            }
        }

        this.afficherOffre();
        alreadyCandidateCheckProcess();
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

    public void afficherPostulants()
    {
        ControllerPostulants.offrestage = offre;
        try
        {
            Controller.instance.switchApparence(new Event(boutonPostulants, null, null));
        } catch (IOException e)
        {
            e.printStackTrace();
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
        if(dao.delete(offre))
        {
            SuccessAlert successAlert = new SuccessAlert(
                    "L'offre a bien été supprimée");
            successAlert.showAndWait();

            this.liste_offres.remove(offre);
            for (Entreprise e : Controller.currentUser.entreprisesCrees)
            {
                // Si elle n'est pas présente, rien ne se passe
                // Solution facile, mais pas optimale
                e.offresStage.remove(offre);
            }
            changerOffreAffichee(new ActionEvent(boutonSuivant, null));
        }
        else
        {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR,
                    "Impossible de supprimer l'offre");
            errorAlert.showAndWait();
        }

    }
}
