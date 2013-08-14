package com.ebuddy.pingserver.cli.interpreter.language;

import com.ebuddy.pingserver.cli.interpreter.evaluator.EvaluationException;
import com.ebuddy.pingserver.cli.interpreter.evaluator.ExpressionContext;
import com.ebuddy.pingserver.cli.interpreter.evaluator.Value;

public class MultiplyExpression extends Expression{

	private final Expression lhs;
	private final Expression rhs;

	public MultiplyExpression( Expression lhs, Expression rhs) {
		this.lhs = lhs;
		this.rhs = rhs;
	}
	
	public String toExpressionString() {
		return "( " +lhs.toExpressionString()+" * "+ rhs.toExpressionString()+" )";
	}
	
	@Override
	public Value evaluate(ExpressionContext context) {
		Value l = context.evaluate(lhs);
		Value r = context.evaluate(rhs);
		if(!l.isNumber())
			throw new EvaluationException("Left hand side of * is not a number", this);
		if(!r.isNumber())
			throw new EvaluationException("Right hand side of * is not a number", this);
		return new Value(l.getNumberValue() * r.getNumberValue());
	}
	

	
}
