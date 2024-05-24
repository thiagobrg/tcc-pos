package br.com.finance.domain.entities;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;


/**
 * 
 * @author Thiago Guimaraes
 * @date 04-05-2024
 */
@Entity(name = "user_account")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_user")
	@SequenceGenerator(name = "seq_user", sequenceName = "seq_user", allocationSize = 1)
	@Column(name = "usac_cd_id")
	private Long id;
	
	@Column(name = "usac_tx_name")
	private String name;
	
	@Column(name = "usac_tx_email", unique = true)
	@NotBlank
	@Size(max = 100)
	private String email;
	
	@Column(name = "usac_tx_password")
	@NotBlank
	@Size(max = 60)
	private String password;
	
	@Column(name = "usac_role_name")
	@Enumerated(EnumType.STRING)
	@NotNull
	private UserRole role;
	
	@Column(name = "usac_dt_created_date")
	@NotNull
	private LocalDateTime createdDate;
	
	@Column(name = "usac_tx_recovery_key")
	private String recoveryKey;

	public User() {}
	
	public User(Long id) {
		this.id = id;
	}

	public String getRecoveryKey() {
		return recoveryKey;
	}

	public void setRecoveryKey(String recoveryKey) {
		this.recoveryKey = recoveryKey;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}
	
	public enum UserRole { 
		
		ROLE_USER(AuthorityUtils.createAuthorityList("ROLE_USER")), 
		ROLE_ADMIN(AuthorityUtils.createAuthorityList("ROLE_USER", "ROLE_ADMIN")); 
		
		private List<GrantedAuthority> authorityList;
		
		private UserRole(List<GrantedAuthority> authorityList) {
			this.authorityList = authorityList;
		}

		public List<GrantedAuthority> getAuthorityList() {
			return authorityList;
		}
	}
}
