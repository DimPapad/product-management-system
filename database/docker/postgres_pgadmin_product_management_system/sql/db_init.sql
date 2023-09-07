-- >>> CREATE EXTENSION <<<
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

--------------------------------------------------------------------------------------------

-- >>> CREATE SCHEMA <<<
CREATE SCHEMA product_management_system
    AUTHORIZATION dim;
	
--------------------------------------------------------------------------------------------
	
-- >>> CREATE TABLES <<<

-- Table: product_management_system.roles

CREATE TABLE IF NOT EXISTS product_management_system.roles
(
	uuid character(36) NOT NULL,
	name character varying(50) NOT NULL,
    CONSTRAINT roles_pkey PRIMARY KEY (uuid),
    CONSTRAINT roles_name_key UNIQUE (name)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS product_management_system.roles
    OWNER to dim;

-- Table: product_management_system.users

CREATE TABLE IF NOT EXISTS product_management_system.users
(
	uuid character(36) NOT NULL,
    last_name character varying(50) NOT NULL,
    first_name character varying(50) NOT NULL,
	username character varying(50) NOT NULL,
    email character varying(50) NOT NULL,
    password character varying(100) NOT NULL,
    role_uuid character(36) NOT NULL,
    CONSTRAINT users_pkey PRIMARY KEY (uuid),
    CONSTRAINT users_username_key UNIQUE (username),
	CONSTRAINT users_email_key UNIQUE (email),
    CONSTRAINT users_role_uuid_fkey FOREIGN KEY (role_uuid)
    REFERENCES product_management_system.roles (uuid) MATCH SIMPLE
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT VALID
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS product_management_system.users
    OWNER to dim;

-- Table: product_management_system.products

CREATE TABLE IF NOT EXISTS product_management_system.products
(
	uuid character(36) NOT NULL,
	name character varying(100) NOT NULL,
    description character varying(300) NOT NULL,
    price numeric(10,2) NOT NULL,
    CONSTRAINT products_pkey PRIMARY KEY (uuid),
    CONSTRAINT proucts_name_key UNIQUE (name)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS product_management_system.products
    OWNER to dim;

-- Table: product_management_system.products_users

CREATE TABLE IF NOT EXISTS product_management_system.products_users
(
	product_uuid character(36) NOT NULL,
	user_uuid character(36) NOT NULL,
	action_log character varying(300),
    action_time timestamp with time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT products_users_product_uuid_fkey FOREIGN KEY (product_uuid)
    REFERENCES product_management_system.products (uuid) MATCH SIMPLE
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT VALID,
    CONSTRAINT products_users_user_uuid_fkey FOREIGN KEY (user_uuid)
    REFERENCES product_management_system.users (uuid) MATCH SIMPLE
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT VALID
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS product_management_system.products_users
    OWNER to dim;

--------------------------------------------------------------------------------------------

-- >>> INSERT INTO TABLES <<<

INSERT INTO product_management_system.roles(uuid, name)
	VALUES (uuid_generate_v4 (), 'Administrator'),
			(uuid_generate_v4 (), 'User');

INSERT INTO product_management_system.users(uuid, last_name, first_name, username, email, password, role_uuid)
	VALUES (uuid_generate_v4 (), 'Papadopoulos', 'Ioannis', 'PapIoan', 'qwerty@ymail.com', '1234', (select uuid from product_management_system.roles where name='User')),
			(uuid_generate_v4 (), 'Papadopoulou', 'Ioanna', 'PapGian', 'qwertz@ymail.com', '1234', (select uuid from product_management_system.roles where name='Administrator'));

INSERT INTO product_management_system.products(uuid, name, description, price)
	VALUES (uuid_generate_v4 (), 'Mobile Phone', 'Android Mobile Phone', 350.6),
			(uuid_generate_v4 (), 'Laptop', 'MacBook M2', 2500);

INSERT INTO product_management_system.products_users(product_uuid, user_uuid, action_log, action_time)
	VALUES ((select uuid from product_management_system.products where name='Mobile Phone'), (select uuid from product_management_system.users where username='PapIoan'), 'ADD', CURRENT_TIMESTAMP),
			((select uuid from product_management_system.products where name='Laptop'), (select uuid from product_management_system.users where username='PapGian'), 'EDIT', CURRENT_TIMESTAMP);