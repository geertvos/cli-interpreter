package com.ebuddy.pingserver.cli.interpreter.language;

import com.ebuddy.pingserver.cli.interpreter.evaluator.ExpressionContext;
import com.ebuddy.pingserver.cli.interpreter.evaluator.Value;

public class EqualsExpression extends Expression{

	private final Expression lhs;
	private final Expression rhs;

	public EqualsExpression( Expression lhs, Expression rhs) {
		this.lhs = lhs;
		this.rhs = rhs;
	}
	
	public String toExpressionString() {
		return lhs.toExpressionString()+" == "+ rhs.toExpressionString();
	}
	
	@Override
	public Value evaluate(ExpressionContext context) {
		Value l = context.evaluate(lhs);
		Value r = context.evaluate(rhs);
		if(l.getType() == r.getType()) {
			return new Value(l.getStringValue().equals(r.getStringValue()));
		} else return new Value(false);
		
	}

	
}
