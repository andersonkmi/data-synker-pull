# Data ingestion API

## Introduction
TBD

## Build

TBD

## Docker container(s)

	$ docker image build -t octo/octodb:latest .
	$ docker container run --detach --name octodb --publish 5432:5432 -v postgres-volume:/var/lib/postgresql/data octo/octodb:latest

## Debug

    java -Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=8000,suspend=n -jar .\build\libs\data-ingestion-api-1.0.0.jar