package sample;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class entrepriseDao extends DAO<Entreprise>
{
    public Entreprise find(int unNumEntreprise)
    {
        Entreprise uneEntreprise = new Entreprise();
        try
        {
            ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                                                            ResultSet.CONCUR_UPDATABLE).executeQuery("SELECT * FROM entreprise WHERE numEntreprise = " + unNumEntreprise);
            if(result.first())
            {
                uneEntreprise = new Entreprise(unNumEntreprise, result.getString("nomEntreprise"));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return uneEntreprise;
    }

    @Override
    public Entreprise find(long id)
    {
        return null;
    }

    public Entreprise create(Entreprise uneEntreprise)
    {
        try
        {
            ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                                                            ResultSet.CONCUR_UPDATABLE).executeQuery("SELECT NEXTVAL('langage_lan_id_seq') as id");
            if(result.first())
            {
                int unNumEntreprise = result.getInt("numEntreprise");
                PreparedStatement prepare = this.connect.prepareStatement("INSERT INTO entreprise (numEntreprise, nomEntreprise) " +
                                                                             "VALUES(?, ?)");
                prepare.setLong(1, unNumEntreprise);
                prepare.setString(2, uneEntreprise.getNomEntreprise());

                prepare.executeUpdate();
                uneEntreprise = this.find(unNumEntreprise);

            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return uneEntreprise;
    }

    public Entreprise update(Entreprise uneEntreprise)
    {
        try
        {
            this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                                         ResultSet.CONCUR_UPDATABLE).executeUpdate("UPDATE entreprise" +
                                                                                      "SET nomEntreprise = '" + uneEntreprise.getNomEntreprise() + "'" +
                                                                                      "WHERE numEntreprise = " + uneEntreprise.getNumEntreprise());
            uneEntreprise = this.find(uneEntreprise.getNumEntreprise());
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return uneEntreprise;
    }

    public void delete(Entreprise uneEntreprise)
    {
        try
        {
            this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                                         ResultSet.CONCUR_UPDATABLE).executeUpdate("DELETE FROM entreprise " +
                                                                                      "WHERE nomEntreprise = " + uneEntreprise.getNumEntreprise());
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
