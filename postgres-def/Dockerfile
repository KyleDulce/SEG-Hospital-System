FROM postgres:16.1-alpine

COPY 1_createdb.sql /docker-entrypoint-initdb.d/1_createdb.sql
COPY 2_insert_data.sql /docker-entrypoint-initdb.d/2_insert_data.sql
