

CREATE TABLE `Allegato` (
  `ID` int(10) unsigned NOT NULL auto_increment,
  `allegato` int(10) NOT NULL,
  `ref_class` varchar(200) NOT NULL,
  `ref_instance` varchar(200) NOT NULL,
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
`ID_Utente_allegati` int(10) NOT NULL,
INDEX(`ID_Utente_allegati`),
PRIMARY KEY ( `ID` ),
INDEX (`owner_user`),
INDEX (`owner_group`),
INDEX (`creation_user`),
INDEX (`creation_date`),
INDEX (`activation_flag`),
INDEX (`deletion_flag`)) ENGINE=InnoDB;
    

CREATE TABLE IF NOT EXISTS `rel_Utente_servizi_antoniano` (
`ID_Utente` int(10) NOT NULL,
`ID_servizi_antoniano` int(10) NOT NULL,
PRIMARY KEY  (`ID_Utente`,`ID_servizi_antoniano`)
) ENGINE=InnoDB;

ALTER TABLE `Utente` 
add `ID_Utente_familiari` int(10) NULL,
add   `situazione_sanitaria` longtext;

INSERT INTO `_system_decode_class` (`id`,`sdc_name`,`activation_flag`,`deletion_flag`) VALUES
(302,'servizi_antoniano',1,0);
INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`) VALUES
(302,0,'it','mensa diurna',1,0),
(302,1,'it','mensa serale',1,0),
(302,2,'it','accoglienza',1,0),
(302,3,'it','san ruffillo',1,0),
(302,4,'it','via gorizia',1,0);


Insert into Allegato (ID_Utente_allegati, allegato, ref_class,ref_instance,  creation_date, creation_user)
SELECT `ID_Utente_eventi`, `allegato`, 'Evento', ID, creation_date, creation_user FROM `Evento` WHERE allegato is not null;

ALTER TABLE `ResidenzaInAlloggio` CHANGE `dal` `dal` DATE NULL DEFAULT NULL ;



