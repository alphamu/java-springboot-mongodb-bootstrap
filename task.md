Version 1.0.0

# Introduction

This document describes a technical task for candidates applying for Sr Java Software Engineering role at Trade Ledger. This is programming task to be completed by you at home.

# Technical task

Please read below and implement as many of these requirements as you can. Your application will still be evaluated even if not all requirements are implemented. If you decided to skip some requirements please explain your decision.

Please create REST API interface which allows client applications to query data from Mongo database.

We will not be able to try your project in Windows environment. Please target OS.X or Linux.

## Database

- Provide a shell script to launch Mongo Database Server on port `27777` as Docker container.
- Provide a way to upload all JSONs from sample data folder.

## Application

- REST API application, lets call it "Search Facade" is to be written in Java demonstrating use of version 8 features.
- Should compile and run under JVM "8".
- Use Gradle build tool version "4.9".
- Should run from the command line as: `./gradlew bootRun`.
- All your tests should pass as: `./gradlew check`.
- Optional (if time permits): provide a way to launch application as Docker container.

## REST API

Export REST API on port `6868`
Implement `GET /:resource/:id` endpoint where `:id` is value of `_id` attribute of relevant resource and `resource` is an entry from sample data.
Implement `GET /:resource/search` endpoint. It should require a parameter `filter`, for example:

```
filter1 = ...
filter2 = ...
filter3 = ...
```

`GET /events/search?filter=filter1&filter=filter2&filter=filter3`

In SQL it would be something like `SELECT * FROM events WHERE filter1 AND filter2 AND filter3`

The filter is JSON object:

```
{
    "attribute": text,   // required
    "operator": text,    // required
    "value": text,       // optional if "range" is provided
    "range": {           // optional if "value" is provided
        "from": text,
        "to": text
    }
}
```

### Operators

Implement the following operators:

- `eq`: equals;
- `gte`: greater than or equals;
- `lte`: less than or equals;

### Validation

- Unknown operators are not allowed.
- Both `value` *and* `range` are not allowed.
- When operator is `gte` *or* `lte` then `range` is not allowed.

### Examples

Having the following data in the database:

```
[
    { "id": 1, "language": "Java", "version": 8, "isJvmBased": "true" },
    { "id": 2, "language": "Java", "version": 7, "isJvmBased": "true" },
    { "id": 3, "language": "Groovy", "version": 3, "isJvmBased": "true" },
    { "id": 4, "language": "Kotlin", "version": 2, "isJvmBased": "true" }
]
```

This query will return all Java objects:

filter1 = `{ "attribute": "language", "operator": "eq", "value": "Java" }`
filter2 = `{ "attribute": "isJvmBased", "operator": "eq", "value": "true" }`

`?filter=filter1&filter=filter2`

This query will return 3 objects with ids 2, 3, 4:

idRange = `{ "attribute": "id", "operator": "eq", "range": { "from": 2, "to": 4 } }`

`?filter=idRange`

## Submit

Please send the zipped project back via email. Only include the source files. 

Good luck and see you in the next round!