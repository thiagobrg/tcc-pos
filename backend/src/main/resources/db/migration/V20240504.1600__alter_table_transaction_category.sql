ALTER TABLE transaction_category DROP CONSTRAINT transaction_category_trca_tx_name_key;
ALTER TABLE transaction_category ADD CONSTRAINT unique_trca_tx_name_usac_cd_id_key UNIQUE (trca_tx_name, usac_cd_id);
