package fr.cils.projet.stage.entity;

import javafx.scene.control.RadioButton;

import java.util.ArrayList;

public class Utilisateur
{
    public int id;
    public String login;
    public String pass;
    public Role role;
    public ArrayList<OffreStage> offreStagesPostulees;
    public ArrayList<Entreprise> entreprisesCrees;
    public RadioButton selecteur;

    public Utilisateur(String login, String pass) // connexion
    {
        this.login = login;
        this.pass = pass;
        offreStagesPostulees = new ArrayList<>();
        entreprisesCrees = new ArrayList<>();
        this.selecteur = new RadioButton();
        this.selecteur.setUserData(this.id);
    }

    public Utilisateur(String login, String pass, Role role) // inscription
    {
        this.login = login;
        this.pass = pass;
        this.role = role;
        offreStagesPostulees = new ArrayList<>();
        entreprisesCrees = new ArrayList<>();
        this.selecteur = new RadioButton();
        this.selecteur.setUserData(this.id);
    }

    public Utilisateur(int id, String login, String pass, Role role)
    {
        this.id = id;
        this.login = login;
        this.pass = pass;
        this.role = role;
        offreStagesPostulees = new ArrayList<>();
        entreprisesCrees = new ArrayList<>();
        this.selecteur = new RadioButton();
        this.selecteur.setUserData(this.id);
    }

    public Utilisateur(int id, String login, String pass, Role role,
                       ArrayList<OffreStage> offreStagesPostulees)
    {
        this.id = id;
        this.login = login;
        this.pass = pass;
        this.role = role;
        this.offreStagesPostulees = offreStagesPostulees;
        this.selecteur = new RadioButton();
        this.selecteur.setUserData(this.id);
    }

       public void postuler(OffreStage offreStage)
    {
        offreStagesPostulees.add(offreStage);
    }

    public void creerEntreprise(Entreprise entreprise)
    {
        entreprisesCrees.add(entreprise);
    }

    public String getLogin()
    {
        return this.login;
    }

    public String getRole()
    {
        return this.role.toString();
    }

    public RadioButton getSelecteur()
    {
        return this.selecteur;
    }
}