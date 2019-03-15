package com.gk.cbl;


import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;



/**
  * 
  * @ClassName: CodeGeneration
  * @Description: 代码生成器
  * @author cheng
  * @date 2018年1月25日 下午2:55:14
  */
public class CodeGeneration {


/**
      * 
      * @Title: main
      * @Description: 生成
      * @param args
      */
public static void main(String[] args) {
    AutoGenerator mpg = new AutoGenerator();


// 全局配置
    GlobalConfig gc = new GlobalConfig();
    gc.setOutputDir("F:\\chen\\orgManager\\src\\main\\java");
    gc.setFileOverride(true);
    gc.setActiveRecord(true);// 不需要ActiveRecord特性的请改为false
    gc.setEnableCache(false);// XML 二级缓存
    gc.setBaseResultMap(true);// XML ResultMap
    gc.setBaseColumnList(true);// XML columList
    //gc.setBaseColumnList(true);//生成基本的SQL片段
    //gc.setBaseResultMap(true)//生成基本的resultMap
    // 自定义文件命名，注意 %s 会自动填充表实体属性！
    gc.setControllerName("%sController");
    gc.setMapperName("%sMapper");
    gc.setXmlName("%sMapper");
    gc.setServiceName("%sService");
    gc.setServiceImplName("%sServiceImpl");
    mpg.setGlobalConfig(gc);

/*    GlobalConfig config = new GlobalConfig();
    config.setActiveRecord(true) // 是否支持AR模式
            .setAuthor("Bean") // 作者
            //.setOutputDir("D:\\workspace_mp\\mp03\\src\\main\\java") // 生成路径
            .setOutputDir("F:\\chen\\orgManager\\src\\main\\java") // 生成路径
            .setFileOverride(true)  // 文件覆盖
            .setIdType(IdType.AUTO) // 主键策略
            .setServiceName("%sService")  // 设置生成的service接口的名字的首字母是否为I
            // IEmployeeService
            .setBaseResultMap(true)//生成基本的resultMap
            .setBaseColumnList(true);//生成基本的SQL片段 */



// 数据源配置
    DataSourceConfig dsc = new DataSourceConfig();
    dsc.setDbType(DbType.MYSQL);
    dsc.setDriverName("com.mysql.cj.jdbc.Driver");
    dsc.setUsername("root");
    dsc.setPassword("123456");
    dsc.setUrl("jdbc:mysql://localhost:3306/org?characterEncoding=utf8&useSSL=false&serverTimezone=GMT");
    mpg.setDataSource(dsc);


// 策略配置
    StrategyConfig strategy = new StrategyConfig();
    strategy.setInclude("dept");
    strategy.setDbColumnUnderline(true);
    strategy.setControllerMappingHyphenStyle(true);
    strategy.setNaming(NamingStrategy.underline_to_camel);//下划线转驼峰命名
    //strategy.setFieldNaming(NamingStrategy.underline_to_camel);



// 包配置
    PackageConfig pc = new PackageConfig();
    pc.setParent("com.gk.cbl");
    pc.setController("controller");
    pc.setService("service");
    pc.setMapper("mapper");
    pc.setEntity("entity");
    pc.setXml("mapper.xml");
    mpg.setPackageInfo(pc);


// 执行生成
    mpg.execute();

    /*
    * GlobalConfig config = new GlobalConfig();
				config.setActiveRecord(true) // 是否支持AR模式
					  .setAuthor("Bean") // 作者
					  //.setOutputDir("D:\\workspace_mp\\mp03\\src\\main\\java") // 生成路径
					  .setOutputDir("F:\\stsworkspace\\MyBatisPlusGenerator\\src\\main\\java") // 生成路径
					  .setFileOverride(true)  // 文件覆盖
					  .setIdType(IdType.AUTO) // 主键策略
					  .setServiceName("%sService")  // 设置生成的service接口的名字的首字母是否为I
					  					   // IEmployeeService
		 			  .setBaseResultMap(true)//生成基本的resultMap
		 			  .setBaseColumnList(true);//生成基本的SQL片段

				//2. 数据源配置
				DataSourceConfig  dsConfig  = new DataSourceConfig();
				dsConfig.setDbType(DbType.MYSQL)  // 设置数据库类型
						.setDriverName("com.mysql.jdbc.Driver")
						.setUrl("jdbc:mysql://localhost:3306/demo")
						.setUsername("root")
						.setPassword("123456");

				//3. 策略配置globalConfiguration中
				StrategyConfig stConfig = new StrategyConfig();
				stConfig.setCapitalMode(true) //全局大写命名
						.setDbColumnUnderline(true)  // 指定表名 字段名是否使用下划线
						.setNaming(NamingStrategy.underline_to_camel) // 数据库表映射到实体的命名策略
						//.setTablePrefix("tbl_")
						.setInclude("employee");  // 生成的表

				//4. 包名策略配置
				PackageConfig pkConfig = new PackageConfig();
				pkConfig.setParent("com.imooc")
						.setMapper("mapper")//dao
						.setService("service")//servcie
						.setController("controller")//controller
						.setEntity("entity")
						.setXml("mapper");//mapper.xml

				//5. 整合配置
				AutoGenerator  ag = new AutoGenerator();
				ag.setGlobalConfig(config)
				  .setDataSource(dsConfig)
				  .setStrategy(stConfig)
				  .setPackageInfo(pkConfig);

				//6. 执行
				ag.execute();

    * */

}
}

