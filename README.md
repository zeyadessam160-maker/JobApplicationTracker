# Job Application Tracker

A full-stack web application for tracking job applications.  
Users can register, log in, manage their profile, and create, update, delete, and view their job applications.

The project uses a Spring Boot backend with JWT authentication and an integrated frontend served from the backend.


---

## Features

- User registration and login
- JWT-based authentication
- Create new job applications
- View all applications of the logged-in user
- Update application details
- Delete applications
- Profile page with name and profile image update
- Change password functionality
- H2 in-memory database 
- MySQL support through a local configuration file

---
## Tech Stack

### Backend
- Java
- Spring Boot
- Spring Security
- JWT
- Spring Data JPA
- H2 Database
- MySQL

### Frontend
- HTML
- CSS
- JavaScript
- Bootstrap
- Font Awesome

---
## How to Run the Project

### 1. Clone the repository
```bash
git clone https://github.com/zeyadessam160-maker/JobApplicationTracker.git
```
### 2. Open the backend project

Open the `backend` folder in your preferred IDE, for example:

- IntelliJ IDEA
- VS Code
- Eclipse

  Make sure the IDE loads it as a Maven project.

### 3. Run the Spring Boot application

---

### 4. Open the application

After the server starts, open:

```text
http://localhost:8080
```

The application redirects automatically to the login page.

You can also open the login page directly:

```text
http://localhost:8080/pages/login.html
```

---

## Default Database: H2

The project uses H2 as the default database, so it can run without installing MySQL.
---

## H2 Console

You can open the H2 database console here:

```text
http://localhost:8080/h2-console
```
Use these values:

```text
JDBC URL: jdbc:h2:mem:testdb
Username: sa
Password:
```
Leave the password empty.
### Main API end points : 
Method  	Endpoint	             What it does

POST	   /auth/register	        Creates a new user account
POST	   /auth/login	            Logs in a user and returns a JWT token
POST	   /auth/change-password	Changes the password of the logged-in user
GET	       /applications	        Loads the applications of the logged-in user
POST	   /applications	        Adds a new job application
PUT	       /applications/{id}	    Updates an existing application
DELETE	   /applications/{id}	    Deletes an application
GET	       /profile	                Loads the profile of the logged-in user
PUT	       /profile	                Updates the profile name or profile image

AUTHOR 
Zeyad Aly

