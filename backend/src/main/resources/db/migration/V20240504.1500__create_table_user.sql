CREATE TABLE user_account (
	usac_cd_id bigint primary key,
	usac_tx_name varchar(60) not null,
	usac_tx_email varchar(100) not null,
	usac_tx_password varchar(60) not null,
	usac_role_name varchar(60) not null,
	usac_dt_created_date timestamp not null
);

CREATE SEQUENCE seq_user START 1 INCREMENT BY 1;