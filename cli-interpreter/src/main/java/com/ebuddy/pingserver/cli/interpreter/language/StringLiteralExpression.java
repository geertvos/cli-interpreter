package com.ebuddy.pingserver.cli.interpreter.language;

import com.ebuddy.pingserver.cli.interpreter.evaluator.ExpressionContext;
import com.ebuddy.pingserver.cli.interpreter.evaluator.Value;

public class StringLiteralExpression extends Expression {

	private final String value;

	public StringLiteralExpression(String value) {
		this.value = value;
	}

	public String toExpressionString() {
		return "\""+value+"\"";
	}

	@Override
	public Value evaluate(ExpressionContext context) {
		return new Value(value);
	}

	
}
