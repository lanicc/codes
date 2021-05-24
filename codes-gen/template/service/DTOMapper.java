package com.souche.component.${p.name}.service.mapper;

import com.souche.component.common.mapper.BaseDTOMapper;
import com.souche.component.${p.name}.api.model.${model.name}DTO;
import com.souche.component.${p.name}.api.model.param.${model.name}AddParam;
import com.souche.component.${p.name}.api.model.param.${model.name}UpdateParam;
import com.souche.component.${p.name}.service.model.${model.name}DO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Created on ${model.createTime}.
 *
 * @author ${model.author}
 * @since ${p.version}
 */
@Mapper
public interface ${model.name}DTOMapper extends BaseDTOMapper<${model.name}DO, ${model.name}DTO, ${model.name}AddParam, ${model.name}UpdateParam> {

        ${model.name}DTOMapper INSTANCE = Mappers.getMapper(${model.name}DTOMapper.class);

}
