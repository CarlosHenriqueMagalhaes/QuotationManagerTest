package br.inatel.projects.quotation.management.exception;

/**
 * Exception class
 * 
 * @author carlos.magalhaes
 * @since 11/10/2022
 */

public class ExceptionCase extends RuntimeException {

	private static final long serialVersionUID = -472493011894298798L;

	public ExceptionCase(String message) {

		super(message);
	}
}
