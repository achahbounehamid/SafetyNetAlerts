package com.safetynet.alerts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@ComponentScan(basePackages = {"com.safetynet.alerts.controller",
//		"com.safetynet.alerts.service",
//		"com.safetynet.alerts.repository",
//		"com.safetynet.alerts.model",
//		"com.safetynet.alerts.dto"})
public class SafetyNetAlertsApplication {

	public static void main(String[] args) {

		SpringApplication.run(SafetyNetAlertsApplication.class, args);
		System.out.println("L'application SafetyNet Alerts a démarré avec succès !");
	}

}
