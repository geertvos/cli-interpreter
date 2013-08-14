package com.ebuddy.pingserver.cli.interpreter.language;

import com.ebuddy.pingserver.cli.interpreter.evaluator.EvaluationException;
import com.ebuddy.pingserver.cli.interpreter.evaluator.ExpressionContext;
import com.ebuddy.pingserver.cli.interpreter.evaluator.Value;
import com.ebuddy.pingserver.cli.interpreter.parser.TokenType;

public class ArrayExpression extends Expression {

	private final Expression index;
	private final Expression parent;


	public ArrayExpression(Expression parent, Expression index) {
		this.parent = parent;
		this.index = index;
	}

	
	public String toExpressionString() {
		return parent.toExpressionString()+TokenType.LEFT_BRACKET+" "+index.toExpressionString()+" "+TokenType.RIGHT_BRACKET;
	}

	@Override
	public Value evaluate(ExpressionContext context) {
		Value object = parent.evaluate(context);
		if(object == null) {
			throw new EvaluationException("Cannot access array index on null value",this);
		}
		if(index != null) {
			Value val = index.evaluate(context);
			if(!val.isNumber()) {
				throw new EvaluationException("Array index should be a number, not a "+val.getType(), index);
			}
			return object.get(val.getNumberValue());
		}
		throw new EvaluationException("Invalid array index.",this);
	}
	
}
