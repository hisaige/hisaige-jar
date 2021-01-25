package com.hisaige.dbcore.support.plugin.test;

import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.hisaige.dbcore.support.plugin.CodeGenerator;

/**
 * 代码生成模板示例
 * @author chenyj
 * 2020/9/19 - 10:37.
 **/
public class ICodeGenerator {
    public static void main(String[] args) {

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3316/hisaige_cloud_test1?useUnicode=true&characterEncoding=UTF-8&rewriteBatchedStatements=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
        // dsc.setSchemaName("public");
//        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("root");

        CodeGenerator.generator(dsc, "hisaige-database-core", "com.hisaige.db3", "easy", "hisaige", "t_", "t_user");
    }
}
