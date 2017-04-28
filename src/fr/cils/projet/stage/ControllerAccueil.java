package fr.cils.projet.stage;

import fr.cils.projet.stage.entity.Role;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class ControllerAccueil
{
    @FXML
    private Button boutonSuiviCandidatures;

    public void initialize()
    {
        if(Controller.currentUser.role != Role.Utilisateur)
            boutonSuiviCandidatures.setVisible(false);
    }

    public void afficherCandidatures()
    {
        try
        {
            Controller.instance.switchApparence(new Event(
                    boutonSuiviCandidatures, null, null));
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
