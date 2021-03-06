USE `gestione_utenti`;

CREATE TABLE `utente` (
	`id_utente` INT(11) NOT NULL AUTO_INCREMENT,
	`nome` VARCHAR(50) NOT NULL,
	`cognome` VARCHAR(50) NOT NULL,
	`email` VARCHAR(50) NOT NULL,
	`telefono` VARCHAR(50) NOT NULL,
	`data_insert` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`data_update` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (`id_utente`),
	UNIQUE INDEX `email` (`email`)
)
COLLATE='latin1_swedish_ci'
ENGINE=InnoDB
;

CREATE TABLE `registro_eventi` (
	`id_evento` INT(11) NOT NULL AUTO_INCREMENT,
	`tipo_evento` VARCHAR(50) NULL DEFAULT NULL,
    `data_insert` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `data_update` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (`id_evento`)
)
COLLATE='latin1_swedish_ci'
ENGINE=InnoDB
;

CREATE TABLE `ordini_completati` (
	`id_evento` INT(11) NULL DEFAULT NULL,
	`order_id` VARCHAR(50) NULL DEFAULT NULL,
	`store_code` VARCHAR(50) NULL DEFAULT NULL,
	`payment_method` VARCHAR(50) NULL DEFAULT NULL,
	`shipping_method` VARCHAR(50) NULL DEFAULT NULL,
	`prezzo_totale` DOUBLE NULL DEFAULT NULL,
	`tassa_totale` DOUBLE NULL DEFAULT NULL,
	`ip` VARCHAR(50) NULL DEFAULT NULL,
	`browser` VARCHAR(50) NULL DEFAULT NULL,
	`carta_di_credito` VARCHAR(50) NULL DEFAULT NULL,
	`data` DATETIME NULL DEFAULT NULL,
	`email` VARCHAR(50) NOT NULL,
    `data_insert` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `data_update` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
)
COLLATE='latin1_swedish_ci'
ENGINE=InnoDB
;

CREATE TABLE `logged_out` (
	`id_evento` INT(11) NULL DEFAULT NULL,
	`ip` VARCHAR(50) NULL DEFAULT NULL,
	`browser` VARCHAR(50) NULL DEFAULT NULL,
	`data` DATETIME NULL DEFAULT NULL,
	`email` VARCHAR(50) NOT NULL,
    `data_insert` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `data_update` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
)
COLLATE='latin1_swedish_ci'
ENGINE=InnoDB
;

CREATE TABLE `logged_in` (
	`id_evento` INT(11) NULL DEFAULT NULL,
	`ip` VARCHAR(50) NULL DEFAULT NULL,
	`browser` VARCHAR(50) NULL DEFAULT NULL,
	`data` DATETIME NULL DEFAULT NULL,
	`email` VARCHAR(50) NOT NULL,
    `data_insert` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `data_update` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
)
COLLATE='latin1_swedish_ci'
ENGINE=InnoDB
;

CREATE TABLE `indirizzi` (
	`via` VARCHAR(50) NULL DEFAULT NULL,
	`citta` VARCHAR(50) NULL DEFAULT NULL,
	`provincia` VARCHAR(50) NULL DEFAULT NULL,
	`cap` VARCHAR(50) NULL DEFAULT NULL,
	`nazione` VARCHAR(50) NULL DEFAULT NULL,
	`email` VARCHAR(50) NULL DEFAULT NULL,
    `data_insert` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `data_update` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
)
COLLATE='latin1_swedish_ci'
ENGINE=InnoDB
;

CREATE TABLE `dettaglio_ordini` (
	`id_evento` INT(11) NULL DEFAULT NULL,
	`order_line_id` VARCHAR(50) NULL DEFAULT NULL,
	`id_prodotto` VARCHAR(50) NULL DEFAULT NULL,
	`quantita` INT(11) NULL DEFAULT NULL,
	`prezzo` DOUBLE NULL DEFAULT NULL,
	`tassa` DOUBLE NULL DEFAULT NULL,
    `data_insert` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `data_update` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
)
COLLATE='latin1_swedish_ci'
ENGINE=InnoDB
;