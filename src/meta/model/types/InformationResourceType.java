<<<<<<< HEAD:RMAN/src/meta/model/types/InformationResourceType.java
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
=======
package meta.model.types;

public enum InformationResourceType {

	ORACLE, CSV;
}
>>>>>>> 78340d376cff8fb635393e55868dec34a1bf4fe2:src/meta/model/types/InformationResourceType.java
