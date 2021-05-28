package org.lanicc.codes.util;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * Created on 2021/5/21.
 *
 * @author lan
 * @since 1.0
 */
@Data
@Accessors(chain = true)
public class JavaType {

    private String name;

    private String declareName;

    private String packageName;

    private boolean needImport;

    public JavaType(String name, String declareName, String packageName, boolean needImport) {
        this.name = name;
        this.declareName = declareName;
        this.packageName = packageName;
        this.needImport = needImport;
    }

    public JavaType(String name, String packageName, boolean needImport) {
        this.name = name;
        this.packageName = packageName;
        this.needImport = needImport;
    }

    public JavaType(String name, boolean needImport) {
        this.name = name;
        this.declareName = name;
        this.needImport = needImport;
    }

    public static JavaType OBJECT = new JavaType(Object.class.getSimpleName(), false);

    public static JavaType LOCAL_DATE_TIME = new JavaType(LocalDateTime.class.getSimpleName(), LocalDateTime.class.getPackage().getName(), true);
}
