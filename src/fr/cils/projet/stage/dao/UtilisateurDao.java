package fr.cils.projet.stage.dao;

import fr.cils.projet.stage.entity.Utilisateur;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UtilisateurDao extends Dao<Utilisateur>
{
    public Utilisateur find(int id)
    {
        Utilisateur unUtilisateur = null;
        try
        {
            PreparedStatement statement = connect.prepareStatement("SELECT * FROM Utilisateur WHERE id= ?");
            statement.setInt(1, id);

            statement.execute();
            ResultSet result = statement.getResultSet();
            if(result.first())
            {
                unUtilisateur = new Utilisateur(result.getInt("id"),
                                                result.getString("login"),
                                                result.getString("pass"));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return unUtilisateur;
    }

    public Utilisateur create(Utilisateur utilisateur)
    {
        try
        {
            PreparedStatement statement = connect.prepareStatement("INSERT INTO Utilisateur (login, pass) " +
                                                                    "VALUES(?, ?)");

            int i = 1; //Permet d'itérer plus facilement sur chacun des paramètres
            statement.setString(i++, utilisateur.login);
            statement.setString(i++, utilisateur.pass);
            statement.executeUpdate();

            utilisateur = this.find(utilisateur.id);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return utilisateur;
    }

    public Utilisateur update(Utilisateur unUtilisateur)
    {
        return null;
    }

    public void delete(Utilisateur unUtilisateur)
    {
        try
        {
            PreparedStatement statement = connect.prepareStatement("DELETE FROM Utilisateur " +
                                                                    "WHERE id = ?");
            statement.setInt(1, unUtilisateur.id);
            statement.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
