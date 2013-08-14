package com.ebuddy.pingserver.cli.interpreter.language;

import com.ebuddy.pingserver.cli.interpreter.evaluator.ExpressionContext;
import com.ebuddy.pingserver.cli.interpreter.evaluator.Value;

public class NumberLiteralExpression extends Expression {

	private final int number;

	public NumberLiteralExpression( int number ) {
		this.number = number;
	}
	
	public String toExpressionString() {
		return String.valueOf(number);
	}

	@Override
	public Value evaluate(ExpressionContext context) {
		return new Value(number);
	}
	

	
}
