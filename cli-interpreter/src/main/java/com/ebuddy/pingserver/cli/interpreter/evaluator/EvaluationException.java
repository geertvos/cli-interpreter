package com.ebuddy.pingserver.cli.interpreter.evaluator;
import com.ebuddy.pingserver.cli.interpreter.language.Expression;


public class EvaluationException extends RuntimeException {

	private static final long serialVersionUID = -650547838393322037L;

	public EvaluationException( String message, Expression e) {
		super(message);
	}
	
}
