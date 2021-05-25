package org.lanicc.codes.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * Created on 2021/5/21.
 *
 * @author lan
 * @since 1.0
 */
@Data
@Accessors(chain = true)
public class Model {

    private String name;

    private String firstLetterLowerCaseName;

    private String upperCaseName;

    private String tableName;

    private String author;

    private String createTime;

    private String desc;

    private List<Field> fields;
}
