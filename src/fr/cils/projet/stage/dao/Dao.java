package sample;

import java.sql.Connection;

public abstract class DAO<T>
{
    public Connection connect = ConnectionPostgreSQL.getInstance();

    public abstract T find(int id); //Permet de récupérer un objet via son ID
    public abstract T create(T obj); //Permet de créer une entrée dans la base de données par rapport à un objet
    public abstract T update(T obj); //Permet de mettre à jour les données d'une entrée dans la base
    public abstract void delete(T obj); //Permet la suppression d'une entrée de la base
}
