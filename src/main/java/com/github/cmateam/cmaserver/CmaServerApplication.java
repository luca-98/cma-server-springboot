package com.github.cmateam.cmaserver;

import com.github.cmateam.cmaserver.configuration.CmaConfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableConfigurationProperties({ CmaConfig.class })
@EnableScheduling
public class CmaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CmaServerApplication.class, args);
	}

}
