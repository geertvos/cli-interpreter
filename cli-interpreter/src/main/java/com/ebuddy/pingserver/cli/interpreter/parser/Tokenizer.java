package com.ebuddy.pingserver.cli.interpreter.parser;

import java.util.List;
import java.util.Vector;

public class Tokenizer {

	private List<Token> tokens;
	private Token last = null;
	private boolean stringMode = false;
	
	public List<Token> tokenize(String line) {
		reset();
		next:
		for(int i=0;i<line.length();i++) {
			char c = line.charAt(i);
			for(TokenType tokenType : TokenType.values()) {
				if(tokenType.isLiteral() && tokenType.getLiteral().equals(String.valueOf(c))) {
					processToken(new Token(tokenType,String.valueOf(c),i));
					continue next;
				}
			}
			switch (c) {
			case '"':
				processToken(new Token(TokenType.STRING, "",i));
				stringMode=!stringMode;
				if(!stringMode) {
					last=null;
				}
				break;
			case '\r':
			case '\n':
			case '	':
			case ' ':
				if(last!=null && last.getType()==TokenType.WS)
					last.add(c);
				else processToken(new Token(TokenType.WS, ""+c,i));
				break;

			default:
				if(c >= '0' && c <= '9') {
					processToken(new Token(TokenType.NUMERIC, ""+c,i));
				} else {
					processToken(new Token(TokenType.STRING,""+c,i));
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

	private void processToken(Token t) {
		if(stringMode) {
			last.add(t);
		} else {
			if(t.is(TokenType.WS)) {
				if(!stringMode)
					last = null;
				return;
			}
			if(last != null) {
				
				//Handle literals
				for(TokenType tokenType : TokenType.values()) {
					if(tokenType.isCompound()) {
						if(last.getType() == tokenType.getLhs() && t.getType() == tokenType.getRhs()) {
							last.setType(tokenType);
							last.add(t);
							return;
						}
						
					}
					if(tokenType.isGreedy()) {
						if(last.getType() == tokenType && tokenType.accepts(t.getType())) {
							last.add(t);
							for(TokenType tokenType2 : TokenType.values()) {
								if(tokenType2.isTerminal() && tokenType2.getLiteral().equalsIgnoreCase(last.getValue())) {
									last.setType(tokenType2);
									return;
								}
							}
							return;
						}
					}
				}
			}
			tokens.add(t);
			last=t;
		}
	}
	
}
