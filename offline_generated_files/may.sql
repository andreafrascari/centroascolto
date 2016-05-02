INSERT INTO `_system_module` (
`ID` ,
`ID__system_menu_item_sm_menu` ,
`sm_name` ,
`sm_order` ,
`sm_java_class` ,
`sm_alternative_text` ,
`sm_description` ,
`sm_show` ,
`sm_position` ,
`sm_default_parameters` ,
`sm_default_call` ,
`sm_auto_load` ,
`ID__system_meta_environment_sme_modules` ,
`owner_user` ,
`owner_group` ,
`creation_date` ,
`creation_user` ,
`last_modification_date` ,
`last_modification_user` ,
`deletion_date` ,
`deletion_user` ,
`deletion_flag` ,
`activation_flag`
)
VALUES (
'0',  '28',  'Menu statistiche',  '2',  'eu.anastasis.serena.application.modules.menu.MenuModule',  'Menu statistiche',  'Menu statistiche',  'Statistiche',  '2', NULL ,  'call',  '3',  '1', NULL , NULL ,  '2015-09-30',  'admin',  '2015-10-03',  'admin', NULL , NULL ,  '0',  '1'
);


INSERT INTO `_system_menu_item` (`ID`, `smi_title`, `smi_order`, `smi_locale`, `smi_association`, `smi_href`, `smi_alternative_text`, `ID__system_menu_item_smi_children`, `owner_user`, `owner_group`, `creation_date`, `creation_user`, `last_modification_date`, `last_modification_user`, `deletion_date`, `deletion_user`, `deletion_flag`, `activation_flag`) VALUES
(28, 'Statistiche', 1, 'it', NULL, '', 'Statistiche', NULL, NULL, NULL, '2016-02-02', 'admin', NULL, NULL, NULL, NULL, 0, 1),
(29, 'Tessere rilasciate', 3, 'it', NULL, '?q=object/filter&p=Utente&t=stat-years&s=tessere-rilasciate', 'Tessere rilasciate', 28, NULL, NULL, '2016-05-01', 'admin', '2016-05-01', 'admin', NULL, NULL, 0, 1),
(30, 'Motivazione tessere', 3, 'it', NULL, '?q=object/filter&p=Utente&t=stat-years&s=motivazione-tessere', 'Motivazione tessere', 28, NULL, NULL, '2016-05-01', 'admin', '2016-05-01', 'admin', NULL, NULL, 0, 1),
(31, 'Utenti per sesso', 3, 'it', NULL, '?q=object/filter&p=Utente&t=stat-years&s=utenti-per-sesso', 'Utenti per sesso', 28, NULL, NULL, '2016-05-01', 'admin', '2016-05-01', 'admin', NULL, NULL, 0, 1),
(32, 'Utenti per paese di provenienza', 3, 'it', NULL, '?q=object/filter&p=Utente&t=stat-years&s=utenti-per-stato', 'Utenti per paese di provenienza', 28, NULL, NULL, '2016-05-01', 'admin', '2016-05-01', 'admin', NULL, NULL, 0, 1),
(33, 'Utenti per stato civile', 3, 'it', NULL, '?q=object/filter&p=Utente&t=stat-years&s=utenti-per-stato-civile', 'Utenti per stato civile', 28, NULL, NULL, '2016-05-01', 'admin', '2016-05-01', 'admin', NULL, NULL, 0, 1),
(34, 'Utenti con/privi di residenza', 3, 'it', NULL, '?q=object/filter&p=Utente&t=stat-years&s=utenti-per-residenza', 'Utenti con/privi di residenza', 28, NULL, NULL, '2016-05-01', 'admin', '2016-05-01', 'admin', NULL, NULL, 0, 1),
(35, 'Eventi', 3, 'it', NULL, '?q=object/filter&p=Utente&t=stat-years&s=eventi', 'Eventi', 28, NULL, NULL, '2016-05-01', 'admin', '2016-05-01', 'admin', NULL, NULL, 0, 1),
(36, 'Eta media al primo colloquio', 3, 'it', NULL, '?q=object/filter&p=Utente&t=stat-eta&y=2015&s=eta-media-primo-colloquio', 'Eta media al primo colloquio', 28, NULL, NULL, '2016-05-01', 'admin', '2016-05-01', 'admin', NULL, NULL, 0, 1);

INSERT INTO _system_instance_auth ( ID, ID__system_group_sia_group, sia_permissions, activation_flag, deletion_flag, creation_user, creation_date, last_modification_user, last_modification_date, sia_instance, sia_class ) VALUES( 45, "3", "111", "1", "0", "admin", "2016-05-01", "admin", "2016-05-01", "28", "_system_module" );
INSERT INTO _system_instance_auth ( ID, ID__system_group_sia_group, sia_permissions, activation_flag, deletion_flag, creation_user, creation_date, last_modification_user, last_modification_date, sia_instance, sia_class ) VALUES( 46, "2", "111", "1", "0", "admin", "2016-05-01", "admin", "2016-05-01", "28", "_system_module" );
INSERT INTO _system_instance_auth ( ID, ID__system_group_sia_group, sia_permissions, activation_flag, deletion_flag, creation_user, creation_date, last_modification_user, last_modification_date, sia_instance, sia_class ) VALUES( 47, "1", "111", "1", "0", "admin", "2016-05-01", "admin", "2016-05-01", "28", "_system_module" );
