package meta.model;

import java.io.Serializable;
import java.util.Map;

public class MetaRelation implements Serializable {

	private static final long serialVersionUID = -3326846988069406450L;
	
	private String parrentTable;
	private Map<String, MetaProperty> parrentIds;
	private String childTable;
	private Map<String, MetaProperty> childIds;

	public MetaRelation() {
		super();
	}

	public MetaRelation(String parrentTable, Map<String, MetaProperty> parrentIds, String childTable,
			Map<String, MetaProperty> childIds) {
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

	public Map<String, MetaProperty> getParrentIds() {
		return parrentIds;
	}

	public void setParrentIds(Map<String, MetaProperty> parrentIds) {
		this.parrentIds = parrentIds;
	}

	public String getChildTable() {
		return childTable;
	}

	public void setChildTable(String childTable) {
		this.childTable = childTable;
	}

	public Map<String, MetaProperty> getChildIds() {
		return childIds;
	}

	public void setChildIds(Map<String, MetaProperty> childIds) {
		this.childIds = childIds;
	}

}
