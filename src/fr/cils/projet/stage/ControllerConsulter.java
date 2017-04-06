package fr.cils.projet.stage;

import fr.cils.projet.stage.dao.OffreStageDao;
import fr.cils.projet.stage.entity.OffreStage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by infol3-70 on 09/03/17.
 */
public class ControllerConsulter
{
    @FXML
    private BorderPane affichage;
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
    private TextField dateDebut;
    @FXML
    private TextField duree;
    @FXML
    private TextField descr;
    @FXML
    private Button boutonPrecedent;
    @FXML
    private Button boutonSuivant;
    @FXML
    private Button fermer;

    private OffreStageDao dao;
    private int idOffre;

    public ControllerConsulter()
    {
        this.dao = new OffreStageDao();
        this.idOffre = 0;
    }

    public void initialize()
    {
        afficherOffre();
    }

    public void afficherOffre()
    {
        OffreStage offre = this.dao.find(this.idOffre);
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
        dateDebut.setText(offre.dateDebut.toString());
        duree.setText(Integer.toString(offre.duree));
        descr.setText(offre.description);
    }

    public void changerOffreAffichee(ActionEvent e) // id precedent, suivant
    {
        int modif=0;
        Button boutonClique = (Button) e.getSource();
        if(boutonClique.getId() == "boutonPrecedent")
        {
            modif = -1;

        }else
        {
            modif = 1;
        }

        if((this.idOffre + modif) >=0 ) // on veut un ID positif ou nul
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
}
