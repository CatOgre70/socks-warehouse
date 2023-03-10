-- liquibase formatted sql

-- changeset vasilydemin:1
CREATE TABLE socks(
                      id bigserial primary key,
                      color varchar(255),
                      cotton_part integer,
                      quantity integer
);

CREATE TABLE users(
                      user_id bigserial primary key,
                      email varchar(255),
                      first_name varchar(255),
                      last_name varchar(255),
                      username varchar(255),
                      password varchar(255),
                      phone varchar(255),
                      role varchar(255),
                      reg_date timestamp,
                      enabled bool
);

create table authorities
(
    id BIGSERIAL NOT NULL,
    username varchar(255),
    authority varchar(255),
    CONSTRAINT authority_id_pkey PRIMARY KEY (id)
);

CREATE TABLE invoices(
                         invoice_id bigserial primary key,
                         local_date_time timestamp,
                         user_id bigint references users(user_id),
                         socks_color varchar(255),
                         cotton_part integer,
                         quantity integer,
                         wh_operation varchar(255)
);