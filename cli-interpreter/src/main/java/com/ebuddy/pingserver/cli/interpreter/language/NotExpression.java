package com.ebuddy.pingserver.cli.interpreter.language;

import com.ebuddy.pingserver.cli.interpreter.evaluator.EvaluationException;
import com.ebuddy.pingserver.cli.interpreter.evaluator.ExpressionContext;
import com.ebuddy.pingserver.cli.interpreter.evaluator.Value;

public class NotExpression extends Expression {
	
	private final Expression expression;

	public NotExpression( Expression e) {
		this.expression = e;
	}
	
	public String toExpressionString() {
		return "!"+expression.toExpressionString();
	}

	@Override
	public Value evaluate(ExpressionContext context) {
		Value v = context.evaluate(expression);
		if(!v.isBoolean())
			throw new EvaluationException("Boolean value expected", this);
		return new Value(!v.getBooleanValue());
	}
	
	
	
}
