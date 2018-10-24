--alter table user_roles drop constraint ..
drop table roles;
--todo drop reference from user_roles
INSERT INTO version (version_id) VALUES (2);