package br.com.finance.api.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import br.com.finance.api.validation.UniqueEmail;
import br.com.finance.domain.entities.User;

/**
 * 
 * @author Thiago Guimaraes
 * @date 04-05-2024
 */
public class UserDTO {
	
	private Long id;
	
	@NotBlank
	@Size(max = 60)
	private String name;
	
	@NotBlank
	@Email
	@UniqueEmail
	private String email;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	@NotBlank
	private String password;
	
	private LocalDateTime createdDate;
	
	public static UserDTO of(User user) {
		
		if(user == null) {
			throw new IllegalArgumentException("Invalid User");
		}
		
		UserDTO userDto = new UserDTO();
		
		userDto.id = user.getId();
		userDto.name = user.getName();
		userDto.email = user.getEmail();
		userDto.password = user.getPassword();
		userDto.createdDate = user.getCreatedDate();
		
		return userDto;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public LocalDateTime getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}
}
