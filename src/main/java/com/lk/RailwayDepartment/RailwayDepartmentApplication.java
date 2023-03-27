package com.lk.RailwayDepartment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.thymeleaf.extras.springsecurity6.dialect.SpringSecurityDialect;


@ServletComponentScan
@SpringBootApplication
public class RailwayDepartmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(RailwayDepartmentApplication.class, args);
	}

	@Bean
	public SpringSecurityDialect springSecurityDialect() {
		return new SpringSecurityDialect();
	}

}
