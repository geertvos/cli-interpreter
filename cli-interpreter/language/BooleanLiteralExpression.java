package com.ebuddy.pingserver.cli.interpreter.language;

import com.ebuddy.pingserver.cli.interpreter.evaluator.ExpressionContext;
import com.ebuddy.pingserver.cli.interpreter.evaluator.Value;

public class BooleanLiteralExpression extends Expression {

	private boolean value;

	public BooleanLiteralExpression( boolean value ) {
		this.value = value;
	}
	
	public String toExpressionString() {
		return ""+this.value;
	}

	@Override
	public Value evaluate(ExpressionContext context) {
		return new Value(value);
	}
	

	
}
