package com.ebuddy.pingserver.cli.interpreter.parser;

public enum TokenType {

	EXCLAMATION("!"),
	DOLLAR("$"),
	EQUALS("="),
	LEFT_BRACE("("),
	RIGHT_BRACE(")"),
	LEFT_ACCO("{"),
	RIGHT_ACCO("}"),
	LEFT_BRACKET("["),
	RIGHT_BRACKET("]"),
	NUMERIC(ParseType.GREEDY),
	STRING(ParseType.GREEDY, NUMERIC),
	WS,
	AMPERSAND("&"),
	PIPE("|"),
	SMALLERTHAN("<"),
	BIGGERTHAN(">"),
	PLUS("+"),
	MINUS("-"),
	STAR("*"),
	SLASH("/"),
	TRUE("true"),
	FALSE("false"),
	
	EQUALSEQUALS(EQUALS,EQUALS),
	NOTEQUALS(EXCLAMATION,EQUALS),
	LOGICAL_AND(AMPERSAND,AMPERSAND),
	LOGICAL_OR(PIPE,PIPE),
	SMALLEREQUALS(SMALLERTHAN,EQUALS),
	BIGGEREQUALS(BIGGERTHAN,EQUALS),
	DOT(".");
	
	private String literal;
	private TokenType lhs;
	private TokenType rhs;
	
	private ParseType parseType;
	private TokenType[] acceptsTypes;
	
	private TokenType() {
		this.literal = super.toString();
	}

	
	private TokenType(String token) {
		this.literal = token;
	}

	private TokenType(TokenType lhs, TokenType rhs) {
		this.lhs = lhs;
		this.rhs = rhs;
	}

	private TokenType(ParseType parseType, TokenType ...tokenTypes) {
		this.acceptsTypes = tokenTypes;
		this.parseType = parseType;
	}
	
	public boolean isCompound() {
		return lhs != null && rhs != null;
	}

	public boolean isLiteral() {
		return literal != null;
	}

	public boolean isGreedy() {
		return ParseType.GREEDY.equals(parseType);
	}
	
	public boolean isTerminal() {
		return literal!=null && literal.length() > 1;
	}
	
	public TokenType getRhs() {
		return rhs;
	}
	
	public TokenType getLhs() {
		return lhs;
	}
	
	public String getLiteral() {
		return literal;
	}
	
	public boolean accepts(TokenType type) {
		if(type.equals(this)) {
			return true;
		}
		for(TokenType t : acceptsTypes) {
			if(t.equals(type)) {
				return true;
			}
		}
		return false;
	}
	
	public String toString() {
		return literal;
	}
	
	private enum ParseType { GREEDY, NON_GREEDY }
	
}
