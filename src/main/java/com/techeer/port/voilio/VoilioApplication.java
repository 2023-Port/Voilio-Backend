package com.techeer.port.voilio;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class VoilioApplication {

  public static void main(String[] args) {
    SpringApplication.run(VoilioApplication.class, args);
  }
}
