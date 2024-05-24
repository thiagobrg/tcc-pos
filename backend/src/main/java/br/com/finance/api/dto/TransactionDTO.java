package br.com.finance.api.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.finance.domain.entities.Transaction;

/**
 * 
 * @author Thiago Guimaraes
 * @date 04-05-2024
 */
public class TransactionDTO {
	
	private Long id;
	
	@NotBlank
	private String description;
	
	@NotNull
	private BigDecimal value;
	
	@NotNull
	private LocalDate date;
	
	@NotBlank
	private String type;
	
	@NotNull
	private Long userId;
	private String userName;
	
	private Long categoryId;
	private String categoryName;
	
	public static TransactionDTO of(Transaction transaction) {
		
		if(transaction == null) {
			throw new IllegalArgumentException("Transaction is invalid!");
		}
		
		TransactionDTO dto = new TransactionDTO();
		dto.id = transaction.getId();
		dto.description = transaction.getDescription();
		dto.value = transaction.getValue();
		dto.date = transaction.getDate();
		dto.type = transaction.getType().toString();
		dto.userId = transaction.getUser().getId();
		dto.userName = transaction.getUser().getName();
		
		if(transaction.getCategory()!=null) {
			dto.categoryId = transaction.getCategory().getId();
			dto.categoryName = transaction.getCategory().getName();
		}
		
		return dto;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public BigDecimal getValue() {
		return value;
	}
	public void setValue(BigDecimal value) {
		this.value = value;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
}
