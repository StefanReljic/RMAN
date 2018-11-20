package meta.model;

import java.io.Serializable;
import java.util.HashMap;

public class MetaRow implements Serializable {

	/**
	 * String is name of the property, MetaItem is representation of property type.
	 */
	private HashMap<String, MetaProperty> items;

	public MetaRow() {
		this.items = new HashMap<String, MetaProperty>();
	}

	public MetaRow(HashMap<String, MetaProperty> items) {
		this.items = items;
	}

	public HashMap<String, MetaProperty> getItems() {
		return items;
	}

	public void setItems(HashMap<String, MetaProperty> items) {
		this.items = items;
	}

}
