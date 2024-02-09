//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package liquibase.datatype.core;

import java.util.Locale;
import liquibase.change.core.LoadDataChange.LOAD_DATA_TYPE;
import liquibase.database.Database;
import liquibase.database.core.FirebirdDatabase;
import liquibase.database.core.OracleDatabase;
import liquibase.database.core.PostgresDatabase;
import liquibase.datatype.DataTypeInfo;
import liquibase.datatype.DatabaseDataType;
import liquibase.datatype.LiquibaseDataType;
import liquibase.util.StringUtil;

@DataTypeInfo(
        name = "bytea",
        aliases = {"longvarbinary", "java.sql.Types.BYTEA", "java.sql.Types.LONGVARBINARY", "java.sql.Types.VARBINARY", "java.sql.Types.BINARY", "varbinary", "binary", "image", "long binary", "long varbinary"},
        minParameters = 0,
        maxParameters = 1,
        priority = 1
)
public class ByteaType extends LiquibaseDataType {
    public ByteaType() {
    }

    public DatabaseDataType toDatabaseDataType(Database database) {
        String originalDefinition = StringUtil.trimToEmpty(this.getRawDefinition());
        if (database instanceof PostgresDatabase) {
            return new DatabaseDataType("BYTEA");
        } else if (database instanceof OracleDatabase) {
            if (originalDefinition.toLowerCase(Locale.US).startsWith("bfile")) {
                return new DatabaseDataType("BFILE");
            } else {
                return !originalDefinition.toLowerCase(Locale.US).startsWith("raw") && !originalDefinition.toLowerCase(Locale.US).startsWith("binary") && !originalDefinition.toLowerCase(Locale.US).startsWith("varbinary") ? new DatabaseDataType("BLOB") : new DatabaseDataType("RAW", this.getParameters());
            }
        } else {
            return database instanceof FirebirdDatabase ? new DatabaseDataType("BLOB") : super.toDatabaseDataType(database);
        }
    }

    public String objectToSql(Object value, Database database) {
        if (value == null) {
            return null;
        } else {
            return value instanceof String ? "'" + value + "'" : value.toString();
        }
    }

    public LOAD_DATA_TYPE getLoadTypeName() {
        return LOAD_DATA_TYPE.BYTEA;
    }
}
