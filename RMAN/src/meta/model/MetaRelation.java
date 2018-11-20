package meta.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class MetaRelation implements Serializable {

	String parrentTable;
	HashMap<String, MetaProperty> parrentIds;
	String childTable;
	HashMap<String, MetaProperty> childIds;

	public MetaRelation() {
		super();
	}

	public MetaRelation(String parrentTable, HashMap<String, MetaProperty> parrentIds, String childTable,
			HashMap<String, MetaProperty> childIds) {
		super();
		this.parrentTable = parrentTable;
		this.parrentIds = parrentIds;
		this.childTable = childTable;
		this.childIds = childIds;
	}

	public String getParrentTable() {
		return parrentTable;
	}

	public void setParrentTable(String parrentTable) {
		this.parrentTable = parrentTable;
	}

	public HashMap<String, MetaProperty> getParrentIds() {
		return parrentIds;
	}

	public void setParrentIds(HashMap<String, MetaProperty> parrentIds) {
		this.parrentIds = parrentIds;
	}

	public String getChildTable() {
		return childTable;
	}

	public void setChildTable(String childTable) {
		this.childTable = childTable;
	}

	public HashMap<String, MetaProperty> getChildIds() {
		return childIds;
	}

	public void setChildIds(HashMap<String, MetaProperty> childIds) {
		this.childIds = childIds;
	}

}
