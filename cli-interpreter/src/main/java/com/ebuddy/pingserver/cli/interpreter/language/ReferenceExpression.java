package com.ebuddy.pingserver.cli.interpreter.language;

import com.ebuddy.pingserver.cli.interpreter.evaluator.EvaluationException;
import com.ebuddy.pingserver.cli.interpreter.evaluator.ExpressionContext;
import com.ebuddy.pingserver.cli.interpreter.evaluator.Value;
import com.ebuddy.pingserver.cli.interpreter.parser.TokenType;

public class ReferenceExpression extends Expression {

	private final String reference;
	private Expression parent;
	private Expression index;

	public ReferenceExpression(String name) {
		this.reference = name;
	}

	public ReferenceExpression(String name, Expression parent) {
		this.reference = name;
		this.parent = parent;
	}

	
	public String toExpressionString() {
		if(parent != null) {
			return parent.toExpressionString()+"."+reference;
		} else {
			return TokenType.DOLLAR.toString()+reference;
		}
	}

	public void setIndex(Expression index) {
		this.index = index;
	}
	
	@Override
	public Value evaluate(ExpressionContext context) {
		Value object = null;
		if(parent != null) {
			object = parent.evaluate(context);
			if(object == null) {
				throw new EvaluationException("Variable "+parent+" does not exist", this);
			}
			if(object.isObject()) {
				object = object.getObjectValue(reference);
				if(object==null) throw new EvaluationException("Variable "+parent.toExpressionString()+" does not have a field "+reference, this);
			} else {
				throw new EvaluationException("Variable "+parent.toExpressionString()+" is not an object but a "+object.getType().toString(), this);
			}

		} else {
			object = context.getVariable(reference);
			if(object==null) throw new EvaluationException("Variable "+reference+" does not exist.", this);
		}
		if(index != null) {
			Value val = index.evaluate(context);
			if(!val.isNumber()) {
				throw new EvaluationException("Array index should be a number, not a "+val.getType(), index);
			}
			return object.get(val.getNumberValue());
		}

		
		return object;
	}
	
}
