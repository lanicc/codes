package org.lanicc.codes.util;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.lanicc.codes.model.Db;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

/**
 * Created on 2021/5/21.
 *
 * @author lan
 * @since 1.0
 */
public class DbMapperImpl implements DbMapper {

    private final SqlSessionFactory sqlSessionFactory;

    public DbMapperImpl(Db db) {
        sqlSessionFactory = newSqlSessionFactory(db);
    }

    public static DataSource newDataSource(Db db) {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(db.getUrl());
        dataSource.setUsername(db.getUsername());
        dataSource.setPassword(db.getPassword());
        dataSource.setMaxActive(2);
        return dataSource;
    }

    public static SqlSessionFactory newSqlSessionFactory(Db db) {
        DataSource dataSource = newDataSource(db);
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("development", transactionFactory, dataSource);
        Configuration configuration = new Configuration(environment);
        configuration.addMapper(DbMapper.class);
        return new SqlSessionFactoryBuilder().build(configuration);
    }

    @Override
    public List<Map<String, String>> describe(String database, String tableName) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        DbMapper mapper = sqlSession.getMapper(DbMapper.class);
        List<Map<String, String>> mapList = mapper.describe(database, tableName);
        sqlSession.close();
        return mapList;
    }

    @Override
    public List<Map<String, String>> showTables(String database) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        DbMapper mapper = sqlSession.getMapper(DbMapper.class);
        List<Map<String, String>> tables = mapper.showTables(database);
        sqlSession.close();
        return tables;
    }
}
