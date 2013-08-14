package com.ebuddy.pingserver.cli.interpreter.parser;

import com.ebuddy.pingserver.cli.interpreter.language.Expression;

public interface ExpressionFactory {

	Expression createExpression(TokenType type, Expression lhs, Expression rhs);

	Expression createExpression(TokenType type, Expression e);

	Expression createExpression(Token token);
	
}
