package com.souche.component.core.${p.name}.api.enums;

import com.souche.component.sharing.common.api.IErrorInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created on ${model.createTime}.
 *
 * @author ${model.author}
 * @since ${p.version}
 */
@AllArgsConstructor
public enum ${model.name}ErrorInfo implements IErrorInfo {

    ${model.upperCaseName}_NOT_EXIST("5001", "${model.desc}不存在"),
    ${model.upperCaseName}_INSERT_FAILED("5002", "${model.desc}新增失败"),
    ${model.upperCaseName}_UPDATE_FAILED("5003", "${model.desc}更新失败");


    @Getter
    private final String errCode;

    @Getter
    private final String errMsg;

}
