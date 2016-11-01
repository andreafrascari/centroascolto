INSERT INTO `_system_decode_class` (`ID`, `sdc_description`, `sdc_name`, `owner_user`, `owner_group`, `creation_date`, `creation_user`, `last_modification_date`, `last_modification_user`, `deletion_date`, `deletion_user`, `deletion_flag`, `activation_flag`) VALUES ('303', NULL, 'classe_alloggio', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '0', '1');

ALTER TABLE `ResidenzaInAlloggio` CHANGE `tipologia_alloggio` `tipologia_alloggio` INT(10) NULL DEFAULT NULL;
ALTER TABLE `ResidenzaInAlloggio` ADD `classe_alloggio` INT NOT NULL AFTER `tipologia_alloggio`;

update `ResidenzaInAlloggio` set classe_alloggio = tipologia_alloggio where tipologia_alloggio < 7;
update `ResidenzaInAlloggio` set classe_alloggio = 7 where tipologia_alloggio >= 7

INSERT INTO `_system_decode_class` (`ID`, `sdc_description`, `sdc_name`, `owner_user`, `owner_group`, `creation_date`, `creation_user`, `last_modification_date`, `last_modification_user`, `deletion_date`, `deletion_user`, `deletion_flag`, `activation_flag`) VALUES ('0', NULL, 'aree_geografiche', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '0', '1');

ALTER TABLE `Utente` ADD `area_geografica_provenienza` INT  AFTER `stato_n`;

delete from _system_decode where sd_class in (203,211,303,304);

update Utente u set area_geografica_provenienza = (select d1.sd_value from _system_decode d1 inner join  _system_decode d2 on d1.ID = d2.ID__system_decode_sd_parent where u.stato_n = d2.sd_value and d1.sd_class = 304 and d2.sd_class=211);

/*
update `_system_decode`set  `ID__system_decode_sd_parent` = 958 where sd_class= 211 and ID in (170,357,356);
update `_system_decode`set  `ID__system_decode_sd_parent` = 959 where sd_class= 211 and ID in (139,141,284,142,148,151,154,158,162,175,179,184,187,195,197,202,239,219,221,207,224,225,228,172,267,271,276,287,297,299,300,305,193,333,346,359,362);
update `_system_decode`set  `ID__system_decode_sd_parent` = 960 where sd_class= 211 and ID in (135,171,196,254,277,347);
update `_system_decode`set  `ID__system_decode_sd_parent` = 961 where sd_class= 211 and ID in (138,155,160,166,167,169,174,180,181,182,185,199,201,210,211,214,191,222,198,223,243,253,260,261,264,268,269,278,280,288,289,173,310,366,320,322,327,328,332,341,343,345,352,369,370);
update `_system_decode`set  `ID__system_decode_sd_parent` = 962 where sd_class= 211 and ID in (137,146,153,188,190,236,205,206,213,215,355,216,217,227,231,238,255,257,265,274,293,283,304,317,330,334,336,337);
update `_system_decode`set  `ID__system_decode_sd_parent` = 963 where sd_class= 211 and ID in (134,152,159,165,186,200,250,256,259,273,303,189,308,309,368,324,325,353,230);
update `_system_decode`set  `ID__system_decode_sd_parent` = 964 where sd_class= 211 and ID in (133,319,143,147,149,354,212,241,234,235,263,237,242,248,247,251,294,295,306,338,340,371,342,348,349,360,367);
update `_system_decode`set  `ID__system_decode_sd_parent` = 965 where sd_class= 211 and ID in (176,150,157,164,168,245,246,301,240,229,232,233,249,262,275,279,282,321,323,331,339,194,363);
update `_system_decode`set  `ID__system_decode_sd_parent` = 966 where sd_class= 211 and ID in (145,204,285,298,286,208);
update `_system_decode`set  `ID__system_decode_sd_parent` = 967 where sd_class= 211 and ID in (140,144,156,177,178,192,218,220,161,291,183,203,226,292,266,326,350,364,358,244,252,258,270,272,281,290,163,296,302,307,312,314,315,316,136,311,313,318,329,335,209,344,351,361,365);

*/











