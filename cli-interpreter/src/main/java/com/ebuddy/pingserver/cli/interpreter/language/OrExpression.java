package com.ebuddy.pingserver.cli.interpreter.language;

import com.ebuddy.pingserver.cli.interpreter.evaluator.EvaluationException;
import com.ebuddy.pingserver.cli.interpreter.evaluator.ExpressionContext;
import com.ebuddy.pingserver.cli.interpreter.evaluator.Value;

public class OrExpression extends Expression{

	private final Expression lhs;
	private final Expression rhs;

	public OrExpression( Expression lhs, Expression rhs) {
		this.lhs = lhs;
		this.rhs = rhs;
	}
	
	public String toExpressionString() {
		return "( " +lhs.toExpressionString()+" || "+ rhs.toExpressionString()+" )";
	}
	
	@Override
	public Value evaluate(ExpressionContext context) {
		Value l = context.evaluate(lhs);
		Value r = context.evaluate(rhs);
		if(!l.isBoolean())
			throw new EvaluationException("Left hand side of OR is not boolean", this);
		if(!r.isBoolean())
			throw new EvaluationException("Right hand side of OR is not boolean", this);
		return new Value(l.getBooleanValue() || r.getBooleanValue());
	}
	
}
