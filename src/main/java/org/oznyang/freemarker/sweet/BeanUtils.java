package org.oznyang.freemarker.sweet;

import freemarker.log.Logger;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 反射获取字段值,
 * 支持a.b.c,a[2]这种写法，可以从map或者list中获取值
 * <p/>
 *
 * @author <a href="mailto:oxsean@gmail.com">sean yang</a>
 * @version V1.0, 10-12-21
 */
public class BeanUtils {

    private static final Pattern delimPattern = Pattern.compile("[\\[\\]\\.]");
    private static final Logger logger = Logger.getLogger(BeanUtils.class.getName());
    private static final Map<Class, Map<String, Field>> fieldCache = new ConcurrentHashMap<Class, Map<String, Field>>();

    @SuppressWarnings("unchecked")
    public static <T> T getValue(Object obj, String name) {
        return (T) getValue(obj, split(name));
    }

    private static String[] split(String str) {
        List<String> tokens = new ArrayList<String>();
        Matcher m = delimPattern.matcher(str);
        int index = 0;
        while (m.find()) {
            if (m.start() > index) {
                String match = str.substring(index, m.start());
                tokens.add(match);
                index = m.end();
            }
        }
        if (index < str.length() - 1)
            tokens.add(str.substring(index));
        return tokens.toArray(new String[tokens.size()]);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getValue(Object obj, String... names) {
        if (names != null) {
            for (String name : names) {
                Object value = getSimpleValue(obj, name);
                if (value != null) {
                } else if (obj instanceof Map) {
                    value = ((Map) obj).get(name);
                } else if (obj instanceof List) {
                    try {
                        value = ((List) obj).get(Integer.parseInt(name));
                    } catch (NumberFormatException ignored) {
                    }
                }
                if (value != null)
                    obj = value;
                else
                    return null;
            }
        }
        return (T) obj;
    }

    public static void setValue(Object obj, String name, Object value) {
        String[] tokens = split(name);
        int lastIndex = tokens.length - 1;
        obj = getValue(obj, Arrays.copyOf(tokens, lastIndex));
        setSimpleValue(obj, tokens[lastIndex], value);
    }


    public static void setSimpleValue(Object obj, String name, Object value) {
        try {
            Field field = getField(obj.getClass(), name);
            if (field != null)
                field.set(obj, value);
        } catch (Exception e) {
            logger.error("set field  [" + name + "]  to value [" + value + "] error", e);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T getSimpleValue(Object obj, String name) {
        try {
            Field field = getField(obj.getClass(), name);
            if (field != null)
                return (T) field.get(obj);
        } catch (Exception e) {
            logger.error("get field [" + name + "] value from [" + obj + "] error", e);
        }
        return null;
    }

    public static Field getField(Class clazz, String name) throws NoSuchFieldException {
        Map<String, Field> fieldMap = fieldCache.get(clazz);
        if (fieldMap == null) {
            fieldMap = new ConcurrentHashMap<String, Field>();
            fieldCache.put(clazz, fieldMap);
        }
        Field field = fieldMap.get(name);
        if (field == null) {
            while (clazz != null) {
                try {
                    field = clazz.getDeclaredField(name);
                    field.setAccessible(true);
                    fieldMap.put(name, field);
                    break;
                } catch (NoSuchFieldException ignored) {
                }
                clazz = clazz.getSuperclass();
            }
        }
        return field;
    }
}
