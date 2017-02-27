package sample;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class offrestageDao extends DAO<OffreStage>
{
    public OffreStage find(int unNumOffreStage)
    {
        OffreStage uneOffreStage = new OffreStage();
        try
        {
            ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                                                            ResultSet.CONCUR_UPDATABLE).executeQuery("SELECT * " +
                                                                                                        "FROM entreprise " +
                                                                                                        "WHERE numEntreprise = " + unNumOffreStage);
            if(result.first())
            {
                uneOffreStage = new OffreStage(unNumOffreStage, result.getString("nomOffreStage"));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return uneOffreStage;
    }

    @Override
    public OffreStage find(long id)
    {
        return null;
    }

    public OffreStage create(OffreStage uneOffreStage)
    {
        try
        {
            ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                                                            ResultSet.CONCUR_UPDATABLE).executeQuery("SELECT NEXTVAL('langage_lan_id_seq') as id");
            if(result.first())
            {
                int unNumOffreStage = result.getInt("numOffreStage");
                PreparedStatement prepare = this.connect.prepareStatement("INSERT INTO ofrrestage (numOffreStage, nomOffreStage) " +
                                                                             "VALUES(?, ?)");
                prepare.setLong(1, unNumOffreStage);
                prepare.setString(2, uneOffreStage.getNomOffreStage());

                prepare.executeUpdate();
                uneOffreStage = this.find(unNumOffreStage);

            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return uneOffreStage;
    }

    public OffreStage update(OffreStage uneOffreStage)
    {
        try
        {
            this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                                         ResultSet.CONCUR_UPDATABLE).executeUpdate("UPDATE entreprise" +
                                                                                      "SET nomEntreprise = '" + uneOffreStage.getNomOffreStage() + "'" +
                                                                                      "WHERE numEntreprise = " + uneOffreStage.getNumOffreStage());
            uneOffreStage = this.find(uneOffreStage.getNumOffreStage());
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return uneOffreStage;
    }

    public void delete(OffreStage uneOffreStage)
    {
        try
        {
            this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                                         ResultSet.CONCUR_UPDATABLE).executeUpdate("DELETE FROM entreprise " +
                                                                                      "WHERE nomEntreprise = " + uneOffreStage.getNumOffreStage());
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
