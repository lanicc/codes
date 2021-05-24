package org.lanicc.codes.model;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Created on 2021/5/21.
 *
 * @author lan
 * @since 1.0
 */
@Data
@Accessors(chain = true)
public class Db {

    private String url;

    private String username;

    private String password;

    private String database;

}
