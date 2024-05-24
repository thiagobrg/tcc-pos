package br.com.finance.domain.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.finance.api.dto.TransactionCategoryDTO;
import br.com.finance.domain.entities.TransactionCategory;
import br.com.finance.domain.entities.User;
import br.com.finance.domain.entities.repositories.TransactionCategoryRepository;

/**
 * 
 * @author Thiago Guimaraes
 * @date 04-05-2024
 */
@Service
public class TransactionCategoryService {

	@Autowired
	private TransactionCategoryRepository transactionCategoryRepository;
	
	public List<TransactionCategoryDTO> findAll() {
		return transactionCategoryRepository.findAll().stream().map(TransactionCategoryDTO::of).collect(Collectors.toList());
	}
	
	public TransactionCategoryDTO findById(Long categoryId) {
		
		Optional<TransactionCategory> category = transactionCategoryRepository.findById(categoryId);
		
		if(category.isPresent()) {
			return TransactionCategoryDTO.of(category.get());
		}
		
		return null;
	}
	
	public TransactionCategoryDTO save(TransactionCategoryDTO transactionCategoryDTO) {
		
		TransactionCategory transactionCategory = null;
		
		if(transactionCategoryDTO.getId()!=null){
			
			Optional<TransactionCategory> optionalTransactionCategory = transactionCategoryRepository.findById(transactionCategoryDTO.getId());
			
			if(optionalTransactionCategory.isPresent()) {
				
				transactionCategory = optionalTransactionCategory.get();
				transactionCategory.setName(transactionCategoryDTO.getName());
			}
			
		}else{
			
			transactionCategory = new TransactionCategory();
			transactionCategory.setName(transactionCategoryDTO.getName());
			
			if(transactionCategoryDTO.getUserId()!=null) {
				transactionCategory.setUser(new User(transactionCategoryDTO.getUserId()));
			}
			
		}
		
		return TransactionCategoryDTO.of(transactionCategoryRepository.save(transactionCategory));
	}
	
	public List<TransactionCategoryDTO> findByUser(Long userId){
		return transactionCategoryRepository.findByUserId(userId).stream().map(TransactionCategoryDTO::of).collect(Collectors.toList());
	}

	public boolean existsById(Long categoryId) {
		return transactionCategoryRepository.existsById(categoryId);
	}
	
	public void delete(Long categoryId) {
		transactionCategoryRepository.deleteById(categoryId);
	}
	
	/**
	 * Valida se ja existe alguma categoria com esse nome.<br>
	 * <br>
	 * Criterios:<br>
	 * 	- Verifica se ja existe uma categoria global com esse nome.<br>
	 * 	- Verifica se ja existe uma categoria do usuario com esse nome.
	 * 
	 * 
	 * @param dto
	 * @return
	 */
	public boolean existsCategoryNameByUserId(TransactionCategoryDTO dto) {
		
		if(dto.getUserId()!=null) {
			return transactionCategoryRepository.existsCategory(dto.getName(), dto.getUserId());
		}
		
		return transactionCategoryRepository.existsByNameAndUserIsNull(dto.getName());
	}
	
	/**
	 * 
	 * @param userId
	 * @return
	 */
	public List<TransactionCategoryDTO> findByUserId(Long userId) {
		return transactionCategoryRepository.findByUserId(userId)
											.stream()
											.map(TransactionCategoryDTO::of)
											.collect(Collectors.toList());
	}
}
