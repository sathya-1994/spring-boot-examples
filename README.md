# spring-boot-examples
spring boot example project with java

## Installing Tools

### Recommendations

- Use **latest version** of Java
- Use **latest version** of "Eclipse IDE for Enterprise Java Developers"
- Remember: Spring Boot 3+ works only with Java 17+

### Introduction
Spring Boot has a lot of magic going for it. 

Developing applications with it is cool and fun.

Spring Boot makes it easy to create stand-alone, production-grade Spring based Applications that you can “just run”. Most Spring Boot applications need very little Spring configuration.

This repository contains cool things about Spring boot Spring Boot and Starter Projects (2 projects).
- A web application to manage our todos
- A basic REST Service to manage questions of a survey

## Spring Boot topics covered
- Basics of Spring Boot
- Basics of Auto Configuration and Spring Boot Magic
- Spring Boot Starter Projects
- Spring Initializr
- DispatcherServlet
- Basic Todo Management Application with Login/Logout
- Model, Controllers, ViewResolver and Filters
- Forms - DataBinding, Validation
- Annotation based approach - @RequestParam, @ModelAttribute, @SessionAttributes etc
- Bootstrap to style the page
- Basic REST Services using Spring Boot Starter Web
- REST Service Content Negotiation with JSON and XML
- Embedded servlet containers : Tomcat, Jetty and Undertow
- Writing Unit and Integration tests using Spring Boot Starter Test
- Profiles and Dynamic Configuration with Spring Boot
- Spring Boot Data JPA
- Spring Boot Actuator
- Spring Security
- Spring Boot Developer Tools and LiveReload
- Unit Testing with JUnit
- Mocking with Mockito (Integration testing)

## Running Examples
- Download the zip or clone the Git repository.
- Unzip the zip file (if you downloaded one)
- Open Command Prompt and Change directory (cd) to folder containing pom.xml
- Open Eclipse 
   - File -> Import -> Existing Maven Project -> Navigate to the folder where you unzipped the zip
   - Select the right project
- Choose the Spring Boot Application file (search for @SpringBootApplication)
- Right Click on the file and Run as Java Application
- You are all Set
	
#### first-springboot-project:
- Java main class: com.example.demo.DemoApplication
- Todo  application login URL: http://localhost:8080/login
- User Id: ```in28minutes```
- Password: ```dummy```
- Note: Login credentials can configured using com.example.demo.web.security.SecurityConfiuration class

#### second-springboot-project: REST with Spring Boot(Spring Boot REST API)
- Java main class: com.in28minutes.springboot.Application
- Survey Questionnaire Data

```
Question question1 = new Question("Question1",
        "Most Popular Cloud Platform Today", Arrays.asList(
                "AWS", "Azure", "Google Cloud", "Oracle Cloud"), "AWS");
Question question2 = new Question("Question2",
        "Fastest Growing Cloud Platform", Arrays.asList(
                "AWS", "Azure", "Google Cloud", "Oracle Cloud"), "Google Cloud");
Question question3 = new Question("Question3",
        "Most Popular DevOps Tool", Arrays.asList(
                "Kubernetes", "Docker", "Terraform", "Azure DevOps"), "Kubernetes");

List<Question> questions = new ArrayList<>(Arrays.asList(question1,
        question2, question3));

Survey survey = new Survey("Survey1", "My Favorite Survey",
        "Description of the Survey", questions);

surveys.add(survey);

```

##### URLs

##### Basic Authorization Header

- Authorization - Basic YWRtaW46cGFzc3dvcmQ=

##### GET

- http://localhost:8080/surveys
- http://localhost:8080/surveys/Survey1
- http://localhost:8080/surveys/Survey1/questions
- http://localhost:8080/surveys/Survey1/questions/Question1
- http://localhost:8080/userDetailses?size=1

###### Response

```
[
    {
        "id": "Survey1",
        "title": "My Favorite Survey",
        "description": "Description of the Survey",
        "questions": [
            {
                "id": "Question1",
                "description": "Most Popular Cloud Platform Today",
                "options": [
                    "AWS",
                    "Azure",
                    "Google Cloud",
                    "Oracle Cloud"
                ],
                "correctAnswer": "AWS"
            },
            {
                "id": "Question2",
                "description": "Fastest Growing Cloud Platform",
                "options": [
                    "AWS",
                    "Azure",
                    "Google Cloud",
                    "Oracle Cloud"
                ],
                "correctAnswer": "Google Cloud"
            },
            {
                "id": "Question3",
                "description": "Most Popular DevOps Tool",
                "options": [
                    "Kubernetes",
                    "Docker",
                    "Terraform",
                    "Azure DevOps"
                ],
                "correctAnswer": "Kubernetes"
            }
        ]
    }
]

```

##### DELETE 

- http://localhost:8080/surveys/Survey1/questions/Question1

###### POST

**URL**: http://localhost:8080/surveys/Survey1/questions/
**Header**: Content-Type:application/json

**Request Body**
```
{
    "description": "Your Favorite Cloud Platform",
    "options": [
        "AWS",
        "Azure",
        "Google Cloud",
        "Oracle Cloud"
    ],
    "correctAnswer": "Google Cloud"
}

```

**URL**: http://localhost:8080/userDetailses
**Header**: Content-Type:application/json
**Request Body**
```
{
"name": "Sathish",
"role": "Admin"
}
```


###### PUT

**URL**: http://localhost:8080/surveys/Survey1/questions/Question1
**Header**: Content-Type:application/json
**Request Body**
```
{
    "id": "Question1",
    "description": "Most Popular Cloud Platform Today Change",
    "options": [
        "AWS",
        "Azure",
        "Google Cloud",
        "Oracle Cloud"
    ],
    "correctAnswer": "Google Cloud"
}

```