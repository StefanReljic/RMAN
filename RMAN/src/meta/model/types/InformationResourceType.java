package meta.model.types;

import java.util.LinkedList;
import java.util.List;

public enum InformationResourceType {

	ORACLE;

	public static List<InformationResourceType> getInformationResourceTypes() {

		List<InformationResourceType> types = new LinkedList<InformationResourceType>();
		types.add(ORACLE);

		return types;
	}

	public static String getString(InformationResourceType type) {

		switch (type) {
		case ORACLE:
			return "ORACLE";
		}

		return "";
	}
}
