package com.cakeShop.controller;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cakeShop.exception.ServiceException;
import com.cakeShop.response.ErrorMessages;

@RestControllerAdvice
public class AppExceptionHandler {

	@ExceptionHandler(value = ServiceException.class)
	public ResponseEntity<Object> ServiceExceptionHandler(ServiceException ex){
		
		ErrorMessages errorMessages = new ErrorMessages();
		errorMessages.setMessage(ex.getLocalizedMessage());
		errorMessages.setTimeStamp(LocalDateTime.now());
		
		return new ResponseEntity<Object>(errorMessages,HttpStatus.BAD_REQUEST);
		
		
	}
	
	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<Object> ExceptionHandler(Exception ex){
		
		ErrorMessages errorMessages = new ErrorMessages();
		errorMessages.setMessage(ex.getLocalizedMessage());
		errorMessages.setTimeStamp(LocalDateTime.now());
		
		return new ResponseEntity<Object>(errorMessages,HttpStatus.BAD_REQUEST);
		
		
	}
}
