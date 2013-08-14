package com.ebuddy.pingserver.cli.interpreter.language;

import com.ebuddy.pingserver.cli.interpreter.evaluator.ExpressionContext;
import com.ebuddy.pingserver.cli.interpreter.evaluator.Value;


public abstract class Expression {

	public abstract String toExpressionString();
	public abstract Value evaluate(ExpressionContext context);
}
