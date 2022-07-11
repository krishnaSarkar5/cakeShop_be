package com.cakeShop.exception;

import lombok.Data;

@Data
public class ServiceException extends RuntimeException{

	private String meesage;

	public ServiceException(String meesage) {
		super(meesage);
		this.meesage = meesage;
	}
	
	
	
}
