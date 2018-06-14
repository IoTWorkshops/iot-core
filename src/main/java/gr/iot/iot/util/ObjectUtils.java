package gr.iot.iot.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by smyrgeorge on 4/12/17.
 */
public class ObjectUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(ObjectUtils.class);

    private ObjectUtils() {

    }

    public static Field getField(Class<?> objectClass, String fieldName) {
        try {
            return objectClass.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            return null;
        }
    }

    public static String getFieldValue(Object object, String propertyName) throws NoSuchFieldException, IllegalAccessException {
        Field field = object.getClass().getDeclaredField(propertyName);
        field.setAccessible(true);
        return field.get(object).toString();
    }

    public static List<Field> getObjectFields(Class<?> objectClass) {
        List<Field> fields = new ArrayList<>();
        Collections.addAll(fields, objectClass.getDeclaredFields());
        return fields;
    }

    public static boolean objectContainsField(Class<?> objectClass, String fieldName) {
        try {
            objectClass.getField(fieldName);
            return true;
        } catch (NoSuchFieldException e) {
            return false;
        }
    }

    public static <T> T objectCopy(T object) {

        try {
            Class<?> objectClass = object.getClass();
            T newObject = (T) object.getClass().newInstance();

            while (objectClass != null) {
                copyFields(object, newObject, objectClass);
                objectClass = objectClass.getSuperclass();
            }

            return newObject;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
    }

    private static <T> T copyFields(T object, T newObject, Class<?> objectClass) throws IllegalAccessException {
        List<Field> fields = new ArrayList<>();
        Collections.addAll(fields, objectClass.getDeclaredFields());

        for (Field field : fields) {
            field.setAccessible(true);
            field.set(newObject, field.get(object));
        }

        return newObject;
    }

    public static <T1, T2> T2 copySameFields(T1 src, T2 dst) throws IllegalAccessException {
        List<Field> fields = new ArrayList<>();
        Collections.addAll(fields, dst.getClass().getDeclaredFields());

        for (Field dstField : fields) {
            dstField.setAccessible(true);
            Field srcField = getField(src.getClass(), dstField.getName());
            if (srcField != null) {
                srcField.setAccessible(true);
                dstField.set(dst, srcField.get(src));
            }
        }

        return dst;
    }
}
