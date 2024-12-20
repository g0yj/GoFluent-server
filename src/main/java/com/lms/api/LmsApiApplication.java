package com.lms.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class LmsApiApplication {

  public static void main(String[] args) {
    SpringApplication.run(LmsApiApplication.class, args);
  }

}
