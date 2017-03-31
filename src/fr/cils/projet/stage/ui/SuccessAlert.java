package fr.cils.projet.stage.ui;

import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;

public class SuccessAlert extends Alert
{
    public SuccessAlert(String contentText)
    {
        super(AlertType.INFORMATION, contentText);
        init();
    }

    private void init()
    {
        this.setGraphic(new ImageView("file:res/success.png"));
        this.setTitle("Succès !");
        this.setHeaderText("Succès !");
    }
}
