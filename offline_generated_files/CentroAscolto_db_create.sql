-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.0.27-community-nt


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO,MYSQL323' */;


--
-- Create schema (null)
--




-- Parte dinamica

CREATE TABLE `Cittadinanza` (
  `ID` int(10) unsigned NOT NULL auto_increment,
  `stato` int(10) NOT NULL,
  `owner_user` int(10) unsigned NULL,
  `owner_group` int(10) unsigned NULL,
  `creation_date` date NOT NULL,
  `creation_user` varchar(100) NOT NULL,
  `last_modification_date` date,
  `last_modification_user` varchar(100),
  `deletion_date` date,
  `deletion_user` varchar(100),
  `deletion_flag` tinyint(1) NOT NULL,
  `activation_flag` tinyint(1) NOT NULL,
`ID_Utente_cittadinanza` int(10) NOT NULL,
INDEX(`ID_Utente_cittadinanza`),
PRIMARY KEY ( `ID` ),
INDEX (`owner_user`),
INDEX (`owner_group`),
INDEX (`creation_user`),
INDEX (`creation_date`),
INDEX (`activation_flag`),
INDEX (`deletion_flag`)) ENGINE=InnoDB;

CREATE TABLE `Competenze` (
  `ID` int(10) unsigned NOT NULL auto_increment,
  `allegato` int(10),
  `descrizione` longtext NOT NULL,
  `tipologia_competenza` int(10) NOT NULL,
  `owner_user` int(10) unsigned NULL,
  `owner_group` int(10) unsigned NULL,
  `creation_date` date NOT NULL,
  `creation_user` varchar(100) NOT NULL,
  `last_modification_date` date,
  `last_modification_user` varchar(100),
  `deletion_date` date,
  `deletion_user` varchar(100),
  `deletion_flag` tinyint(1) NOT NULL,
  `activation_flag` tinyint(1) NOT NULL,
`ID_Utente_competenze` int(10) NOT NULL,
INDEX(`ID_Utente_competenze`),
PRIMARY KEY ( `ID` ),
INDEX (`owner_user`),
INDEX (`owner_group`),
INDEX (`creation_user`),
INDEX (`creation_date`),
INDEX (`activation_flag`),
INDEX (`deletion_flag`)) ENGINE=InnoDB;

CREATE TABLE `Contatto` (
  `ID` int(10) unsigned NOT NULL auto_increment,
  `altro` varchar(200),
  `descrizione` longtext NOT NULL,
  `numero_telefono` varchar(200),
  `owner_user` int(10) unsigned NULL,
  `owner_group` int(10) unsigned NULL,
  `creation_date` date NOT NULL,
  `creation_user` varchar(100) NOT NULL,
  `last_modification_date` date,
  `last_modification_user` varchar(100),
  `deletion_date` date,
  `deletion_user` varchar(100),
  `deletion_flag` tinyint(1) NOT NULL,
  `activation_flag` tinyint(1) NOT NULL,
`ID_Utente_contatti` int(10) NOT NULL,
INDEX(`ID_Utente_contatti`),
PRIMARY KEY ( `ID` ),
INDEX (`owner_user`),
INDEX (`owner_group`),
INDEX (`creation_user`),
INDEX (`creation_date`),
INDEX (`activation_flag`),
INDEX (`deletion_flag`)) ENGINE=InnoDB;

CREATE TABLE `Documento` (
  `ID` int(10) unsigned NOT NULL auto_increment,
  `allegato` int(10),
  `allegato_1` int(10),
  `allegato_2` int(10),
  `descrizione` longtext NOT NULL,
  `numero` varchar(200),
  `rilasciato_da` varchar(200),
  `scadenza` DATE,
  `tipologia_documento` int(10) NOT NULL,
  `owner_user` int(10) unsigned NULL,
  `owner_group` int(10) unsigned NULL,
  `creation_date` date NOT NULL,
  `creation_user` varchar(100) NOT NULL,
  `last_modification_date` date,
  `last_modification_user` varchar(100),
  `deletion_date` date,
  `deletion_user` varchar(100),
  `deletion_flag` tinyint(1) NOT NULL,
  `activation_flag` tinyint(1) NOT NULL,
`ID_Utente_documenti` int(10) NOT NULL,
INDEX(`ID_Utente_documenti`),
PRIMARY KEY ( `ID` ),
INDEX (`owner_user`),
INDEX (`owner_group`),
INDEX (`creation_user`),
INDEX (`creation_date`),
INDEX (`activation_flag`),
INDEX (`deletion_flag`)) ENGINE=InnoDB;

CREATE TABLE `Nota` (
  `ID` int(10) unsigned NOT NULL auto_increment,
  `allegato` int(10),
  `data` DATE NOT NULL,
  `testo` longtext NOT NULL,
  `tipo_nota` int(10) NOT NULL,
  `owner_user` int(10) unsigned NULL,
  `owner_group` int(10) unsigned NULL,
  `creation_date` date NOT NULL,
  `creation_user` varchar(100) NOT NULL,
  `last_modification_date` date,
  `last_modification_user` varchar(100),
  `deletion_date` date,
  `deletion_user` varchar(100),
  `deletion_flag` tinyint(1) NOT NULL,
  `activation_flag` tinyint(1) NOT NULL,
`ID_Utente_note` int(10) NOT NULL,
INDEX(`ID_Utente_note`),
PRIMARY KEY ( `ID` ),
INDEX (`owner_user`),
INDEX (`owner_group`),
INDEX (`creation_user`),
INDEX (`creation_date`),
INDEX (`activation_flag`),
INDEX (`deletion_flag`)) ENGINE=InnoDB;

CREATE TABLE `Operatore` (
  `ID` int(10) unsigned NOT NULL auto_increment,
    `user_email` varchar(200),
  `user_name` varchar(200) NOT NULL,
  `user_phone_number` varchar(200),
  `user_user_id` varchar(200) NOT NULL,
  `owner_user` int(10) unsigned NULL,
  `owner_group` int(10) unsigned NULL,
  `creation_date` date NOT NULL,
  `creation_user` varchar(100) NOT NULL,
  `last_modification_date` date,
  `last_modification_user` varchar(100),
  `deletion_date` date,
  `deletion_user` varchar(100),
  `deletion_flag` tinyint(1) NOT NULL,
  `activation_flag` tinyint(1) NOT NULL,
PRIMARY KEY ( `ID` ),
INDEX (`owner_user`),
INDEX (`owner_group`),
INDEX (`creation_user`),
INDEX (`creation_date`),
INDEX (`activation_flag`),
INDEX (`deletion_flag`)) ENGINE=InnoDB;

CREATE TABLE `ResidenzaInAlloggio` (
  `ID` int(10) unsigned NOT NULL auto_increment,
  `al` DATE,
  `allegato` int(10),
  `dal` DATE NOT NULL,
  `descrizione` longtext NOT NULL,
  `tipologia_alloggio` int(10) NOT NULL,
  `owner_user` int(10) unsigned NULL,
  `owner_group` int(10) unsigned NULL,
  `creation_date` date NOT NULL,
  `creation_user` varchar(100) NOT NULL,
  `last_modification_date` date,
  `last_modification_user` varchar(100),
  `deletion_date` date,
  `deletion_user` varchar(100),
  `deletion_flag` tinyint(1) NOT NULL,
  `activation_flag` tinyint(1) NOT NULL,
`ID_Utente_residenze_in_alloggio` int(10) NOT NULL,
INDEX(`ID_Utente_residenze_in_alloggio`),
PRIMARY KEY ( `ID` ),
INDEX (`owner_user`),
INDEX (`owner_group`),
INDEX (`creation_user`),
INDEX (`creation_date`),
INDEX (`activation_flag`),
INDEX (`deletion_flag`)) ENGINE=InnoDB;

CREATE TABLE `ServizioDiRiferimento` (
  `ID` int(10) unsigned NOT NULL auto_increment,
  `info` longtext,
  `referente` varchar(200),
  `tipo_servizio` int(10) NOT NULL,
  `owner_user` int(10) unsigned NULL,
  `owner_group` int(10) unsigned NULL,
  `creation_date` date NOT NULL,
  `creation_user` varchar(100) NOT NULL,
  `last_modification_date` date,
  `last_modification_user` varchar(100),
  `deletion_date` date,
  `deletion_user` varchar(100),
  `deletion_flag` tinyint(1) NOT NULL,
  `activation_flag` tinyint(1) NOT NULL,
`ID_Utente_servizi_di_riferimento` int(10) NOT NULL,
INDEX(`ID_Utente_servizi_di_riferimento`),
PRIMARY KEY ( `ID` ),
INDEX (`owner_user`),
INDEX (`owner_group`),
INDEX (`creation_user`),
INDEX (`creation_date`),
INDEX (`activation_flag`),
INDEX (`deletion_flag`)) ENGINE=InnoDB;

CREATE TABLE `Tessera` (
  `ID` int(10) unsigned NOT NULL auto_increment,
  `emissione` DATE,
  `sabatodomenica` int(10),
  `scadenza` DATE,
  `tipologia_tessera` int(10) NOT NULL,
  `owner_user` int(10) unsigned NULL,
  `owner_group` int(10) unsigned NULL,
  `creation_date` date NOT NULL,
  `creation_user` varchar(100) NOT NULL,
  `last_modification_date` date,
  `last_modification_user` varchar(100),
  `deletion_date` date,
  `deletion_user` varchar(100),
  `deletion_flag` tinyint(1) NOT NULL,
  `activation_flag` tinyint(1) NOT NULL,
`ID_Utente_tessere` int(10) NOT NULL,
INDEX(`ID_Utente_tessere`),
PRIMARY KEY ( `ID` ),
INDEX (`owner_user`),
INDEX (`owner_group`),
INDEX (`creation_user`),
INDEX (`creation_date`),
INDEX (`activation_flag`),
INDEX (`deletion_flag`)) ENGINE=InnoDB;

CREATE TABLE `Utente` (
  `ID` int(10) unsigned NOT NULL auto_increment,
  `altre_lingue` varchar(200),
  `altro` longtext,
  `automunito` int(10),
  `cf` varchar(200),
    `cognome` varchar(200) NOT NULL,
    `comune_famiglia` varchar(200),
  `comune_n` varchar(200),
    `data_arrivo_bo` DATE,
  `data_arrivo_it` DATE,
  `data_n` DATE NOT NULL,
  `data_primo_colloquio` DATE NOT NULL,
  `deceduto` int(10),
    `domicilio_cap` varchar(200),
  `domicilio_comune` varchar(200),
  `domicilio_provincia` varchar(200),
  `domicilio_via` varchar(200),
  `espulso` int(10),
  `figli` int(10),
  `foto` int(10),
  `lingua_it` int(10),
  `madrelingua` varchar(200),
  `nome` varchar(200) NOT NULL,
  `nota_domicilio` varchar(200),
  `percentuale_inv` int(10),
  `privo_residenza` int(10),
  `residenza_cap` varchar(200),
  `residenza_comune` varchar(200),
  `residenza_provincia` varchar(200),
  `residenza_via` varchar(200),
    `scheda` int(10),
    `sesso` int(10) NOT NULL,
  `situazione_economica` int(10),
  `stato_civile` int(10),
  `stato_famiglia` int(10),
  `stato_n` int(10) NOT NULL,
    `titolo_studio` int(10),
  `owner_user` int(10) unsigned NULL,
  `owner_group` int(10) unsigned NULL,
  `creation_date` date NOT NULL,
  `creation_user` varchar(100) NOT NULL,
  `last_modification_date` date,
  `last_modification_user` varchar(100),
  `deletion_date` date,
  `deletion_user` varchar(100),
  `deletion_flag` tinyint(1) NOT NULL,
  `activation_flag` tinyint(1) NOT NULL,
`ID_Operatore_primi_colloqui` int(10) NOT NULL,
INDEX(`ID_Operatore_primi_colloqui`),
PRIMARY KEY ( `ID` ),
INDEX (`owner_user`),
INDEX (`owner_group`),
INDEX (`creation_user`),
INDEX (`creation_date`),
INDEX (`activation_flag`),
INDEX (`deletion_flag`)) ENGINE=InnoDB;




-- Fine parte dinamica 



/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
