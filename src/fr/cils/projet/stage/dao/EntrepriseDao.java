package fr.cils.projet.stage.dao;

import fr.cils.projet.stage.entity.Entreprise;
import fr.cils.projet.stage.entity.Utilisateur;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EntrepriseDao extends Dao<Entreprise>
{
    public Entreprise find(int id)
    {
        Entreprise entreprise = null;
        try
        {
            PreparedStatement statement =
                    connect.prepareStatement("SELECT * FROM Entreprise WHERE id = ?");
            statement.setInt(1, id);

            statement.execute();
            ResultSet result = statement.getResultSet();
            if(result.first())
            {
                entreprise = new Entreprise(result.getInt("id"),
                                            result.getString("raisonSociale"), result.getString("mail"),
                                            result.getString("ville"), result.getString("rue"),
                                            result.getString("codePostal"), result.getString("tel"),
                                            result.getString("secteurActivite"));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return entreprise;
    }

    public ArrayList<Entreprise> findAll(Utilisateur utilisateur)
    {
        ArrayList<Entreprise> listeEntreprises = new ArrayList<>();
        try
        {
            int IDutilisateur = utilisateur.getId();
            PreparedStatement statement = connect.prepareStatement("SELECT * FROM Entreprise WHERE Utilisateur_id = ?");
            statement.setInt(1, IDutilisateur);

            statement.execute();
            ResultSet result = statement.getResultSet();
            if(result.first())
            {
                Entreprise entreprise = new Entreprise(result.getInt("id"),
                                                        result.getString("raisonSociale"),
                                                        result.getString("mail"),
                                                        result.getString("ville"),
                                                        result.getString("rue"),
                                                        result.getString("codePostal"),
                                                        result.getString("tel"),
                                                        result.getString("secteurActivite"));

                listeEntreprises.add(entreprise);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return listeEntreprises;
    }

    public Entreprise create(Entreprise entreprise)
    {
        try
        {
            PreparedStatement statement =
                    connect.prepareStatement("INSERT INTO Entreprise " +
                            "(raisonSociale, mail, ville, rue, codePostal, tel, secteurActivite)" +
                            "VALUES(?, ?, ?, ?, ?, ?, ?)");
            int i = 1; //Permet d'itérer plus facilement sur chacun des paramètres
            statement.setString(i++, entreprise.raisonSociale);
            statement.setString(i++, entreprise.mail);
            statement.setString(i++, entreprise.ville);
            statement.setString(i++, entreprise.rue);
            statement.setString(i++, entreprise.codePostal);
            statement.setString(i++, entreprise.tel);
            statement.setString(i++, entreprise.secteurActivite);

            statement.executeUpdate();
            entreprise = find(entreprise.id);

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return entreprise;
    }

    @Override
    public Entreprise update(Entreprise entreprise)
    {
        return null;
    }

    /**
     * supprime l'entreprise passée en paramètre
     * si l'entreprise a posté une offre de stage, supprime l'offre de stage concernée
     * @param entreprise passée en paramètre sera supprimée et toutes les offres de stage correspondantes
     */
    public void delete(Entreprise entreprise)
    {
        try
        {
            PreparedStatement suppressionOffreStage = this.connect.prepareStatement("DELETE FROM OffreStage" +
                                                                                        "WHERE Entreprise_id= ?");
            suppressionOffreStage.setInt(1, entreprise.id);
            suppressionOffreStage.executeUpdate();


            PreparedStatement statement = this.connect.prepareStatement("DELETE FROM Entreprise " +
                                                                            "WHERE id = ?");
            statement.setInt(1, entreprise.id);
            statement.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
