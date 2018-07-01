ALTER TABLE `Utente` ADD `progetti_richiedenti_asilo` INT NULL AFTER `situazione_sanitaria`, ADD `progetti_richiedenti_asilo_dove` INT NULL AFTER `progetti_richiedenti_asilo`;

INSERT INTO `_system_decode_class` (`ID`, `sdc_description`, `sdc_name`, `owner_user`, `owner_group`, `creation_date`, `creation_user`, `last_modification_date`, `last_modification_user`, `deletion_date`, `deletion_user`, `deletion_flag`, `activation_flag`) 
VALUES ('305', NULL, 'progetti richiedenti asilo', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '0', '1');

INSERT INTO `_system_decode_class` (`ID`, `sdc_description`, `sdc_name`, `owner_user`, `owner_group`, `creation_date`, `creation_user`, `last_modification_date`, `last_modification_user`, `deletion_date`, `deletion_user`, `deletion_flag`, `activation_flag`) 
VALUES ('306', NULL, 'progetti richiedenti asilo: dove', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '0', '1');

INSERT INTO `_system_decode` ( `sd_description`, `sd_value`, `sd_locale`, `sd_notes`, `ID__system_decode_sd_parent`, `sd_image`, `owner_user`, `owner_group`, `creation_date`, `creation_user`, `last_modification_date`, `last_modification_user`, `deletion_date`, `deletion_user`, `deletion_flag`, `activation_flag`, `sd_class`) VALUES
( 'CAS', 1, 'it', NULL, NULL, NULL, NULL, NULL, '2016-02-02', 'admin', NULL, NULL, NULL, NULL, 0, 1, 305),
( 'SPRAR', 2, 'it', NULL, NULL, NULL, NULL, NULL, '2016-02-02', 'admin', NULL, NULL, NULL, NULL, 0, 1, 305),
( 'Accoglienza Richiedenti Asilo', 3, 'it', NULL, NULL, NULL, NULL, NULL, '2016-02-02', 'admin', NULL, NULL, NULL, NULL, 0, 1, 305);


INSERT INTO `_system_decode` ( `sd_description`, `sd_value`, `sd_locale`, `sd_notes`, `ID__system_decode_sd_parent`, `sd_image`, `owner_user`, `owner_group`, `creation_date`, `creation_user`, `last_modification_date`, `last_modification_user`, `deletion_date`, `deletion_user`, `deletion_flag`, `activation_flag`, `sd_class`) VALUES
( 'ER', 1, 'it', NULL, NULL, NULL, NULL, NULL, '2016-02-02', 'admin', NULL, NULL, NULL, NULL, 0, 1, 306), 
( 'Fuori ER', 2, 'it', NULL, NULL, NULL, NULL, NULL, '2016-02-02', 'admin', NULL, NULL, NULL, NULL, 0, 1, 306);

INSERT INTO `_system_menu_item` ( `smi_title`, `smi_order`, `smi_locale`, `smi_association`, `smi_href`, `smi_alternative_text`, `ID__system_menu_item_smi_children`, `owner_user`, `owner_group`, `creation_date`, `creation_user`, `last_modification_date`, `last_modification_user`, `deletion_date`, `deletion_user`, `deletion_flag`, `activation_flag`) VALUES ( 'Utenti: nuovi e rinnovi', '3', 'it', NULL, '?q=object/filter&p=Utente&t=stat&y=2018&s=utenti-nuovi-rinnovi', 'Utenti: nuovi e rinnovi', '30', NULL, NULL, '2016-05-01', 'admin', '2016-05-08', 'admin', NULL, NULL, '0', '1');
INSERT INTO `_system_menu_item` ( `smi_title`, `smi_order`, `smi_locale`, `smi_association`, `smi_href`, `smi_alternative_text`, `ID__system_menu_item_smi_children`, `owner_user`, `owner_group`, `creation_date`, `creation_user`, `last_modification_date`, `last_modification_user`, `deletion_date`, `deletion_user`, `deletion_flag`, `activation_flag`) VALUES ( 'Utenti con interventi', '9', 'it', NULL, '?q=object/filter&p=Utente&t=stat&y=2018&s=utenti-attivazioni-accoglienza-inserimenti', 'Utenti con interventi', '30', NULL, NULL, '2016-05-01', 'admin', '2016-05-08', 'admin', NULL, NULL, '0', '1');

INSERT INTO _system_menu_item (  smi_href, smi_order, ID__system_menu_item_smi_children, smi_locale, smi_title, activation_flag, smi_alternative_text, deletion_flag, creation_user, creation_date, last_modification_user, last_modification_date ) VALUES(  "?q=object/filter&p=Inserimento_Lavorativo", "21", "17", "it", "Inserimenti lavorativi", "1", "Inserimenti lavorativi", "0", "admin", "2018-05-09", "admin", "2018-05-09" );
INSERT INTO _system_menu_item ( smi_href, smi_order, ID__system_menu_item_smi_children, smi_locale, smi_title, activation_flag, smi_alternative_text, deletion_flag, creation_user, creation_date, last_modification_user, last_modification_date ) VALUES(  "?q=object/filter&p=Accoglienza", "23", "17", "it", "Accoglienze", "1", "Accoglienze", "0", "admin", "2018-05-09", "admin", "2018-05-09" );
INSERT INTO _system_menu_item (  smi_href, smi_order, ID__system_menu_item_smi_children, smi_locale, smi_title, activation_flag, smi_alternative_text, deletion_flag, creation_user, creation_date, last_modification_user, last_modification_date ) VALUES(  "?q=object/filter&p=Attivazione", "25", "17", "it", "Attivazioni", "1", "Attivazioni", "0", "admin", "2018-05-09", "admin", "2018-05-09" );

-- giugno

INSERT INTO `_system_menu_item` ( `smi_title`, `smi_order`, `smi_locale`, `smi_association`, `smi_href`, `smi_alternative_text`, `ID__system_menu_item_smi_children`, `owner_user`, `owner_group`, `creation_date`, `creation_user`, `last_modification_date`, `last_modification_user`, `deletion_date`, `deletion_user`, `deletion_flag`, `activation_flag`) VALUES ( 'Età media utenti con tessera', '3', 'it', NULL, '?q=object/filter&p=Utente&t=stat-eta&y=2018&s=eta-media-utenti-attivi', 'Età media utenti con tessera', '30', NULL, NULL, '2016-05-01', 'admin', '2016-05-08', 'admin', NULL, NULL, '0', '1');

INSERT INTO `_system_decode_class` (`id`,`sdc_name`,`activation_flag`,`deletion_flag`) VALUES
(307,'anno',1,0);
INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_notes`,`sd_description`,`activation_flag`,`deletion_flag`) VALUES
(307,1,'it','a1','2018',1,0),
(307,2,'it','a11','2019',1,0),
(307,3,'it','a2','2020',1,0),
(307,4,'it','a21','2021',1,0),
(307,5,'it','a3','2022',1,0),
(307,6,'it','a31','2023',1,0),
(307,7,'it','a4','2024',1,0),
(307,8,'it','a41','2025',1,0),
(307,9,'it','a5','2026',1,0),
(307,10,'it','a51','2027',1,0),
(307,11,'it','a6','2028',1,0),
(307,12,'it','a61','2029',1,0),
(307,13,'it','a7','2030',1,0);

INSERT INTO `_system_decode_class` (`id`,`sdc_name`,`activation_flag`,`deletion_flag`) VALUES
(308,'mese',1,0);
INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_notes`,`sd_description`,`activation_flag`,`deletion_flag`) VALUES
(308,1,'it','a1','gennaio',1,0),
(308,2,'it','a11','febbraio',1,0),
(308,3,'it','a2','marzo',1,0),
(308,4,'it','a22','aprile',1,0),
(308,5,'it','a3','maggio',1,0),
(308,6,'it','a33','giugno',1,0),
(308,7,'it','a4','luglio',1,0),
(308,8,'it','a44','agosto',1,0),
(308,9,'it','a5','settembre',1,0),
(308,10,'it','a55','ottobre',1,0),
(308,11,'it','a6','novembre',1,0),
(308,12,'it','a66','dicembre',1,0);

CREATE TABLE `Spesa` (
  `ID` int(10) unsigned NOT NULL auto_increment,
  `anno` int(10) NOT NULL,
  `mese` int(10) NOT NULL,
  `note` longtext,
  `solo_percorsi` double,
  `tipo_di_spesa` varchar(200) NOT NULL,
  `unrra` double,
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
`ID_Operatore_spese_utenti` int(10) NOT NULL,
INDEX(`ID_Operatore_spese_utenti`),
`ID_Utente_spese` int(10) NOT NULL,
INDEX(`ID_Utente_spese`),
PRIMARY KEY ( `ID` ),
INDEX (`owner_user`),
INDEX (`owner_group`),
INDEX (`creation_user`),
INDEX (`creation_date`),
INDEX (`activation_flag`),
INDEX (`deletion_flag`)) ENGINE=InnoDB;