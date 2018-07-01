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


INSERT INTO `_system_decode_class` (`id`,`sdc_name`,`activation_flag`,`deletion_flag`) VALUES
(213,'dove',1,0);
INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`) VALUES
(213,0,'it','San Ruffillo',1,0),
(213,1,'it','Via Gorizia',1,0),
(213,2,'it','Centro ascolto',1,0);
INSERT INTO `_system_decode_class` (`id`,`sdc_name`,`activation_flag`,`deletion_flag`) VALUES
(211,'stato',1,0);
INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`) VALUES
INSERT INTO `_system_decode_class` (`id`,`sdc_name`,`activation_flag`,`deletion_flag`) VALUES
(214,'iter',1,0);
INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`) VALUES
(214,0,'it','iniziale',1,0),
(214,1,'it','intermedia',1,0),
(214,2,'it','finale',1,0);
INSERT INTO `_system_decode_class` (`id`,`sdc_name`,`activation_flag`,`deletion_flag`) VALUES
(200,'tipologia_competenza',1,0);
INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`) VALUES
(200,0,'it','muratore',1,0);
INSERT INTO `_system_decode_class` (`id`,`sdc_name`,`activation_flag`,`deletion_flag`) VALUES
(201,'tipologia_documento',1,0);
INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`) VALUES
(201,0,'it','Patente',1,0);
INSERT INTO `_system_decode_class` (`id`,`sdc_name`,`activation_flag`,`deletion_flag`) VALUES
(301,'tipo_documento',1,0);
INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`) VALUES
(301,0,'it','verbale',1,0),
(301,1,'it','report',1,0);
INSERT INTO `_system_decode_class` (`id`,`sdc_name`,`activation_flag`,`deletion_flag`) VALUES
(202,'tipo_nota',1,0);
INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`) VALUES
(202,0,'it','Sanitaria',1,0);
INSERT INTO `_system_decode_class` (`id`,`sdc_name`,`activation_flag`,`deletion_flag`) VALUES
(215,'con',1,0);
INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`) VALUES
(215,0,'it','beneficiario',1,0),
(215,1,'it','tutor aziendale',1,0),
(215,2,'it','ente avviante',1,0),
(215,3,'it','mental coach',1,0);
INSERT INTO `_system_decode_class` (`id`,`sdc_name`,`activation_flag`,`deletion_flag`) VALUES
(203,'tipologia_alloggio',1,0);
INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`) VALUES
(203,0,'it','affitto',1,0);
INSERT INTO `_system_decode_class` (`id`,`sdc_name`,`activation_flag`,`deletion_flag`) VALUES
(303,'classe_alloggio',1,0);
INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`) VALUES
INSERT INTO `_system_decode_class` (`id`,`sdc_name`,`activation_flag`,`deletion_flag`) VALUES
(204,'tipo_servizio',1,0);
INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`) VALUES
(204,0,'it','SST',1,0);
INSERT INTO `_system_decode_class` (`id`,`sdc_name`,`activation_flag`,`deletion_flag`) VALUES
(307,'anno',1,0);
INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`) VALUES
(307,0,'it','2018',1,0);
INSERT INTO `_system_decode_class` (`id`,`sdc_name`,`activation_flag`,`deletion_flag`) VALUES
(308,'mese',1,0);
INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`) VALUES
(308,0,'it','gennaio',1,0);
INSERT INTO `_system_decode_class` (`id`,`sdc_name`,`activation_flag`,`deletion_flag`) VALUES
(205,'tipologia_tessera',1,0);
INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`) VALUES
(205,0,'it','salute',1,0);
INSERT INTO `_system_decode_class` (`id`,`sdc_name`,`activation_flag`,`deletion_flag`) VALUES
(300,'sabatodomenica',1,0);
INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`) VALUES
INSERT INTO `_system_decode_class` (`id`,`sdc_name`,`activation_flag`,`deletion_flag`) VALUES
(304,'area_geografica_provenienza',1,0);
INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`) VALUES
INSERT INTO `_system_decode_class` (`id`,`sdc_name`,`activation_flag`,`deletion_flag`) VALUES
(211,'stato_n',1,0);
INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`) VALUES
(211,0,'it','Bulgaria',1,0);
INSERT INTO `_system_decode_class` (`id`,`sdc_name`,`activation_flag`,`deletion_flag`) VALUES
(208,'sesso',1,0);
INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`) VALUES
(208,0,'it','maschio',1,0),
(208,1,'it','femmina',1,0),
(208,2,'it','trans',1,0);
INSERT INTO `_system_decode_class` (`id`,`sdc_name`,`activation_flag`,`deletion_flag`) VALUES
(300,'espulso',1,0);
INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`) VALUES
INSERT INTO `_system_decode_class` (`id`,`sdc_name`,`activation_flag`,`deletion_flag`) VALUES
(300,'deceduto',1,0);
INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`) VALUES
INSERT INTO `_system_decode_class` (`id`,`sdc_name`,`activation_flag`,`deletion_flag`) VALUES
(300,'privo_residenza',1,0);
INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`) VALUES
INSERT INTO `_system_decode_class` (`id`,`sdc_name`,`activation_flag`,`deletion_flag`) VALUES
(210,'stato_civile',1,0);
INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`) VALUES
(210,0,'it','Celibe/Nubile',1,0);
INSERT INTO `_system_decode_class` (`id`,`sdc_name`,`activation_flag`,`deletion_flag`) VALUES
(211,'stato_famiglia',1,0);
INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`) VALUES
INSERT INTO `_system_decode_class` (`id`,`sdc_name`,`activation_flag`,`deletion_flag`) VALUES
(212,'titolo_studio',1,0);
INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`) VALUES
(212,0,'it','Diploma',1,0);
INSERT INTO `_system_decode_class` (`id`,`sdc_name`,`activation_flag`,`deletion_flag`) VALUES
(209,'situazione_economica',1,0);
INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`) VALUES
(209,0,'it','privo di reddito',1,0);
INSERT INTO `_system_decode_class` (`id`,`sdc_name`,`activation_flag`,`deletion_flag`) VALUES
(300,'automunito',1,0);
INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`) VALUES
(300,0,'it','si',1,0),
(300,1,'it','no',1,0);
INSERT INTO `_system_decode_class` (`id`,`sdc_name`,`activation_flag`,`deletion_flag`) VALUES
(207,'lingua_it',1,0);
INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`) VALUES
(207,0,'it','madrelingua',1,0);
INSERT INTO `_system_decode_class` (`id`,`sdc_name`,`activation_flag`,`deletion_flag`) VALUES
(302,'servizi_antoniano',1,0);
INSERT INTO `_system_decode` (`sd_class`,`sd_value`,`sd_locale`,`sd_description`,`activation_flag`,`deletion_flag`) VALUES
(302,0,'it','mensa diurna',1,0),
(302,1,'it','mensa serale',1,0),
(302,2,'it','accoglienza',1,0),
(302,3,'it','san ruffillo',1,0),
(302,4,'it','via gorizia',1,0);


-- Fine parte dinamica 


/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
