package com.souche.component.${p.name}.api;

import com.souche.component.${p.name}.api.model.${model.name}DTO;
import com.souche.component.${p.name}.api.model.param.${model.name}AddParam;
import com.souche.component.${p.name}.api.model.param.${model.name}UpdateParam;
import com.souche.component.${p.name}.api.model.param.${model.name}DeleteParam;
import com.souche.component.sharing.common.query.QueryParam;

import java.util.List;

/**
 * Created on ${model.createTime}.
 *
 * @author ${model.author}
 * @since ${p.version}
 */
public interface ${model.name}Service {


    /**
     * 创建${model.desc}
     *
     * @param addDTO 新增${model.desc}信息
     * @return 数据库${model.desc}记录
     * @see ${model.name}AddParam
     * @see ${model.name}DTO
     */
    ${model.name}DTO add(${model.name}AddParam addDTO);

    /**
     * 根据主键修改${model.desc}
     *
     * @param updateDTO 待修改${model.desc}信息
     * @return 数据库${model.desc}记录
     * @see ${model.name}UpdateParam
     * @see ${model.name}DTO
     */
    ${model.name}DTO updateByPrimaryKey(${model.name}UpdateParam updateDTO);

    /**
     * 根据条件查询单个${model.desc}
     *
     * @param param 查询条件
     * @return 数据库${model.desc}记录
     * @see QueryParam
     */
    ${model.name}DTO findOne(QueryParam param);

    /**
     * 根据条件查询多个${model.desc}
     *
     * @param param 查询条件
     * @return 数据库${model.desc}记录
     * @see QueryParam
     */
    List<${model.name}DTO> findList(QueryParam param);

    /**
     * 根据用户主键删除${model.desc}
     * 删除失败会抛出异常报错
     *
     * @param param 待删除的信息
     */
    void deleteByPrimaryKey(${model.name}DeleteParam param);
}
