package com.ebuddy.pingserver.cli.interpreter.parser.rules;

import java.util.ListIterator;

import com.ebuddy.pingserver.cli.interpreter.language.Expression;
import com.ebuddy.pingserver.cli.interpreter.parser.ParseException;
import com.ebuddy.pingserver.cli.interpreter.parser.Token;

public class ExpectedLiteralRule implements ParseRule {

	public Expression parse(Token t, ListIterator<Token> i) {
		throw new ParseException("Expected a literal, but got "+t.getType(), t);
	}

}
