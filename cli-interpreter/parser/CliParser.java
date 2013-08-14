package com.ebuddy.pingserver.cli.interpreter.parser;
import java.util.List;
import java.util.ListIterator;
import java.util.Vector;

import com.ebuddy.pingserver.cli.interpreter.language.AndExpression;
import com.ebuddy.pingserver.cli.interpreter.language.BiggerExpression;
import com.ebuddy.pingserver.cli.interpreter.language.BooleanLiteralExpression;
import com.ebuddy.pingserver.cli.interpreter.language.DivideExpression;
import com.ebuddy.pingserver.cli.interpreter.language.EqualsExpression;
import com.ebuddy.pingserver.cli.interpreter.language.Expression;
import com.ebuddy.pingserver.cli.interpreter.language.MinusExpression;
import com.ebuddy.pingserver.cli.interpreter.language.MultiplyExpression;
import com.ebuddy.pingserver.cli.interpreter.language.NegateExpression;
import com.ebuddy.pingserver.cli.interpreter.language.NotExpression;
import com.ebuddy.pingserver.cli.interpreter.language.NumberLiteralExpression;
import com.ebuddy.pingserver.cli.interpreter.language.OrExpression;
import com.ebuddy.pingserver.cli.interpreter.language.PlusExpression;
import com.ebuddy.pingserver.cli.interpreter.language.ReferenceExpression;
import com.ebuddy.pingserver.cli.interpreter.language.SmallerExpression;
import com.ebuddy.pingserver.cli.interpreter.language.StringLiteralExpression;


public class CliParser {

	private Tokenizer tokanizer = new Tokenizer();

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
		return parseBoolean(t,i);
	}
	
	private ParsedCommand parseCommand(Token t, ListIterator<Token> i) {
		String commandName = t.getValue();
		List<Expression> arguments = new Vector<Expression>();
		while(i.hasNext()) {
			Token token = i.next();
			if(!token.is(TokenType.WS)) {
				Expression e = parseBoolean(token,i);
				arguments.add(e);
			}
		}
		return new ParsedCommand(commandName,arguments);
	}
	
	public Expression parseBoolean(Token t, ListIterator<Token> i) {
		Expression lhs = parseEquals(t, i);
		if( i.hasNext() ) {
			/*
			 * Look ahead 1 step
			 */
			Token next = i.next();
			if( next.getType() == TokenType.LOGICAL_AND) {
				Expression rhs = parseBoolean(i.next(), i);
				return new AndExpression(lhs, rhs);
			}
			else if( next.getType() == TokenType.LOGICAL_OR) {
				Expression rhs = parseBoolean(i.next(), i);
				return new OrExpression(lhs, rhs);
			}
			/*
			 * Nothing useful found, stepping back.
			 */
			i.previous();
		}
		return lhs;
	}

	private Expression parseEquals(Token t, ListIterator<Token> i) {
		Expression lhs = parseComparison(t, i);
		if( i.hasNext() ) {
			/*
			 * Look ahead 1 step
			 */
			Token next = i.next();
			if( next.getType() == TokenType.EQUALSEQUALS) {
				Expression rhs = parseEquals(i.next(), i);
				return new EqualsExpression(lhs, rhs);
			}
			else if( next.getType() == TokenType.NOTEQUALS) {
				Expression rhs = parseEquals(i.next(), i);
				return new NotExpression(new EqualsExpression(lhs, rhs));
			}
			/*
			 * Nothing useful found, stepping back.
			 */
			i.previous();
		}
		return lhs;
	}
	
	private Expression parseComparison(Token t, ListIterator<Token> i) {
		Expression lhs = parseArithmetic(t, i);
		if( i.hasNext() ) {
			/*
			 * Look ahead 1 step
			 */
			Token next = i.next();
			if( next.getType() == TokenType.SMALLERTHAN) {
				Expression rhs = parseComparison(i.next(), i);
				return new SmallerExpression(lhs, rhs);
			}
			else if( next.getType() == TokenType.SMALLEREQUALS) {
				Expression rhs = parseComparison(i.next(), i);
				return new OrExpression(new SmallerExpression(lhs, rhs),new EqualsExpression(lhs, rhs));
			}
			else if( next.getType() == TokenType.BIGGERTHAN) {
				Expression rhs = parseComparison(i.next(), i);
				return new BiggerExpression(lhs, rhs);
			}
			else if( next.getType() == TokenType.BIGGEREQUALS) {
				Expression rhs = parseComparison(i.next(), i);
				return new OrExpression(new BiggerExpression(lhs, rhs),new EqualsExpression(lhs, rhs));
			}
			/*
			 * Nothing useful found, stepping back.
			 */
			i.previous();
		}
		return lhs;
	}	
	
	private Expression parseArithmetic(Token t, ListIterator<Token> i) {
		Expression lhs = parseMoreArithmetic(t, i);
		if( i.hasNext() ) {
			/*
			 * Look ahead 1 step
			 */
			Token next = i.next();
			if( next.getType() == TokenType.PLUS) {
				Expression rhs = parseArithmetic(i.next(), i);
				return new PlusExpression(lhs, rhs);
			}
			else if( next.getType() == TokenType.MINUS) {
				Expression rhs = parseArithmetic(i.next(), i);
				return new MinusExpression(lhs, rhs);
			}
			/*
			 * Nothing useful found, stepping back.
			 */
			i.previous();
		}
		return lhs;
	}
	
	private Expression parseMoreArithmetic(Token t, ListIterator<Token> i) {
		Expression lhs = parseBraces(t, i);
		if( i.hasNext() ) {
			/*
			 * Look ahead 1 step
			 */
			Token next = i.next();
			if( next.getType() == TokenType.STAR) {
				Expression rhs = parseMoreArithmetic(i.next(), i);
				return new MultiplyExpression(lhs, rhs);
			}
			else if( next.getType() == TokenType.SLASH) {
				Expression rhs = parseMoreArithmetic(i.next(), i);
				return new DivideExpression(lhs, rhs);
			}
			/*
			 * Nothing useful found, stepping back.
			 */
			i.previous();
		}
		return lhs;
	}

	private Expression parseBraces(Token t, ListIterator<Token> i) {
		if(t.is(TokenType.LEFT_BRACE)) {
			if(i.hasNext()) {
				Token token = i.next();
				if(token.is(TokenType.RIGHT_BRACE)) {
					throw new ParseException("Empty braces not allowed", token);
				} else {
					Expression e = parseBoolean(token,i);
					if(i.hasNext()) {
						token = i.next();
						if(!token.is(TokenType.RIGHT_BRACE)) {
							throw new ParseException("Expected ')' but found: "+token.getValue(), token);
						}
						return e;
					}
				}
			}
			throw new ParseException("Expected ')' but found nothing.", t);
		} else {
			return parseVariable(t, i);
		}
	}
	
	
	
	private Expression parseVariable(Token t, ListIterator<Token> i) {
		if(t.is(TokenType.EXCLAMATION)) {
			t=i.next();
			Expression e = parseBoolean(t, i);
			return new NotExpression(e);
		}
		if(t.is(TokenType.MINUS)) {
			t=i.next();
			Expression e = parseBraces(t, i);
			return new NegateExpression(e);
		}
		if(t.is(TokenType.DOLLAR)) {
			t=i.next();
			return new ReferenceExpression(t.getValue());
		} else {
			return parseLiteral(t, i);
		}
	}
	
	private Expression parseLiteral(Token t, ListIterator<Token> i) {
		if(t.is(TokenType.TRUE)) {
			return new BooleanLiteralExpression(true);
		}
		else if(t.is(TokenType.FALSE)) {
			return new BooleanLiteralExpression(false);
		}
		else if(t.is(TokenType.STRING)) {
			return new StringLiteralExpression(t.getValue());
		}
		else if(t.is(TokenType.NUMERIC)) {
			return new NumberLiteralExpression(Integer.parseInt(t.getValue()));
		} 
		throw new ParseException("Expected literal, found: "+t.getType().name()+" "+t.getValue(),t);

	}
	
	
}
