package com.ebuddy.pingserver.cli.interpreter.parser.rules;

import java.util.ListIterator;

import com.ebuddy.pingserver.cli.interpreter.language.Expression;
import com.ebuddy.pingserver.cli.interpreter.parser.ExpressionFactory;
import com.ebuddy.pingserver.cli.interpreter.parser.Token;
import com.ebuddy.pingserver.cli.interpreter.parser.TokenType;

public class LiteralParseRule extends BaseFactoryParseRule {

	private final TokenType[] acceptedTypes;

	public LiteralParseRule(ExpressionFactory expressionFactory, ParseRule next, TokenType ... acceptedTypes) {
		super(expressionFactory,next);
		this.acceptedTypes = acceptedTypes;
	}
	
	public Expression parse(Token t, ListIterator<Token> i) {
		for(TokenType type : acceptedTypes) {
			if(t.getType() == type) {
				return expressionFactory.createExpression(t);
			}
		}
		return nextRule.parse(t, i);
	}

}
