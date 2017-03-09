package fr.cils.projet.stage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * Created by infol3-70 on 09/03/17.
 */
public class ControllerEntreprise
{
    @FXML
    private Button envoyerEntreprise;
    @FXML
    private Button annulerEntreprise;
    @FXML
    private TextField nomEntr;
    @FXML
    private TextField adresse;
    @FXML
    private TextField codePostal;
    @FXML
    private TextField ville;
    @FXML
    private TextField mail;
    @FXML
    private TextField tel;
    @FXML
    private TextField secteur;

    public void clear(ActionEvent actionEvent)
    {
        nomEntr.clear();
        adresse.clear();
        codePostal.clear();
        ville.clear();
        mail.clear();
        tel.clear();
        secteur.clear();
    }

    public void creerEntreprise(ActionEvent actionEvent)
    {
        String nomEntreprise = nomEntr.getText();
        String adre = adresse.getText();
        String codeP = codePostal.getText();
        String v = ville.getText();
        String email = mail.getText();
        String telephone = tel.getText();
        String sect = secteur.getText();

        /*

        insertion base de données

         */
    }
}
