package br.com.finance.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.finance.api.dto.TransactionCategoryDTO;
import br.com.finance.domain.service.TransactionCategoryService;

/**
 * 
 * @author Thiago Guimaraes
 * @date 04-05-2024
 */
@RestController
@RequestMapping(value = "/core/transaction-category")
public class TransactionCategoryController {
	
	@Autowired
	private TransactionCategoryService transactionCategoryService;
	
	@GetMapping
	public List<TransactionCategoryDTO> findAll(){
		return transactionCategoryService.findAll();
	}
	
	@GetMapping(value = "/user/{userId}")
	public  List<TransactionCategoryDTO> findByUserId(@PathVariable Long userId){
		return transactionCategoryService.findByUserId(userId);
	}
	
	@GetMapping(value = "/{categoryId}")
	public ResponseEntity<TransactionCategoryDTO> findById(@PathVariable Long categoryId){
		
		TransactionCategoryDTO transactionCategoryDTO = transactionCategoryService.findById(categoryId);
		if(transactionCategoryDTO!=null) {
			return new ResponseEntity<>(transactionCategoryDTO, HttpStatus.OK);
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<TransactionCategoryDTO> save(@Valid @RequestBody TransactionCategoryDTO transactionCategoryDTO) {
		
		TransactionCategoryDTO dto = transactionCategoryService.save(transactionCategoryDTO);
		
		if(dto!=null) {
			return new ResponseEntity<>(dto, HttpStatus.CREATED);
		}
		
		return ResponseEntity.badRequest().build();
	}
	
	@PutMapping(value = "/{categoryId}")
	public ResponseEntity<TransactionCategoryDTO> update(@PathVariable Long categoryId, @Valid @RequestBody TransactionCategoryDTO transactionCategoryDTO){
		
		transactionCategoryDTO.setId(categoryId);
		
		TransactionCategoryDTO dto = transactionCategoryService.save(transactionCategoryDTO);
		
		if(dto!=null) {
			return new ResponseEntity<>(dto, HttpStatus.CREATED);
		}
		
		return ResponseEntity.badRequest().build();
	}
	
	@DeleteMapping(value = "/{categoryId}")
	public ResponseEntity<TransactionCategoryDTO> delete(@PathVariable Long categoryId){
		
		if(transactionCategoryService.existsById(categoryId)) {
			transactionCategoryService.delete(categoryId);
			
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.notFound().build();
	}
}
