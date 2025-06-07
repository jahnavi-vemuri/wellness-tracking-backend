This is the Spring Boot backend for the Wellness Tracking System. It enables users to track physical activities, mental health status, and meal diets. The system also includes secure authentication (JWT + Google 2FA), RESTful APIs, and a chatbot powered by Groq's Llama 3 model.


🚀 Tech Stack
- Language: Java 17
- Framework: Spring Boot
- Database: MySQL
- Authentication: JWT + Google 2FA
- Build Tool: Maven
- Deployment: Render


Project Structure:
src/
- config/                -> Security, CORS, JWT configurations
- controller/            -> REST API endpoints
- dto/                   -> Data Transfer Objects
- model/                 -> Entity classes
- repository/            -> JPA Repositories
- service/               -> Business logic
- utils/                 -> Helper classes (e.g., TokenUtil)
- WellnessApplication.java -> Main Spring Boot application

🔐 Security Features
- JWT-based authentication and session management
- 2-Factor Authentication with Google Authenticator
- Secure password storage with BCrypt
- Role-based access control


🔗 REST API Endpoints (Sample)
| Method | Endpoint           | Description                  |
| ------ | ------------------ | ---------------------------- |
| POST   | `/auth/signup`     | Register a new user          |
| POST   | `/auth/login`      | Login and receive JWT        |
| POST   | `/auth/verify-2fa` | Verify 2FA code              |
| GET    | `/api/physical`    | Fetch physical activity logs |
| POST   | `/api/mental`      | Submit mental health entry   |
| POST   | `/api/meal`        | Submit meal tracking data    |
| GET    | `/api/dashboard`   | Get summary statistics       |


🛠️ Setup Instructions
- Clone the repo: 
git clone https://github.com/your-org/wellness-backend.git
cd wellness-backend

- Configure Application Properties
Edit src/main/resources/application.properties:
spring.datasource.url=jdbc:mysql://localhost:3306/wellness
spring.datasource.username=root
spring.datasource.password=yourpassword
jwt.secret=your_jwt_secret
Run the application:

- Run the Application
./mvnw spring-boot:run

📤 Deployment (Render)
- Connected via GitHub
- Auto-deploys on push to main

Backend URL: https://wellness-tracking-backend-1.onrender.com 
