package com.goaltracker.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.goaltracker.service.DuplicateNameException;
import com.goaltracker.service.ResourceNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	@ExceptionHandler(DuplicateNameException.class)
	public ResponseEntity<Object> handleDuplicateNameException(WebRequest request, DuplicateNameException exception) {
		LOGGER.warn(exception.getMessage());
		
		String bodyOfResponse = "name should be unique";
		
		return handleExceptionInternal(exception, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<Object> handleResourceNotFoundException(WebRequest request, ResourceNotFoundException exception) {
		LOGGER.warn(exception.getMessage());
		
		String bodyOfResponse = "resource can not be found";
		
		return handleExceptionInternal(exception, bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}

}
