package br.com.finance.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.finance.api.dto.UserDTO;
import br.com.finance.domain.service.UserService;

/**
 * @author Thiago Guimaraes
 * @date 04-05-2024
 */
@RestController
@RequestMapping(value = "/core/user")
public class UserController {
	
	@Autowired private UserService userService;
	
	@GetMapping
	public List<UserDTO> findAll() {
		return userService.findAll();
	}
	
	@GetMapping(value = "/{userId}")
	public ResponseEntity<UserDTO> find(@PathVariable Long userId) {
		UserDTO dto = userService.findById(userId);
		
		if(dto!=null) {
			return new ResponseEntity<>(dto, HttpStatus.OK);
		}
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/{userId}")
	public ResponseEntity<UserDTO> update(@PathVariable Long userId, @RequestBody UserDTO userDto){
		
		if(userService.exitsById(userId)) {
			
			userDto.setId(userId);
			UserDTO dto = userService.save(userDto);
			
			if(dto!=null) {
				return new ResponseEntity<>(dto, HttpStatus.CREATED);
			}
		}
		
		return ResponseEntity.badRequest().build();
	}
	
	@DeleteMapping("/{userId}")
	public ResponseEntity<UserDTO> delete(@PathVariable Long userId){
		
		if(userService.exitsById(userId)) {
			 userService.delete(userId);
			
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.notFound().build();
	}
}
