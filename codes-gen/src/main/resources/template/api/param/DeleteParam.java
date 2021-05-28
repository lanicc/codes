package com.souche.component.core.${p.name}.api.model.param;

import lombok.Data;

import java.io.Serializable;
<#list modelImports as mip>
import ${mip.packageName}.${mip.name};
</#list>

/**
 * ${model.desc}删除参数
 *
 * @author ${model.author}
 * @since ${p.version}
 */
@Data
public class ${model.name}DeleteParam implements Serializable {
<#list model.fields as field>
<#if field.primary>

    /**
     * ${field.desc}
     */
    ${field.scope} ${field.type} ${field.name};
</#if>
</#list>

    /**
     * 操作者
     */
    private String modifier;
}
