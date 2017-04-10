package fr.cils.projet.stage.dao;

import fr.cils.projet.stage.Db;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;

public abstract class Dao<T>
{

    //Liaison à la db. Seules les sous classes de Dao<T> l'utilise
    //Package-private
    Connection connect = Db.getInstance();

    /**
     * Cherche un objet T avec un identifiant id.
     * @param id l'identifiant de l'objet T cherché
     * @return T l'objet T ou null si celui-ci n'est pas trouvé ou si une erreur est survenue
     */
    public abstract T find(int id);

    /**
     * Insère l'objet obj dans la db
     * @param obj l'objet que l'on souhaite persister
     * @return T l'objet T inséré dans la db ou null si celui-ci n'est pas trouvé
     */
    public abstract T create(T obj);

    /**
     * Met à jour l'obj obj dans la db
     * @param obj qui sera mis à jour
     * @return un booléen indiquant true si la requête s'est bien exécutée, false sinon
     */
    public abstract boolean update(T obj);

    /**
     * Supprime l'objet obj de la db en le sélectionnant via son id
     * @param obj l'objet que l'on souhaite supprimer de la db
     */
    public abstract void delete(T obj);


    /**
     * Crypte le mot de passe de l'uitilisateur
     * source : https://www.developpez.net/forums/d92122/java/general-java/apis/securite/algorithme-hachage-type-md5-sha1/
     * @param mdp le mot de passe de l'utilisateur à crypter
     * @return le mot de passe crypté sous forme de chaîne de caractères
     */
    public String mdpCryptage(String mdp)
    {

        byte[] uniqueKey = mdp.getBytes();
        byte[] hash = null;

        try
        {
            hash = MessageDigest.getInstance("SHA1").digest(uniqueKey); //MD2, MD5, SHA-1, SHA-256, SHA-384, SHA-512
        }
        catch (NoSuchAlgorithmException e)
        {
            throw new Error(e);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        StringBuffer hashString = new StringBuffer();
        for (int i = 0; i < hash.length; ++i)
        {
            String hex = Integer.toHexString(hash[i]);
            if ( hex.length() == 1 )
            {
                hashString.append('0');
                hashString.append(hex.charAt(hex.length()-1));
            }
            else
            {
                hashString.append(hex.substring(hex.length()-2));
            }
        }
        return hashString.toString();
    }
}
