# log-viewer

## Dependencies
1. Java
1. Maven
1. MySQL

## Running
To build the application run `mvn package`. This will create the jar inside of the `target` directory.
To run the application, you can run `java -jar target/log-viewer-0.0.1-SNAPSHOT.jar`

This will create a server on port 8080. Navigate to http://localhost:8080 to access the app.

You will also need to create a database. You can find table information in `src/main/resources/schema.sql`. Update the database.xml to have the correct database information.
