package fr.cils.projet.stage.dao;

import fr.cils.projet.stage.entity.Entreprise;
import fr.cils.projet.stage.entity.OffreStage;
import fr.cils.projet.stage.entity.Utilisateur;

import java.sql.*;
import java.util.ArrayList;

public class OffreStageDao extends Dao<OffreStage>
{
    public OffreStage find(int id)
    {
        OffreStage offreStage = null;
        try
        {
            PreparedStatement statement = connect.prepareStatement("SELECT * FROM OffreStage WHERE id= ?");
            statement.setInt(1, id);
            statement.execute();
            ResultSet result = statement.getResultSet();
            if(result.first())
            {
                offreStage = new OffreStage(result.getInt("id"),
                        result.getString("libelle"),
                        result.getString("description"),
                        result.getString("domaine"),
                        result.getDate("dateDebut").toLocalDate(),
                        result.getInt("duree"),
                        result.getBoolean("estValide"),
                        new EntrepriseDao().find(result.getInt("Entreprise_id")));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }

        return offreStage;
    }

    public ArrayList<OffreStage> findAll()
    {
        ArrayList<OffreStage> listeOffresStage = new ArrayList<>();
        try
        {
            PreparedStatement statement = connect.prepareStatement("SELECT * FROM OffreStage");
            statement.execute();
            ResultSet result = statement.getResultSet();
            if(result.first())
            {
                while (!result.isAfterLast())
                {
                    OffreStage offreStage = new OffreStage(result.getInt("id"),
                                                            result.getString("libelle"),
                                                            result.getString("description"),
                                                            result.getString("domaine"),
                                                            result.getDate("dateDebut").toLocalDate(),
                                                            result.getInt("duree"),
                                                            result.getBoolean("estValide"),
                                                            new EntrepriseDao().find(result.getInt("Entreprise_id")));

                    listeOffresStage.add(offreStage);
                    result.next();
                }
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }

        return listeOffresStage;
    }

    public OffreStage create(OffreStage offreStage)
    {
        try
        {
                PreparedStatement statement = connect.prepareStatement("INSERT INTO OffreStage " +
                        "(libelle, description, domaine, dateDebut, duree, estValide, Entreprise_id) " +
                        "VALUES(?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

                int i = 1; //Permet d'itérer plus facilement sur chacun des paramètres
                statement.setString(i++, offreStage.libelle);
                statement.setString(i++, offreStage.description);
                statement.setString(i++, offreStage.domaine);
                statement.setDate(i++, Date.valueOf(offreStage.dateDebut));
                statement.setInt(i++, offreStage.duree);
                statement.setBoolean(i++, offreStage.estValide);
                statement.setInt(i++, offreStage.entrepriseAssociee.id);
                statement.executeUpdate();

                ResultSet keys = statement.getGeneratedKeys();
                keys.next();
                offreStage.id = keys.getInt(1);
                offreStage = find(offreStage.id);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }

        return offreStage;
    }

    public boolean update(OffreStage offreStage)
    {
        try
        {
            PreparedStatement modificationOffreStage = this.connect.prepareStatement("UPDATE OffreStage " +
                                                                                            "SET libelle=?, description=?, " +
                                                                                                "domaine=?, dateDebut=?, " +
                                                                                                "duree=?, estValide=? " +
                                                                                            "WHERE id=?");

            int i = 1;
            modificationOffreStage.setString(i++, offreStage.libelle);
            modificationOffreStage.setString(i++, offreStage.description);
            modificationOffreStage.setString(i++, offreStage.domaine);
            modificationOffreStage.setDate(i++, Date.valueOf(offreStage.dateDebut));
            modificationOffreStage.setInt(i++, offreStage.duree);
            modificationOffreStage.setBoolean(i++, offreStage.estValide);
            modificationOffreStage.setInt(i++, offreStage.id);

            modificationOffreStage.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean delete(OffreStage offreStage)
    {
        try
        {
            PreparedStatement supprimerUtilisateurHasOffreStage = connect.prepareStatement("DELETE FROM Utilisateur_has_OffreStage WHERE OffreStage_id = ?");
            supprimerUtilisateurHasOffreStage.setInt(1, offreStage.id);
            supprimerUtilisateurHasOffreStage.executeUpdate();

            PreparedStatement statement = connect.prepareStatement("DELETE FROM OffreStage WHERE id = ?");
            statement.setInt(1, offreStage.id);
            statement.executeUpdate();
            return true;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }
}
