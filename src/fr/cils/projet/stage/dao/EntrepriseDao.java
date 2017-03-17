package fr.cils.projet.stage.dao;

import fr.cils.projet.stage.entity.Entreprise;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
                entreprise =
                        new Entreprise(result.getInt("id"),
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

    public void delete(Entreprise entreprise)
    {
        try
        {
            PreparedStatement statement =
                    this.connect.prepareStatement("DELETE FROM Entreprise WHERE id = ?");
            statement.setInt(1, entreprise.id);
            statement.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
