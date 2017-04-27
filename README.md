# Projet offres de stage 
POO class group project, using JavaFX

## What's the goal ?

Using design patterns efficiently on a small project in collaboration with others, while learning JavaFX and git workflow

[Project description](http://puu.sh/usywA/59c0bce468.pdf) (french)

## Task assignment
- [Horoneru](https://github.com/Horoneru) : Repo admin, general logic, main reviewer, menu designer, database diagram, merge conflicts resolver, role logic
- [Antoria](https://github.com/Antoria) : UI (fxml), original skin, controller logic, admin management logic
- [Bobpty](https://github.com/Bobpty) : Database layer, SHA-1 hashes logic, writing report, class diagram, sequence diagram, use-case diagram

## How to run
- Download the jdbc mysql connector through the mysql site and extract it somewhere
- Add the jar in the extracted files to your libraries in your IDE settings
- Make sure the settings in [Db.java](src/fr/cils/projet/stage/Db.java) are correct regarding your database location and credentials
- Import the database structure by running the [INSTALL.sql](INSTALL.sql) file

You're good to go ! The initial admin credentials are : 
- login : admin
- pass : admin
