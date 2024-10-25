package com.video.rental.exception;

public class FilmNotAvailableException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FilmNotAvailableException(String message) {
		super(message);
	}

}
