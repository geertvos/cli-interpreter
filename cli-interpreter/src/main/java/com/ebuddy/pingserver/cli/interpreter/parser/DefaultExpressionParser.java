package com.ebuddy.pingserver.cli.interpreter.parser;

import static com.ebuddy.pingserver.cli.interpreter.parser.TokenType.BIGGEREQUALS;
import static com.ebuddy.pingserver.cli.interpreter.parser.TokenType.BIGGERTHAN;
import static com.ebuddy.pingserver.cli.interpreter.parser.TokenType.EQUALSEQUALS;
import static com.ebuddy.pingserver.cli.interpreter.parser.TokenType.EXCLAMATION;
import static com.ebuddy.pingserver.cli.interpreter.parser.TokenType.FALSE;
import static com.ebuddy.pingserver.cli.interpreter.parser.TokenType.LEFT_BRACE;
import static com.ebuddy.pingserver.cli.interpreter.parser.TokenType.LOGICAL_AND;
import static com.ebuddy.pingserver.cli.interpreter.parser.TokenType.LOGICAL_OR;
import static com.ebuddy.pingserver.cli.interpreter.parser.TokenType.MINUS;
import static com.ebuddy.pingserver.cli.interpreter.parser.TokenType.NOTEQUALS;
import static com.ebuddy.pingserver.cli.interpreter.parser.TokenType.NUMERIC;
import static com.ebuddy.pingserver.cli.interpreter.parser.TokenType.PLUS;
import static com.ebuddy.pingserver.cli.interpreter.parser.TokenType.RIGHT_BRACE;
import static com.ebuddy.pingserver.cli.interpreter.parser.TokenType.SLASH;
import static com.ebuddy.pingserver.cli.interpreter.parser.TokenType.SMALLEREQUALS;
import static com.ebuddy.pingserver.cli.interpreter.parser.TokenType.SMALLERTHAN;
import static com.ebuddy.pingserver.cli.interpreter.parser.TokenType.STAR;
import static com.ebuddy.pingserver.cli.interpreter.parser.TokenType.STRING;
import static com.ebuddy.pingserver.cli.interpreter.parser.TokenType.TRUE;

import com.ebuddy.pingserver.cli.interpreter.language.DefaultExpressionFactory;
import com.ebuddy.pingserver.cli.interpreter.parser.rules.BranchingParseRule;
import com.ebuddy.pingserver.cli.interpreter.parser.rules.ExpectedLiteralRule;
import com.ebuddy.pingserver.cli.interpreter.parser.rules.LiteralParseRule;
import com.ebuddy.pingserver.cli.interpreter.parser.rules.NestedParseRule;
import com.ebuddy.pingserver.cli.interpreter.parser.rules.ParseRule;
import com.ebuddy.pingserver.cli.interpreter.parser.rules.UnaryParseRule;
import com.ebuddy.pingserver.cli.interpreter.parser.rules.VariableParseRule;

public class DefaultExpressionParser extends ConfigurableParser {

	public DefaultExpressionParser() {
		super(new DefaultExpressionFactory());
		ExpectedLiteralRule terminatingRule = new ExpectedLiteralRule();
		LiteralParseRule literalRule = new LiteralParseRule(expressionFactory, terminatingRule, TRUE, FALSE, STRING, NUMERIC);
		VariableParseRule variableRule = new VariableParseRule(literalRule);
		UnaryParseRule unaryRule = new UnaryParseRule(expressionFactory, variableRule, EXCLAMATION, MINUS, PLUS);
		NestedParseRule bracesRule = new NestedParseRule(LEFT_BRACE, RIGHT_BRACE, unaryRule);
		ParseRule moreArithmeticRule = new BranchingParseRule(expressionFactory, bracesRule,STAR, SLASH);
		ParseRule arithmeticRule = new BranchingParseRule(expressionFactory, moreArithmeticRule, PLUS, MINUS);
		ParseRule comparisonRule = new BranchingParseRule(expressionFactory, arithmeticRule, SMALLERTHAN, SMALLEREQUALS, BIGGERTHAN, BIGGEREQUALS);
		ParseRule equalityRule = new BranchingParseRule(expressionFactory, comparisonRule, EQUALSEQUALS, NOTEQUALS);
		initialRuleGroup = new BranchingParseRule(expressionFactory, equalityRule, LOGICAL_AND, LOGICAL_OR);
		variableRule.setNestedRule(initialRuleGroup);
		bracesRule.setNestedRule(initialRuleGroup);

		
	}

}
