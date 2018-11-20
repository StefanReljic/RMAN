package meta.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class MetaEntity implements Serializable {

	private String entityName;
	private MetaRow metaRow;
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

	public List<MetaRelation> getRelations() {
		return relations;
	}

	public void setRelations(List<MetaRelation> relations) {
		this.relations = relations;
	}

}
