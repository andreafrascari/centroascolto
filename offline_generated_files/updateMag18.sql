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


