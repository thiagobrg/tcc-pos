CREATE TABLE transaction (
	tran_cd_id BIGINT PRIMARY KEY,
	tran_tx_description VARCHAR(300) NOT NULL,
	tran_nm_value NUMERIC(15,2) NOT NULL,
	tran_dt_date date NOT NULL,
	tran_tx_type VARCHAR(10) NOT NULL,
	usac_cd_id BIGINT NOT NULL REFERENCES user_account (usac_cd_id) ON UPDATE NO ACTION ON DELETE CASCADE,
	trca_cd_id BIGINT NOT NULL REFERENCES transaction_category (trca_cd_id) ON UPDATE NO ACTION ON DELETE CASCADE
);

CREATE SEQUENCE seq_transaction START 1 INCREMENT BY 1;