package com.sstec.spring.aurora;

import com.sstec.spring.aurora.config.CustomContextInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.stereotype.Component;

import java.security.Security;

@SpringBootApplication
public class AuroraReadReplicaApplication {

	private static final Logger logger = LoggerFactory.getLogger(AuroraReadReplicaApplication.class);

	public static void main(String[] args) {

		logger.info(String.format("networkaddress.cache.ttl: %s", Security.getProperty("networkaddress.cache.ttl")));
		logger.info(String.format("networkaddress.cache.negative.ttl: %s", Security.getProperty("networkaddress.cache.negative.ttl")));

		new SpringApplicationBuilder(AuroraReadReplicaApplication.class)
				.initializers(new CustomContextInitializer())
				.run(args);
	}

}
