package com.ebuddy.pingserver.cli.interpreter.parser;


public class ParseException extends RuntimeException {

	private Token token;
	
	public ParseException(String message, Token t) {
		super(message);
		this.token = t;
	}
	
	public Token getToken() {
		return token;
	}
	
}
