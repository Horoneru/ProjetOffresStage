package fr.cils.projet.stage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

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
    private TextField descr;

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
        String nomEntreprise = nomEntr.getText();
        String domaineOffre = domOffre.getText();
        String titre = intitule.getText();
        String dateDebut = dateDeb.getText();
        String temps = duree.getText();
        String description = descr.getText();


       /*

        insertion base de données

         */
    }
}
