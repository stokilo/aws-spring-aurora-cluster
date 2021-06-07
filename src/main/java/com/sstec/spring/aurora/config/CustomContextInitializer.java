package com.sstec.spring.aurora.config;

import com.amazonaws.services.secretsmanager.AWSSecretsManagerClient;
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
import com.amazonaws.services.secretsmanager.model.GetSecretValueResult;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;


public class CustomContextInitializer implements
        ApplicationContextInitializer<ConfigurableApplicationContext> {


    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
        ConfigurableEnvironment environment = configurableApplicationContext.getEnvironment();

        GetSecretValueResult result = AWSSecretsManagerClient.builder().build().
                getSecretValue(new GetSecretValueRequest().withSecretId("/config/aws-spring-aurora-cluster"));

        String secretString = result.getSecretString();

        // secretString.password is a db password created with CDK
        // here we could do something with it while context is initialized i.e. we can add custom property like:

        // Map<String, Object> myProperties = new HashMap<>();
        // myProperties.put("secret.aurora.password", ....);
        // environment.getPropertySources().addFirst(new MapPropertySource("custom-props", new Hash....));
    }

}