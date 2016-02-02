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



insert into `_system_user` 
(`user_name`,`user_user_id`,`user_password`,`user_phone_number`,`creation_date`, `creation_user`,`deletion_flag`, `activation_flag`)
SELECT concat(`nome`,' ', `cognome`), `username`, `password`, `operativo`,curdate(),'admin',0,1 FROM `operatori` WHERE ID > 1;


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

insert into `Cittadinanza`
(`ID_Utente_cittadinanza`,`stato`,`creation_date`, `creation_user`,`deletion_flag`, `activation_flag`)
SELECT `utente_id`, `stato_id`,curdate(),'admin',0,1 FROM `nazionalita` ;

insert into `Contatto` 
(`ID_Utente_contatti`,`numero_telefono`,`descrizione`,`creation_date`, `creation_user`,`deletion_flag`, `activation_flag`)
SELECT `utente_id`, `numero`, `descrizione`,curdate(),'admin',0,1 FROM `telefoni`;

insert into `Tessera` 
(`ID_Utente_tessere`,`emissione`,`scadenza`,`tipologia_tessera`,`sabatodomenica`,`creation_date`, `creation_user`,`deletion_flag`, `activation_flag`)
SELECT `utente_id`, `emissione`, `scadenza`, `tipologia`, `sabatodomenica`,curdate(),'admin',0,1 FROM `tessere`;

delete from Utente;
insert into `Utente`
(`ID`,`nome`,`cognome`,`cf`,`figli`,`data_n`,`data_primo_colloquio`,`data_arrivo_it`,`data_arrivo_bo`,`sesso`,`automunito`,`espulso`,`deceduto`,`privo_residenza`,
`residenza_via`,`residenza_cap`,`residenza_comune`,`domicilio_via`,`domicilio_cap`,`domicilio_comune`,`nota_domicilio`,`stato_n`,`comune_n`,`stato_famiglia`,
`comune_famiglia`,`titolo_studio`,`stato_civile`,`lingua_it`,`altre_lingue`,`madrelingua`,`ID_Operatore_primi_colloqui`,`percentuale_inv`,
`situazione_economica`,`altro`,`creation_date`, `creation_user`,`deletion_flag`, `activation_flag`)
SELECT `ID`, `nome`, `cognome`, `cf`, `figli`, `data_n`, `data_primo_colloquio`, `data_arrivo_it`, `data_arrivo_bo`, `sesso`, `automunito`, `espulso`, `deceduto`, 
`privo_residenza`, `residenza_via`, `residenza_cap`, 
(select comune from tipi_comuni where ID = `residenza_comune`) as `residenza_comune`, 
`domicilio_via`, `domicilio_cap`,
(select comune from tipi_comuni where ID = `domicilio_comune`)as `domicilio_comune`,  
`nota_domicilio`, `stato_n`,
(select comune from tipi_comuni where ID = `comune_n`) as `comune_n`, 
`stato_famiglia`, 
(select comune from tipi_comuni where ID = `comune_famiglia`) as `comune_famiglia`,  
`titolo_studio`, `stato_civile`, `lingua_it`, `altre_lingue`, `madrelingua`, `op_primo_colloquio`, `percentuale_inv`, `situazione_economica`, `altro`, curdate(),'admin',0,1 FROM `utenti`;


