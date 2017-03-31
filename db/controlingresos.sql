-- MySQL Script generated by MySQL Workbench
-- 03/30/17 19:26:05
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema controlingresos
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Table `movements`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `movements` (
  `movement_id` INT NOT NULL AUTO_INCREMENT,
  `concept` VARCHAR(256) NOT NULL,
  `amount` FLOAT NOT NULL,
  `description` VARCHAR(256) NULL,
  `date` DATE NOT NULL,
  `type` INT(1) NOT NULL,
  PRIMARY KEY (`movement_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `users` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NULL,
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`user_id`))
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
