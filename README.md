# Vulcanos - Galaxy Forecast Service
REST service used by Vulcans to share, with other civilizations, meteorological information about the galaxy.


### Compiling
Run the following command to compile the application.

> mvn clean package

### Running the service
After compiling the code the can be excecuted using the following command:

> java -jar target/forecaster-1.0.0.jar

### Loading initial forecast data
To manually load forecast predictions, make an HTTP **POST** to the following url:

https://{replace-with-service-host}/forecast/load

For example:

https://localhost:8080/forecast/load

#### Sample response
The load operation will response with son information about the loaded forecasts.
```
{
    "Most rainy day(s)": [108, 252, 288, 432, 468, 612, 648, 792, 828, 972, 1008, 1152, 1188, 1332, 1368, 1512, 1548, 1692, 1728, 1872, 1908, 2052, 2088, 2232, 2268, 2412, 2448, 2592, 2628, 2772, 2808, 2952, 2988, 3132, 3168, 3312, 3348, 3492, 3528],
    "Drought periods": "81",
    "Optimal periods": "0",
    "Rainy periods": "81",
    "Normal periods": "82"
}
```

### Loading initial forecast data
To query for an specific day forecast, make an HTTP **GET** to the following url:

https://{replace-with-service-host}/clima?day=<day>

For example:

https://localhost:8080/clima?dia=566

#### Sample response
Sample response for https://localhost:8080/clima?dia=566

```
{
    dia: 566,
    clima: "RAIN"
}
```

## Author

* **Julian De Tomasi**