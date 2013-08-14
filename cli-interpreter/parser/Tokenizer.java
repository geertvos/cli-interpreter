package com.ebuddy.pingserver.cli.interpreter.parser;

import java.util.List;
import java.util.Vector;

public class Tokenizer {

	private List<Token> tokens;
	private Token last = null;
	private boolean stringMode = false;
	
	public List<Token> tokenize(String line) {
		reset();
		for(int i=0;i<line.length();i++) {
			char c = line.charAt(i);
			switch (c) {
			case '=':
				add(new Token(TokenType.EQUALS,"=",i));
				break;
			case '&':
				add(new Token(TokenType.AMPERSAND,"&",i));
				break;
			case '|':
				add(new Token(TokenType.PIPE,"|",i));
				break;
			case '!':
				add(new Token(TokenType.EXCLAMATION,"!",i));
				break;
			case '{':
				add(new Token(TokenType.LEFT_ACCO,"{",i));
				break;
			case '}':
				add(new Token(TokenType.RIGHT_ACCO,"}",i));
				break;
			case '(':
				add(new Token(TokenType.LEFT_BRACE,"(",i));
				break;
			case ')':
				add(new Token(TokenType.RIGHT_BRACE,")",i));
				break;
			case '"':
				add(new Token(TokenType.STRING, "",i));
				stringMode=!stringMode;
				if(!stringMode) {
					last=null;
				}
				break;
			case '$':
				add(new Token(TokenType.DOLLAR,"$",i));
				break;
			case '<':
				add(new Token(TokenType.SMALLERTHAN,"<",i));
				break;
			case '>':
				add(new Token(TokenType.BIGGERTHAN,">",i));
				break;
			case '+':
				add(new Token(TokenType.PLUS,"+",i));
				break;
			case '-':
				add(new Token(TokenType.MINUS,"-",i));
				break;
			case '*':
				add(new Token(TokenType.STAR,"*",i));
				break;
			case '/':
				add(new Token(TokenType.SLASH,"/",i));
				break;
			case '\r':
			case '\n':
			case '	':
			case ' ':
				if(last!=null && last.getType()==TokenType.WS)
					last.add(c);
				else add(new Token(TokenType.WS, ""+c,i));
				break;

			default:
				if(c >= '0' && c <= '9') {
					add(new Token(TokenType.NUMERIC, ""+c,i));
				} else {
					add(new Token(TokenType.STRING,""+c,i));
				}
				break;
			}
		}
		return tokens;
	}

	private void reset() {
		stringMode = false;
		last = null;
		tokens = new Vector<Token>();
	}

	private void add(Token t) {
		if(stringMode) {
			last.add(t);
		} else {
			if(t.is(TokenType.WS)) {
				if(!stringMode)
					last = null;
				return;
			}
			if(last != null) {
				if(last.getType() == TokenType.SMALLERTHAN && t.getType() == TokenType.EQUALS) {
					last.setType(TokenType.SMALLEREQUALS);
					last.add(t);
					return;
				}
				if(last.getType() == TokenType.BIGGERTHAN && t.getType() == TokenType.EQUALS) {
					last.setType(TokenType.BIGGEREQUALS);
					last.add(t);
					return;
				}
				if(last.getType() == TokenType.NUMERIC && t.getType() == TokenType.NUMERIC) {
					last.add(t);
					return;
				}
				if(last.getType() == TokenType.NUMERIC && t.getType() == TokenType.STRING) {
					last.add(t);
					last.setType(TokenType.STRING);
					return;
				}
				if(last.getType() == TokenType.AMPERSAND && t.getType() == TokenType.AMPERSAND) {
					last.setType(TokenType.LOGICAL_AND);
					last.add(t);
					return;
				}
				if(last.getType() == TokenType.PIPE && t.getType() == TokenType.PIPE) {
					last.setType(TokenType.LOGICAL_OR);
					last.add(t);
					return;
				}
				if(last.getType() == TokenType.EQUALS && t.getType() == TokenType.EQUALS) {
					last.setType(TokenType.EQUALSEQUALS);
					last.add(t);
					return;
				}
				if(last.getType() == TokenType.EXCLAMATION && t.getType() == TokenType.EQUALS) {
					last.setType(TokenType.NOTEQUALS);
					last.add(t);
					return;
				}
				if(last.getType() == TokenType.STRING) {
					last.add(t);
					if(last.getValue().equalsIgnoreCase("true")) {
						last.setType(TokenType.TRUE);
					} else if(last.getValue().equalsIgnoreCase("false")) {
						last.setType(TokenType.FALSE);
					}
					return;
				}
			}
			tokens.add(t);
			last=t;
		}
	}
	
}
