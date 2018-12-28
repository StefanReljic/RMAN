package meta.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MetaEntity implements Serializable {

	private static final long serialVersionUID = -918748377884983891L;

	private String entityName;
	private MetaRow metaRow;
	private Map<String, MetaProperty> metaIds;
	private List<MetaRelation> relations;

	public MetaEntity() {

		this.entityName = "";
		this.metaRow = new MetaRow();
		this.relations = new LinkedList<MetaRelation>();
	}

	public MetaEntity(String entityName) {

		this.entityName = entityName;
		this.metaRow = new MetaRow();
		this.relations = new LinkedList<MetaRelation>();
	}

	public MetaEntity(String entityName, MetaRow metaRow) {

		this.entityName = entityName;
		this.metaRow = metaRow;
		this.relations = new LinkedList<MetaRelation>();
	}

	public MetaEntity(String entityName, MetaRow metaRow, List<MetaRelation> relations) {

		this.entityName = entityName;
		this.metaRow = metaRow;
		this.relations = relations;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public MetaRow getMetaRow() {
		return metaRow;
	}

	public void setMetaRow(MetaRow metaRow) {
		this.metaRow = metaRow;
	}

	public Map<String, MetaProperty> getMetaIds() {
		return metaIds;
	}

	public void setMetaIds(Map<String, MetaProperty> metaIds) {
		this.metaIds = metaIds;
	}

	public List<MetaRelation> getRelations() {
		return relations;
	}

	public void setRelations(List<MetaRelation> relations) {
		this.relations = relations;
	}

	@Override
	public String toString() {

		return getEntityName();
	}
}