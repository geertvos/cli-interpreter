package com.ebuddy.pingserver.cli.interpreter.evaluator;
import java.util.HashMap;
import java.util.Map;

import com.ebuddy.pingserver.cli.interpreter.language.Expression;


public class ExpressionContext {

	private Map<String,Value> variables = new HashMap<String, Value>();
	
	public void setVariable(String identifier, Value value) {
		System.out.println("$"+identifier+" = "+value.toString());
		this.variables.put(identifier, value);
	}
	
	public Value evaluate( Expression e ) {
		return e.evaluate(this);
	}
	
	public Value getVariable(String name) {
		return variables.get(name);
	}

	public void reset(String name) {
		variables.remove(name);
	}
	
}
