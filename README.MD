# Trade Ledger: Spring boot and MongoDB

All requested features have been implemented.
Please read the notes.
TL;DR Skip to "Steps to run the code".

## Requirements

- Java 8
- Docker

## Notes

### Tackling the reserved `_id` problem

#### Challenges

- `_id` is reserved in MongoDB and after import it lacks ObjectId definition, hence the field is not searchable.
- Possible approaches considered were to:
- - Programmatically importing the data so the object id could be created properly.
- - `sed` the `_id` field to modify it to something like `"_id":{"$oid":"5c0716d2ae04d039cc55b38f"}`
- - `sed` the `_id` to change it to something like `document_id` and auto generate `_id`. This approach required least processing IMO.
- - The solution I ended up taking which is a hybrid, main benefit being speeding up my development time.

#### Solution

When the application is run, it runs a migration script that copies all the `_id` values into a `document_id` field.
When you try to fetch by `_id` the application will actually search on `document_id`

### Tacking the JSON Object as Query parameter

#### Challenge

- Even after URLEscaping the JSON string, it's still not URL safe.
- Possible solutions considered:
- - Using `GET` with a body, this is allowed by the HTTP specifications, though it can be problematic with libraries. However the project specifically called for a query parameter.
- - Using `POST` with body. Same issue as above, plus, it's a `POST` and the requirement is for a `GET`.

#### Solution

The requirements did not specify how the JSON object should be encoded or, that it should be plain text.
As such I chose to encode it as Base64 and URL escape it (Sample below in the RUN section and the unit tests).

### Unit tests

Not as through as I would like. However, this has taken a lot of time and I've add an example demonstrating object mocking.
Really, any more unit tests I write will just be the same thing rehashed in different ways.

### Code structure

The code uses an MVC structure. One optimization I would have liked would be to add Promise to Repository queries.
However, since Spring uses Tomcat which implement a threadpool, the benefits of this are debateable.


## Steps to run the code

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
```

## 2. Drop into the mongodb bash shell and import `sample-data`

In the command above we mapped the `sample-data/` directory to `/sample-data` in the container.
In the next step, we will drop into the docker container and run the import script.

```
docker exec -it mongodb bash
## Once you drop into bash
## You can run:
cd sample-data && ./import-data
## NOTE: If because of Windows line end the script doesn't work, do it manually
## Or, open the script in vim and save the file in linux format `:set ff=unix` then `:wq`.
## Commands to do it manually
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
