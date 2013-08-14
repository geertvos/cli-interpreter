package com.ebuddy.pingserver.cli.interpreter.language;

import com.ebuddy.pingserver.cli.interpreter.evaluator.ExpressionContext;
import com.ebuddy.pingserver.cli.interpreter.evaluator.Value;

public class BooleanLiteralExpression extends Expression {

	private final boolean value;

	public BooleanLiteralExpression( boolean value ) {
		this.value = value;
	}
	
	public String toExpressionString() {
		return String.valueOf(value);
	}

	@Override
	public Value evaluate(ExpressionContext context) {
		return new Value(value);
	}
	

	
}
