# WinterHold - Library Management System

<h3>README Translation</h3>
<ul>
  <li><a href="">English</a></li>
  <li><a href="">Indonesia</a></li>
</ul>
<br>

WinterHold is a modern and scalable Library Management System built using Spring Boot with a microservices architecture. This project provides a seamless and efficient way to manage books, borrowers, and transactions with high availability and fault tolerance.

<h2>✨ Features</h2>

* Microservices Architecture – Independent services for modular development and scalability.

* Message Broker (Kafka) – Event-driven communication between services.

* Circuit Breaker (Resilience4j) – Ensures system reliability by handling service failures gracefully.

* API Gateway – A Centralized entry point for routing.

* Service Discovery (Eureka) – Dynamic service registration and discovery.

* Custom Error Pages – User-friendly error handling with custom 404 and 503 pages.

<h2>🏗️ Tech Stack</h2>

* Backend: Java, Spring Boot, Spring Cloud, Spring Data JPA

* Database: SQL Server

* Message Broker: Apache Kafka

* Service Discovery: Eureka Server

* API Gateway: Spring Cloud Gateway

* Circuit Breaker: Resilience4j

<h2>📜 Microservices Overview</h2>

<img src="https://github.com/fer-nando65/WinterHold-microservice/blob/master/screenshot/architecture_microservice.JPG">

* Customer Service – Manages user accounts, borrowers, and library members.

* Library Service – Handles books, authors, and categories.

* Loan Service – Manages book borrowing and returns.

* Notification Service – Listens to events and sends notifications.

* API Gateway – Routes requests to appropriate services.

* Eureka - Register and Manages Server Address.

<h2>📸 Screenshots</h2>

Here are some screenshots of the application:

Database Diagram
<img src="https://github.com/fer-nando65/WinterHold-microservice/blob/master/screenshot/db_full.png">

* Home
<img src="https://github.com/fer-nando65/WinterHold-microservice/blob/master/screenshot/home.JPG">

* Author Page
<img src="https://github.com/fer-nando65/WinterHold-microservice/blob/master/screenshot/author.JPG">

* Category Page
<img src="https://github.com/fer-nando65/WinterHold-microservice/blob/master/screenshot/category.JPG">

* Book Page
<img src="https://github.com/fer-nando65/WinterHold-microservice/blob/master/screenshot/book.JPG">

* Loan Page
<img src="https://github.com/fer-nando65/WinterHold-microservice/blob/master/screenshot/loan.JPG">

* Validation
<img src="https://github.com/fer-nando65/WinterHold-microservice/blob/master/screenshot/validation.JPG">

* Error Page
<img src="https://github.com/fer-nando65/WinterHold-microservice/blob/master/screenshot/errorpage.JPG">

<h2>🤝 Contributing</h2>

Feel free to fork the repository and contribute via pull requests.

<h2>📜 License</h2>

This project is created for learning purposes and is open-source. You are free to use it as needed.

<h2>Enjoy using WinterHold – Your ultimate Library Management System! 📚❄️</h2>
