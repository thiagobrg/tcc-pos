package br.com.finance.api.controllers;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.finance.api.dto.TransactionCategoryDTO;
import br.com.finance.api.dto.TransactionDTO;
import br.com.finance.api.dto.UserTransactionSummaryDTO;
import br.com.finance.api.param.TransactionParam;
import br.com.finance.domain.service.TransactionService;

/**
 * 
 * @author Thiago Guimaraes
 * @date 04-05-2024
 */
@RestController
@RequestMapping(value = "/core/transaction")
public class TransactionController {
	
	@Autowired
	private TransactionService transactionService;
	
	@GetMapping
	public List<TransactionDTO> findAll(@AuthenticationPrincipal User user){
		return transactionService.findAll();
	}
	
	@GetMapping(value = "/{transactionId}")
	public ResponseEntity<TransactionDTO> findById(@PathVariable Long transactionId){
		
		TransactionDTO dto = transactionService.findById(transactionId);
		
		if(dto!=null) {
			return new ResponseEntity<TransactionDTO>(dto, HttpStatus.OK);
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping( value = "/user/{userId}")
	public List<TransactionDTO> findAllByUserId(@PathVariable Long userId){
		return transactionService.findAllByUserId(userId);
	}
	
	@DeleteMapping(value = "/{transactionId}")
	public ResponseEntity<TransactionCategoryDTO> delete(@PathVariable Long transactionId){
		
		if(transactionService.existsById(transactionId)) {
			transactionService.delete(transactionId);
			
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping( value = "/param")
	public List<TransactionDTO> findByParam(@RequestBody TransactionParam param){
		return transactionService.findByParam(param);
	}
	
	@PostMapping
	public ResponseEntity<TransactionDTO> save(@Valid @RequestBody TransactionDTO dto) {
		
		TransactionDTO transactionDTO = transactionService.save(dto);
		
		if(transactionDTO!=null) {
			return new ResponseEntity<TransactionDTO>(dto, HttpStatus.CREATED);
		}
		
		return ResponseEntity.badRequest().build();
	}
	
	@PutMapping("/{transactionId}")
	public ResponseEntity<TransactionDTO> save(@PathVariable Long transactionId, @Valid @RequestBody TransactionDTO dto) {
		
		if(transactionService.existsById(transactionId)) {
			dto.setId(transactionId);
		}
		
		TransactionDTO transactionDTO = transactionService.save(dto);
		
		if(transactionDTO!=null) {
			return new ResponseEntity<TransactionDTO>(dto, HttpStatus.CREATED);
		}
		
		return ResponseEntity.badRequest().build();
	}
	
	@GetMapping( value = "/summary/user/{userId}")
	public ResponseEntity<UserTransactionSummaryDTO> findUserTransactionSummary(@PathVariable Long userId, @RequestParam String date){
			
		if(date == null || date.isEmpty()) {
			return ResponseEntity.badRequest().build();
		}
		
		LocalDate localDate = Instant.ofEpochMilli(Long.parseLong(date)).atZone(ZoneOffset.UTC).toLocalDate();
		
		UserTransactionSummaryDTO dto = transactionService.getUserTransactionSummaryByMonth(userId, localDate);
		
		return new ResponseEntity<UserTransactionSummaryDTO>(dto, HttpStatus.OK);
	}
	
}
