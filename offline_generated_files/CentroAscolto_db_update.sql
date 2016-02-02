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

ALTER TABLE `Accoglienza` 
  CHANGE `dal` `dal` DATE NOT NULL,
  CHANGE `al` `al` DATE,
  CHANGE `dove` `dove` int(10) NOT NULL,
    CHANGE `note` `note` longtext,
  CHANGE `allegato` `allegato` int(10),
CHANGE `ID_Utente_presa_in_carico_accoglienza` `ID_Utente_presa_in_carico_accoglienza` int(10) NOT NULL,
INDEX(`ID_Utente_presa_in_carico_accoglienza`),
DROP PRIMARY KEY,
ADD PRIMARY KEY ( `ID` ),
INDEX (`owner_user`),
INDEX (`owner_group`),
INDEX (`creation_user`),
INDEX (`creation_date`),
INDEX (`activation_flag`),
INDEX (`deletion_flag`)
;

ALTER TABLE `Attivazione` 
  CHANGE `dal` `dal` DATE NOT NULL,
  CHANGE `al` `al` DATE,
  CHANGE `obiettivi` `obiettivi` longtext NOT NULL,
  CHANGE `descrizione` `descrizione` longtext NOT NULL,
    CHANGE `valutazione_finale` `valutazione_finale` longtext,
  CHANGE `note` `note` longtext,
  CHANGE `allegato` `allegato` int(10),
CHANGE `ID_Utente_presa_in_carico_attivazioni` `ID_Utente_presa_in_carico_attivazioni` int(10) NOT NULL,
INDEX(`ID_Utente_presa_in_carico_attivazioni`),
DROP PRIMARY KEY,
ADD PRIMARY KEY ( `ID` ),
INDEX (`owner_user`),
INDEX (`owner_group`),
INDEX (`creation_user`),
INDEX (`creation_date`),
INDEX (`activation_flag`),
INDEX (`deletion_flag`)
;

ALTER TABLE `Cittadinanza` 
  CHANGE `stato` `stato` int(10) NOT NULL,
CHANGE `ID_Utente_cittadinanza` `ID_Utente_cittadinanza` int(10) NOT NULL,
INDEX(`ID_Utente_cittadinanza`),
DROP PRIMARY KEY,
ADD PRIMARY KEY ( `ID` ),
INDEX (`owner_user`),
INDEX (`owner_group`),
INDEX (`creation_user`),
INDEX (`creation_date`),
INDEX (`activation_flag`),
INDEX (`deletion_flag`)
;

ALTER TABLE `ClassificazioneIcfCentroAscolto` 
  CHANGE `iter` `iter` int(10) NOT NULL,
  CHANGE `data` `data` DATE NOT NULL,
    CHANGE `note` `note` longtext,
CHANGE `ID_Accoglienza_classificazioni_icf_accoglienza` `ID_Accoglienza_classificazioni_icf_accoglienza` int(10) NOT NULL,
INDEX(`ID_Accoglienza_classificazioni_icf_accoglienza`),
CHANGE `ID_Attivazione_classificazioni_icf_attivazione` `ID_Attivazione_classificazioni_icf_attivazione` int(10) NOT NULL,
INDEX(`ID_Attivazione_classificazioni_icf_attivazione`),
CHANGE `ID_Inserimento_Lavorativo_classificazioni_icf_inserimento_lavo` `ID_Inserimento_Lavorativo_classificazioni_icf_inserimento_lavo` int(10) NOT NULL,
INDEX(`ID_Inserimento_Lavorativo_classificazioni_icf_inserimento_lavo`),
DROP PRIMARY KEY,
ADD PRIMARY KEY ( `ID` ),
INDEX (`owner_user`),
INDEX (`owner_group`),
INDEX (`creation_user`),
INDEX (`creation_date`),
INDEX (`activation_flag`),
INDEX (`deletion_flag`)
;

ALTER TABLE `Competenze` 
  CHANGE `tipologia_competenza` `tipologia_competenza` int(10) NOT NULL,
  CHANGE `descrizione` `descrizione` longtext NOT NULL,
  CHANGE `allegato` `allegato` int(10),
CHANGE `ID_Utente_competenze` `ID_Utente_competenze` int(10) NOT NULL,
INDEX(`ID_Utente_competenze`),
DROP PRIMARY KEY,
ADD PRIMARY KEY ( `ID` ),
INDEX (`owner_user`),
INDEX (`owner_group`),
INDEX (`creation_user`),
INDEX (`creation_date`),
INDEX (`activation_flag`),
INDEX (`deletion_flag`)
;

ALTER TABLE `Contatto` 
  CHANGE `numero_telefono` `numero_telefono` varchar(200),
  CHANGE `altro` `altro` varchar(200),
  CHANGE `descrizione` `descrizione` longtext NOT NULL,
CHANGE `ID_Utente_contatti` `ID_Utente_contatti` int(10) NOT NULL,
INDEX(`ID_Utente_contatti`),
DROP PRIMARY KEY,
ADD PRIMARY KEY ( `ID` ),
INDEX (`owner_user`),
INDEX (`owner_group`),
INDEX (`creation_user`),
INDEX (`creation_date`),
INDEX (`activation_flag`),
INDEX (`deletion_flag`)
;

ALTER TABLE `Documento` 
  CHANGE `tipologia_documento` `tipologia_documento` int(10) NOT NULL,
  CHANGE `numero` `numero` varchar(200),
  CHANGE `scadenza` `scadenza` DATE,
  CHANGE `rilasciato_da` `rilasciato_da` varchar(200),
  CHANGE `descrizione` `descrizione` longtext NOT NULL,
  CHANGE `allegato` `allegato` int(10),
  CHANGE `allegato_1` `allegato_1` int(10),
  CHANGE `allegato_2` `allegato_2` int(10),
CHANGE `ID_Utente_documenti` `ID_Utente_documenti` int(10) NOT NULL,
INDEX(`ID_Utente_documenti`),
DROP PRIMARY KEY,
ADD PRIMARY KEY ( `ID` ),
INDEX (`owner_user`),
INDEX (`owner_group`),
INDEX (`creation_user`),
INDEX (`creation_date`),
INDEX (`activation_flag`),
INDEX (`deletion_flag`)
;

ALTER TABLE `Evento` 
  CHANGE `data` `data` DATE NOT NULL,
  CHANGE `tipo_nota` `tipo_nota` int(10) NOT NULL,
  CHANGE `testo` `testo` longtext NOT NULL,
  CHANGE `allegato` `allegato` int(10),
CHANGE `ID_Operatore_eventi_registrati` `ID_Operatore_eventi_registrati` int(10) NOT NULL,
INDEX(`ID_Operatore_eventi_registrati`),
CHANGE `ID_Utente_eventi` `ID_Utente_eventi` int(10) NOT NULL,
INDEX(`ID_Utente_eventi`),
DROP PRIMARY KEY,
ADD PRIMARY KEY ( `ID` ),
INDEX (`owner_user`),
INDEX (`owner_group`),
INDEX (`creation_user`),
INDEX (`creation_date`),
INDEX (`activation_flag`),
INDEX (`deletion_flag`)
;

ALTER TABLE `Incontro_Inserimento_Lavorativo` 
  CHANGE `data` `data` DATE NOT NULL,
  CHANGE `con` `con` int(10) NOT NULL,
  CHANGE `descrizione` `descrizione` longtext NOT NULL,
  CHANGE `note` `note` longtext,
  CHANGE `allegato` `allegato` int(10),
CHANGE `ID_Inserimento_Lavorativo_diario_incontri` `ID_Inserimento_Lavorativo_diario_incontri` int(10) NOT NULL,
INDEX(`ID_Inserimento_Lavorativo_diario_incontri`),
DROP PRIMARY KEY,
ADD PRIMARY KEY ( `ID` ),
INDEX (`owner_user`),
INDEX (`owner_group`),
INDEX (`creation_user`),
INDEX (`creation_date`),
INDEX (`activation_flag`),
INDEX (`deletion_flag`)
;

ALTER TABLE `Inserimento_Lavorativo` 
  CHANGE `dal` `dal` DATE NOT NULL,
  CHANGE `al` `al` DATE,
  CHANGE `ente_avviante` `ente_avviante` longtext NOT NULL,
  CHANGE `persone_di_riferimento` `persone_di_riferimento` longtext NOT NULL,
  CHANGE `settore_inserimento` `settore_inserimento` longtext NOT NULL,
  CHANGE `mansioni` `mansioni` longtext NOT NULL,
  CHANGE `punti_di_forza` `punti_di_forza` longtext NOT NULL,
  CHANGE `punti_di_debolezza` `punti_di_debolezza` longtext NOT NULL,
  CHANGE `obiettivi` `obiettivi` longtext NOT NULL,
  CHANGE `descrizione` `descrizione` longtext NOT NULL,
      CHANGE `valutazione_finale` `valutazione_finale` longtext,
  CHANGE `note` `note` longtext,
  CHANGE `allegato` `allegato` int(10),
CHANGE `ID_Utente_presa_in_carico_inserimenti_lavorativi` `ID_Utente_presa_in_carico_inserimenti_lavorativi` int(10) NOT NULL,
INDEX(`ID_Utente_presa_in_carico_inserimenti_lavorativi`),
DROP PRIMARY KEY,
ADD PRIMARY KEY ( `ID` ),
INDEX (`owner_user`),
INDEX (`owner_group`),
INDEX (`creation_user`),
INDEX (`creation_date`),
INDEX (`activation_flag`),
INDEX (`deletion_flag`)
;

ALTER TABLE `ItemIcfCentroAscolto` 
  CHANGE `codice_centro_ascolto` `codice_centro_ascolto` varchar(200) NOT NULL,
  CHANGE `descrizione` `descrizione` longtext NOT NULL,
  CHANGE `classificazione` `classificazione` int(10),
  CHANGE `allegato` `allegato` int(10),
CHANGE `ID_ClassificazioneIcfCentroAscolto_item` `ID_ClassificazioneIcfCentroAscolto_item` int(10) NOT NULL,
INDEX(`ID_ClassificazioneIcfCentroAscolto_item`),
DROP PRIMARY KEY,
ADD PRIMARY KEY ( `ID` ),
INDEX (`owner_user`),
INDEX (`owner_group`),
INDEX (`creation_user`),
INDEX (`creation_date`),
INDEX (`activation_flag`),
INDEX (`deletion_flag`)
;

ALTER TABLE `_system_user` 
  CHANGE `user_user_id` `user_user_id` varchar(200) NOT NULL,
  CHANGE `user_name` `user_name` varchar(200) NOT NULL,
  CHANGE `user_email` `user_email` varchar(200),
  CHANGE `user_phone_number` `user_phone_number` varchar(200),
    DROP PRIMARY KEY,
ADD PRIMARY KEY ( `ID` ),
INDEX (`owner_user`),
INDEX (`owner_group`),
INDEX (`creation_user`),
INDEX (`creation_date`),
INDEX (`activation_flag`),
INDEX (`deletion_flag`)
;

ALTER TABLE `ResidenzaInAlloggio` 
  CHANGE `tipologia_alloggio` `tipologia_alloggio` int(10) NOT NULL,
  CHANGE `dal` `dal` DATE NOT NULL,
  CHANGE `al` `al` DATE,
  CHANGE `descrizione` `descrizione` longtext NOT NULL,
  CHANGE `allegato` `allegato` int(10),
CHANGE `ID_Utente_residenze_in_alloggio` `ID_Utente_residenze_in_alloggio` int(10) NOT NULL,
INDEX(`ID_Utente_residenze_in_alloggio`),
DROP PRIMARY KEY,
ADD PRIMARY KEY ( `ID` ),
INDEX (`owner_user`),
INDEX (`owner_group`),
INDEX (`creation_user`),
INDEX (`creation_date`),
INDEX (`activation_flag`),
INDEX (`deletion_flag`)
;

ALTER TABLE `ServizioDiRiferimento` 
  CHANGE `tipo_servizio` `tipo_servizio` int(10) NOT NULL,
  CHANGE `referente` `referente` varchar(200),
  CHANGE `info` `info` longtext,
CHANGE `ID_Utente_servizi_di_riferimento` `ID_Utente_servizi_di_riferimento` int(10) NOT NULL,
INDEX(`ID_Utente_servizi_di_riferimento`),
DROP PRIMARY KEY,
ADD PRIMARY KEY ( `ID` ),
INDEX (`owner_user`),
INDEX (`owner_group`),
INDEX (`creation_user`),
INDEX (`creation_date`),
INDEX (`activation_flag`),
INDEX (`deletion_flag`)
;

ALTER TABLE `Tessera` 
  CHANGE `tipologia_tessera` `tipologia_tessera` int(10) NOT NULL,
  CHANGE `emissione` `emissione` DATE,
  CHANGE `scadenza` `scadenza` DATE,
  CHANGE `sabatodomenica` `sabatodomenica` int(10),
CHANGE `ID_Utente_tessere` `ID_Utente_tessere` int(10) NOT NULL,
INDEX(`ID_Utente_tessere`),
DROP PRIMARY KEY,
ADD PRIMARY KEY ( `ID` ),
INDEX (`owner_user`),
INDEX (`owner_group`),
INDEX (`creation_user`),
INDEX (`creation_date`),
INDEX (`activation_flag`),
INDEX (`deletion_flag`)
;

ALTER TABLE `Utente` 
  CHANGE `scheda` `scheda` int(10),
  CHANGE `nome` `nome` varchar(200) NOT NULL,
  CHANGE `cognome` `cognome` varchar(200) NOT NULL,
  CHANGE `data_primo_colloquio` `data_primo_colloquio` DATE NOT NULL,
  CHANGE `data_n` `data_n` DATE NOT NULL,
  CHANGE `data_arrivo_it` `data_arrivo_it` DATE,
  CHANGE `data_arrivo_bo` `data_arrivo_bo` DATE,
    CHANGE `stato_n` `stato_n` int(10) NOT NULL,
  CHANGE `comune_n` `comune_n` varchar(200),
  CHANGE `cf` `cf` varchar(200),
  CHANGE `sesso` `sesso` int(10) NOT NULL,
  CHANGE `espulso` `espulso` int(10),
  CHANGE `deceduto` `deceduto` int(10),
  CHANGE `foto` `foto` int(10),
  CHANGE `privo_residenza` `privo_residenza` int(10),
  CHANGE `residenza_comune` `residenza_comune` varchar(200),
  CHANGE `residenza_cap` `residenza_cap` varchar(200),
  CHANGE `residenza_via` `residenza_via` varchar(200),
  CHANGE `residenza_provincia` `residenza_provincia` varchar(200),
  CHANGE `stato_civile` `stato_civile` int(10),
  CHANGE `stato_famiglia` `stato_famiglia` int(10),
  CHANGE `comune_famiglia` `comune_famiglia` varchar(200),
  CHANGE `figli` `figli` int(10),
  CHANGE `titolo_studio` `titolo_studio` int(10),
  CHANGE `situazione_economica` `situazione_economica` int(10),
  CHANGE `altro` `altro` longtext,
  CHANGE `automunito` `automunito` int(10),
  CHANGE `percentuale_inv` `percentuale_inv` int(10),
  CHANGE `madrelingua` `madrelingua` varchar(200),
  CHANGE `lingua_it` `lingua_it` int(10),
  CHANGE `altre_lingue` `altre_lingue` varchar(200),
  CHANGE `domicilio_comune` `domicilio_comune` varchar(200),
  CHANGE `domicilio_cap` `domicilio_cap` varchar(200),
  CHANGE `domicilio_via` `domicilio_via` varchar(200),
  CHANGE `domicilio_provincia` `domicilio_provincia` varchar(200),
  CHANGE `nota_domicilio` `nota_domicilio` varchar(200),
                    CHANGE `ID_Operatore_primi_colloqui` `ID_Operatore_primi_colloqui` int(10) NOT NULL,
INDEX(`ID_Operatore_primi_colloqui`),
DROP PRIMARY KEY,
ADD PRIMARY KEY ( `ID` ),
INDEX (`owner_user`),
INDEX (`owner_group`),
INDEX (`creation_user`),
INDEX (`creation_date`),
INDEX (`activation_flag`),
INDEX (`deletion_flag`)
;




-- Fine parte dinamica 



/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
