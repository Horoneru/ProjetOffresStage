package fr.cils.projet.stage.entity;

import java.util.ArrayList;

public class Utilisateur
{
    public int id;
    public String login;
    public String pass;
    public Role role;
    public ArrayList<OffreStage> offreStagesPostulees;

    public Utilisateur(String login, String pass) // connexion
    {
        this.login = login;
        this.pass = pass;
        offreStagesPostulees = new ArrayList<>();
    }

    public Utilisateur(String login, String pass, Role role) // inscription
    {
        this.login = login;
        this.pass = pass;
        this.role = role;
        offreStagesPostulees = new ArrayList<>();
    }

    public Utilisateur(int id, String login, String pass, Role role)
    {
        this.id = id;
        this.login = login;
        this.pass = pass;
        this.role = role;
        offreStagesPostulees = new ArrayList<>();
    }

    public Utilisateur(int id, String login, String pass, Role role,
                       ArrayList<OffreStage> offreStagesPostulees)
    {
        this.id = id;
        this.login = login;
        this.pass = pass;
        this.role = role;
        this.offreStagesPostulees = offreStagesPostulees;
    }

    public void postuler(OffreStage offreStage)
    {
        offreStagesPostulees.add(offreStage);
    }
}