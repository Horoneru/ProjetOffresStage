package fr.cils.projet.stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe implémentant le pattern Singleton et représentant la base de donnée
 */
public class Db
{
    private static String url = "jdbc:mysql://localhost:3306/projetstage";

    /*
        Config de base pour la plupart des serveurs tout-en-un pour les tests
     */

    private static String user = "root";

    private static String pass = "";

    /**
     * L'objet connexion  base de donnée située à url
     */

    private static Connection connexion;


    /**
     * Cette méthode donne l'instance nécessaire à la gestion de la base de donnée.
     * L'instance retournée n'est créé qu'au premier appel (lazy-loading)
     * @return l'instance qui sera utilisée globalement pour gérer la base de données
     */
    public static Connection getInstance()
    {
        if(connexion == null)
        {
            try
            {
                connexion = DriverManager.getConnection(url, user, pass);
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return connexion;
    }
}
