package org.lanicc.codes.model;

import lombok.Data;
import lombok.experimental.Accessors;
import org.lanicc.codes.util.JavaType;

/**
 * Created on 2021/5/20.
 *
 * @author lan
 * @since 1.0
 */
@Data
@Accessors(chain = true)
public class Field {

    private boolean primary;

    private String scope;

    private String type;

    private JavaType javaType;

    private String name;

    private String desc;

    private boolean showOnAdd;

    private boolean showOnUpdate;

}
