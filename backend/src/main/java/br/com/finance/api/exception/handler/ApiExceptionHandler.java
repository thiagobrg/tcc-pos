package br.com.finance.api.exception.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * 
 * @author Thiago Guimaraes
 * @date 04-05-2024
 */
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
	
	@Autowired
	private MessageSource messageSource;
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		Map<String, String> errors = new HashMap<>();
		
		for(ObjectError error : ex.getBindingResult().getAllErrors()) {
			
			String field = null;
			
			if(error instanceof FieldError) {
				field = ((FieldError) error).getField();
			}else {
				field = error.getObjectName();
			}
			
			
			errors.put(field, messageSource.getMessage(error, LocaleContextHolder.getLocale()));
		}
		
		return super.handleExceptionInternal(ex, errors, headers, status, request);
	}
	
	
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
		return super.handleExceptionInternal(ex, body, headers, status, request);
	}
}
	
