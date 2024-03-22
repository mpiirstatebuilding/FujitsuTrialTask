# Application user guide

## Main application

Running the application is fairly straightforward, it just requires a bit of setup.

Before doing anything the schema.sql script at src/main/resources/schema.sql must be activated on the main database in order to generate the WEATHER_DATA table. Then all that's needed to do is run FujitsuTrialTaskApplication.java and the application will be running with weather data being updated regularly.

Parameters like the weather data source URL, the cron for scheduling database updates and variables for calculating delivery fees are all in the application.properties file, if there is a need to configure them further.

The application features one GET endpoint "/api/deliveryfee" that returns a delivery fee to the client. The endpoint has the request parameters
- "city" - required, by default permitted inputs are tallinn, tartu and paernu
- "vehicle" - required, by default permitted inputs are car, scooter and bike
- "timestamp" - optional, format is UNIX epoch in seconds, used to search for specific weather data entries to calculate fees from, if not present the application just uses the latest data for the specified city

## Running tests

Testing needs the same kind of setup as the main application since the tests also use a database. The same schema.sql file can be used for initialization but the database will also need to be populated with the data.sql file at src/test/resources/data.sql. After that it's good to go.
