SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';


-- -----------------------------------------------------
-- Table `t_customer`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `t_customer` ;

CREATE  TABLE IF NOT EXISTS `t_customer` (
  `id_customer` INT(11) NOT NULL AUTO_INCREMENT ,
  `cust_details` LONGTEXT NULL DEFAULT NULL ,
  `cust_name` VARCHAR(30) NOT NULL ,
  `cust_status` INT(11) NOT NULL ,
  PRIMARY KEY (`id_customer`) )
ENGINE = InnoDB
AUTO_INCREMENT = 7
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `t_customer_address`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `t_customer_address` ;

CREATE  TABLE IF NOT EXISTS `t_customer_address` (
  `id_address` INT(11) NOT NULL AUTO_INCREMENT ,
  `id_customer` INT(11) NOT NULL ,
  `street_name` VARCHAR(60) NULL DEFAULT NULL ,
  `number` INT(11) NULL DEFAULT NULL ,
  `complement` VARCHAR(45) NULL DEFAULT NULL ,
  `city` VARCHAR(45) NULL DEFAULT NULL ,
  `zipcode` VARCHAR(15) NULL DEFAULT NULL ,
  `country` VARCHAR(3) NULL DEFAULT NULL ,
  `state` VARCHAR(3) NULL DEFAULT NULL ,
  PRIMARY KEY (`id_address`) ,
  CONSTRAINT `fk_t_customer_address_t_customer1`
    FOREIGN KEY (`id_customer` )
    REFERENCES `t_customer` (`id_customer` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 7
DEFAULT CHARACTER SET = latin1;

CREATE INDEX `idx_fk_t_customer_address_t_customer1` ON `t_customer_address` (`id_customer` ASC) ;


-- -----------------------------------------------------
-- Table `t_customer_contact`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `t_customer_contact` ;

CREATE  TABLE IF NOT EXISTS `t_customer_contact` (
  `id_contact` INT(11) NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(45) NULL DEFAULT NULL ,
  `email` VARCHAR(45) NULL DEFAULT NULL ,
  `office_phone_number` VARCHAR(20) NULL DEFAULT NULL ,
  `mobile_phone_number` VARCHAR(20) NULL DEFAULT NULL ,
  `contact_status` INT(11) NOT NULL ,
  `id_customer` INT(11) NOT NULL ,
  PRIMARY KEY (`id_contact`) ,
  CONSTRAINT `fk_t_customer_contact_t_customer1`
    FOREIGN KEY (`id_customer` )
    REFERENCES `t_customer` (`id_customer` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = latin1;

CREATE INDEX `idx_t_customer_contact_t_customer1` ON `t_customer_contact` (`id_customer` ASC) ;


-- -----------------------------------------------------
-- Table `t_pf`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `t_pf` ;

CREATE  TABLE IF NOT EXISTS `t_pf` (
  `id_customer` INT(11) NOT NULL ,
  `cpf` VARCHAR(14) NOT NULL ,
  `rg` BIGINT(20) NULL DEFAULT NULL ,
  PRIMARY KEY (`id_customer`) ,
  CONSTRAINT `fk_t_pf_t_customer1`
    FOREIGN KEY (`id_customer` )
    REFERENCES `t_customer` (`id_customer` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `t_pj`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `t_pj` ;

CREATE  TABLE IF NOT EXISTS `t_pj` (
  `id_customer` INT(11) NOT NULL ,
  `cnpj_cgc` VARCHAR(18) NOT NULL ,
  `inscr_estadual` BIGINT(20) NULL DEFAULT NULL ,
  `inscr_municipal` BIGINT(20) NULL DEFAULT NULL ,
  PRIMARY KEY (`id_customer`) ,
  CONSTRAINT `fk_t_pj_t_customer1`
    FOREIGN KEY (`id_customer` )
    REFERENCES `t_customer` (`id_customer` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `t_order`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `t_order` ;

CREATE  TABLE IF NOT EXISTS `t_order` (
  `id_order` BIGINT(20) NOT NULL AUTO_INCREMENT ,
  `close_date` DATE NULL DEFAULT NULL ,
  `deliver_date` DATE NULL DEFAULT NULL ,
  `order_description` VARCHAR(255) NOT NULL ,
  `open_date` DATE NOT NULL ,
  `order_code` VARCHAR(20) NOT NULL ,
  `order_price` DOUBLE NULL DEFAULT NULL ,
  `qtd_item` INT(11) NOT NULL ,
  `order_status` INT(11) NOT NULL ,
  `id_customer` INT(11) NULL DEFAULT NULL ,
  PRIMARY KEY (`id_order`) ,
  CONSTRAINT `FKFCE0E5C42F52B8D9`
    FOREIGN KEY (`id_customer` )
    REFERENCES `t_customer` (`id_customer` ))
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = latin1;

CREATE INDEX `FKFCE0E5C42F52B8D9` ON `t_order` (`id_customer` ASC) ;


-- -----------------------------------------------------
-- Table `t_production_order`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `t_production_order` ;

CREATE  TABLE IF NOT EXISTS `t_production_order` (
  `id_po` BIGINT(20) NOT NULL AUTO_INCREMENT ,
  `deliver_date` DATE NULL DEFAULT NULL ,
  `due_date` DATE NOT NULL ,
  `estimated_time` DOUBLE NULL DEFAULT NULL ,
  `open_date` DATE NOT NULL ,
  `po_number` INT(11) NOT NULL ,
  `qtd_pieces` INT(11) NULL DEFAULT NULL ,
  `sketch_number` VARCHAR(10) NULL DEFAULT NULL ,
  `po_status` INT(11) NOT NULL ,
  `id_order` BIGINT(20) NULL DEFAULT NULL ,
  PRIMARY KEY (`id_po`) ,
  CONSTRAINT `FKD6FB84B392541461`
    FOREIGN KEY (`id_order` )
    REFERENCES `t_order` (`id_order` ))
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = latin1;

CREATE INDEX `FKD6FB84B392541461` ON `t_production_order` (`id_order` ASC) ;


-- -----------------------------------------------------
-- Table `t_billing`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `t_billing` ;

CREATE  TABLE IF NOT EXISTS `t_billing` (
  `id_billing` BIGINT NOT NULL AUTO_INCREMENT ,
  `billing_final_value` DOUBLE NOT NULL ,
  `billing_date` DATE NOT NULL ,
  `billing_payment_type` INT NOT NULL ,
  `id_order` BIGINT(20) NOT NULL ,
  `billing_status` INT NOT NULL ,
  PRIMARY KEY (`id_billing`) ,
  CONSTRAINT `fk_t_billing_t_order1`
    FOREIGN KEY (`id_order` )
    REFERENCES `t_order` (`id_order` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `idx_fk_t_billing_t_order1` ON `t_billing` (`id_order` ASC) ;


-- -----------------------------------------------------
-- Table `t_billing_installments`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `t_billing_installments` ;

CREATE  TABLE IF NOT EXISTS `t_billing_installments` (
  `id_billing_installments` BIGINT NOT NULL AUTO_INCREMENT ,
  `installment_value` DOUBLE NOT NULL ,
  `installment_date` DATE NOT NULL ,
  `installment_status` INT NOT NULL ,
  `id_billing` BIGINT NOT NULL ,
  `installment_payment_date` DATE NULL ,
  `installment_final_value` DOUBLE NULL ,
  PRIMARY KEY (`id_billing_installments`) ,
  CONSTRAINT `fk_t_billing_installments_t_billing1`
    FOREIGN KEY (`id_billing` )
    REFERENCES `t_billing` (`id_billing` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `idx_fk_t_billing_installments_t_billing1` ON `t_billing_installments` (`id_billing` ASC) ;



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
