package com.ebuddy.pingserver.cli.interpreter.parser.rules;

import java.util.ListIterator;

import com.ebuddy.pingserver.cli.interpreter.language.Expression;
import com.ebuddy.pingserver.cli.interpreter.parser.ExpressionFactory;
import com.ebuddy.pingserver.cli.interpreter.parser.Token;
import com.ebuddy.pingserver.cli.interpreter.parser.TokenType;

public class BranchingParseRule extends BaseFactoryParseRule {

	private final TokenType[] tokenTypes;

	public BranchingParseRule(ExpressionFactory expressionFactory, ParseRule nextRule, TokenType ... acceptedTokenTypes) {
		super(expressionFactory,nextRule);
		this.tokenTypes = acceptedTokenTypes;
	}
	
	public Expression parse(Token t, ListIterator<Token> i) {
		Expression lhs = nextRule.parse(t, i);
		if( i.hasNext() ) {
			/*
			 * Look ahead 1 step
			 */
			Token next = i.next();
			for(TokenType type : tokenTypes) {
				if( next.getType() == type) {
					Expression rhs = parse(i.next(), i);
					return expressionFactory.createExpression(type, lhs, rhs);
				}
			}
			/*
			 * Nothing useful found, stepping back.
			 */
			i.previous();
		}
		return lhs;
	}

}
