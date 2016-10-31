INSERT INTO `_system_decode_class` (`ID`, `sdc_description`, `sdc_name`, `owner_user`, `owner_group`, `creation_date`, `creation_user`, `last_modification_date`, `last_modification_user`, `deletion_date`, `deletion_user`, `deletion_flag`, `activation_flag`) VALUES ('303', NULL, 'classe_alloggio', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '0', '1');

ALTER TABLE `ResidenzaInAlloggio` CHANGE `tipologia_alloggio` `tipologia_alloggio` INT(10) NULL DEFAULT NULL;
ALTER TABLE `ResidenzaInAlloggio` ADD `classe_alloggio` INT NOT NULL AFTER `tipologia_alloggio`;

update `ResidenzaInAlloggio` set classe_alloggio = tipologia_alloggio where tipologia_alloggio < 7;
update `ResidenzaInAlloggio` set classe_alloggio = 7 where tipologia_alloggio >= 7
