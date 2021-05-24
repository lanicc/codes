package org.lanicc.codes.util;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * Created on 2021/5/21.
 *
 * @author lan
 * @since 1.0
 */
public interface DbMapper {

    @Select("SELECT COLUMN_NAME, DATA_TYPE, COLUMN_KEY, COLUMN_COMMENT FROM INFORMATION_SCHEMA.COLUMNS where table_schema = #{database} AND table_name  = #{tableName}")
    List<Map<String, String>> describe(@Param("database") String database, @Param("tableName") String tableName);

    @Select("select TABLE_NAME,CREATE_TIME,TABLE_COMMENT from information_schema.tables where table_schema=#{database}")
    List<Map<String, String>> showTables(@Param("database") String database);
}
