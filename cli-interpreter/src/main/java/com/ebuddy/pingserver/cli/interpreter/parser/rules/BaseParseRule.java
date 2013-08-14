package com.ebuddy.pingserver.cli.interpreter.parser.rules;


public abstract class BaseParseRule implements ParseRule {
	
	protected final ParseRule nextRule;

	public BaseParseRule(ParseRule nextRule) {
		super();
		this.nextRule = nextRule;
	}


}
