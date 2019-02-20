package meta.model;

import java.util.List;

public class ChildParrentModel {

	private MetaEntity child;
	private List<MetaEntity> parrents;

	public ChildParrentModel(MetaEntity child, List<MetaEntity> parrents) {

		this.child = child;
		this.parrents = parrents;
	}

	public MetaEntity getChild() {
		return child;
	}

	public void setChild(MetaEntity child) {
		this.child = child;
	}

	public List<MetaEntity> getParrents() {
		return parrents;
	}

	public void setParrents(List<MetaEntity> parrents) {
		this.parrents = parrents;
	}

	@Override
	public String toString() {

		return child.getEntityName();
	}
}