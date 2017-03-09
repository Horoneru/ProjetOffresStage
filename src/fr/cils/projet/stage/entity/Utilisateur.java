package fr.cils.projet.stage.entity;

public class Utilisateur
{
    public int id;
    public String login;
    public String pass;
    public enum role { Entreprise, Etudiant }

    public Utilisateur(String login, String pass)
    {
        this.login = login;
        this.pass = pass;
    }

    public Utilisateur(int id, String login, String pass)
    {
        this.id = id;
        this.login = login;
        this.pass = pass;
    }
}