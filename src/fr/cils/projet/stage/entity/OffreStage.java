package fr.cils.projet.stage.entity;

import java.util.Date;

public class OffreStage
{

    public int id;

    public String libelle;

    public String description;

    public String domaine;

    public Date dateDebut;

    public int duree;

    public boolean estValide;

    public OffreStage(String libelle, String description, String domaine,
                      Date dateDebut, int duree, String chemin, boolean estValide)
    {
        this.libelle = libelle;
        this.description = description;
        this.domaine = domaine;
        this.dateDebut = dateDebut;
        this.duree = duree;
        this.estValide = estValide;
    }

    public OffreStage(int id, String libelle, String description, String domaine,
                      Date dateDebut, int duree, boolean estValide)
    {
        this.id = id;
        this.libelle = libelle;
        this.description = description;
        this.domaine = domaine;
        this.dateDebut = dateDebut;
        this.duree = duree;
        this.estValide = estValide;
    }
}
