package com.tests.reportservice;

import com.tests.reportservice.config.SpringConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(SpringConfig.class)
@ComponentScan(basePackages = {"com.tests"})
public class ReportServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReportServiceApplication.class, args);
	}
}
