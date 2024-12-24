package com.safetynet.alerts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication

public class SafetyNetAlertsApplication {

	public static void main(String[] args) {

		SpringApplication.run(SafetyNetAlertsApplication.class, args);
		System.out.println("L'application SafetyNet Alerts a démarré avec succès !");
	}

}
