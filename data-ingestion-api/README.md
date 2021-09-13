# Data ingestion API

## 1. Docker container(s)
To run and test locally the project requires a Postgresql docker container and in order to set it up locally:

	$ cd docker/postgres
    $ docker image build -t octo/octodb:latest .
	$ docker container run --detach --name octodb --publish 5432:5432 -v postgres-volume:/var/lib/postgresql/data octo/octodb:latest

## 2. Build

First yoo need to build and publish the project [Cloud lift](https://github.com/andersonkmi/cloud-lift).

Build this project by running the following command:
    
    $ gradle clean build

This command will build and generate the jar package.

## 3. Debug

    java -Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=8000,suspend=n -jar .\build\libs\data-ingestion-api-<version>.jar

Once the app is running you can attach to the debug port using remote debug functionality.

## 4. API endpoints

In order to make this sandbox more plausible, this project provides some endpoints related to manage invoices. For more details about
the endpoints please refer to the Swagger specification [here](openapi/data-ingestion-api.yaml).