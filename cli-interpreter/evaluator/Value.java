package com.ebuddy.pingserver.cli.interpreter.evaluator;
import java.util.Map;


public class Value {

	private Map<String,Value> values;
	private String value;
	private ValueType type;

	public Value(Map<String,Value> values) {
		this.values = values;
		this.type = ValueType.Object;
	}

	public Value(boolean value) {
		this.value = value?"true":"false";
		this.type = ValueType.Boolean;
	}
	
	public Value(int value) {
		this.value = ""+value;
		this.type = ValueType.Number;
	}
	
	
	public Value(String value) {
		this.value = value;
		this.type = ValueType.String;
	}
	
	public String getStringValue() {
		return value;
	}
	
	public boolean getBooleanValue() {
		return value.equals("true");
	}
	
	public int getNumberValue() {
		return Integer.parseInt(value);
	}

	public Value getObjectValue(String identifier) {
		return values.get(identifier);
	}

	public boolean isBoolean() {
		return this.type.equals(ValueType.Boolean);
	}
	
	public boolean isString() {
		return this.type.equals(ValueType.String);
	}

	public boolean isObject() {
		return this.type.equals(ValueType.Object);
	}

	public boolean isNumber() {
		return this.type.equals(ValueType.Number);
	}

	public ValueType getType() {
		return type;
	}
	
	public String toString() {
		if(type == ValueType.Object) {
			return values.toString();
		} else {
			return value+"("+type.name()+")";
		}
	}
	
}
