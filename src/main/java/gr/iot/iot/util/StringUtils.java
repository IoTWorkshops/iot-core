package gr.iot.iot.util;

import com.google.common.base.CaseFormat;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.UUID;

/**
 * Created by smyrgeorge on 6/7/17.
 */
public class StringUtils {

    private StringUtils() {

    }

    public static String generateRandomString() {
        SecureRandom secureRandom = new SecureRandom();
        return new BigInteger(130, secureRandom).toString(32);
    }

    public static String generateUuid() {
        return UUID.randomUUID().toString();
    }

    public static boolean isBoolean(String value) {
        return value != null && Arrays.stream(new String[]{"true", "false", "1", "0"})
            .anyMatch(b -> b.equalsIgnoreCase(value));
    }

    public static String snakeCaseToCamelCase(String text) {
        return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, text);
    }

    public static String camelCaseToSnakeCase(String text) {
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, text);
    }
}
