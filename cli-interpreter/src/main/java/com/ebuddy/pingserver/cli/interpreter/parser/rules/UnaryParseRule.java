package com.ebuddy.pingserver.cli.interpreter.parser.rules;

import java.util.ListIterator;

import com.ebuddy.pingserver.cli.interpreter.language.Expression;
import com.ebuddy.pingserver.cli.interpreter.parser.ExpressionFactory;
import com.ebuddy.pingserver.cli.interpreter.parser.ParseException;
import com.ebuddy.pingserver.cli.interpreter.parser.Token;
import com.ebuddy.pingserver.cli.interpreter.parser.TokenType;

public class UnaryParseRule extends BaseFactoryParseRule  {

	private final TokenType[] acceptedTypes;
	
	public UnaryParseRule(ExpressionFactory factory, ParseRule next, TokenType ... types) {
		super(factory,next);
		this.acceptedTypes = types;
	}
	
	public Expression parse(Token t, ListIterator<Token> i) {
		for(TokenType type : acceptedTypes) {
			if(t.is(type)) {
				if(!i.hasNext()) {
					throw new ParseException("Unexpected end of expression, \""+type.getLiteral()+"\" should be followed by a value or variable.", t);
				}
				t=i.next();
				Expression e = parse(t, i);
				return expressionFactory.createExpression(type, e);
			}
		}
		return nextRule.parse(t, i);
	}
	
}
