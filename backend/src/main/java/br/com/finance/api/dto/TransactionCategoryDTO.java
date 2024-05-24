package br.com.finance.api.dto;

import javax.validation.constraints.NotBlank;

import br.com.finance.api.validation.UniqueTransactionCategory;
import br.com.finance.domain.entities.TransactionCategory;

/**
 * 
 * @author Thiago Guimaraes
 * @date 04-05-2024
 */
@UniqueTransactionCategory
public class TransactionCategoryDTO {
	
	private Long id;
	
	@NotBlank
	private String name;
	private Long userId;
	private String userName;
	
	public static TransactionCategoryDTO of(TransactionCategory transactionCategory) {
		
		TransactionCategoryDTO dto = new TransactionCategoryDTO();
		dto.id = transactionCategory.getId();
		dto.name = transactionCategory.getName();
		
		if(transactionCategory.getUser()!=null) {
			dto.userId = transactionCategory.getUser().getId();
			dto.userName = transactionCategory.getUser().getName();
		}
		
		return dto;
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
	
}
