package com.souche.component.${p.name}.api.model.param;

import lombok.Data;

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
public class ${model.name}UpdateParam implements Serializable {
<#list model.fields as field>
<#if field.showOnUpdate>
    /**
     * ${field.desc}
     */
    ${field.scope} ${field.type} ${field.name};
    </#if>
</#list>
}
