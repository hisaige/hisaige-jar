package com.hisaige.dbcore.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author chenyj
 * 2020/5/16 - 17:10.
 **/
@ConfigurationProperties(prefix = "spring.datasource.hisaige.druid.setting")
public class DbProperties {

    private String mapperLocation; //resource下数据库xml文件夹名称

//    private String mapperPackage;

    private StatViewServlet statViewServlet = new StatViewServlet();

    public class StatViewServlet {
        private String enable = "false"; //是否启用druid监控，true | false

        private String urlPattern = "/druid/*"; // 监控属性，监控web前缀 /druid/*

        private String allow; // 监控属性-白名单，如有多个ip,逗号分隔，不能有空格

        private String deny; // 监控属性-黑名单，如有多个ip,逗号分隔，不能有空格

        private String username; // 监控属性

        private String password; // 监控属性

        private String resetEnable; // 监控属性

        public String getEnable() {
            return enable;
        }

        public void setEnable(String enable) {
            this.enable = enable;
        }

        public String getUrlPattern() {
            return urlPattern;
        }

        public void setUrlPattern(String urlPattern) {
            this.urlPattern = urlPattern;
        }

        public String getAllow() {
            return allow;
        }

        public void setAllow(String allow) {
            this.allow = allow;
        }

        public String getDeny() {
            return deny;
        }

        public void setDeny(String deny) {
            this.deny = deny;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getResetEnable() {
            return resetEnable;
        }

        public void setResetEnable(String resetEnable) {
            this.resetEnable = resetEnable;
        }
    }

    public String getMapperLocation() {
        return mapperLocation;
    }

    public void setMapperLocation(String mapperLocation) {
        this.mapperLocation = mapperLocation;
    }

//    public String getMapperPackage() {
//        return mapperPackage;
//    }
//
//    public void setMapperPackage(String mapperPackage) {
//        this.mapperPackage = mapperPackage;
//    }

    public StatViewServlet getStatViewServlet() {
        return statViewServlet;
    }

    public void setStatViewServlet(StatViewServlet statViewServlet) {
        this.statViewServlet = statViewServlet;
    }
}
