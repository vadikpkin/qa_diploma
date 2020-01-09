
### How to start:

1 . clone https://github.com/vadikpkin/qa_diploma

* to use MySQL 

   1 .run docker container from folder 'artifacts/gate-simulator'
    ``` 
    docker-compose up -d
    ```
   2 . run docker container with MySQL 
     ``` 
    docker-compose -f docker-compose-mysql.yml up -d
    ``` 
   3 . run artifacts/aqa-shop.jar on your local host
   ``` 
   java -Dspring.datasource.url=jdbc:mysql://localhost:3306/app -jar aqa-shop.jar 
   ```

  4 . open terminal and type 
  ```
  ./gradlew test -Ddb.url=jdbc:mysql://localhost:3306/app
  ```
  5 . check report at /build/reports/tests/test/index.html

* to use PostgreSQL 

   1 .run docker container from folder 'artifacts/gate-simulator'
    ``` 
    docker-compose up -d
    ```
   2 . run docker container with MySQL 
     ``` 
    docker-compose -f docker-compose-postgresql.yml up -d
    ``` 
   3 . run artifacts/aqa-shop.jar on your local host
   ``` 
   java -Dspring.datasource.url=jdbc:postgresql://localhost:5432/app -jar aqa-shop.jar 
   ```

  4 . open terminal and type 
  ```
  ./gradlew test -Ddb.url=jdbc:postgresql://localhost:5432/app
  ```
  5 . check report at /build/reports/tests/test/index.html  
  
### Testing plan

To check testing plan go to [Plan.md](https://github.com/vadikpkin/qa_diploma/blob/master/Plan.md)
