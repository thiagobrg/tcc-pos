package br.com.finance.domain.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import br.com.finance.domain.entities.User;
import br.com.finance.domain.entities.repositories.UserRepository;

/**
 * 
 * @author Thiago Guimaraes
 * @date 04-05-2024
 */
@Component
public class CustomUserDetailService implements UserDetailsService{
	
	private final UserRepository userRepository;
	
	@Autowired
	public CustomUserDetailService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		User user = Optional.ofNullable(userRepository.findByEmail(email))
										.orElseThrow( () -> new UsernameNotFoundException("User Not Found!"));
		
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), user.getRole().getAuthorityList());
	}
	
}
