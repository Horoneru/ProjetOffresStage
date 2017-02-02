package fr.cils.projet.stage.entity;

/**
 * Created by Horoneru on 02/02/2017.
 */
public class Entreprise
{
    public Entreprise(String raisonSociale, String ville, String rue, String codePostal, String tel, String secteurActivite)
    {
        this.raisonSociale = raisonSociale;
        this.ville = ville;
        this.rue = rue;
        this.codePostal = codePostal;
        this.tel = tel;
        this.secteurActivite = secteurActivite;
    }

    public String raisonSociale;

    public String ville;

    public String rue;

    public String codePostal;

    public String tel;

    public String secteurActivite;
}
