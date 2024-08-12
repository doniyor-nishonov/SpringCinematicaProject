
# Cinematica

**Cinematica** is a comprehensive web application designed to manage cinema ticketing, event scheduling, and user interactions. It provides an easy-to-use interface for both administrators and users to manage cinema-related activities.

## Table of Contents

- [Introduction](#introduction)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Setup and Installation](#setup-and-installation)
- [Usage](#usage)
- [Project Structure](#project-structure)
- [Contributing](#contributing)
- [License](#license)
- [Contact](#contact)

## Introduction

Cinematica is a Spring Boot-based application designed to streamline the process of managing cinema events, ticket sales, and user profiles. The application allows administrators to create and manage cinema events, while users can purchase tickets and view their transaction history.

## Features

- **Event Management**: Create and manage cinema events with details like name, seating capacity, sound system, and event images.
- **Ticket Purchase System**: Users can purchase tickets, which generates a page with a QR code containing all relevant details such as cinema name, movie name, purchased seats, and price.
- **User Profile**: Users can view and manage their profiles, including transaction history.
- **Dynamic Web Pages**: Utilizes Thymeleaf for dynamic content rendering, with enhanced styles and animations.
- **Security**: Implemented security features for user authentication and authorization.

## Technologies Used

- **Java 17**
- **Spring Boot**
- **Spring Security**
- **Thymeleaf**
- **HTML/CSS**
- **JavaScript**
- **MySQL** (or any other relational database)
- **Maven** (for project management)

## Setup and Installation

### Prerequisites

- JDK 17 or later
- Maven
- MySQL (or your preferred database)
- IDE (IntelliJ IDEA, Eclipse, etc.)

### Steps

1. **Clone the Repository:**

   ```bash
   git clone https://github.com/doniyor-nishonov/SpringCinematicaProject.git
   ```

2. **Navigate to the Project Directory:**

   ```bash
   cd SpringCinematicaProject
   ```

3. **Configure the Database:**

   - Update the `application.properties` file with your database configuration:

   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/cinematica
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

4. **Build the Project:**

   ```bash
   mvn clean install
   ```

5. **Run the Application:**

   ```bash
   mvn spring-boot:run
   ```

   The application will start on `http://localhost:8080`.

## Usage

1. **Access the Application:**
   - Open your browser and navigate to `http://localhost:8080`.
   
2. **User Actions:**
   - Register/Login to access your profile.
   - Browse and purchase tickets for available cinema events.
   - View transaction history and manage your profile.

3. **Admin Actions:**
   - Login as an admin to create and manage cinema events.
   - View and manage user information.

## Project Structure

```
SpringCinematicaProject/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/cinematica/
│   │   │       ├── controller/    # Controllers for handling requests
│   │   │       ├── model/         # Entity classes
│   │   │       ├── repository/    # Repositories for database operations
│   │   │       ├── service/       # Service layer for business logic
│   │   │       └── security/      # Security configuration and utilities
│   │   ├── resources/
│   │   │   ├── templates/         # Thymeleaf templates
│   │   │   ├── static/            # Static resources (CSS, JS, images)
│   │   │   ├── application.properties # Application configuration
│   │   └── webapp/
│   │       └── WEB-INF/           # Web configuration files
│   └── test/                      # Unit and integration tests
│
├── pom.xml                        # Maven configuration file
└── README.md                      # Project documentation
```

## Contributing

Contributions are welcome! Please fork this repository and submit a pull request with your changes. Ensure your code adheres to the coding standards and includes appropriate tests.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for more information.

## Contact

For any inquiries or feedback, please contact [Doniyor Nishonov](mailto:nishonovd80@gmail.com).
