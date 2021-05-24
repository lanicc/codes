package com.souche.component.${p.name}.service.impl;

import com.souche.component.common.service.BaseService;
import com.souche.component.${p.name}.api.${model.name}Service;
import com.souche.component.${p.name}.api.model.${model.name}DTO;
import com.souche.component.${p.name}.api.model.param.${model.name}AddParam;
import com.souche.component.${p.name}.api.model.param.${model.name}UpdateParam;
import com.souche.component.${p.name}.service.dao.${model.name}Dao;
import com.souche.component.${p.name}.service.mapper.${model.name}DTOMapper;
import com.souche.component.${p.name}.service.model.${model.name}DO;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("${model.firstLetterLowerCaseName}Service")
public class ${model.name}ServiceImpl extends BaseService<${model.name}DO, ${model.name}DTO, ${model.name}AddParam, ${model.name}UpdateParam> implements ${model.name}Service {

    @Getter
    private final String primaryKeyPrefix = "${model.firstLetterLowerCaseName}";

    @Getter
    private final ${model.name}DTOMapper dtoMapper = ${model.name}DTOMapper.INSTANCE;

    @Getter
    @Autowired
    private ${model.name}Dao dao;

}
