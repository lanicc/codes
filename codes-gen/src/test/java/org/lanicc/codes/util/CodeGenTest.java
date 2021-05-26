package org.lanicc.codes.util;

import freemarker.template.TemplateException;
import org.apache.maven.shared.invoker.MavenInvocationException;
import org.junit.Before;
import org.junit.Test;
import org.lanicc.codes.model.Db;
import org.lanicc.codes.model.Project;

import java.io.IOException;
import java.util.Arrays;

/**
 * Created on 2021/5/21.
 *
 * @author lan
 * @since 1.0
 */
public class CodeGenTest {

    CodeGen codeGen;

    @Before
    public void setUp() throws Exception {
        //输出路径
        String outputPath = "/Users/lan/Desktop/project";
        codeGen = new CodeGen(
                outputPath,
                new Project()
                        .setAuthor("lan")   //作者
                        .setName("organization") //名称，工程名=component-core-name, api-module=component-name-api, service-module=component-name-service, 包名com.souche.component.${project.name}
                        .setFirstLetterUpperCaseName("Org")     //默认会根据project.name生成
                        .setDesc("组织组件")
                        .setVersion("2.0.0-SNAPSHOT")
                        .setRemovedPrefix("")   // 删除表前缀
                        .setTables(Arrays.asList("org", "org_node", "org_node_info"))    //需要生成代码的表
                        .setDb(
                                new Db()
                                        .setUrl("jdbc:mysql://localhost:3307/components")   //jdbc_url
                                        .setUsername("root")
                                        .setPassword("canal_MYSQL")
                                        .setDatabase("components")  //db name
                        )
        );
    }

    @Test
    public void gen() throws TemplateException, IOException {
        codeGen.gen();
    }

    @Test
    public void pack() throws MavenInvocationException {
        codeGen.pack("/usr/local/Cellar/maven/3.8.1");
    }
}
