package com.mishang.mishang_system;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;

import java.util.Collections;

public class CodeGenerator {
    public static void main(String[] args) {
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/mishang_store?serverTimezone=Asia/Shanghai", "root", "0318Chia&")
                .globalConfig(builder -> {
                    builder.author("banshengsang") // 作者名
                            .outputDir(System.getProperty("user.dir") + "/src/main/java");// 输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.mishang.mishang_system") // 你的包名
                            .entity("entity")
                            .mapper("mapper")
                            .service("service")
                            .serviceImpl("service.impl")
                            .controller("controller")
                            .pathInfo(Collections.singletonMap(OutputFile.xml, System.getProperty("user.dir") + "/src/main/resources/mapper"));
                })
                .strategyConfig(builder -> {
                    builder.addInclude(
                            "customer",
                            "customer_preference",
                            "inventory_batch",
                            "product",
                            "orders",
                            "order_item",
                            "account_log"); // 这里改成你要生成的表名，比如 customer
                    //.addTablePrefix("t_", "sys_"); // 表前缀，如果你的表没有前缀就留空
                })
                .templateEngine(new VelocityTemplateEngine())
                .execute();
    }

}
