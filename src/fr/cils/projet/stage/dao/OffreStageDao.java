package fr.cils.projet.stage.dao;

import fr.cils.projet.stage.entity.Entreprise;
import fr.cils.projet.stage.entity.OffreStage;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
        }

        return offreStage;
    }

    public OffreStage create(OffreStage offreStage)
    {
        try
        {
                PreparedStatement statement = connect.prepareStatement("INSERT INTO OffreStage " +
                        "(libelle, description, domaine, dateDebut, duree, estValide, Entreprise_id) " +
                        "VALUES(?, ?, ?, ?, ?, ?, ?)");

                int i = 1; //Permet d'itérer plus facilement sur chacun des paramètres
                statement.setString(i++, offreStage.libelle);
                statement.setString(i++, offreStage.description);
                statement.setString(i++, offreStage.domaine);
                statement.setDate(i++, Date.valueOf(offreStage.dateDebut));
                statement.setInt(i++, offreStage.duree);
                statement.setBoolean(i++, offreStage.estValide);
                statement.setInt(i++, offreStage.entrepriseAssociee.id);
                statement.executeUpdate();

                offreStage = this.find(offreStage.id);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return offreStage;
    }

    public OffreStage update(OffreStage offreStage)
    {
        return null;
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
