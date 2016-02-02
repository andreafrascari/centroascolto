/* ONE SHOT: DECO */

INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`,`creation_user`,`creation_date`) 
select 212, ID, 'it', nome, 1, 0, 'admin',curdate() from  `tipi_titoli_studio`;

INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`,`creation_user`,`creation_date`) 
select 204, ID, 'it', nome, 1, 0, 'admin',curdate() from  `tipi_tipo_servizio`;

INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`,`creation_user`,`creation_date`) 
select 205, ID, 'it', nome, 1, 0, 'admin',curdate() from  `tipi_tessere`;

INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`,`creation_user`,`creation_date`) 
select 203, ID, 'it', nome, 1, 0, 'admin',curdate() from  `tipi_alloggi`;

INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`,`creation_user`,`creation_date`) 
select 211, ID, 'it', nome, 1, 0, 'admin',curdate() from  `tipi_stati`;

INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`,`creation_user`,`creation_date`) 
select 210, ID, 'it', nome, 1, 0, 'admin',curdate() from  `tipi_stato_civile`;

INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`,`creation_user`,`creation_date`) 
select 200, ID, 'it', nome, 1, 0, 'admin',curdate() from  `tipi_competenze`;

INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`,`creation_user`,`creation_date`) 
select 209, ID, 'it', nome, 1, 0, 'admin',curdate() from  `tipi_situazione_economica`;

INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`,`creation_user`,`creation_date`) 
select 202, ID, 'it', nome, 1, 0, 'admin',curdate() from  `tipi_nota`;

INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`,`creation_user`,`creation_date`) 
select 201, ID, 'it', nome, 1, 0, 'admin',curdate() from  `tipi_documenti`;


/*
INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`,`creation_user`,`creation_date`) 
select 2, ID, 'it', nome, 1, 0, 'admin',curdate() from  `tipi_area_intervento`;


INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`,`creation_user`,`creation_date`) 
select 212, ID, 'it', nome, 1, 0, 'admin',curdate() from  `tipi_comuni`;
*/


/* data */

insert into ResidenzaInAlloggio (`tipologia_alloggio`, `dal`, `al`, `descrizione`, `creation_date`, `creation_user`,  `deletion_flag`, `activation_flag`, `ID_Utente_residenze_in_alloggio`)
SELECT  `tipologia`, `data`,null, `descrizione`,curdate(),'admin', 0,1, `utente_id` FROM `alloggi` ;

insert into  `Competenze` 
(`tipologia_competenza`, `descrizione`,`creation_date`, `creation_user`, `deletion_flag`, `activation_flag`, `ID_Utente_competenze`)
SELECT  `tipologia`, `descrizione`,curdate(),'admin',0,1,  `utente_id` FROM `competenze`;

insert into `Documento`
(`ID_Utente_documenti`,`scadenza`,`tipologia_documento`,`numero`,`descrizione`,`rilasciato_da`,`creation_date`, `creation_user`,`deletion_flag`, `activation_flag`)
SELECT `utente_id`, `scadenza`, `tipologia`, `numero`, `descrizione`, `rilasciato_da`,curdate(),'admin',0,1 FROM `documenti`;

insert into `Evento` 
(`data`,ID_Operatore_eventi_registrati,`ID_Utente_eventi`,`tipo_nota`,`testo`,`creation_date`, `creation_user`,`deletion_flag`, `activation_flag`)
SELECT `data`, `operatore_id`, `utente_id`, `tipologia`, `testo`,curdate(),'admin',0,1 FROM `note` ;









