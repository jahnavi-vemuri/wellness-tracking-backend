This is the Spring Boot backend for the Wellness Tracking System, which supports tracking physical activities, mental health status, and meal diets. It includes secure authentication, RESTful APIs, and integration with Groq's Llama 3 chatbot.

🚀 Tech Stack
Language: Java 17

Framework: Spring Boot

Database: MySQL

Authentication: JWT + Google 2FA

Build Tool: Maven

Deployment: Render

Others: DTO Pattern, REST APIs, Layered Architecture

📁 Project Structure
bash
Copy
Edit
src/
├── config/               # Security, CORS, JWT
├── controller/           # REST API endpoints
├── dto/                  # Data Transfer Objects
├── model/                # Entity classes
├── repository/           # JPA Repositories
├── service/              # Business logic
├── utils/                # Helper classes (e.g., TokenUtil)
└── WellnessApplication.java
🔐 Security Features
JWT-based login/session handling

2-Factor Authentication (Google Authenticator)

Encrypted passwords using BCrypt


🔗 REST API Endpoints (Sample)
Method	Endpoint	Description
POST	/auth/signup	Register user
POST	/auth/login	Login + JWT
POST	/auth/verify-2fa	Google 2FA
GET	/api/physical	Get physical activity logs
POST	/api/mental	Submit mental health entry
POST	/api/meal	Submit meal tracking
GET	/api/dashboard	Summary stats

🛠️ Setup Instructions
Clone the repo

bash
Copy
Edit
git clone https://github.com/your-org/wellness-backend.git
cd wellness-backend
Update application.properties:

properties
Copy
Edit
spring.datasource.url=jdbc:mysql://localhost:3306/wellness
spring.datasource.username=root
spring.datasource.password=yourpassword
jwt.secret=your_jwt_secret
Run the application:

bash
Copy
Edit
./mvnw spring-boot:run
📤 Deployment (Render)
Connected via GitHub

Auto-deploys on push to main

Uses .render.yaml or Render dashboard config

Environment variables set via Render UI

✅ Testing
Unit tests for services and utilities (JUnit)

Manual post-deployment tests using Postman

Regression checklist for core APIs

