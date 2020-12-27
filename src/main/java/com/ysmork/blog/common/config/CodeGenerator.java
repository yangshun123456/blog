package com.ysmork.blog.common.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.Scanner;

/**
 * @author yangshun
 * @version 1.0
 * @program: blog
 * @description: mybatis-plus代码生成配置
 * @date 2020/9/22 0:37
 */
public class CodeGenerator {


    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotBlank(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    public static void main(String[] args) {
        //1、全局配置
        GlobalConfig config = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        config.setActiveRecord(true)//开启AR模式
                .setAuthor("YangShun")//设置作者
                .setOutputDir(projectPath + "/src/main/java")//生成路径(一般在此项目的src/main/java下)
                .setFileOverride(true)//第二次生成会把第一次生成的覆盖掉
                .setOpen(true)//生成完毕后是否自动打开输出目录
                //.setSwagger2(true)//实体属性 Swagger2 注解
                //.setIdType(IdType.AUTO)//主键策略
                .setServiceName("%sService")//生成的service接口名字首字母是否为I，这样设置就没有I
                .setMapperName ("%sMapper")
                .setBaseResultMap(true)//生成resultMap
                .setBaseColumnList(true);//在xml中生成基础列
        //2、数据源配置
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.MYSQL)//数据库类型
                .setDriverName("com.mysql.cj.jdbc.Driver")
                .setUrl("jdbc:mysql://47.115.188.221:3306/ys_blog?useUnicode=true&characterEncoding=UTF-8&serverTimezone=GMT%2B8")
                .setUsername("root")
                .setPassword("123456");
        //3、策略配置
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig.setCapitalMode(true)//开启全局大写命名
                .setNaming(NamingStrategy.underline_to_camel)//表名映射到实体的命名策略(下划线到驼峰)
                //表字段映射属性名策略(未指定按naming)
                .setColumnNaming(NamingStrategy.underline_to_camel)
                //.setTablePrefix("tb_")//表名前缀
                //.setSuperEntityClass("你自己的父类实体,没有就不用设置!")
                //.setSuperEntityColumns("id");//写于父类中的公共字段
                //.setSuperControllerClass("自定义继承的Controller类全称，带包名,没有就不用设置!")
                .setRestControllerStyle(true) //生成 @RestController 控制器
                .setEntityLombokModel(true)//使用lombok
                .setInclude(scanner ("表名，用,隔开").split(","));//逆向工程使用的表
        //4、包名策略配置
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent("com.ysmork.blog")//设置包名的parent
                .setMapper("dao")
//                .setService("service")
//                .setController("controller")
                .setEntity("entity")
                .setXml("classpath:/mapper");//设置xml文件的目录
        TemplateConfig tc = new TemplateConfig ();
        tc.setController ("");
        tc.setService ("");
        tc.setServiceImpl ("");
        //5、整合配置
        AutoGenerator autoGenerator = new AutoGenerator();
        autoGenerator.setGlobalConfig(config)
                .setDataSource(dataSourceConfig)
                .setStrategy(strategyConfig)
                .setPackageInfo(packageConfig)
                .setTemplate (tc);
        //6、执行
        autoGenerator.execute();
    }


}
