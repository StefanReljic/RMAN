package model;

import java.util.HashMap;

public class Row {

	private String tableName;
	private HashMap<String, Item> items;

	public Row() {
		this.items = new HashMap<>();
	}

	public Row(String tableName, HashMap<String, Item> items) {
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

	public HashMap<String, Item> getItems() {
		return items;
	}

	public void setItems(HashMap<String, Item> items) {
		this.items = items;
	}

}
