package org.lanicc.codes.util;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Created on 2021/5/21.
 *
 * @author lan
 * @since 1.0
 */
public class JdbcTypes {

    static Map<String, JavaType> javaTypeMap = new HashMap<>();

    static {
        register("varchar", String.class, false);
        register("json", String.class, false);
        register("int", Integer.class, false);
        register("bigint", Long.class, false);
        register("datetime", LocalDateTime.class, true);
        register("timestamp", LocalDateTime.class, true);
    }

    public static JavaType of(String dbType) {
        return javaTypeMap.getOrDefault(dbType, JavaType.OBJECT);
    }

    private static void register(String dbType, Class<?> clazz, boolean needImport) {
        javaTypeMap.put(dbType, new JavaType(clazz.getSimpleName(), clazz.getPackage().getName(), needImport));
    }
}
