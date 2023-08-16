# Pokedex

## Requirements
- Docker and Docker Compose installed.
- Available ports: 5432 and 8080 to start database and pokedex application.
- Software to send REST requests(Postman).
- Internet connection and access to maven central repository to download project dependencies.

## Run the System
We can easily run the whole with only a single command:
```bash
docker-compose up
```
If the file 'docker-compose.yml' is not in the same path from which the command is run, it is necessary to specify the path to the file:
```bash
docker-compose up -f <path_file>
```

Docker will pull the PostgreSQL and Spring Boot images (if our machine does not have it before).

The services can be run on the background with command:
```bash
docker-compose up -d
```

## Stop the System
Stopping all the running containers is also simple with a single command:
```bash
docker-compose down
```

If you need to stop and remove all containers, networks, and all images used by any service in <em>docker-compose.yml</em> file, use the command:
```bash
docker-compose down --rmi all
```

## Using application
To use the application is required open the software to send requests(Postman).
The pokedex application has minimal security through an in-memory user. Therefore, in all REST requests it is necessary to send a header with a basic authentication. The example user to use is:
- <b>User</b>: heytrade
- <b>Password</b>: heyPassword

### Documentation
If you want to see the requests are available in pokedex application, go to the Swagger documentation:
```bash
http://localhost:8080/pokedex/swagger-ui/index.html
```

### Examples
In file 'heytrade.postman_collection.json' have a example requests for testing Pokedex application features:
- Search for Pokemon by text.
- Filter Pokemon by type.
- Add and remove a Pokemon to/from your Favourites.
- Retrieve all pokemons and favourite pokemons.
- Get Pokemon details.
