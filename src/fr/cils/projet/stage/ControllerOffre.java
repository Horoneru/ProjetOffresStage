package fr.cils.projet.stage;

import fr.cils.projet.stage.dao.OffreStageDao;
import fr.cils.projet.stage.entity.OffreStage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import java.time.LocalDate;

/**
 * Created by infol3-70 on 09/03/17.
 */
public class ControllerOffre
{
    @FXML
    private Button envoyerOffre;
    @FXML
    private Button annulerOffre;
    @FXML
    private TextField nomEntr;
    @FXML
    private TextField domOffre;
    @FXML
    private TextField intitule;
    @FXML
    private DatePicker dateDeb;
    @FXML
    private TextField duree;
    @FXML
    private TextField descr;

    OffreStageDao dao;

    public ControllerOffre()
    {
        this.dao = new OffreStageDao();
    }

    public void clear(ActionEvent actionEvent)
    {
        nomEntr.clear();
        domOffre.clear();
        intitule.clear();
        dateDeb.clear();
        duree.clear();
        descr.clear();
    }

    public void creerOffre(ActionEvent actionEvent)
    {
        Boolean estValide = true;
        String nomEntreprise = nomEntr.getText();
        String domaineOffre = domOffre.getText();
        String titre = intitule.getText();
        LocalDate dateDebut = dateDeb.getValue();
        int temps = Integer.parseInt(duree.getText());
        String description = descr.getText();

        if(dateDebut.compareTo(dateDebut.minusDays(1)) > 0) estValide = false;
        
        OffreStage offre = new OffreStage(titre, description, domaineOffre,
                dateDebut, temps, estValide);

        this.dao.create(offre);

        //TODO : rajouter checks et indiquer à l'utilisateur le succès ou non de l'envoi

    }
}
