# Data ingestion API

## Docker container(s)
To run and test locally the project requires a Postgresql docker container and in order to set it up locally:

	$ cd docker/postgres
    $ docker image build -t octo/octodb:latest .
	$ docker container run --detach --name octodb --publish 5432:5432 -v postgres-volume:/var/lib/postgresql/data octo/octodb:latest

## Build
In order to build this project run the following command:
    
    $ gradle clean build

This command will build and generate the jar package.

## Debug

    java -Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=8000,suspend=n -jar .\build\libs\data-ingestion-api-1.0.0.jar

Once the app is running you can attach to the debug port using remote debug functionality.