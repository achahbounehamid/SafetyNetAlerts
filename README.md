# SafetyNet Alerts

## Description
SafetyNet Alerts est une application back-end développée avec **Spring Boot** qui vise à fournir des informations aux services de secours.

## Fonctionnalités principales
- API REST pour la gestion des données de résidents et casernes.
- Architecture respectant les principes **MVC** et **SOLID**.

## Prérequis
- **Java 8+**
- **Spring Boot**
- **Maven**
- **Git**
- **IDE** 


## Installation
1. Clonez le repository :
   ```bash
   git clone https://github.com/achahbounehamid/safetynet-alerts.git
   cd safetynetAlerts
2. Installez les dépendances Maven :
   mvn clean install
3. Pour lancer l'application :
   mvn spring-boot:run
   L'application sera disponible à l'adresse : http://localhost:8080.
4. Endpoints principaux:
   Méthode HTTP: GET, POST, PUT et DELETE
5. Couverture de test
   Couverture globale : 83%
6. Branches couvertes : 62%
7. Outils utilisés
   JaCoCo pour la couverture de code.
   SureFire pour les rapports de test.