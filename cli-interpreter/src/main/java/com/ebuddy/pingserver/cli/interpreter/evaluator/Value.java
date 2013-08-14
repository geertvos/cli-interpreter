package com.ebuddy.pingserver.cli.interpreter.evaluator;
import java.util.List;
import java.util.Map;

public class Value {

	private Map<String,Value> values;
	private List<Value> valuesList;
	private String value;
	private ValueType type;

	public Value(Map<String,Value> values) {
		this.values = values;
		this.type = ValueType.Object;
	}

	public Value(List<Value> list) {
		this.valuesList = list;
		this.type = ValueType.Array;
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
	
	public boolean isArray() {
		return this.type.equals(ValueType.Array);
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

	public Value get(int position) {
		return valuesList.get(0);
	}
	
	public ValueType getType() {
		return type;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		result = prime * result + ((values == null) ? 0 : values.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Value other = (Value) obj;
		if (type != other.type)
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		if (values == null) {
			if (other.values != null)
				return false;
		} else if (!values.equals(other.values))
			return false;
		return true;
	}

	public String toString() {
		if(type == ValueType.Object) {
			return values.toString();
		} else {
			return value+"("+type.name()+")";
		}
	}
	
}
