package com.ebuddy.pingserver.cli.interpreter.parser;

public class Token {

	private final int start;
	private String value;
	private TokenType type;
	
	public Token( TokenType type, String value, int start ) {
		this.type = type;
		this.value = value;
		this.start = start;
	}

	public String getValue() {
		return value;
	}

	public TokenType getType() {
		return type;
	}
	
	public void setType(TokenType newType) {
		this.type = newType;
	}
	
	public void add( char c) {
		this.value += c;
	}
	
	public void add( Token t) {
		this.value += t.getValue();
	}
	
	public String toString() {
		return "{ "+value+" | "+type.name()+" }";
	}
	
	public boolean is(TokenType t) {
		return type.equals(t);
	}
	
	public int getStart() {
		return start;
	}
	
}
