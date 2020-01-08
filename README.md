
### How to start:

1 . clone https://github.com/vadikpkin/qa_diploma

2 . run docker container from folder 'artifacts/gate-simulator'
``` 
docker-compose up -d
```

3 . run docker container with database
 * to use PostgreSQL type this from root folder
 ``` 
 docker-compose -f docker-compose-postgresql.yml up -d
 ``` 
* to use MySQL type this from root folder
 ``` 
 docker-compose -f docker-compose-mysql.yml up -d
 ``` 
 P.S. you should change artifacts/application.properties for each of databases

4 . run artifacts/aqa-shop.jar on you local host
``` 
java -jar aqa-shop.jar 
```

5 . open terminal and and type ```./gradlew test``` or ```./gradlew.bat test,``` for Windows.


### Testing plan

To check testing plan go to [Plan.md](https://github.com/vadikpkin/qa_diploma/blob/master/Plan.md)
