package br.com.finance.domain.entities.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.finance.domain.entities.Transaction;

/**
 * 
 * @author Thiago Guimaraes
 * @date 04-05-2024
 */
public interface TransactionRepository extends JpaRepository<Transaction, Long>, TransactionRepositoryCustom {
	
	/**
	 * 
	 * @param userId
	 * @return
	 */
	List<Transaction> findByUserId(Long userId);
	
}
