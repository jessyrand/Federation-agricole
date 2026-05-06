
create database agricultural_federation_db;

create user agricultural_federation_db_manager with password '123456';

grant connect on database agricultural_federation_db to agricultural_federation_db_manager;

\c agricultural_federation_db

grant usage, create on schema public to agricultural_federation_db_manager;

alter default privileges in schema public
grant select, insert, update, delete on tables to agricultural_federation_db_manager;

alter default privileges in schema public
grant usage, update, select on sequences to agricultural_federation_db_manager ;