package com.hisaige.dbcore.support.plugin;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.hisaige.dbcore.controller.BaseController;
import com.hisaige.dbcore.service.BaseService;
import com.hisaige.dbcore.service.impl.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author chenyj
 * 2020/9/17 - 18:33.
 **/
public class CodeGenerator {

    private static final Logger LOG = LoggerFactory.getLogger(CodeGenerator.class);

    /**
     * 代码生成器静态方法
     * @param dsc 数据源配置
     * @param projectModule 模块名
     * @param parentPackage 父包名
     * @param moduleName 子包名
     * @param author 代码生成作者
     */
    public static void generator(DataSourceConfig dsc, String projectModule, String parentPackage, String moduleName, String author, String tablePrefix, String ... includeTableName){
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");

        if(!StringUtils.isBlank(author)){
            gc.setAuthor("chenyj");
        }

        gc.setOpen(false);

        if(!StringUtils.isBlank(projectModule)){
            projectPath = projectPath + "/" + projectModule;
        }
        LOG.info("projectPath: {}", projectPath);

        gc.setOutputDir(projectPath + "/src/main/java");

        // 包配置
        PackageConfig pc = new PackageConfig();
//        pc.setModuleName(scanner("easyCode-hisaige"));
        //在基础包路径下生成代码的基础模块名，如 easyCode，则最终生成的代码将在com.hisaige.easy.easyCode包下
        pc.setModuleName(moduleName);
        //生成代码的基础包路径 如com.hisaige.easy
        pc.setParent(parentPackage);

        mpg.setPackageInfo(pc);

        mpg.setGlobalConfig(gc); // 设置全局配置
        mpg.setDataSource(dsc); //设置数据源

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        String templatePath = "/templates/mapper.xml.vm";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        String finalProjectPath = projectPath;

        String xmlFilePath = finalProjectPath + "/src/main/resources/mybatis-mappers/";

        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return xmlFilePath + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });

        File xmlFilePathFile = new File(xmlFilePath);

        if(!xmlFilePathFile.exists() || !xmlFilePathFile.isDirectory()){
            if(!xmlFilePathFile.mkdir()){
                LOG.warn("xmlFilePathFile:{} -- create failed!", xmlFilePathFile);
            }
        }


        LOG.info("mybatis xml file path: {}", xmlFilePath);
        cfg.setFileOutConfigList(focList);


        cfg.setFileCreate((configBuilder, fileType, filePath) -> {
            // 判断自定义文件夹是否需要创建
//                checkDir("调用默认方法创建的目录，自定义目录用");
//                if (fileType == FileType.MAPPER) {
//                    // 已经生成 mapper 文件判断存在，不想重新生成返回 false
//                    return !new File(filePath).exists();
//                }
            // 允许生成模板文件
            return true;
        });

        mpg.setCfg(cfg);


        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();

        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        templateConfig.setEntity("templates/entity.java.vm");
        templateConfig.setController("templates/controller.java.vm");
        templateConfig.setService("templates/service.java.vm");
        templateConfig.setServiceImpl("templates/serviceImpl.java.vm");
        templateConfig.setMapper("templates/mapper.java.vm");
        templateConfig.setXml("templates/mapper.xml.vm");
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);

//        strategy.setSuperEntityClass("你自己的父类实体,没有就不用设置!");
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        // 公共父类
        strategy.setSuperControllerClass(BaseController.class);
        strategy.setSuperServiceClass(BaseService.class);
        strategy.setSuperServiceImplClass(BaseServiceImpl.class);
        strategy.setSuperMapperClass("com.hisaige.core.db.mapper.BaseMapper");
        // 写于父类中的公共字段
//        strategy.setSuperEntityColumns("id");//也即不自动生成的字段
        //表名，多个英文逗号分割
//        strategy.setInclude(scanner("表名").split(","));
        strategy.setInclude(includeTableName);
//        strategy.setTablePrefix("t_");
        strategy.setControllerMappingHyphenStyle(true);
//        strategy.setTablePrefix(pc.getModuleName() + "_");
        strategy.setTablePrefix(tablePrefix);

        mpg.setStrategy(strategy);
//        mpg.setTemplateEngine(new FreemarkerTemplateEngine());//使用默认模板引擎VelocityTemplateEngine
        mpg.execute();
    }
}