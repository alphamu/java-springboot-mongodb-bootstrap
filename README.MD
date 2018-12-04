# Spring boot and MongoDB bootstrap

## Requirements

- Java 8
- Docker

## Run mongo DB in it's own container

Run Mongo DB on port 2777:

```
docker run --rm --name mongodb -p 27777:27017 -e MONGODB_USERNAME=my_user -e MONGODB_PASSWORD=password123 -e MONGODB_DATABASE=my_database bitnami/mongodb:latest
## OR
docker run --rm --name mongodb -p 27777:27017 bitnami/mongodb:latest
```

If you need to mount a directory to the container just add a `-v` parameter. If you need to change the working directory, add a `-w` parameter:

Example: Map the current working directory to a directory at /dump in the docker container.

```
docker exec -it mongodb bash
## Once you drop into bash
## You can run:
cd sample-data && ./import-data
## Or, do it manually
mongoimport --username my_user --password "password123" --db my_database --collection users --file sample-data/users.json --jsonArray
mongoimport --username my_user --password "password123" --db my_database --collection events --file sample-data/events.json --jsonArray
```

```
Command explained
-t              : Allocate a pseudo-tty
-i              : Keep STDIN open even if not attached
-v, --volume=[host-src:]container-dest[:<options>]: Bind mount a volume.
-w=""           : Working directory inside the container
```

Drop into Mongo DB containers bash instance when it's running. Create an other terminal and run:

```
docker exec -it mongodb bash
```

Once in bash, you can run migration scripts.


## Run the SprintBoot app

```
./gradlew bootRun
```
