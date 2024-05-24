package br.com.finance.domain.entities.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.finance.domain.entities.User;

/**
 * 
 * @author Thiago Guimaraes
 * @date 04-05-2024
 */
public interface UserRepository extends JpaRepository<User, Long> {
	
	boolean existsByEmail(String email);
	
	User findByEmail(String email);

	User findByRecoveryKey(String token);

	boolean existsByRecoveryKey(String token);
}
