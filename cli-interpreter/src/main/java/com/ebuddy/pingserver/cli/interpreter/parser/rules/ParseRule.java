package com.ebuddy.pingserver.cli.interpreter.parser.rules;

import java.util.ListIterator;

import com.ebuddy.pingserver.cli.interpreter.language.Expression;
import com.ebuddy.pingserver.cli.interpreter.parser.Token;

public interface ParseRule {

	Expression parse(Token t, ListIterator<Token> i);
	
}
