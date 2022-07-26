package com.spa.springCommuProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.ApplicationPidFileWriter;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class SpringCommuProjectApplication {

	static {
		System.setProperty("com.amazonaws.sdk.disableEc2Metadata", "true");
	}

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(SpringCommuProjectApplication.class);
		application.addListeners(new ApplicationPidFileWriter());
		application.run(args);
	}

}
