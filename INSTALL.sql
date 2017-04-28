-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema projetstage
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `projetstage` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `projetstage` ;

-- -----------------------------------------------------
-- Table `projetstage`.`Utilisateur`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projetstage`.`Utilisateur` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '',
  `login` VARCHAR(45) NOT NULL COMMENT '',
  `pass` VARCHAR(200) NOT NULL COMMENT '',
  `role` VARCHAR(20) NOT NULL COMMENT '',
  PRIMARY KEY (`id`)  COMMENT '',
  UNIQUE INDEX `login_UNIQUE` (`login` ASC)  COMMENT '')
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `projetstage`.`Entreprise`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projetstage`.`Entreprise` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '',
  `raisonSociale` VARCHAR(100) NOT NULL COMMENT '',
  `mail` VARCHAR(60) NULL COMMENT '',
  `ville` VARCHAR(100) NOT NULL COMMENT '',
  `rue` VARCHAR(80) NOT NULL COMMENT '',
  `codePostal` VARCHAR(5) NOT NULL COMMENT '',
  `tel` VARCHAR(10) NOT NULL COMMENT '',
  `secteurActivite` VARCHAR(60) NOT NULL COMMENT '',
  `Utilisateur_id` INT NOT NULL COMMENT '',
  PRIMARY KEY (`id`, `Utilisateur_id`)  COMMENT '',
  INDEX `fk_Entreprise_Utilisateur1_idx` (`Utilisateur_id` ASC)  COMMENT '',
  CONSTRAINT `fk_Entreprise_Utilisateur1`
    FOREIGN KEY (`Utilisateur_id`)
    REFERENCES `projetstage`.`Utilisateur` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `projetstage`.`OffreStage`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projetstage`.`OffreStage` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '',
  `libelle` VARCHAR(50) NOT NULL COMMENT '',
  `description` MEDIUMTEXT NOT NULL COMMENT '',
  `domaine` VARCHAR(45) NOT NULL COMMENT '',
  `dateDebut` DATE NOT NULL COMMENT '',
  `duree` INT NOT NULL COMMENT '',
  `estValide` TINYINT(1) NOT NULL COMMENT '',
  `Entreprise_id` INT NOT NULL COMMENT '',
  PRIMARY KEY (`id`)  COMMENT '',
  INDEX `fk_OffreStage_Entreprise_idx` (`Entreprise_id` ASC)  COMMENT '',
  CONSTRAINT `fk_OffreStage_Entreprise`
    FOREIGN KEY (`Entreprise_id`)
    REFERENCES `projetstage`.`Entreprise` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `projetstage`.`Utilisateur_has_OffreStage`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projetstage`.`Utilisateur_has_OffreStage` (
  `Utilisateur_id` INT NOT NULL COMMENT '',
  `OffreStage_id` INT NOT NULL COMMENT '',
  `validee` TINYINT(1) NULL DEFAULT 0 COMMENT '',
  PRIMARY KEY (`Utilisateur_id`, `OffreStage_id`)  COMMENT '',
  INDEX `fk_Utilisateur_has_OffreStage_OffreStage1_idx` (`OffreStage_id` ASC)  COMMENT '',
  INDEX `fk_Utilisateur_has_OffreStage_Utilisateur1_idx` (`Utilisateur_id` ASC)  COMMENT '',
  CONSTRAINT `fk_Utilisateur_has_OffreStage_Utilisateur1`
    FOREIGN KEY (`Utilisateur_id`)
    REFERENCES `projetstage`.`Utilisateur` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Utilisateur_has_OffreStage_OffreStage1`
    FOREIGN KEY (`OffreStage_id`)
    REFERENCES `projetstage`.`OffreStage` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- ------------------------------------------------------
-- Création de l'admin initial
-- ------------------------------------------------------
INSERT INTO Utilisateur VALUES(1,  "admin", "d033e22ae348aeb5660fc2140aec35850c4da997", "Admin");