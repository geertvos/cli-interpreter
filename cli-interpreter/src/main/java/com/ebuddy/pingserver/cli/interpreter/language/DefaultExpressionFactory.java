package com.ebuddy.pingserver.cli.interpreter.language;

import com.ebuddy.pingserver.cli.interpreter.parser.ExpressionFactory;
import com.ebuddy.pingserver.cli.interpreter.parser.ParseException;
import com.ebuddy.pingserver.cli.interpreter.parser.Token;
import com.ebuddy.pingserver.cli.interpreter.parser.TokenType;

public class DefaultExpressionFactory implements ExpressionFactory {

	public Expression createExpression(TokenType type, Expression lhs, Expression rhs) {
		if(type == TokenType.LOGICAL_AND) {
			return new AndExpression(lhs,rhs);
		}
		else if(type == TokenType.LOGICAL_OR) {
			return new OrExpression(lhs,rhs);
		}
		else if(type == TokenType.EQUALSEQUALS) {
			return new EqualsExpression(lhs,rhs);
		}
		else if(type == TokenType.NOTEQUALS) {
			return new NotExpression(new EqualsExpression(lhs, rhs));
		}
		else if(type == TokenType.SMALLERTHAN) {
			return new LessThanExpression(lhs, rhs);
		}
		else if(type == TokenType.SMALLEREQUALS) {
			return new OrExpression(new LessThanExpression(lhs, rhs),new EqualsExpression(lhs, rhs));
		}
		else if(type == TokenType.BIGGERTHAN) {
			return new GreaterThanExpression(lhs, rhs);
		}
		else if(type == TokenType.BIGGEREQUALS) {
			return new OrExpression(new GreaterThanExpression(lhs, rhs),new EqualsExpression(lhs, rhs));
		}
		else if(type == TokenType.PLUS) {
			return new AddExpression(lhs,rhs);
		}
		else if(type == TokenType.MINUS) {
			return new SubstractExpression(lhs,rhs);
		}
		else if(type == TokenType.STAR) {
			return new MultiplyExpression(lhs,rhs);
		}
		else if(type == TokenType.SLASH) {
			return new DivideExpression(lhs,rhs);
		}

		throw new ParseException("Unable to create expression for token type.", null);
	}

	public Expression createExpression(TokenType type, Expression e) {
		if(type == TokenType.PLUS) {
			return e;
		}
		else if(type == TokenType.MINUS) {
			return new NegateExpression(e);
		}
		else if(type == TokenType.EXCLAMATION) {
			return new NotExpression(e);
		}
		else if(type == TokenType.MINUS) {
			return new NegateExpression(e);
		}
		throw new ParseException("Unable to create expression for token type.", null);
	}

	public Expression createExpression(Token token) {
		if(token.getType() == TokenType.TRUE) {
			return new BooleanLiteralExpression(true);
		}
		else if(token.getType() == TokenType.FALSE) {
			return new BooleanLiteralExpression(false);
		}
		else if(token.getType() == TokenType.STRING) {
			return new StringLiteralExpression(token.getValue());
		}
		else if(token.getType() == TokenType.NUMERIC) {
			return new NumberLiteralExpression(Integer.parseInt(token.getValue()));
		} 
		throw new ParseException("Unable to create expression for token.", token);
	}

}
