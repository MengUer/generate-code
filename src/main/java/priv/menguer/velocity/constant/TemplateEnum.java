package priv.menguer.velocity.constant;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import priv.menguer.commons.core.util.FileUtils;
import priv.menguer.commons.core.util.StrUtils;
import priv.menguer.velocity.config.GenConfig;
import priv.menguer.velocity.entity.TableInfo;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.Properties;

/**
 * @author menguer@126.com
 * @package priv.menguer.velocity.constant
 * @file java
 * @description
 * @date 2020-7-24 23:25:44
 * @verifier
 * @check
 * @update
 * @remark
 */
public enum TemplateEnum {
    ENTITY("entity"),

    DAO("dao"),

    SERVICE("service"),

    SERVICEIMPL("serviceImpl"),

    CONTROLLER("controller"),

    MAPPER("mapper");

    private final String value;
    private final Template template;

    TemplateEnum(String value) {
        this.value = value;
        VelocityEngine velocityEngine = new VelocityEngine();
        Properties properties = new Properties();
        properties.setProperty("resource.loader", "class");
        properties.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        velocityEngine.init(properties);
        template = velocityEngine.getTemplate("/template" + (GenConfig.databaseType.isEmpty() ? "" : "/" + GenConfig.databaseType) + "/" + value + ".vm", "utf-8");
    }

    /**
     * 生成文件
     *
     * @param tableInfo
     * @author menguer@126.com
     * @time 2024/12/8 20:35
     */
    public void generateFile(TableInfo tableInfo) {
        String tableName = tableInfo.getTableName();
        String className = StrUtils.underlineToCamel(tableName, false);
        String subproject = tableInfo.getTableSubproject();
        // 设置模版中的必要参数
        VelocityContext context = new VelocityContext();
        context.put("tableName", tableName);
        context.put("className", className);
        context.put("tableSubproject", subproject);
        context.put("tableComment", tableInfo.getTableComment());
        context.put("projectName", subproject == null ? GenConfig.projectName : GenConfig.projectName + "." + subproject);
        context.put("list", tableInfo.getColumns());
        // 获取生成文件的路径
        String filePath = getGeneratePath(subproject, className);
        // 生成文件,如果文件已经存在,会生成新文件覆盖旧文件
        try {
            Writer writer = new PrintWriter(filePath);
            template.merge(context, writer);
            writer.close();
            System.out.println("生成文件：" + filePath);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取生成文件路径
     *
     * @param subproject
     * @param className
     * @return
     * @author menguer@126.com
     * @time 2024/12/8 20:35
     */
    private String getGeneratePath(final String subproject, final String className) {
        StringBuilder generatePath = new StringBuilder(GenConfig.saveFilePath);
        if (subproject != null) {
            generatePath.append("\\").append(subproject);
        }
        generatePath.append("\\src\\com");
        if (ENTITY.value.equals(value)) {
            generatePath.append("\\entity");
            FileUtils.createFolder(generatePath.toString());
            return generatePath.append("\\").append(className).append(".java").toString();
        }
        if (DAO.value.equals(value)) {
            generatePath.append("\\dao");
            FileUtils.createFolder(generatePath.toString());
            return generatePath.append("\\").append(className).append("Mapper.java").toString();
        }
        if (MAPPER.value.equals(value)) {
            generatePath.append("\\dao\\xml");
            FileUtils.createFolder(generatePath.toString());
            return generatePath.append("\\").append(className).append("Mapper.xml").toString();
        }
        if (SERVICE.value.equals(value)) {
            generatePath.append("\\service");
            FileUtils.createFolder(generatePath.toString());
            return generatePath.append("\\").append(className).append("Service.java").toString();
        }
        if (SERVICEIMPL.value.equals(value)) {
            generatePath.append("\\service\\impl");
            FileUtils.createFolder(generatePath.toString());
            return generatePath.append("\\").append(className).append("ServiceImpl.java").toString();
        }
        if (CONTROLLER.value.equals(value)) {
            generatePath.append("\\controller");
            FileUtils.createFolder(generatePath.toString());
            return generatePath.append("\\").append(className).append("Controller.java").toString();
        }
        return generatePath.append("\\").append(className).append(".txt").toString();
    }
}