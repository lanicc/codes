package com.souche.component.${p.name}.service.model;

import com.souche.component.sharing.common.model.BaseModel;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
@TableName("${model.tableName}")
@EqualsAndHashCode(callSuper = true)
public class ${model.name}DO extends BaseModel {
<#list model.fields as field>
    /**
     * ${field.desc}
     */
<#if field.primary>
    @TableId
</#if>
    ${field.scope} ${field.type} ${field.name};
</#list>

}
