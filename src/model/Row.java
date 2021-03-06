package model;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Row {

	private String tableName;
	private Map<String, Item> items;

	public Row() {
		this.items = new LinkedHashMap<>();
	}

	public Row(String tableName, Map<String, Item> items) {
		super();
		this.tableName = tableName;
		this.items = items;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public Map<String, Item> getItems() {
		return items;
	}

	public void setItems(Map<String, Item> items) {
		this.items = items;
	}

	@Override
	public String toString() {

		String s = getTableName() + "\n";
		List<String> keys = getItems().keySet().stream().collect(Collectors.toList());
		for (String key : keys) {
			s += key + " = " + getItems().get(key).getValue() + "\n";
		}

		return s;
	}

	@Override
	public boolean equals(Object obj) {

		if (obj == null)
			return false;

		if (obj instanceof Row) {

			Row objRow = (Row) obj;

			if (!objRow.getTableName().equals(getTableName()))
				return false;

			List<String> objRowKeys = objRow.getItems().keySet().stream().filter(key -> key.toLowerCase().startsWith("id"))
					.collect(Collectors.toList());

			boolean result = true;

			if (objRowKeys.size() == 0) {

				List<String> keys = objRow.getItems().keySet().stream().collect(Collectors.toList());
				for (String key : keys) {
					if (((Item) objRow.getItems().get(key).getValue()).getValue().equals(((Item) getItems().get(key).getValue()).getValue())) {
						result = false;
						break;
					}
				}

				return result;
			}

			for (String key : objRowKeys) {
				if (!objRow.getItems().get(key).getValue().equals(getItems().get(key).getValue())) {
					result = false;
					break;
				}
			}
			return result;
		}

		return super.equals(obj);
	}

	public Item findItemByName(String name) {

		return getItems().get(name);
	}

	public Map<String, Class<?>> getColumnTypes() {

		Map<String, Class<?>> types = new LinkedHashMap<String, Class<?>>();
		List<String> keys = getItems().keySet().stream().collect(Collectors.toList());
		for (String key : keys)
			types.put(key, getItems().get(key).getClassType());

		return types;
	}
}