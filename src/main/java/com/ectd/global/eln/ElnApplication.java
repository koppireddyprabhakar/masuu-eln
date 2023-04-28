package com.ectd.global.eln;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
@ComponentScan({"com.ectd.global.*"})
public class ElnApplication  extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(ElnApplication.class, args);
	}

}
