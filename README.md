# Mars Rover Problem



## Run the application

- Clone the project
- If you open the project from Intellij Idea, Spring will be automatically configured as maven files are uploaded in the repository.
- Run the app or go to *MarsRoverControllerTest* Class in the Test directory and run the tests
- Defult port should be **8080**, but you can change it in *"src/main/resources/application.properties"* file.


## REST API

You can access the endpoint at [http://localhost:8080/coordinate](http://localhost:8080/coordinate)

Request body

```sh
{
    "coordinates":
    {
        "x": 4,
        "y": 2
    },
    "heading": "EAST",
    "obstacles": [
         {
            "x": 5,
            "y": 3
        }
    ],
    "commands": "FLFFFRFLB"
}
```


Response

```sh
{
    "coordinates": {
        "x": 5,
        "y": 2
    },
    "heading": "EAST STOPPED"
}
```

` Obstacles is optional`

` Any other missed field will cause 'Bad request' Response`

` Obstacles at the input Coordinates will cause "Bad request" also`

Response

```sh
{
    "message": "Bad request"
}
```
