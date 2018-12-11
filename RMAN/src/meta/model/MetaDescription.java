package meta.model;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
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

	public MetaEntity findMetaEntityByName(String entityName) {

		if (entityName == null)
			return null;

		for (MetaEntity metaEntity : metaEntities)
			if (metaEntity.getEntityName().equals(entityName))
				return metaEntity;

		return null;
	}

	public static MetaDescription deserialize(byte[] bytes) {

		if (bytes == null)
			return null;
		ObjectInputStream ois;
		try {
			ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
			ois = new ObjectInputStream(bis);
			Object object = ois.readObject();
			ois.close();
			bis.close();

			return (MetaDescription) object;

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}
