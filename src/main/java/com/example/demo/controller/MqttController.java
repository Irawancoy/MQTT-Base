package com.example.demo.controller;

import org.eclipse.paho.mqttv5.client.MqttClient;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.eclipse.paho.mqttv5.common.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("mqtt")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MqttController {

  private final MqttClient client;
  private final String defaultTopic = "test/topic"; 
  private final String defaultMessage = "Hello from Spring Boot"; 

  @PostMapping("publish")
  public void publishMessage() throws MqttException {
    if (client.isConnected()) {
      try {
        var message = new MqttMessage(defaultMessage.getBytes());
        client.publish(defaultTopic, message);
        log.info("Message published to topic '{}': {}", defaultTopic, defaultMessage);
      } catch (MqttException e) {
        log.error("Failed to publish message to topic '{}'", defaultTopic, e);
        throw new RuntimeException("Failed to publish message", e);
      }
    } else {
      throw new RuntimeException("MQTT Client is not connected");
    }
  }
}
