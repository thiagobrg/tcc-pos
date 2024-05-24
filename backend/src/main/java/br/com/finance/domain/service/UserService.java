package br.com.finance.domain.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.finance.api.dto.UserDTO;
import br.com.finance.api.param.ResetPasswordParam;
import br.com.finance.domain.entities.User;
import br.com.finance.domain.entities.repositories.UserRepository;

/**
 * 
 * @author Thiago Guimaraes
 * @date 04-05-2024
 */
@Service
public class UserService {
	
	@Autowired private UserRepository userRepository;
	@Autowired private JavaMailSender mailSender;

	public void delete(Long userId) {
		userRepository.deleteById(userId);
	}
	
	/**
	 * 
	 * @param userId
	 * @return True if exists a user with a Id passed by param.
	 */
	public boolean exitsById(Long userId) {
		return userRepository.existsById(userId);
	}
	
	public List<UserDTO> findAll() {
		List<User> entityList = userRepository.findAll();
		
		return entityList.stream().map(UserDTO::of).collect(Collectors.toList());
	}

	public UserDTO findByEmail(String email) {
		
		User user = userRepository.findByEmail(email);
		
		if(user!=null) {
			return UserDTO.of(user);
		}
		
		return null;
	}
	
	public UserDTO findById(Long userId) {
		
		Optional<User> user = userRepository.findById(userId);
		
		if(user.isPresent()) {
			return UserDTO.of(user.get());
		}
		
		return null;
	}

	public boolean generateRecoveryKey(String email) {
		
		try {
			String uuid = UUID.randomUUID().toString();
			uuid = uuid.replaceAll("-", "").substring(0, 8).toUpperCase();
			
			User user = userRepository.findByEmail(email);
			user.setRecoveryKey(uuid);
			
			userRepository.save(user);
			
			SimpleMailMessage message = new SimpleMailMessage();
			
			message.setSubject("[FINANCE] Recuperação de senha");
			
			StringBuilder sb = new StringBuilder();
			sb.append("Olá "+user.getName()+"," ).append("\n\n\n");
			sb.append("Você solicitou a recuperação de senha para sua conta, utilize o TOKEN: " + uuid + " para realizar.").append("\n\n\n");
			sb.append("Att, Finance");
			
	        message.setText(sb.toString());
	        message.setTo(email);

            mailSender.send(message);
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

	public boolean isEmailAlreadyInUse(String email) {
		return userRepository.existsByEmail(email);
	}
	
	/**
	 * Reseta a senha do usuario caso o TOKEN seja valido.
	 * 
	 * @param param
	 * @return
	 */
	public boolean resetPassword(ResetPasswordParam param) {
		
		try {
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			
			User user = userRepository.findByRecoveryKey(param.getToken());
			user.setPassword(passwordEncoder.encode(param.getPassword()));
			user.setRecoveryKey(null);
			
			userRepository.save(user);
			
		} catch (Exception e) {
			return false;
		}
		
		return true;
	}

	/**
	 * 
	 * @param userDto
	 * @return
	 */
	public UserDTO save(UserDTO userDto) {
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		
		User user = null;
		
		// If has a Id is a update.
		if(userDto.getId()!=null) {
			
			Optional<User> optionalUser = userRepository.findById(userDto.getId());
			
			if(optionalUser.isPresent()) {
				
				user = optionalUser.get();
				
				user.setEmail(userDto.getEmail());
				user.setPassword( passwordEncoder.encode(userDto.getPassword()));
				user.setName(userDto.getName());
			}
			
		}else {
			
			user = new User();
			user.setEmail(userDto.getEmail());
			user.setCreatedDate(LocalDateTime.now());
			user.setRole(User.UserRole.ROLE_USER);
			user.setPassword(passwordEncoder.encode(userDto.getPassword()));
			user.setName(userDto.getName());
		}
		
		return UserDTO.of(userRepository.save(user));
	}

	public boolean exitsByToken(String token) {
		return userRepository.existsByRecoveryKey(token);
	}
}
