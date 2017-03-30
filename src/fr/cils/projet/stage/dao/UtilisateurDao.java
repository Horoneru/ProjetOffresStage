package fr.cils.projet.stage.dao;

import fr.cils.projet.stage.entity.OffreStage;
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
                                                result.getString("pass"),
                                                result.getBoolean("estEntreprise"));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return unUtilisateur;
    }

    public Utilisateur find(String login)
    {
        Utilisateur unUtilisateur = null;
        try
        {
            PreparedStatement statement = connect.prepareStatement("SELECT * FROM Utilisateur WHERE login= ?");
            statement.setString(1, login);

            statement.execute();
            ResultSet result = statement.getResultSet();
            if(result.first())
            {
                unUtilisateur = new Utilisateur(result.getInt("id"),
                                                result.getString("login"),
                                                result.getString("pass"),
                                                result.getBoolean("estEntreprise"));
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
            PreparedStatement statement = connect.prepareStatement("INSERT INTO Utilisateur (login, pass, estEntreprise) " +
                                                                    "VALUES(?, ?, ?)");

            int i = 1; //Permet d'itérer plus facilement sur chacun des paramètres
            statement.setString(i++, utilisateur.login);
            statement.setString(i++, utilisateur.pass);
            statement.setBoolean(i++, utilisateur.estEntreprise);
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

    public void delete(Utilisateur utilisateur)
    {
        try
        {
            PreparedStatement suppressionOffreStage = connect.prepareStatement("DELETE FROM Utilisateur_has_OffreStage" +
                                                                                "WHERE Utilisateur_id= ?");
            suppressionOffreStage.setInt(1, utilisateur.id);
            suppressionOffreStage.executeUpdate();

            PreparedStatement statement = connect.prepareStatement("DELETE FROM Utilisateur " +
                                                                    "WHERE id = ?");
            statement.setInt(1, utilisateur.id);
            statement.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * méthode créant un lien "postuler" entre un utilisateur et une offre de stage
     * @param utilisateur souhaitant postuler pour l'offre de stage saisie en paramètre
     * @param offreStage sélectionnée par l'utilisateur passé en paramètre
     * @return true si l'association a été correctement effectuée sinon elle est attrapée par une exception
     */
    public boolean postuler(Utilisateur utilisateur, OffreStage offreStage)
    {
        try
        {
            PreparedStatement statement = connect.prepareStatement("INSERT INTO Utilisateur_has_OffreStage (Utilisateur_id, OffreStage_id" +
                                                                        "VALUES (?, ?))");

            int i = 1; //Permet d'itérer plus facilement sur chacun des paramètres
            statement.setInt(i++, utilisateur.id);
            statement.setInt(i++, offreStage.id);
            statement.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return true;
    }
}
