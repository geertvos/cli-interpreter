package com.ebuddy.lang.cli_interpreter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.ebuddy.pingserver.cli.interpreter.evaluator.EvaluationException;
import com.ebuddy.pingserver.cli.interpreter.evaluator.ExpressionContext;
import com.ebuddy.pingserver.cli.interpreter.evaluator.Value;
import com.ebuddy.pingserver.cli.interpreter.evaluator.Value;
import com.ebuddy.pingserver.cli.interpreter.language.Expression;
import com.ebuddy.pingserver.cli.interpreter.parser.ConfigurableParser;
import com.ebuddy.pingserver.cli.interpreter.parser.DefaultExpressionParser;
import com.ebuddy.pingserver.cli.interpreter.parser.ParseException;

public class CliParserTest {
	
	private ExpressionContext context = new ExpressionContext();

	@Test(groups = "integration")
	public void testArrayReferenceExpressions() {
		context.setVariable("value", new Value(Arrays.asList(new Value("geert"),new Value("vos"))));
		context.setVariable("value", new Value(Arrays.asList(new Value("geert"),new Value(Arrays.asList(new Value("geert"),new Value("vos"))))));
		test("$value[0]", new Value("geert"));
		test("$value[2+-1][0]", new Value("geert"));
		test("$value[8-7][1]", new Value("vos"));
	}
		
	
	@Test(groups = "integration")
	public void testReferenceExpressions() {
		context.setVariable("value", new Value(true));
		context.setVariable("number", new Value(1));
		test("$value", new Value(true));
		test("-$number", new Value(-1));
		try {
			test("$value2", new Value(true));
			Assert.fail("Should fail because of missing value.");
		} catch (EvaluationException exception) {
		}
	}

	@Test(groups = "integration")
	public void testSubReferenceExpressions() {
		Map<String, Value> values = new HashMap<String, Value>();
		values.put("b", new Value(true));
		Value object = new Value(values);
		context.setVariable("a", object);
		context.setVariable("d", new Value(1));
		test("$a.b", new Value(true));
		try {
			test("$a.c", new Value(true));
		} catch(EvaluationException e) {
			Assert.assertEquals(e.getMessage(), "Variable $a does not have a field c");
		}
		try {
			test("$d.a", new Value(true));
		} catch(EvaluationException e) {
			Assert.assertEquals(e.getMessage(), "Variable $d is not an object but a Number");
		}
	}

	@Test(groups = "integration")
	public void testLogicExpressions() {
		test("1==1", new Value(true));
		test("1!=2", new Value(true));
		test("1==2", new Value(false));
		test("2==1", new Value(false));
		test("true", new Value(true));
		test("!true", new Value(false));
		test("false", new Value(false));
		test("!false", new Value(true));
		test("true && true", new Value(true));
		test("false && true", new Value(false));
		test("true && false", new Value(false));
		test("false && false", new Value(false));
		test("true || false", new Value(true));
		test("true || false", new Value(true));
		test("false || true", new Value(true));
		test("true || true", new Value(true));
		test("false || false", new Value(false));
		test("false || false", new Value(false));
		test("(true || false) && true", new Value(true));

	}


	@Test(groups = "integration")
	public void testComplexExpressions() {
		test("(2*4)+1 < 10 && -1+1 < 10 && true && !false", new Value(true));
	}

	@Test(groups = "integration")
	public void testArithmeticExpressions() {
		test("1", new Value(1));
		test("+1", new Value(1));
		test("++1", new Value(1));
		test("+--1", new Value(1));
		test("-1", new Value(-1));
		test("--1", new Value(1));
		test("---1", new Value(-1));
		test("1+1", new Value(2));
		test("1*3", new Value(3));
		test("4/2", new Value(2));
		test("2+2*2", new Value(6));
		test("(2+2)*2", new Value(8));
		test("2+(2*2)", new Value(6));
		test("((2)+2)*2", new Value(8));
		test("(2+2)*(2+2)", new Value(16));
		test("1 + 1", new Value(2));
		test("1 * 3", new Value(3));
		test("4 / 2", new Value(2));
		test("2 + 2 * 2", new Value(6));
		test(" ( 2 + 2 ) * 2 ", new Value(8));
		test(" ( ( 2 ) + 2 ) * 2 ", new Value(8));
		test(" ( 2 + 2 ) * ( 2 + 2 )", new Value(16));
	}

	@Test(groups = "integration")
	public void testComparisonExpressions() {
		test("1 < 2", new Value(true));
		test("1 <= 1", new Value(true));
		test("1 <= 2", new Value(true));
		test("2 < 1", new Value(false));
		test("2 <= 1", new Value(false));
		test("2 <= 1", new Value(false));

		test("2 > 1", new Value(true));
		test("2 >= 1", new Value(true));
		test("2 >= 2", new Value(true));
		test("2 > 1", new Value(true));
		test("2 >= 1", new Value(true));
		test("2 >= 2", new Value(true));
	}

	@Test(groups = "integration")
	public void testStringExpressions() {
		test("hallo", new Value("hallo"));
		test("\"hallo\"", new Value("hallo"));
		test("\"hallo/\"", new Value("hallo/"));
		test("\"hallo\"+\"!\"", new Value("hallo!"));
		test("hallo   ", new Value("hallo"));
		test("\"hallo   \"", new Value("hallo   "));
		test("\"$\"", new Value("$"));
		test("\".\"", new Value("."));
	}

	@Test(groups = "integration")
	public void testInvalidExpressions() {

		try {
			test("2=1", new Value(1));
			Assert.fail();
		} catch (ParseException exception) {
		}
		try {
			test("==", new Value(1));
			Assert.fail();
		} catch (ParseException exception) {
		}
		try {
			test("!", new Value(1));
			Assert.fail();
		} catch (ParseException exception) {
		}
		try {
			test("-", new Value(1));
			Assert.fail();
		} catch (ParseException exception) {
		}
		try {
			test("+", new Value(1));
			Assert.fail();
		} catch (ParseException exception) {
		}
		try {
			test("&&", new Value(1));
			Assert.fail();
		} catch (ParseException exception) {
		}
	}

	private void test(String expression, Value expectedValue) {
		ConfigurableParser parser = new DefaultExpressionParser();
		Expression parsedExpression = parser.parseExpression(expression);
		Value value = context.evaluate(parsedExpression);
		Assert.assertEquals(value, expectedValue);
	}

}
