# Requirements

- docker
- docker-compose
- JDK 17

# How to Run

`docker-compose up -d` wait a few seconds for the db to start

## Create the table

`./gradlew flywayMigrate`

## Run the tests

`./gradlew test`

## Start the app

`./gradlew bootRun`

## Manual test

```bash
curl -i -X POST \
-H "Content-Type: application/text" \
-d '18148426-89e1-11ee-b9d1-0242ac120002|1X1D14|John Smith|Likes Apricots|Rides A Bike|6.2|12.1
3ce2d17b-e66a-4c1e-bca3-40eb1c9222c7|2X2D24|Mike Smith|Likes Grape|Drives an SUV|35.0|95.5
1afb6f5d-a7c2-4311-a92d-974f3180ff5e|3X3D35|Jenny Walters|Likes Avocados|Rides A Scooter|8.5|15.3' \
http://localhost:8080/files/process
```

# Assumptions

- file validation fails globally, not at row level
- `http://checkip.amazonaws.com` is used to find the public ip address of the machine where the app is running
- to keep it simple the database that runs locally is used by both the app and by the integration tests



