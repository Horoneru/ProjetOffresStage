package fr.cils.projet.stage.entity;

import java.util.Date;

public class OffreStage
{

    public OffreStage(String libelleOffre, String descriptionOffre, String domaineOffre, Date dateDebutOffre, int dureeOffre, String cheminOffre, boolean estValide)
    {
        this.libelleOffre = libelleOffre;
        this.descriptionOffre = descriptionOffre;
        this.domaineOffre = domaineOffre;
        this.dateDebutOffre = dateDebutOffre;
        this.dureeOffre = dureeOffre;
        this.cheminOffre = cheminOffre;
        this.estValide = estValide;
    }

    public String libelleOffre;

    public String descriptionOffre;

    public String domaineOffre;

    public Date dateDebutOffre;

    public int dureeOffre;

    public String cheminOffre;

    public boolean estValide;
}
