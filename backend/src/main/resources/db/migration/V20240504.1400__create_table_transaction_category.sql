CREATE TABLE transaction_category (
	trca_cd_id BIGINT primary key,
	trca_tx_name varchar(250) not null UNIQUE,
	usac_cd_id BIGINT references user_account (usac_cd_id) ON UPDATE NO ACTION ON DELETE CASCADE
);

CREATE SEQUENCE seq_transaction_category START 1 INCREMENT BY 1;