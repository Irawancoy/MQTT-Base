package com.example.demo.configuration;

import org.eclipse.paho.mqttv5.client.MqttClient;
import org.eclipse.paho.mqttv5.client.MqttConnectionOptionsBuilder;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.configuration.properties.MqttProp;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class ApplicationConfig {

  @Bean
  public MqttClient mqttClient(MqttProp prop) {
    log.info("Broker Address : "+prop.getBrokerAddress());
    try {
      var options = new MqttConnectionOptionsBuilder()
          .automaticReconnect(true)
          .cleanStart(true)
          .connectionTimeout(30)
          .username(prop.getUsername())
          .password(prop.getPasswordBytes())
          .build();

      var client = new MqttClient(prop.getBrokerAddress(), prop.getClientId());
      client.connect(options);

      return client;
    } catch (MqttException e) {
      // Log the exception
      e.printStackTrace();
      throw new RuntimeException("Failed to connect to MQTT broker", e);
    }
  }
}