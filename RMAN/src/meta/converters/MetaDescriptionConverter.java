package meta.converters;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;

import meta.model.MetaDescription;
import meta.model.MetaEntity;
import meta.model.MetaInfo;
import meta.model.MetaProperty;
import meta.model.MetaRelation;
import meta.model.MetaRow;

public class MetaDescriptionConverter {

	/**
	 * Returns JSON representation of MetaDescription object.
	 * 
	 * @param MetaDescription
	 * @return JSONObject
	 */
	public JSONObject metaDescriptionToString(MetaDescription metaDescription) {

		JSONObject result = new JSONObject();
		JSONObject metaInfo = new JSONObject();
		JSONArray metaEntities = new JSONArray();

		MetaInfo metaInfoObject = metaDescription.getMetaInfo();

		metaInfo.put("user", metaInfoObject.getUser());
		metaInfo.put("password", metaInfoObject.getPassword());
		metaInfo.put("host", metaInfoObject.getHost());
		metaInfo.put("port", metaInfoObject.getPort());
		metaInfo.put("resourceId", metaInfoObject.getResourceId());
		metaInfo.put("fullResourcePath", metaInfoObject.getFullResourcePath());

		List<MetaEntity> metaEntityObjects = metaDescription.getMetaEntities();
		for (MetaEntity entity : metaEntityObjects) {

			JSONObject metaEntity = new JSONObject();
			JSONArray metaRow = new JSONArray();
			JSONArray metaRelations = new JSONArray();

			/**
			 * Converts meta row and its meta properties.
			 */
			MetaRow metaRowObject = entity.getMetaRow();
			HashMap<String, MetaProperty> metaRowItems = metaRowObject.getItems();
			List<String> keys = metaRowItems.keySet().stream().collect(Collectors.toList());
			for (String key : keys) {

				JSONObject metaProperty = new JSONObject();
				MetaProperty metaPropertyObject = metaRowItems.get(key);

				metaProperty.put("propertyName", key);
				metaProperty.put("type", metaPropertyObject.getType());
				metaProperty.put("classType", metaPropertyObject.getClass().getName());

				metaRow.put(metaPropertyObject);
			}

			/**
			 * Converts meta relations for current entity.
			 */
			List<MetaRelation> metaRelationObjects = entity.getRelations();
			for (MetaRelation relation : metaRelationObjects) {

				JSONObject metaRelation = new JSONObject();
				JSONArray metaParrentIds = new JSONArray();
				JSONArray metaChildIds = new JSONArray();

				HashMap<String, MetaProperty> parrentIds = relation.getParrentIds();
				keys = parrentIds.keySet().stream().collect(Collectors.toList());
				for (String key : keys) {

					JSONObject metaProperty = new JSONObject();
					MetaProperty metaPropertyObject = parrentIds.get(key);

					metaProperty.put("propertyName", key);
					metaProperty.put("type", metaPropertyObject.getType());
					metaProperty.put("classType", metaPropertyObject.getClass().getName());

					metaParrentIds.put(metaPropertyObject);
				}

				HashMap<String, MetaProperty> childIds = relation.getChildIds();
				keys = childIds.keySet().stream().collect(Collectors.toList());
				for (String key : keys) {

					JSONObject metaProperty = new JSONObject();
					MetaProperty metaPropertyObject = childIds.get(key);

					metaProperty.put("propertyName", key);
					metaProperty.put("type", metaPropertyObject.getType());
					metaProperty.put("classType", metaPropertyObject.getClass().getName());

					metaChildIds.put(metaPropertyObject);
				}

				metaRelation.put("parrentTable", relation.getParrentTable());
				metaRelation.put("childTable", relation.getChildTable());
				metaRelation.put("parrentIds", metaParrentIds);
				metaRelation.put("childIds", metaChildIds);

				metaRelations.put(metaRelation);
			}

			metaEntity.put("entityName", entity.getEntityName());
			metaEntity.put("metaRow", metaRow);
			metaEntity.put("metaRelations", metaRelations);

			metaEntities.put(metaEntity);
		}

		result.put("metaInfo", metaInfo);
		result.put("metaEntities", metaEntities);

		return result;
	}

	/**
	 * Returns MetaDescriptionObject based on file JSON content.
	 * 
	 * @param File
	 * @return MetaDescription
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public MetaDescription fileToMetaDescription(File file) throws IOException, ClassNotFoundException {

		MetaDescription result = new MetaDescription();

		byte[] bytes = new byte[(int) file.length()];
		InputStream is = new FileInputStream(file);
		is.read(bytes, 0, (int) file.length());
		is.close();
		String jsonContent = new String(bytes);

		JSONObject metaDescription = new JSONObject(jsonContent);

		JSONObject metaInfo = metaDescription.getJSONObject("metaInfo");
		MetaInfo metaInfoObject = new MetaInfo();
		metaInfoObject.setUser(metaInfo.getString("user"));
		metaInfoObject.setPassword(metaInfo.getString("password"));
		metaInfoObject.setHost(metaInfo.getString("host"));
		metaInfoObject.setPort(metaInfo.getInt("port"));
		metaInfoObject.setResourceId(metaInfo.getString("resourceId"));
		metaInfoObject.setFullResourcePath(metaInfo.getString("fullResourcePath"));

		JSONArray metaEntities = metaDescription.getJSONArray("metaEntities");
		List<MetaEntity> metaEntityObjects = new LinkedList<MetaEntity>();
		for (int i = 0; i < metaEntities.length(); ++i) {

			JSONObject entity = metaEntities.getJSONObject(i);
			JSONArray metaRow = entity.getJSONArray("metaRow");
			JSONArray metaRelations = entity.getJSONArray("metaRelations");

			MetaEntity metaEntityObject = new MetaEntity();

			MetaRow metaRowObject = new MetaRow();
			for (int j = 0; j < metaRow.length(); ++j) {

				JSONObject metaProperty = metaRow.getJSONObject(j);
				MetaProperty metaPropertyObject = new MetaProperty();

				metaPropertyObject.setType(metaProperty.getString("type"));

				// critical part of code
				metaPropertyObject.setClassType(Class.forName(metaProperty.getString("classType")));

				metaRowObject.getItems().put(metaProperty.getString("propertyName"), metaPropertyObject);
			}

			List<MetaRelation> metaRelationObjects = new LinkedList<MetaRelation>();
			for (int j = 0; j < metaRelations.length(); ++j) {

				JSONObject metaRelation = metaRelations.getJSONObject(j);
				JSONArray parrentIds = metaRelation.getJSONArray("parrentIds");
				JSONArray childIds = metaRelation.getJSONArray("childIds");

				MetaRelation metaRelationObject = new MetaRelation();
				HashMap<String, MetaProperty> parrentIdObjects = new HashMap<String, MetaProperty>();
				HashMap<String, MetaProperty> childIdObjects = new HashMap<String, MetaProperty>();

				for (int k = 0; k < parrentIds.length(); ++k) {

					JSONObject metaProperty = parrentIds.getJSONObject(k);
					MetaProperty metaPropertyObject = new MetaProperty();
					metaPropertyObject.setType(metaProperty.getString("type"));
					metaPropertyObject.setClassType(Class.forName(metaProperty.getString("classType")));

					parrentIdObjects.put(metaProperty.getString("propertyName"), metaPropertyObject);
				}

				for (int k = 0; k < childIds.length(); ++k) {

					JSONObject metaProperty = childIds.getJSONObject(k);
					MetaProperty metaPropertyObject = new MetaProperty();
					metaPropertyObject.setType(metaProperty.getString("type"));
					metaPropertyObject.setClassType(Class.forName(metaProperty.getString("classType")));

					childIdObjects.put(metaProperty.getString("propertyName"), metaPropertyObject);
				}

				metaRelationObject.setParrentTable(metaRelation.getString("parrentTable"));
				metaRelationObject.setChildTable(metaRelation.getString("childTable"));
				metaRelationObject.setParrentIds(parrentIdObjects);
				metaRelationObject.setChildIds(childIdObjects);

				metaRelationObjects.add(metaRelationObject);
			}

			metaEntityObject.setEntityName(entity.getString("entityName"));
			metaEntityObject.setMetaRow(metaRowObject);
			metaEntityObject.setRelations(metaRelationObjects);

			metaEntityObjects.add(metaEntityObject);
		}

		result.setMetaInfo(metaInfoObject);
		result.setMetaEntities(metaEntityObjects);

		return result;
	}
}