package org.hospital.hrms;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
//@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class HrmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(HrmsApplication.class, args);
	}

}
