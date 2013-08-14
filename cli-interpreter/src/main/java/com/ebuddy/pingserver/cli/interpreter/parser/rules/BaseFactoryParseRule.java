package com.ebuddy.pingserver.cli.interpreter.parser.rules;

import com.ebuddy.pingserver.cli.interpreter.parser.ExpressionFactory;

public abstract class BaseFactoryParseRule extends BaseParseRule {
	
	protected final ExpressionFactory expressionFactory;

	public BaseFactoryParseRule(ExpressionFactory expressionFactory, ParseRule nextRule) {
		super(nextRule);
		this.expressionFactory = expressionFactory;
	}


}
