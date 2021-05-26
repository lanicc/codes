package com.souche.component.core.${p.name}.service.impl;

import com.souche.component.sharing.common.api.IErrorInfo;
import com.souche.component.sharing.common.enums.${p.firstLetterUpperCaseName}ErrorInfo;
import com.souche.component.sharing.common.service.BaseService;
import com.souche.component.core.${p.name}.api.${model.name}Service;
import com.souche.component.core.${p.name}.api.model.${model.name}DTO;
import com.souche.component.core.${p.name}.api.model.param.${model.name}AddParam;
import com.souche.component.core.${p.name}.api.model.param.${model.name}UpdateParam;
import com.souche.component.core.${p.name}.api.model.param.${model.name}DeleteParam;
import com.souche.component.core.${p.name}.service.dao.${model.name}Dao;
import com.souche.component.core.${p.name}.service.mapper.${model.name}DTOMapper;
import com.souche.component.core.${p.name}.service.model.${model.name}DO;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("${model.firstLetterLowerCaseName}Service")
public class ${model.name}ServiceImpl extends BaseService<${model.name}DO, ${model.name}DTO, ${model.name}AddParam, ${model.name}UpdateParam, ${model.name}DeleteParam> implements ${model.name}Service {

    @Getter
    private final String primaryKeyPrefix = "${model.firstLetterLowerCaseName}";

    @Getter
    private final ${model.name}DTOMapper dtoMapper = ${model.name}DTOMapper.INSTANCE;

    @Getter
    private final IErrorInfo objectNotExistError = ${p.firstLetterUpperCaseName}ErrorInfo.${model.upperCaseName}_NOT_EXIST;
    @Getter
    private final IErrorInfo insertOperateFailedError = ${p.firstLetterUpperCaseName}ErrorInfo.${model.upperCaseName}_INSERT_FAILED;
    @Getter
    private final IErrorInfo updateOperateFailedError = ${p.firstLetterUpperCaseName}ErrorInfo.${model.upperCaseName}_UPDATE_FAILED;

    @Getter
    @Autowired
    private ${model.name}Dao dao;

}
