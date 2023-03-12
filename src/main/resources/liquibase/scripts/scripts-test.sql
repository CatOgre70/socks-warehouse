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

-- changeset vasilydemin:2
INSERT INTO users(email, first_name, last_name, username, password, phone, role, reg_date, enabled)
VALUES('admin@gmail.com', 'Admin', 'Adminov', 'admin@gmail.com',
       '{bcrypt}$2a$10$ooiRA3czh6yKJWouSGZmAukQQBtk9CzyOWpYvUbLi1kQ/Q9E082i.', '+7(900)8901234',
       'ADMIN', '2023-03-10', true);
INSERT INTO authorities(username, authority)
VALUES('admin@gmail.com', 'ROLE_ADMIN');