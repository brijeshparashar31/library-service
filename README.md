# library-service
The application demonstrates the functionality requested in the problem statement. 
It is implemented with following features: 
* It exposes three endpoints as getBook and getAuthor and createBook using Spring Boot Rest APIs. 
* It is built using Java 17. 
* It uses in memory H2 DB.  
* It has Integration Tests covering all the Success and Error Test scenarios.



## Software & Libraries

* Spring Boot, JPA, Java 17, mapstruct, lombok, In memory H2 Database, Git, Maven, Intellij(Eclipse can be used as well). 

    
    
## Plugins

* maven compiler plugin to generate implementation code for mapstruct interface.  

## Building and Deploying Application

* Checkout the project from this location : https://github.com/brijeshparashar31/library-service
* Application can be built by using an IDE or by using maven command : mvn clean install.
* Run the application (using the LibraryApplication class) as a spring boot application from the IDE.
* The application on startup will create the DB tables and populate the tables with predefiend scripts using  /resources/schema.sql and /resources/data.sql.   
* Once the application is up it can be tested using the postman collection scripts.

## Testing the application -
Just running a maven clean install will run all the tests and validate the code completely.

Note : this include positive as well as negative tests. 

All the tests are self explanatory with their names.
Still added comments for more info on what each test is doing.

Or

Alternatively the service can be brought up as spring boot service in intellij, using the steps given in previous section.
and following URLs/Curl can be used for verifying the functionality of GET and POST endpoint state in the problem statement .
* Scenario 1 - Fetching a book with Id.  (positive test)
  Request http://localhost:8080/library/book/10000001
  Response : {"id":10000001,"title":"A Study in Scarlet","author":{"id":2,"name":"Sherlock Holmes","books":null}}
* Scenario 2 - Fetching list of books for an author with AuthorId. (positive test) 
  Request : http://localhost:8080/library/author/2
  Response: {"id":2,"name":"Sherlock Holmes","books":[{"id":10000001,"title":"A Study in Scarlet","author":null},{"id":10000002,"title":"The Valley of Fear","author":null}]}
* Scenario 3 - Create a book with existing author. 
  Curl request: 
  
  curl --location 'http://localhost:8080/library/book' \
  --header 'Content-Type: application/json' \
  --data '{
  "title": "Ready or not 1",
    "author": {
    "id": 1
    }
  }'
Response : 200 OK