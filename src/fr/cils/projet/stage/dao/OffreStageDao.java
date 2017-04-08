package fr.cils.projet.stage.dao;

import fr.cils.projet.stage.entity.Entreprise;
import fr.cils.projet.stage.entity.OffreStage;

import java.sql.*;

public class OffreStageDao extends Dao<OffreStage>
{
    public OffreStage find(int id)
    {
        OffreStage offreStage = null;
        try
        {
            PreparedStatement statement =
                    connect.prepareStatement("SELECT * FROM OffreStage WHERE id= ?");
            statement.setInt(1, id);

            statement.execute();
            ResultSet result = statement.getResultSet();

            if(result.first())
            {
                PreparedStatement selectEntrepriseParId = connect.prepareStatement("SELECT FROM Entreprise WHERE id= ?");
                statement.setInt(1, id);
                statement.execute();

                ResultSet resultRequeteEntreprise = selectEntrepriseParId.getResultSet();

                if (resultRequeteEntreprise.first())
                {
                    Entreprise entreprise = new Entreprise(resultRequeteEntreprise.getInt("id"),
                                                            resultRequeteEntreprise.getString("raisonSociale"),
                                                            resultRequeteEntreprise.getString("mail"),
                                                            resultRequeteEntreprise.getString("ville"),
                                                            resultRequeteEntreprise.getString("rue"),
                                                            resultRequeteEntreprise.getString("codePostal"),
                                                            resultRequeteEntreprise.getString("tel"),
                                                            resultRequeteEntreprise.getString("secteurActivite"));

                    offreStage =
                            new OffreStage(result.getInt("id"), result.getString("libelle"),
                                    result.getString("description"), result.getString("domaine"),
                                    result.getDate("dateDebut").toLocalDate(), result.getInt("duree"),
                                    result.getBoolean("estValide"), entreprise);
                }
                offreStage =
                        new OffreStage(result.getInt("id"), result.getString("libelle"),
                        result.getString("description"), result.getString("domaine"),
                        result.getDate("dateDebut").toLocalDate(), result.getInt("duree"),
                        result.getBoolean("estValide"));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }

        return offreStage;
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
                                                                                                "duree=?, estValide=?" +
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

    public void delete(OffreStage offreStage)
    {
        try
        {
            PreparedStatement statement =
                    connect.prepareStatement("DELETE FROM OffreStage WHERE id = ?");
            statement.setInt(1, offreStage.id);
            statement.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
