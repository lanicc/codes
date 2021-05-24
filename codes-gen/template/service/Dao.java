package com.souche.component.${p.name}.service.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.souche.component.${p.name}.service.model.${model.name}DO;

import java.util.List;

/**
 * ${model.desc}(${model.tableName})数据库访问层
 *
 * @author ${model.author}
 * @since ${p.version}
 */
public interface ${model.name}Dao extends BaseMapper<${model.name}DO> {

}
