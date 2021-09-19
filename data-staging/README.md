# Data staging lambda function

This lambda function has the purpose to process the S3 files produced by the data ingestion API and save them into the DynamoDB
table to be used as a source of modifications.

## 1. Build

Build this project by running the following command:
    
    $ sbt clean assembly

This command will build and generate the jar package inside the folder target/scala-2.13

## 2. AWS lambda configuration

The following configuration should be done for this lambda function:

### 2.1. Runtime settings

- Runtime: Java 11 (Corretto)
- Handler: *org.codecraftlabs.octo.InvoiceProcessor::handleRequest*

### 2.2. General configurations

- Memory (MB): 1024
- Timeout: 15 seconds

### 2.3. Environment variables

- DYNAMO_DB_TABLE: name of the table where the data will be stored.

### 2.4. Trigger

Set up a trigger for S3 events and point to the S3 bucket used by the data ingestion API.