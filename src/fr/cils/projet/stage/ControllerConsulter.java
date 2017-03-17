package fr.cils.projet.stage;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

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

    public void afficherOffre()
    {
        nomEntr.setText("...");
    }
}
