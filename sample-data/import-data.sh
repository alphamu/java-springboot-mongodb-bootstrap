#!/bin/bash
# docker run --rm --name mongodb -p 27777:27017 -v $PWD/sample-data:/sample-data bitnami/mongodb:latest
## Once you drop into bash
mongoimport --username my_user --password "password123" --db my_database --collection events --file events.json --jsonArray
##
mongoimport --username my_user --password "password123" --db my_database --collection users --file users.json --jsonArray
