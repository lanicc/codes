package org.lanicc.codes.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * Created on 2021/5/21.
 *
 * @author lan
 * @since 1.0
 */
@Data
@Accessors(chain = true)
public class Project {

    private String name;

    private String version;

    private String desc;

    private Db db;

    private List<String> tables;

    private String removedPrefix;

    private String author;

    private List<Model> models;

    public String getProjectName() {
        return "component-core-" + name;
    }
    public String getApiModuleName() {
        return "component-" + name + "-api";
    }
    public String getServiceModuleName() {
        return "component-" + name + "-service";
    }

}
