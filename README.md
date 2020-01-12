
### How to start:

1 . clone https://github.com/vadikpkin/qa_diploma

* to use MySQL 

   1 . run terminal from root folder of a project
   
   2 . run docker container with a gate-service
    ``` 
    sudo docker-compose -f ./artifacts/gate-simulator/docker-compose.yml up -d
    ```
   3 . run docker container with MySQL
     ``` 
    docker-compose -f docker-compose-mysql.yml up -d
    ``` 
   4 . run SUT on your local host
   
   ``` 
   cd artifacts
   
   java -Dspring.datasource.url=jdbc:mysql://localhost:3306/app -Dspring.datasource.username=admin -Dspring.datasource.password=password -jar aqa-shop.jar
   ```

  5 . open terminal from root folder and type 
  ```
  ./gradlew test -Ddb.url=jdbc:mysql://localhost:3306/app
  ```
  6 . check report at /build/reports/tests/test/index.html

* to use PostgreSQL 

   1 . run terminal from root folder of a project
   
   2 . run docker container with gate-service
    ``` 
    sudo docker-compose -f ./artifacts/gate-simulator/docker-compose.yml up -d
    ```
   3 . run docker container with PostgreSQL
    ``` 
    docker-compose -f docker-compose-postgresql.yml up -d
    ``` 
   4 . run SUT on your local host
      
     ```
     cd artifacts
   
     java -Dspring.datasource.url=jdbc:postgresql://localhost:5432/app -Dspring.datasource.username=admin -Dspring.datasource.password=password -jar aqa-shop.jar
     ```

  5 . open terminal in root folder and type 
  ```
  ./gradlew test -Ddb.url=jdbc:mysql://localhost:3306/app
  ```
  6 . check report at /build/reports/tests/test/index.html

### Testing plan

To check testing plan go to [Plan.md](https://github.com/vadikpkin/qa_diploma/blob/master/Plan.md)
