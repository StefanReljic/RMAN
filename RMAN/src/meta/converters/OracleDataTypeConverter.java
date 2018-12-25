package meta.converters;

import meta.model.types.OracleDataType;

public class OracleDataTypeConverter {

	public static Class<?> getType(OracleDataType dataType) {

		if (dataType == null)
			return null;

		switch (dataType) {
		case BFILE:
			return oracle.sql.BFILE.class;

		case BINARY_DOUBLE:
			return oracle.sql.BINARY_DOUBLE.class;

		case BINARY_FLOAT:
			return oracle.sql.BINARY_FLOAT.class;

		case BLOB:
			return java.sql.Blob.class;

		case CHAR:
			return String.class;

		case CHAR_VARYING:
			return String.class;

		case CHARACTER:
			return String.class;

		case CHARACTER_VARYING:
			return String.class;

		case CLOB:
			return String.class;

		case DATE:
			return java.sql.Timestamp.class;

		case DEC:
			return java.math.BigDecimal.class;

		case DECIMAL:
			return java.math.BigDecimal.class;

		case DOUBLE_PRECISION:
			return Double.class;

		case FLOAT:
			return Double.class;

		case INT:
			return Integer.class;

		case INTEGER:
			return Integer.class;

		case INTERVAL_DAY:
			return String.class;

		case INTERVAL_YEAR:
			return String.class;

		case LONG:
			return String.class;

		case LONG_RAW:
			return Byte[].class;

		case LONG_VARCHAR:
			return String.class;

		case NATIONAL_CHAR:
			return String.class;

		case NATIONAL_CHAR_VARYING:
			return String.class;

		case NATIONAL_CHARACTER:
			return String.class;

		case NATIONAL_CHARACTER_VARYING:
			return String.class;

		case NCHAR:
			return String.class;

		case NCHAR_VARYING:
			return String.class;

		case NCLOB:
			return oracle.sql.NCLOB.class;

		case NUMBER:
			return java.math.BigDecimal.class;

		case NUMERIC:
			return java.math.BigDecimal.class;

		case NVARCHAR2:
			return String.class;

		case RAW:
			return Byte[].class;

		case REAL:
			return Float.class;

		case ROWID:
			return oracle.sql.ROWID.class;

		case SMALL_INT:
			return Integer.class;

		case TIMESTAMP:
			return java.sql.Timestamp.class;

		case UROWID:
			return oracle.sql.ROWID.class;

		case VARCHAR:
			return String.class;

		case VARCHAR2:
			return String.class;

		default:
			return String.class;
		}

	}

	public static Class<?> getTypeFromString(String dataType) {

		if (dataType == null)
			return null;

		if (dataType.startsWith("BFILE"))
			return oracle.sql.BFILE.class;

		if (dataType.startsWith("BINARY_DOUBLE"))
			return oracle.sql.BINARY_DOUBLE.class;

		if (dataType.startsWith("BINARY_FLOAT"))
			return oracle.sql.BINARY_FLOAT.class;

		if (dataType.startsWith("BLOB"))
			return java.sql.Blob.class;

		if (dataType.startsWith("CHAR"))
			return String.class;

		if (dataType.startsWith("CHAR_VARYING"))
			return String.class;

		if (dataType.startsWith("CHARACTER"))
			return String.class;

		if (dataType.startsWith("CHARACTER_VARYING"))
			return String.class;

		if (dataType.startsWith("CLOB"))
			return String.class;

		if (dataType.startsWith("DATE"))
			return java.sql.Timestamp.class;

		if (dataType.startsWith("DEC"))
			return java.math.BigDecimal.class;

		if (dataType.startsWith("DECIMAL"))
			return java.math.BigDecimal.class;

		if (dataType.startsWith("DOUBLE_PRECISION"))
			return Double.class;

		if (dataType.startsWith("FLOAT"))
			return Double.class;

		if (dataType.startsWith("INT"))
			return Integer.class;

		if (dataType.startsWith("INTEGER"))
			return Integer.class;

		if (dataType.startsWith("INTERVAL_DAY"))
			return String.class;

		if (dataType.startsWith("INTERVAL_YEAR"))
			return String.class;

		if (dataType.startsWith("LONG"))
			return String.class;

		if (dataType.startsWith("LONG_RAW"))
			return Byte[].class;

		if (dataType.startsWith("LONG_VARCHAR"))
			return String.class;

		if (dataType.startsWith("NATIONAL_CHAR"))
			return String.class;

		if (dataType.startsWith("NATIONAL_CHAR_VARYING"))
			return String.class;

		if (dataType.startsWith("NATIONAL_CHARACTER"))
			return String.class;

		if (dataType.startsWith("NATIONAL_CHARACTER_VARYING"))
			return String.class;

		if (dataType.startsWith("NCHAR"))
			return String.class;

		if (dataType.startsWith("NCHAR_VARYING"))
			return String.class;

		if (dataType.startsWith("NCLOB"))
			return oracle.sql.NCLOB.class;

		if (dataType.startsWith("NUMBER"))
			return java.math.BigDecimal.class;

		if (dataType.startsWith("NUMERIC"))
			return java.math.BigDecimal.class;

		if (dataType.startsWith("NVARCHAR2"))
			return String.class;

		if (dataType.startsWith("RAW"))
			return Byte[].class;

		if (dataType.startsWith("REAL"))
			return Float.class;

		if (dataType.startsWith("ROWID"))
			return oracle.sql.ROWID.class;

		if (dataType.startsWith("SMALL_INT"))
			return Integer.class;

		if (dataType.startsWith("TIMESTAMP"))
			return java.sql.Timestamp.class;

		if (dataType.startsWith("UROWID"))
			return oracle.sql.ROWID.class;

		if (dataType.startsWith("VARCHAR"))
			return String.class;

		if (dataType.startsWith("VARCHAR2"))
			return String.class;

		return String.class;

	}
}
