package meta.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class MetaDescription implements Serializable {

	private static final long serialVersionUID = -3177385009031570330L;
	
	private MetaInfo metaInfo;
	private List<MetaEntity> metaEntities;

	public MetaDescription() {

		this.metaInfo = new MetaInfo();
		this.metaEntities = new LinkedList<MetaEntity>();
	}

	public MetaDescription(MetaInfo metaInfo, List<MetaEntity> metaEntities) {

		this.metaInfo = metaInfo;
		this.metaEntities = metaEntities;
	}

	public MetaInfo getMetaInfo() {
		return metaInfo;
	}

	public void setMetaInfo(MetaInfo metaInfo) {
		this.metaInfo = metaInfo;
	}

	public List<MetaEntity> getMetaEntities() {
		return metaEntities;
	}

	public void setMetaEntities(List<MetaEntity> metaEntities) {
		this.metaEntities = metaEntities;
	}

}
