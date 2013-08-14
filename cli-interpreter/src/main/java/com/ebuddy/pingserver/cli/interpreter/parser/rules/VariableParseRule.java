package com.ebuddy.pingserver.cli.interpreter.parser.rules;

import java.util.ListIterator;

import com.ebuddy.pingserver.cli.interpreter.language.ArrayExpression;
import com.ebuddy.pingserver.cli.interpreter.language.Expression;
import com.ebuddy.pingserver.cli.interpreter.language.ReferenceExpression;
import com.ebuddy.pingserver.cli.interpreter.parser.ParseException;
import com.ebuddy.pingserver.cli.interpreter.parser.Token;
import com.ebuddy.pingserver.cli.interpreter.parser.TokenType;

public class VariableParseRule extends BaseParseRule {
	
	private ParseRule nestedRule;
	
	public VariableParseRule(ParseRule nextRule) {
		super(nextRule);
	}
	
	public Expression parse(Token t, ListIterator<Token> i) {
		if(t.is(TokenType.DOLLAR)) {
			if(!i.hasNext()) {
				throw new ParseException("Unexpected end of expression, $ should be followed by variable name.", t);
			}
			t=i.next();
			if(!t.is(TokenType.STRING)) {
				throw new ParseException("Invalid variable name.", t);
			}
			Expression expression = new ReferenceExpression(t.getValue());
			while(i.hasNext()) {
				t = i.next();
				if(t.is(TokenType.LEFT_BRACKET)) {
					t = i.next();
					Expression index = nestedRule.parse(t, i);
					t = i.next();
					if(t.is(TokenType.RIGHT_BRACKET)) {
						expression = new ArrayExpression(expression, index);
					}
				} else if(t.is(TokenType.DOT)) {
					t = i.next();
					expression = new ReferenceExpression(t.getValue(), expression);
				} else {
					i.previous();
					return expression;
				}
			}
			return expression;
		}
		return nextRule.parse(t,i);
	}

	public void setNestedRule(ParseRule nestedRule) {
		this.nestedRule = nestedRule;
	}
	
	
}
