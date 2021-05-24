package com.souche.component.${p.name}.api.model;

import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.io.Serializable;
<#list modelImports as mip>
import ${mip.packageName}.${mip.name};
</#list>

/**
 * ${model.desc}(${model.tableName})表实体类
 *
 * @author ${model.author}
 * @since ${p.version}
 */
@Data
@FieldNameConstants
public class ${model.name}DTO implements Serializable {

<#list model.fields as field>

    /**
     * ${field.desc}
     */
    ${field.scope} ${field.type} ${field.name};
</#list>

}