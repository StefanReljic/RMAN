package meta.model;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

public class MetaRow implements Serializable {

	private static final long serialVersionUID = -1294388804459581350L;

	/**
	 * String is name of the property, MetaItem is representation of property type.
	 */
	private Map<String, MetaProperty> items;

	public MetaRow() {
		this.items = new LinkedHashMap<String, MetaProperty>();
	}

	public MetaRow(Map<String, MetaProperty> items) {
		this.items = items;
	}

	public Map<String, MetaProperty> getItems() {
		return items;
	}

	public void setItems(Map<String, MetaProperty> items) {
		this.items = items;
	}

}