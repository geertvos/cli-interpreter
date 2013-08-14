package com.ebuddy.pingserver.cli.interpreter.language;

import com.ebuddy.pingserver.cli.interpreter.evaluator.EvaluationException;
import com.ebuddy.pingserver.cli.interpreter.evaluator.ExpressionContext;
import com.ebuddy.pingserver.cli.interpreter.evaluator.Value;

public class NegateExpression extends Expression {
	
	private Expression expression;

	public NegateExpression( Expression e) {
		this.expression = e;
	}
	
	public String toExpressionString() {
		return "-"+expression.toExpressionString();
	}

	@Override
	public Value evaluate(ExpressionContext context) {
		Value v = context.evaluate(expression);
		if(!v.isNumber())
			throw new EvaluationException("Number expected", this);
		return new Value(-v.getNumberValue());
	}
	
	
	
}
