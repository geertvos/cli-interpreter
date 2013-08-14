package com.ebuddy.pingserver.cli.interpreter.parser;
import java.util.List;

import com.ebuddy.pingserver.cli.interpreter.language.Expression;


public class ParsedCommand {

	private String name;
	private List<Expression> arguments;
	
	public ParsedCommand( String name, List<Expression> arguments ) {
		this.name = name;
		this.arguments = arguments;
	}
	
	public String toCommandString() {
		String command  = name+" ";
		for(Expression e : arguments) 
			command+=e.toExpressionString()+" ";
		return command;
	}
	
	public String getName() {
		return name;
	}
	
	public List<Expression> getArguments() {
		return arguments;
	}
	
}
