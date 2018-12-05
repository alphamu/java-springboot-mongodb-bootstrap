# Trade Ledger: Spring boot and MongoDB

## Requirements

- Java 8
- Docker

## Step to run the code

1. Run the mongodb container.
2. Drop into the mongodb container bash shell and import data
3. Wait till you see the Mona Lisa.
4. Run the code using `./gradlew bootRun`. Sample curls below.

## 1. Run MongoDB in it's own container

All commands assume you are in the projects root directory.
This is the same directory as this README file.

Run MongoDB on port 2777:

```
docker run -it --rm --name mongodb -p 27777:27017 -e MONGODB_USERNAME=my_user -e MONGODB_PASSWORD=password123 -e MONGODB_DATABASE=my_database -v $PWD/sample-data:/sample-data bitnami/mongodb:latest
## OR
docker run -it --rm --name mongodb -p 27777:27017 -v $PWD/sample-data:/sample-data bitnami/mongodb:latest
```

## 2. Drop into the mongodb bash shell and import `sample-data`

In the command above we mapped the `sample-data/` directory to `/sample-data` in the container.
In the next step, we will drop into the docker container and run the import script.

```
docker exec -it mongodb bash
## Once you drop into bash
## You can run:
cd sample-data && ./import-data
## Or, do it manually
mongoimport --username my_user --password "password123" --db my_database --collection users --file sample-data/users.json --jsonArray
mongoimport --username my_user --password "password123" --db my_database --collection events --file sample-data/events.json --jsonArray
```

## Understanding the docker command

```
Command explained
-t              : Allocate a pseudo-tty
-i              : Keep STDIN open even if not attached
-v, --volume=[host-src:]container-dest[:<options>]: Bind mount a volume.
-w=""           : Working directory inside the container.
--rm            : Remove container on exit.
```

## Run the SprintBoot app

```
./gradlew bootRun
## check the output.
## Test fetch by ID
curl 'http://localhost:6868/events/507f191e810c19729de8aae0' -H 'Connection: keep-alive' -H 'Cache-Control: max-age=0' -H 'Upgrade-Insecure-Requests: 1' -H 'User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36' -H 'Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8' -H 'Accept-Encoding: gzip, deflate, br' -H 'Accept-Language: en-AU,en;q=0.9,ur-PK;q=0.8,ur;q=0.7,en-GB;q=0.6,en-US;q=0.5,la;q=0.4' --compressed
## Test search with filter
curl 'http://localhost:6868/users/search?filter=eyAiYXR0cmlidXRlIjogIndvcmtzdGF0aW9uIiwgIm9wZXJhdG9yIjogImVxIiwgInZhbHVlIjogIjE5Mi4xNjguMS4xMCIgfQ%3D%3D' -H 'Connection: keep-alive' -H 'Cache-Control: max-age=0' -H 'Upgrade-Insecure-Requests: 1' -H 'User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36' -H 'Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8' -H 'Accept-Encoding: gzip, deflate, br' -H 'Accept-Language: en-AU,en;q=0.9,ur-PK;q=0.8,ur;q=0.7,en-GB;q=0.6,en-US;q=0.5,la;q=0.4' --compressed
```
