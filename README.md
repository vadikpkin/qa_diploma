### Before start
To run this service you should have a prepared postgresql database. Properties are in artifacts/application.properties

### How to start:

1. clone https://github.com/vadikpkin/qa_diploma
2. run docker container from folder 'artifacts/gate-simulator'
``` docker-compose up -d```
that will start container with node.js app on your 9999 port.
3. run artifacts/aqa-shop.jar on you local host
``` java -jar aqa-shop.jar ```
that will execute java app on your 8080 port

### Testing plan

To check testing plan go to [Plan.md](https://github.com/vadikpkin/qa_diploma/blob/master/Plan.md)
