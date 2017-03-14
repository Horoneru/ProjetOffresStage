package fr.cils.projet.stage.entity;

import java.util.ArrayList;

public class Utilisateur
{
    public int id;
    public String login;
    public String pass;
    public enum role { Entreprise, Etudiant }
    public ArrayList<OffreStage> offreStagesPostulees;

    public Utilisateur(int id, String login, String pass)
    {
        this.id = id;
        this.login = login;
        this.pass = pass;
        offreStagesPostulees = new ArrayList<>();
    }

    public Utilisateur(int id, String login, String pass,
                       ArrayList<OffreStage> offreStagesPostulees)
    {
        this.id = id;
        this.login = login;
        this.pass = pass;
        this.offreStagesPostulees = offreStagesPostulees;
    }
}