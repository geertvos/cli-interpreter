package com.ebuddy.pingserver.cli.interpreter.language;

import com.ebuddy.pingserver.cli.interpreter.evaluator.EvaluationException;
import com.ebuddy.pingserver.cli.interpreter.evaluator.ExpressionContext;
import com.ebuddy.pingserver.cli.interpreter.evaluator.Value;

public class ReferenceExpression extends Expression {

	private String reference;

	public ReferenceExpression(String name) {
		this.reference = name;
	}

	public String toExpressionString() {
		return "$"+reference;
	}

	@Override
	public Value evaluate(ExpressionContext context) {
		String names[] = reference.split("\\.");
		Value object = null;
		for(String name : names) {
			if(object == null) {
				object = context.getVariable(name);
			} else {
				if(object.isObject()) {
					object = object.getObjectValue(name);
				} else {
					throw new EvaluationException("Variable "+reference+" does not exist", this);
				}
			}
		}
		if(object==null) throw new EvaluationException("Variable "+reference+" does not exist", this);

		return object;
	}
	
}
