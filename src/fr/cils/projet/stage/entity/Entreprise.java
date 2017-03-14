package fr.cils.projet.stage.entity;

import java.util.ArrayList;

public class Entreprise
{
    public int id;

    public String raisonSociale;

    public String ville;

    public String rue;

    public String codePostal;

    public String tel;

    public String secteurActivite;

    public ArrayList<OffreStage> offresStage;


    public Entreprise(int id, String raisonSociale, String ville, String rue,
                      String codePostal, String tel, String secteurActivite)
    {
        this.id = id;
        this.raisonSociale = raisonSociale;
        this.ville = ville;
        this.rue = rue;
        this.codePostal = codePostal;
        this.tel = tel;
        this.secteurActivite = secteurActivite;
        this.offresStage = new ArrayList<>();
    }

    public Entreprise(int id, String raisonSociale, String ville, String rue,
                      String codePostal, String tel, String secteurActivite,
                      ArrayList<OffreStage> offresStage)
    {
        this.id = id;
        this.raisonSociale = raisonSociale;
        this.ville = ville;
        this.rue = rue;
        this.codePostal = codePostal;
        this.tel = tel;
        this.secteurActivite = secteurActivite;
        this.offresStage = offresStage;
    }
}
