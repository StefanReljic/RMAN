package meta.model;

import java.io.Serializable;

public class MetaProperty implements Serializable {

	private String type;
	private Class classType;

	public MetaProperty() {
		super();
	}

	public MetaProperty(String type, Class classType) {

		this.type = type;
		this.classType = classType;
	}

	/**
	 * Returns class name of this type.
	 */
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

}
