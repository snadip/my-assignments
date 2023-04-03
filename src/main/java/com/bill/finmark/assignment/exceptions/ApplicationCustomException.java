package com.bill.finmark.assignment.exceptions;

import jakarta.annotation.Nullable;

public class ApplicationCustomException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public ApplicationCustomException(@Nullable final String message) {
        super(message);
    }
    
}
