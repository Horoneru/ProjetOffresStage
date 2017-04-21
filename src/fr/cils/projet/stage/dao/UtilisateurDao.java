package fr.cils.projet.stage.dao;

import fr.cils.projet.stage.Controller;
import fr.cils.projet.stage.entity.OffreStage;
import fr.cils.projet.stage.entity.Role;
import fr.cils.projet.stage.entity.Utilisateur;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class UtilisateurDao extends Dao<Utilisateur>
{
    public Utilisateur find(int id)
    {
        Utilisateur utilisateur = null;
        try
        {
            PreparedStatement statement = connect.prepareStatement("SELECT * FROM Utilisateur WHERE id= ?");
            statement.setInt(1, id);
            statement.execute();
            ResultSet result = statement.getResultSet();

            if(result.first())
            {
                utilisateur = new Utilisateur(result.getInt("id"),
                        result.getString("login"),
                        result.getString("pass"),
                        Role.valueOf(result.getString("role")),
                        new UtilisateurDao().findAllOffresStage(utilisateur));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }
        return utilisateur;
    }

    public Utilisateur find(String login)
    {
        Utilisateur utilisateur = null;
        try
        {
            PreparedStatement statement = connect.prepareStatement("SELECT * FROM Utilisateur WHERE login= ?");
            statement.setString(1, login);
            statement.execute();
            ResultSet result = statement.getResultSet();

            if(result.first())
            {
                utilisateur = new Utilisateur(result.getInt("id"),
                        result.getString("login"),
                        result.getString("pass"),
                        Role.valueOf(result.getString("role")),
                        new UtilisateurDao().findAllOffresStage(utilisateur));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }

        return utilisateur;
    }

    public ArrayList<Utilisateur> findAll()
    {
        ArrayList<Utilisateur> listeUtilisateurs = new ArrayList<>();
        try
        {
            PreparedStatement statement = connect.prepareStatement("SELECT * FROM Utilisateur");
            statement.execute();
            ResultSet result = statement.getResultSet();

            while (result.next())
            {
                Utilisateur utilisateur = new Utilisateur(result.getInt("id"),
                        result.getString("login"),
                        result.getString("pass"),
                        Role.valueOf(result.getString("role")));

                listeUtilisateurs.add(utilisateur);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }

        return listeUtilisateurs;
    }

    public Utilisateur create(Utilisateur utilisateur)
    {
        try
        {
            PreparedStatement statement = connect.prepareStatement("INSERT INTO Utilisateur (login, pass, role) " +
                    "VALUES(?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

            int i = 1; //Permet d'itérer plus facilement sur chacun des paramètres
            statement.setString(i++, utilisateur.login);
            statement.setString(i++, Controller.chiffrementSHA1(utilisateur.pass));
            statement.setString(i++, utilisateur.role.name());
            statement.executeUpdate();

            ResultSet keys = statement.getGeneratedKeys();
            keys.next();
            utilisateur.id = keys.getInt(1);
            utilisateur = find(utilisateur.id);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }

        return utilisateur;
    }

    public boolean update(Utilisateur utilisateur)
    {
        try
        {
            PreparedStatement modificationUtilisateur = this.connect.prepareStatement("UPDATE Utilisateur " +
                    "SET login=?, pass=?, role=?" +
                    " WHERE id=?");

            int i = 1;
            modificationUtilisateur.setString(i++, utilisateur.login);
            modificationUtilisateur.setString(i++, Controller.chiffrementSHA1(utilisateur.pass));
            modificationUtilisateur.setString(i++, utilisateur.role.name());
            modificationUtilisateur.setInt(i++, utilisateur.id);

            modificationUtilisateur.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean delete(Utilisateur utilisateur)
    {
        try
        {
            PreparedStatement suppressionOffreStage = connect.prepareStatement("DELETE FROM Utilisateur_has_OffreStage WHERE Utilisateur_id= ?");
            suppressionOffreStage.setInt(1, utilisateur.id);
            suppressionOffreStage.executeUpdate();

            PreparedStatement statement = connect.prepareStatement("DELETE FROM Utilisateur WHERE id = ?");
            statement.setInt(1, utilisateur.id);
            statement.executeUpdate();
            return true;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return false;
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
            PreparedStatement statement = connect.prepareStatement("INSERT INTO Utilisateur_has_OffreStage (Utilisateur_id, OffreStage_id) VALUES (?, ?)");

            int i = 1; //Permet d'itérer plus facilement sur chacun des paramètres
            statement.setInt(i++, utilisateur.id);
            statement.setInt(i++, offreStage.id);
            statement.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public ArrayList<OffreStage> findAllOffresStage(Utilisateur utilisateur)
    {
        ArrayList<OffreStage> listeOffresStagePostulees = new ArrayList<>();
        try
        {
            PreparedStatement statement = connect.prepareStatement("SELECT * " +
                                                                        "FROM Utilisateur_has_OffreStage as U, OffreStage as O " +
                                                                        "WHERE U.OffreStage_id = O.id " +
                                                                        "AND U.Utilisateur_id = ?");
            statement.setInt(1, utilisateur.id);
            statement.execute();
            ResultSet result = statement.getResultSet();

            while (result.next())
            {
                OffreStage offreStage = new OffreStage(result.getInt("id"),
                                                        result.getString("libelle"),
                                                        result.getString("description"),
                                                        result.getString("domaine"),
                                                        result.getDate("dateDebut").toLocalDate(),
                                                        result.getInt("duree"),
                                                        result.getBoolean("estValide"),
                                                        new EntrepriseDao().find(result.getInt("Entreprise_id")));

                listeOffresStagePostulees.add(offreStage);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }

        return listeOffresStagePostulees;
    }
}
