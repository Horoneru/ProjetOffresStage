package fr.cils.projet.stage;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class Controller
{
    @FXML
    private GridPane apparenceGenerale;

    public void switchApparence(MouseEvent mouseEvent) throws IOException
    {

        Button boutonClique = (Button) mouseEvent.getSource();
        apparenceGenerale.getChildren().remove(apparenceGenerale.lookup("#fxmlActif"));

        switch (boutonClique.getId())
        {
            case "btn1": // Apparence 1
                apparenceGenerale.add(FXMLLoader.load(getClass().getResource("ui/apparence1.fxml")),1,0);
                break;

            case "btn2":
                apparenceGenerale.add(FXMLLoader.load(getClass().getResource("ui/apparence2.fxml")),1,0);
                break;

            case "btn3":
                apparenceGenerale.add(FXMLLoader.load(getClass().getResource("ui/apparence3.fxml")),1,0);
                break;

            default:
                System.out.println("nope");
                break;
        }
    }
}
