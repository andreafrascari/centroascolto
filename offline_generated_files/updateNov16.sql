
ALTER TABLE `ResidenzaInAlloggio` CHANGE `tipologia_alloggio` `tipologia_alloggio` INT(10) NULL DEFAULT NULL;
ALTER TABLE `ResidenzaInAlloggio` ADD `classe_alloggio` INT NOT NULL AFTER `tipologia_alloggio`;

update `ResidenzaInAlloggio` set classe_alloggio = tipologia_alloggio where tipologia_alloggio < 7;
update `ResidenzaInAlloggio` set classe_alloggio = 7 where tipologia_alloggio >= 7;


ALTER TABLE `Utente` ADD `area_geografica_provenienza` INT  AFTER `stato_n`;



update Utente u set area_geografica_provenienza = (select d1.sd_value from _system_decode d1 inner join  _system_decode d2 on d1.ID = d2.ID__system_decode_sd_parent where u.stato_n = d2.sd_value and d1.sd_class = 304 and d2.sd_class=211);

