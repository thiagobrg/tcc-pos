package br.com.finance.domain.entities.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.finance.domain.entities.TransactionCategory;

/**
 * 
 * @author Thiago Guimaraes
 * @date 04-05-2024
 */
public interface TransactionCategoryRepository extends JpaRepository<TransactionCategory, Long> {

	@Query(value = "select * from transaction_category where usac_cd_id = ?1 OR usac_cd_id is null", nativeQuery = true)
	List<TransactionCategory> findByUserId(Long userId);
	
	@Query(value = "select count(*)>0 from transaction_category where trca_tx_name = ?1 AND ( usac_cd_id = ?2 OR usac_cd_id is null)", nativeQuery = true)
	boolean existsCategory(String name, Long userId);

	boolean existsByNameAndUserIsNull(String name);
	
}
