package fr.cils.projet.stage;

import fr.cils.projet.stage.dao.OffreStageDao;
import fr.cils.projet.stage.entity.OffreStage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import sun.java2d.pipe.SpanShapeRenderer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    private TextField dateDeb;
    @FXML
    private TextField duree;
    @FXML
    private TextField cheminStockage;
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
        cheminStockage.clear();
        descr.clear();
    }

    public void creerOffre(ActionEvent actionEvent)
    {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date dateDebut = null;
        try
        {
            dateDebut = sdf.parse(dateDeb.getText());
        }catch(ParseException e){e.printStackTrace();}

        boolean estValide = true;
        try
        {
            Date ajd = sdf.parse(sdf.format(new Date()));
            if(ajd.compareTo(dateDebut) > 0) estValide = false;

            // la date du jour est supérieure à la date de début de stage

        }catch(ParseException e){e.printStackTrace();}

        OffreStage offre = new OffreStage(intitule.getText(), descr.getText(), domOffre.getText(),
                dateDebut, Integer.parseInt(duree.getText()), estValide);

        this.dao.create(offre);


    }
}
