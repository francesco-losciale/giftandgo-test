# Gift and Go test


# TODO 

x- rest controller and test
x - add validation of the file
- feature flag to disable validation
- finish all the happy paths and only afterwards thing to edge cases-  to save time

- create script to run mysql docker container 
- Flyway migration
- bash script to initialise and start tests

- Java 17? replace lombok with records?

- lombok use everywhere
- 
assumptions: 

- file validation fails globally, not at row level



curl -X POST \
    -H "Content-Type: application/text" \
    -d '18148426-89e1-11ee-b9d1-0242ac120002|1X1D14|John Smith|Likes Apricots|Rides A Bike|6.2|12.1
    3ce2d17b-e66a-4c1e-bca3-40eb1c9222c7|2X2D24|Mike Smith|Likes Grape|Drives an SUV|35.0|95.5
    1afb6f5d-a7c2-4311-a92d-974f3180ff5e|3X3D35|Jenny Walters|Likes Avocados|Rides A Scooter|8.5|15.3' \
    http://localhost:8080/files/process 


# How to run

docker-compose up -d && ./gradlew flywayMigrate

