package priv.menguer.velocity.config;

import java.io.File;
import java.util.Collections;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import priv.menguer.commons.core.constant.BaseConstant;
import priv.menguer.commons.core.util.FileUtils;

/**
 * @author menguer@126.com
 * @description
 * @date 2023年12月26日 下午12:59:15
 * @verifier
 * @check
 * @update
 * @remark
 */
public class GenConfig {
    /**
     * 表详情来源1-数据库；2-excl；3-数据库和excel
     */
//    public static String generateType;

    /**
     * 数据库类型
     */
    public static String databaseType = "";
    //
    // /**
    // * 数据库地址
    // */
    // public static String databaseUrl;
    //
    // /**
    // * 数据库用户名
    // */
    // public static String databaseUser;
    //
    // /**
    // * 数据库密码
    // */
    // public static String databasePassword;
    //
    // /**
    // * 需要生成实体的模式，为空则默认为当前用户
    // */
    public static String databaseSchema;
    //
    // /**
    // * 需要生成实体的表，多个用英文逗号隔开
    // */
    public static String databaseTables;
    //
    // /**
    // * 工程英文简称
    // */
    // public static String projectAbbName;
    //
    // /**
    // * 工程中文名称
    // */
    // public static String projectName;
    //
    // /**
    // * 数据库字段文件的绝对路径
    // */
    public static String databaseInfoFilePath;
    //
    // /**
    // * 文件保存路径
    // */
    // public static String saveFilePath;

    public static String driveClassName;
    public static String url;
    public static String user;
    public static String password;

    public static String schemaName;
    /**
     * 表类型，例如TABLE、VIEW
     */
    public static String tableType;
    public static Set<String> tableNames;

    public static String saveFilePath;
    public static String projectName;

    public static boolean generateEntity;
    public static boolean generateDao;
    public static boolean generateMapper;
    public static boolean generateService;
    public static boolean generateServiceImpl;
    public static boolean generateController;

    // 初始化全局配置
    public static void init() {
        // 初始化配置文件
        Properties properties = FileUtils.getProperties(FileUtils.getProgramDir() + "Config.properties");
        databaseType = properties.getProperty("databaseType");
//        generateType = properties.getProperty("generateType");
        driveClassName = properties.getProperty("driveClassName");
        url = properties.getProperty("url");
        user = properties.getProperty("user");
        password = properties.getProperty("password");

        schemaName = properties.getProperty("schemaName");
        tableType = properties.getProperty("tableType");
        String tns = properties.getProperty("tableNames");
        if (tns != null && !tns.isEmpty()) {
            tableNames = new HashSet<>();
            Collections.addAll(tableNames, tns.split(","));
        }

        saveFilePath = properties.getProperty("saveFilePath");
        if (saveFilePath == null || saveFilePath.isEmpty()) {
            saveFilePath = System.getProperty("user.dir") + File.separator + "GenerateCode";
        } else {
            saveFilePath = saveFilePath.replace("{schemaName}", schemaName);
        }
        projectName = properties.getProperty("projectName");

        generateEntity = BaseConstant.YES.equals(properties.getProperty("generateEntity"));
        generateDao = BaseConstant.YES.equals(properties.getProperty("generateDao"));
        generateMapper = BaseConstant.YES.equals(properties.getProperty("generateMapper"));
        generateService = BaseConstant.YES.equals(properties.getProperty("generateService"));
        generateServiceImpl = BaseConstant.YES.equals(properties.getProperty("generateServiceImpl"));
        generateController = BaseConstant.YES.equals(properties.getProperty("generateController"));
    }
}
