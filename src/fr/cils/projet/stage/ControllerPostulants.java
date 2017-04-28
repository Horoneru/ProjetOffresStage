package fr.cils.projet.stage;

import fr.cils.projet.stage.dao.EntrepriseDao;
import fr.cils.projet.stage.dao.OffreStageDao;
import fr.cils.projet.stage.dao.UtilisateurDao;
import fr.cils.projet.stage.entity.OffreStage;
import fr.cils.projet.stage.entity.Postulat;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.ArrayList;

public class ControllerPostulants
{
    @FXML
    private TableView tableauPostulants;
    @FXML
    private TableColumn colonneId;
    @FXML
    private TableColumn colonneValide;
    @FXML
    private TableColumn colonneSelection;
    @FXML
    private Button validerPostulant;
    private OffreStageDao offreStageDao;
    private EntrepriseDao entrepriseDao;
    @FXML
    private ToggleGroup groupeRadioListe;
    static OffreStage offrestage;

    public ControllerPostulants()
    {
        offreStageDao = new OffreStageDao();
        entrepriseDao = new EntrepriseDao();
    }

    @FXML
    public void initialize()
    {
        afficherListePostulants();
    }
    
    public void validerPostulant()
    {
        int id = (int) this.groupeRadioListe.getSelectedToggle().getUserData();
        for ( Object o : tableauPostulants.getItems())
        {
            Postulat p = (Postulat) o;
            if(p.postulant.id == id && p.estValidee)
            {
                Alert alreadyConfirmedAlert = new Alert(Alert.AlertType.INFORMATION,
                        "Cette candidature est déjà confirmée");
                alreadyConfirmedAlert.showAndWait();
                return;
            }
        }
        if(!entrepriseDao.valider(id, offrestage.id))
        {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR,
                    "Une erreur est survenue lors de la validation de cette candidature...");
            errorAlert.showAndWait();
            return;
        }
        try
        {
            Controller.instance.switchApparence(new Event(validerPostulant, null, null));
        }catch(IOException e){e.printStackTrace();}
    }

    public void afficherListePostulants()
    {
        ArrayList<Postulat> listePostulants = offreStageDao.findAllPostulants(offrestage);
        this.groupeRadioListe = new ToggleGroup();

        int ligne = 1;
        for(Postulat p : listePostulants)
        {
            if(ligne == 1) p.selecteur.setSelected(true);
            p.selecteur.setToggleGroup(this.groupeRadioListe);
            ligne++;
        }

        ObservableList<Postulat> data = FXCollections.observableArrayList(listePostulants);

        colonneId.setCellValueFactory(new PropertyValueFactory<>("login"));
        colonneValide.setCellValueFactory(new PropertyValueFactory<>("estValidee"));
        colonneSelection.setCellValueFactory(new PropertyValueFactory<>("selecteur"));

        tableauPostulants.setItems(data);

    }
}
