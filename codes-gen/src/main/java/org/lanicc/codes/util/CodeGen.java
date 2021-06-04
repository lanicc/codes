package org.lanicc.codes.util;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.apache.maven.shared.invoker.*;
import org.lanicc.codes.model.Field;
import org.lanicc.codes.model.Model;
import org.lanicc.codes.model.Project;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created on 2021/5/21.
 *
 * @author lan
 * @since 1.0
 */
@SuppressWarnings("ResultOfMethodCallIgnored")
public class CodeGen {

    
    private final String basePath;

    private final Project project;

    private final DbMapper dbMapper;

    private final String database;

    private final Configuration cfg;

    public CodeGen(String outBasePath, Project project) throws IOException {
        this.basePath = outBasePath;
        this.project = project;
        if (StrUtil.isBlank(this.project.getFirstLetterUpperCaseName())) {
            this.project.setFirstLetterUpperCaseName(StrUtil.upperFirst(project.getName()));
        }

        this.dbMapper = new DbMapperImpl(project.getDb());
        this.database = project.getDb().getDatabase();

        String templatePath = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("")).getPath() + "../classes/template";

        this.cfg = new Configuration(Configuration.VERSION_2_3_22);
        cfg.setDirectoryForTemplateLoading(new File(templatePath));
        cfg.setDefaultEncoding(StandardCharsets.UTF_8.name());
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
    }

    public void gen() throws TemplateException, IOException {

        buildProjectModel();

        pom();

        src();
    }


    public void pack(String mavenHome) throws MavenInvocationException {
        InvocationRequest request = new DefaultInvocationRequest();
        request.setPomFile(new File(basePath + "/" + project.getProjectName() + "/pom.xml"));
        request.setGoals(Collections.singletonList("package"));
        Invoker invoker = new DefaultInvoker();
        invoker.setMavenHome(new File(mavenHome));
        invoker.setLogger(new PrintStreamLogger(System.err, InvokerLogger.ERROR));
        invoker.setOutputHandler(System.out::println);

        InvocationResult result = invoker.execute(request);
        System.out.println(result.getExitCode());

    }


    private void src() throws TemplateException, IOException {
        String srcBasePath = "/src/main/java/com/souche/component/core/" + project.getName() + "/";
        String apiSrcBasePath = basePath + "/" + project.getProjectName() + "/" + project.getApiModuleName() + srcBasePath + "/api";
        String serviceSrcBasePath = basePath + "/" + project.getProjectName() + "/" + project.getServiceModuleName() + srcBasePath + "/service";
        String testSrcBasePath = basePath + "/" + project.getProjectName() + "/" + project.getServiceModuleName() + "/src/test/java/com/souche/component/core/" + project.getName() + "/service";
        String testResourceBasePath = basePath + "/" + project.getProjectName() + "/" + project.getServiceModuleName() + "/src/test/resources";

        Map<String, Object> data = new HashMap<>();
        data.put("p", project);

        List<Model> models = project.getModels();
        for (Model model : models) {
            data.put("model", model);
            importsOf(data, model, null);

            process(apiSrcBasePath, "enums/" + model.getName() + "ErrorInfo.java", "api/ErrorInfo.java", data);
            process(apiSrcBasePath, model.getName() + "Service.java", "api/service.java", data);
            process(apiSrcBasePath, "model/" + model.getName() + "DTO.java", "api/DTO.java", importsOf(data, model, Field::isShowOnDto));
            process(apiSrcBasePath, "model/param/" + model.getName() + "AddParam.java", "api/param/AddParam.java", importsOf(data, model, Field::isShowOnAdd));
            process(apiSrcBasePath, "model/param/" + model.getName() + "UpdateParam.java", "api/param/UpdateParam.java", importsOf(data, model, Field::isShowOnUpdate));
            process(apiSrcBasePath, "model/param/" + model.getName() + "DeleteParam.java", "api/param/DeleteParam.java", importsOf(data, model, Field::isPrimary));

            process(serviceSrcBasePath, "model/" + model.getName() + "DO.java", "service/DO.java", importsOf(data, model, null));
            process(serviceSrcBasePath, "mapper/" + model.getName() + "DTOMapper.java", "service/DTOMapper.java", data);
            process(serviceSrcBasePath, "dao/" + model.getName() + "Dao.java", "service/Dao.java", data);
            process(serviceSrcBasePath, "impl/" + model.getName() + "ServiceImpl.java", "service/ServiceImpl.java", data);
        }

        data.remove("model");
        data.remove("modelImports");
        process(testSrcBasePath, "BaseTests.java", "test/BaseTests.java", data);
        process(testResourceBasePath, "application-context.xml", "test/application-context.xml", data);
        process(testResourceBasePath, "db-config.properties", "test/db-config.properties", data);
    }

    private Map<String, Object> importsOf(Map<String, Object> data, Model model, Predicate<Field> predicate) {
        data.put("modelImports", importsOf(model, predicate));
        return data;
    }

    private List<JavaType> importsOf(Model model, Predicate<Field> predicate) {
        Stream<Field> stream = model.getFields().stream();

        if (predicate != null) {
            stream = stream.filter(predicate);
        }
        return stream
                .map(Field::getJavaType)
                .filter(JavaType::isNeedImport)
                .distinct()
                .collect(Collectors.toList());
    }

    private void pom() throws IOException, TemplateException {
        Map<String, Object> data = new HashMap<>();
        data.put("p", project);

        process(basePath + "/" + project.getProjectName(), "pom.xml", "pom.xml", data);
        process(basePath + "/" + project.getProjectName() + "/" + project.getApiModuleName(), "pom.xml", "api/pom.xml", data);
        process(basePath + "/" + project.getProjectName() + "/" + project.getServiceModuleName(), "pom.xml", "service/pom.xml", data);
    }

//    private void copy(String sourcePath, String destPath) throws IOException {
//        IoUtil.copy(new FileInputStream(sourcePath), new FileOutputStream(destPath));
//    }

    private void process(String path, String fileName, String templateFile, Map<String, Object> data) throws IOException, TemplateException {
        System.out.println(path + "/" + fileName);
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(path + "/" + fileName);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
        }
        Template template = cfg.getTemplate(templateFile);
        template.process(data, new FileWriter(file));
    }

    private void buildProjectModel() {
        List<Map<String, String>> showTables = dbMapper.showTables(database);
        List<String> tables = project.getTables();
        if (CollectionUtil.isNotEmpty(tables)) {
            Set<String> tableSet = new HashSet<>(tables);
            showTables =
                    showTables.stream()
                            .filter(tm -> tableSet.contains(tm.get("TABLE_NAME")))
                            .collect(Collectors.toList());
        }

        String createTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
        List<Model> models =
                showTables
                        .stream()
                        .map(tm -> {
                                    Model model = new Model()
                                            .setAuthor(project.getAuthor())
                                            .setCreateTime(createTime)
                                            .setDesc(tm.get("TABLE_COMMENT"))
                                            .setTableName(tm.get("TABLE_NAME"))
                                            .setFields(buildField(tm.get("TABLE_NAME")));
                                    String tableName = tm.get("TABLE_NAME");
                                    tableName = StrUtil.isNotBlank(project.getRemovedPrefix()) ? tableName.replace(project.getRemovedPrefix(), "") : tableName;
                                    model.setName(StrUtil.upperFirst(StrUtil.toCamelCase(tableName)));
                                    model.setFirstLetterLowerCaseName(StrUtil.lowerFirst(StrUtil.toCamelCase(tableName)));
                                    model.setUpperCaseName(tableName.toUpperCase());
                                    return model;
                                }
                        )
                        .collect(Collectors.toList());


        project.setModels(models);
    }

    private List<Field> buildField(String table) {
        List<Map<String, String>> describeList = dbMapper.describe(database, table);
        return describeList.stream()
                .map(fm -> {
                            JavaType javaType = typeMatchToJava(fm.get("DATA_TYPE"));
                            boolean primary = primary(fm.get("COLUMN_KEY"));
                            String column_name = StrUtil.toCamelCase(fm.get("COLUMN_NAME"));
                            String fieldType = javaType.getDeclareName();
                            if (column_name.equalsIgnoreCase("isTest")) {
                                fieldType = "Boolean";
                            }
                            return new Field()
                                    .setPrimary(primary)
                                    .setScope("private")
                                    .setType(fieldType)
                                    .setJavaType(javaType)
                                    .setName(column_name)
                                    .setDesc(fm.get("COLUMN_COMMENT"))
                                    .setShowOnDto(!"dateDelete".contains(column_name.toLowerCase()))
                                    .setShowOnAdd(!"dateCreate,dateUpdate,modifier,dateDelete".toLowerCase().contains(column_name.toLowerCase()) && !primary)
                                    .setShowOnUpdate(!"dateCreate,creator,dateUpdate,dateDelete".toLowerCase().contains(column_name.toLowerCase()));
                        }
                )
                .collect(Collectors.toList());
    }

    private JavaType typeMatchToJava(String dbType) {
        return JdbcTypes.of(dbType.toLowerCase());
    }

    private boolean primary(String keyType) {
        return keyType.equalsIgnoreCase("PRI");
    }

}
