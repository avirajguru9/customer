package com.mangement.customer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateCustomerException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = -368409154922889013L;

	public DuplicateCustomerException(String message) {
        super(message);
    }
}

