#!/usr/bin/env sh

# check your java.security if following flag is set to true: security.overridePropertiesFile
# we want to define custom DNS caching settings for this application only, not in global java.security config
mvn spring-boot:run