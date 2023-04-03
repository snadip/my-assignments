package com.bill.finmark.assignment.validations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.bill.finmark.assignment.exceptions.ApplicationCustomException;
import com.bill.finmark.assignment.exceptions.GenericErrorRespose;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class CustomExceptionHandler {

	Logger logger = LoggerFactory.getLogger(CustomExceptionHandler.class);
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<GenericErrorRespose> globalExceptionHandler(Exception ex, WebRequest request) {
		
		logger.error("Error : "+ex);
		GenericErrorRespose errorRespose = new GenericErrorRespose(HttpStatus.INTERNAL_SERVER_ERROR.value(), new Date(),
				ex.getMessage(), new ArrayList<>(), request.getDescription(false));

		return new ResponseEntity<GenericErrorRespose>(errorRespose, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<GenericErrorRespose> noHandlerFoundException(ApplicationCustomException ex,
			WebRequest request) {
		logger.error("Error : "+ex);
		GenericErrorRespose errorRespose = new GenericErrorRespose(HttpStatus.NOT_FOUND.value(), new Date(), ex.getMessage(),
				new ArrayList<>(), request.getDescription(false));

		return new ResponseEntity<GenericErrorRespose>(errorRespose, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(ApplicationCustomException.class)
	public ResponseEntity<GenericErrorRespose> applicationCustomException(ApplicationCustomException ex,
			WebRequest request) {
		logger.error("Error : "+ex);
		GenericErrorRespose errorRespose = new GenericErrorRespose(HttpStatus.BAD_REQUEST.value(), new Date(), ex.getMessage(),
				new ArrayList<>(), request.getDescription(false));

		return new ResponseEntity<GenericErrorRespose>(errorRespose, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<GenericErrorRespose> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,WebRequest request) {
		logger.error("Error : "+ex);
		List<String> details = new ArrayList<>();
		for (ObjectError error : ex.getBindingResult().getAllErrors()) {
			details.add(error.getDefaultMessage());
		}
		GenericErrorRespose errorRespose = new GenericErrorRespose(HttpStatus.BAD_REQUEST.value(), new Date(),
				"Validation Failed", new ArrayList<>(), request.getDescription(false));
		return new ResponseEntity<GenericErrorRespose>(errorRespose, HttpStatus.BAD_REQUEST);
	}

}
