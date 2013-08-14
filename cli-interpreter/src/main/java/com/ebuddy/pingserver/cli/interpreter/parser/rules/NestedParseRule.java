package com.ebuddy.pingserver.cli.interpreter.parser.rules;

import java.util.ListIterator;

import com.ebuddy.pingserver.cli.interpreter.language.Expression;
import com.ebuddy.pingserver.cli.interpreter.parser.ParseException;
import com.ebuddy.pingserver.cli.interpreter.parser.Token;
import com.ebuddy.pingserver.cli.interpreter.parser.TokenType;

public class NestedParseRule extends BaseParseRule {

	private ParseRule nestedRule;
	private TokenType leftToken;
	private TokenType rightToken;
	
	public NestedParseRule(TokenType left, TokenType right, ParseRule nextRule) {
		super(nextRule);
		this.leftToken = left;
		this.rightToken = right;
	}
	
	public Expression parse(Token t, ListIterator<Token> i) {
		if(t.is(leftToken)) {
			if(i.hasNext()) {
				Token token = i.next();
				if(token.is(rightToken)) {
					throw new ParseException("Empty "+leftToken.getLiteral()+rightToken.getLiteral()+" not allowed.", token);
				} else {
					Expression e = nestedRule.parse(token, i);
					if(i.hasNext()) {
						token = i.next();
						if(!token.is(rightToken)) {
							throw new ParseException("Expected '"+rightToken.getLiteral()+"' but found: "+token.getValue(), token);
						}
						return e;
					}
				}
			}
			throw new ParseException("Expected '"+rightToken.getLiteral()+"' but found nothing.", t);
		} else {
			return nextRule.parse(t, i);
		}
	}
	
	public void setNestedRule(ParseRule nested) {
		this.nestedRule = nested;
	}

}
