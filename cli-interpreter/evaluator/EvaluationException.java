package com.ebuddy.pingserver.cli.interpreter.evaluator;
import com.ebuddy.pingserver.cli.interpreter.language.Expression;


public class EvaluationException extends RuntimeException {

	public EvaluationException( String message, Expression e) {
		super(message);
	}
	
}
