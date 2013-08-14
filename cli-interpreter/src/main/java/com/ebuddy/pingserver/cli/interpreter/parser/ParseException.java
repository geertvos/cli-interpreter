package com.ebuddy.pingserver.cli.interpreter.parser;


public class ParseException extends RuntimeException {

	private static final long serialVersionUID = -3513502936658863669L;

	private final Token token;
	
	public ParseException(String message, Token t) {
		super(message);
		this.token = t;
	}
	
	public Token getToken() {
		return token;
	}
	
}
