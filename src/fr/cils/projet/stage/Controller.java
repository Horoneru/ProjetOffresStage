package fr.cils.projet.stage;

import fr.cils.projet.stage.entity.Role;
import fr.cils.projet.stage.entity.Utilisateur;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller
{
    @FXML
    private GridPane apparenceGenerale;
    @FXML
    private Button btnConsulterOffre;
    @FXML
    private Button btnAjoutOffre;
    @FXML
    private Button btnAjoutEntreprise;
    @FXML
    private Button btnAbout;

    static Utilisateur currentUser;

    @FXML
    public void initialize()
    {
        //impossible de rajouter correctement de graphic depuis le FXML
        //Donc on le fait en code
        btnConsulterOffre.setGraphic(new ImageView("file:res/consulter-offre.png"));
        btnAjoutOffre.setGraphic(new ImageView("file:res/ajouter-offre.png"));
        btnAjoutEntreprise.setGraphic(new ImageView("file:res/ajouter-entreprise.png"));
        btnAbout.setGraphic(new ImageView("file:res/about.png"));
        //TODO : Rajouter les icônes de l'admin sur les boutons associés

        if(currentUser.role == Role.Utilisateur)
        {
            btnAjoutEntreprise.setDisable(true);
            btnAjoutOffre.setDisable(true);
            //TODO : disable les éléments de l'admin en plus
        }

        else if(currentUser.role == Role.Admin)
        {
            //TODO : disable les éléments de l'admin
        }
    }

    public void switchApparence(MouseEvent mouseEvent) throws IOException
    {

        Button boutonClique = (Button) mouseEvent.getSource();
        apparenceGenerale.getChildren().remove(apparenceGenerale.lookup("#fxmlActif"));

        //TODO : rajouter les ids des nouveaux boutons admins et leurs fxml
        switch (boutonClique.getId())
        {
            case "btnConsulterOffre":
                apparenceGenerale.add(FXMLLoader.load(getClass().getResource("ui/consulter-offre.fxml")), 1, 0);
                break;

            case "btnAjoutOffre":
                apparenceGenerale.add(FXMLLoader.load(getClass().getResource("ui/creer-offre.fxml")), 1, 0);
                break;

            case "btnAjoutEntreprise":
                apparenceGenerale.add(FXMLLoader.load(getClass().getResource("ui/creer-entreprise.fxml")), 1, 0);
                break;
            default:
                System.out.println("nope");
                break;
        }
    }

    public void showAbout(MouseEvent mouseEvent)
    {
        Alert aboutPopup = new Alert(Alert.AlertType.INFORMATION);
        aboutPopup.setTitle("À propos");
        aboutPopup.setHeaderText("À propos");
        aboutPopup.setContentText("Ce logiciel a été développé par :\n" +
                "- Horoneru\n" +
                "- Antoria\n" +
                "- Bobpty\n" +
                "Vous pouvez trouver le code source de ce logiciel sur Github");
        aboutPopup.showAndWait();
    }

    public static void changerMenuPrincipal(Stage stage, Parent root)
    {
        Scene scene = new Scene(root); // on affiche la nouvelle fenêtre
        stage.setScene(scene);
        stage.show();
    }
}
