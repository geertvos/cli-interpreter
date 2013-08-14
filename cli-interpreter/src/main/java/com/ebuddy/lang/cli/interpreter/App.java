package com.ebuddy.lang.cli.interpreter;

import com.ebuddy.pingserver.cli.interpreter.evaluator.ExpressionContext;
import com.ebuddy.pingserver.cli.interpreter.evaluator.Value;
import com.ebuddy.pingserver.cli.interpreter.language.Expression;
import com.ebuddy.pingserver.cli.interpreter.parser.ConfigurableParser;
import com.ebuddy.pingserver.cli.interpreter.parser.DefaultExpressionParser;
import com.ebuddy.pingserver.cli.interpreter.parser.ParsedCommand;

public class App 
{
    public static void main( String[] args )
    {
        ExpressionContext context = new ExpressionContext();
    	ConfigurableParser parser = new DefaultExpressionParser();
        ParsedCommand command = parser.parse("send-message \"hi\" 1+2*7 1 != true");
        System.out.print(command.getName()+" ");
        for(Expression expression : command.getArguments()) {
        	Value value = context.evaluate(expression);
        	System.out.print(value.getStringValue()+" ");
        }
    }
}
