package fr.cils.projet.stage.entity;

import javafx.scene.control.RadioButton;

public class Postulat
{
    public Utilisateur postulant;
    public OffreStage offreStagePostulee;
    public boolean estValidee;
    public RadioButton selecteur;

    public Postulat(Utilisateur postulant, OffreStage offreStagePostulee, boolean estValidee)
    {
        this.postulant = postulant;
        this.offreStagePostulee = offreStagePostulee;
        this.estValidee = estValidee;
        selecteur = new RadioButton();
        selecteur.setUserData(postulant.id);
    }

    public String getLogin()
    {
        return this.postulant.login;
    }

    public String getEstValidee()
    {
        //Woohoo ternaires
        return estValidee ? "Validée" : "En attente";
    }

    public RadioButton getSelecteur()
    {
        return this.selecteur;
    }
}
