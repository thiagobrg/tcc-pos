package br.com.finance.api.controllers;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.finance.api.dto.HomeDashboardDTO;
import br.com.finance.domain.service.TransactionService;

/**
 * 
 * @author Thiago Guimaraes
 * @date 04-05-2024
 */

@RestController
@RequestMapping(value = "/home")
public class HomeController {
	
	@Autowired
	private TransactionService transactionService;
		
	@GetMapping( value = "/dashboard/{userId}")
	public ResponseEntity<HomeDashboardDTO> dashboardData(@PathVariable Long userId, @RequestParam String date){
		
		if(date == null || date.isEmpty()) {
			return ResponseEntity.badRequest().build();
		}
		
		LocalDate localDate = Instant.ofEpochMilli(Long.parseLong(date)).atZone(ZoneOffset.UTC).toLocalDate();
		
		HomeDashboardDTO dto = transactionService.getHomeDashboardUserByMonth(userId, localDate);
		
		return ResponseEntity.ok(dto);
	}

}
