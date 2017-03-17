package fr.cils.projet.stage.entity;

import java.util.ArrayList;

public class Utilisateur
{
    public int id;
    public String login;
    public String pass;
    public boolean estEntreprise;
    public ArrayList<OffreStage> offreStagesPostulees;

    public Utilisateur(int id, String login, String pass, boolean estEntreprise)
    {
        this.id = id;
        this.login = login;
        this.pass = pass;
        this.estEntreprise = estEntreprise;
        offreStagesPostulees = new ArrayList<>();
    }

    public Utilisateur(int id, String login, String pass, boolean estEntreprise, ArrayList<OffreStage> offreStagesPostulees)
    {
        this.id = id;
        this.login = login;
        this.pass = pass;
        this.estEntreprise = estEntreprise;
        this.offreStagesPostulees = offreStagesPostulees;
    }
}