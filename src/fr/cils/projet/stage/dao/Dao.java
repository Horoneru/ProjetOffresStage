package fr.cils.projet.stage.dao;

import fr.cils.projet.stage.Db;

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
}
