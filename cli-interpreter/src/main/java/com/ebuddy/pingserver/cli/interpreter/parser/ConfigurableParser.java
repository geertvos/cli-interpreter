package com.ebuddy.pingserver.cli.interpreter.parser;
import java.util.List;
import java.util.ListIterator;
import java.util.Vector;

import com.ebuddy.pingserver.cli.interpreter.language.DefaultExpressionFactory;
import com.ebuddy.pingserver.cli.interpreter.language.Expression;
import com.ebuddy.pingserver.cli.interpreter.parser.rules.ParseRule;

public class ConfigurableParser {

	private Tokenizer tokanizer = new Tokenizer();
	protected ExpressionFactory expressionFactory = new DefaultExpressionFactory();
	protected ParseRule initialRuleGroup;
	
	public ConfigurableParser(ExpressionFactory expressionFactory) {
		this.expressionFactory = expressionFactory;
	}
	
	public ParsedCommand parse( String line ) {
		
		List<Token> tokens = tokanizer.tokenize(line);
		ListIterator<Token> i = tokens.listIterator();
		while(i.hasNext()) {
			Token t = i.next();
			if(t.is(TokenType.STRING)) {
				return parseCommand(t,i);
			} else {
				throw new ParseException("Unexpected token, expected command but found "+t.getType().name(),t);
			}
		}
		throw new ParseException("Unable to parse input", null);
	}

	public Expression parseExpression( String line ) {
		List<Token> tokens = tokanizer.tokenize(line);
		ListIterator<Token> i = tokens.listIterator();
		Token t = i.next();
		if(t==null) {
			throw new ParseException("Unable to parse input. Input is empty.", null);
		}
		Expression expression = initialRuleGroup.parse(t, i);
		if(i.hasNext()) {
			throw new ParseException("Unexpected token, expected end of expression.", t);
		}
		return expression;
	}
	
	private ParsedCommand parseCommand(Token t, ListIterator<Token> i) {
		String commandName = t.getValue();
		List<Expression> arguments = new Vector<Expression>();
		while(i.hasNext()) {
			Token token = i.next();
			if(!token.is(TokenType.WS)) {
				Expression e = initialRuleGroup.parse(t, i);
				arguments.add(e);
			}
		}
		return new ParsedCommand(commandName,arguments);
	}
	
}
