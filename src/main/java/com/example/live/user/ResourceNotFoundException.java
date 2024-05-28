package com.example.live.user;

public class ResourceNotFoundException extends RuntimeException{
    /**
	 * 
	 */
	private static final long serialVersionUID = 4185221885073076026L;

	public ResourceNotFoundException(String message) {
        super(message);
    }
}
