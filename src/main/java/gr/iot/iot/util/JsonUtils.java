package gr.iot.iot.util;

import org.postgresql.util.PGobject;

import java.util.List;
import java.util.Map;

/**
 * Created on 9/30/16.
 */
public class JsonUtils {

    private JsonUtils() {

    }

    public static String jdbcJsonRowsToJsonArrayString(List<Map<String, Object>> rows) {
        StringBuilder stringBuilder = new StringBuilder(2048);
        stringBuilder.append('[');
        for (int i = 0; i < rows.size(); i++) {
            Map<String, Object> row = rows.get(i);
            for (String property : row.keySet()) {
                stringBuilder.append(((PGobject) row.get(property)).getValue());
                if (i != rows.size() - 1) {
                    stringBuilder.append(',');
                }
            }
        }
        stringBuilder.append(']');
        return stringBuilder.toString();
    }
}
