package br.com.finance.api.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.finance.api.dto.UserDTO;
import br.com.finance.api.param.ResetPasswordParam;
import br.com.finance.domain.service.UserService;

/**
 * 
 * @author Thiago Guimaraes
 * @date 04-05-2024
 */
@RestController
public class RegisterController {
	
	@Autowired private UserService userService;
	
	@PostMapping(value = "/register")
	public ResponseEntity<UserDTO> create(@Valid @RequestBody UserDTO userDto){
		
		UserDTO dto = userService.save(userDto);
		
		if(dto!=null) {
			return new ResponseEntity<>(dto, HttpStatus.CREATED);
		}
		return ResponseEntity.badRequest().build();
	}
	
	@SuppressWarnings("rawtypes")
	@GetMapping( value="/recovery")
	public ResponseEntity recovery(@RequestParam String email){
		
		if(userService.isEmailAlreadyInUse(email)) {
			
			Boolean success = userService.generateRecoveryKey(email);
			
			if(success) {
				 ResponseEntity.ok().build();
				 
			}else {
				 ResponseEntity.badRequest().build();
			}
			
		}else {
			return ResponseEntity.notFound().build();
		}
		
		return null;
	}
	
	@SuppressWarnings("rawtypes")
	@PostMapping( value="/reset")
	public ResponseEntity resetPassword(@RequestBody ResetPasswordParam param){
		
		if(userService.exitsByToken(param.getToken())) {
			
			Boolean success = userService.resetPassword(param);
			
			if(success) {
				 ResponseEntity.ok().build();
				 
			}else {
				 ResponseEntity.badRequest().build();
			}
			
		}else {
			return ResponseEntity.notFound().build();
		}
		
		return null;
	
	}

}
