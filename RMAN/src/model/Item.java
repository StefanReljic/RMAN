package model;

import java.awt.Image;

public class Item {

	String type;
	Class classType;
	Object value;

	public Item(String type, Object value) {
		super();
		this.type = type;
		this.value = value;

		switch (type) {

		case "int":
			this.classType = Integer.class;
			break;

		case "float":
			this.classType = Float.class;
			break;

		case "double":
			this.classType = Double.class;
			break;

		case "char":
			this.classType = Character.class;
			break;

		case "string":
			this.classType = String.class;
			break;

		case "boolean":
			this.classType = Boolean.class;
			break;

		case "byte":
			this.classType = Byte.class;
			break;

		case "photo":
			this.classType = Image.class;
			break;

		case "audio":
			this.classType = Byte.class; // dodati klasu
			break;

		case "video":
			this.classType = Byte.class; // dodati klasu
			break;

		default:
			break;
		}

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
