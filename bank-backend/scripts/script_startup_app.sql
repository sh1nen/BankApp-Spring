CREATE SCHEMA IF NOT EXISTS bankingschema;
SELECT * FROM bankingschema.role;
INSERT INTO `bankingschema`.`role` (`role_id`, `name`) VALUES ('0', 'ROLE_USER');
INSERT INTO `bankingschema`.`role` (`role_id`, `name`) VALUES ('1', 'ROLE_ADMIN');