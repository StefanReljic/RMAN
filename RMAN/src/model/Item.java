package model;

public class Item {

	String type;
	Class classType;
	Object value;

	public Item(String type, Object value) {
		super();

		this.type = type;
		this.value = value;
		
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Class getClassType() {
		return classType;
	}

	public void setClassType(Class classType) {
		this.classType = classType;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

}
