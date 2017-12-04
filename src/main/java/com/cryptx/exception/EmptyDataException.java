package com.cryptx.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class EmptyDataException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3403763927682586519L;

	public EmptyDataException(String message) {
		super(message);
	}

}
