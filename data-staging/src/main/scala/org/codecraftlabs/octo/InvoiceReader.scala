package org.codecraftlabs.octo

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import com.amazonaws.services.dynamodbv2.document.{DynamoDB, Item}
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.amazonaws.services.s3.model.GetObjectRequest
import org.apache.log4j.{LogManager, Logger}
import org.codecraftlabs.octo.InvoiceJsonField.{Amount, BillToName, CompanyName, Contents, InvoiceId, InvoiceTrackingNumber, Name, RequestType, Status, Timestamp}
import org.json.JSONObject

import java.io.{BufferedReader, InputStreamReader}
import scala.util.Properties

object InvoiceReader {
  private val logger: Logger = LogManager.getLogger(getClass)
  private val s3Service = AmazonS3ClientBuilder.standard().build()

  def readInvoiceTracking(bucket: String, key: String): Unit = {
    logger.info("Starting S3 object processing")

    val s3Object = s3Service.getObject(new GetObjectRequest(bucket, key))
    val s3ObjectInputStream = s3Object.getObjectContent
    val reader = new BufferedReader(new InputStreamReader(s3ObjectInputStream))
    var line: String = null

    // loads all the lines into the buffer
    val buffer = new StringBuffer()
    while ({line = reader.readLine; line != null}) {
      buffer.append(line)
    }

    // converts to the json object
    val json: JSONObject = new JSONObject(buffer.toString)
    logger.info("Request type: " + json.get("requestType"))
    logger.info("Invoice tracking number: " + json.getLong("invoiceTracking"))
    val invoiceTrackingRecord = extractJsonValues(json)

    // Sends the record to DynamoDB (for now)
    if (invoiceTrackingRecord.isDefined) {
      logger.info(s"Invoice tracking record after parsing: '${invoiceTrackingRecord.get}'")
      putItem(invoiceTrackingRecord.get)
    }
  }

  private def extractJsonValues(json: JSONObject): Option[InvoiceTracking] = {
    val requestType = json.getString("requestType")

    requestType match {
      case "CREATE" => Some(extractInvoiceCreationJsonFields(json, requestType))
      case "UPDATE" => Some(extractInvoiceCreationJsonFields(json, requestType))
      case "PATCH" => Some(extractInvoicePatchJsonFields(json))
      case "DELETE" => Some(extractInvoiceDeleteJsonFields(json))
      case _ => None
    }
  }

  private def extractInvoiceCreationJsonFields(json: JSONObject, requestType: String): InvoiceTracking = {
    val timestamp: Long = json.getLong(Timestamp.toString)
    val invoiceTrackingNumber: Long = json.getLong(InvoiceTrackingNumber.toString)
    val contents: JSONObject = json.getJSONObject(Contents.toString)
    val invoiceId: String = contents.getString(InvoiceId.toString)
    val name: String = contents.getString(Name.toString)
    val amount: Double = contents.getDouble(Amount.toString)
    val companyName: String = contents.getString(CompanyName.toString)
    val billToName: String = contents.getString(BillToName.toString)
    val status: String = contents.getString(Status.toString)
    InvoiceTracking(invoiceTrackingNumber,
                    requestType,
                    timestamp,
                    invoiceId,
                    Some(name),
                    Some(amount),
                    Some(companyName),
                    Some(billToName),
                    Some(status),
                    "PENDING_SUBMISSION")
  }

  private def extractInvoicePatchJsonFields(json: JSONObject): InvoiceTracking = {
    val timestamp: Long = json.getLong(Timestamp.toString)
    val invoiceTrackingNumber: Long = json.getLong(InvoiceTrackingNumber.toString)
    val contents: JSONObject = json.getJSONObject(Contents.toString)
    val invoiceId: String = contents.getString(InvoiceId.toString)
    var name: Option[String] = None
    var amount: Option[Double] = None
    var status: Option[String] = None

    if (json.has(Name.toString)) {
      name = Some(json.getString(Name.toString))
    }

    if (json.has(Amount.toString)) {
      amount = Some(json.getDouble(Amount.toString))
    }

    if (json.has(Status.toString)) {
      status = Some(json.getString(Status.toString))
    }

    InvoiceTracking(invoiceTrackingNumber, "PATCH", timestamp, invoiceId, name, amount, None, None, status, "PENDING_SUBMISSION")
  }

  private def extractInvoiceDeleteJsonFields(json: JSONObject): InvoiceTracking = {
    val timestamp: Long = json.getLong(Timestamp.toString)
    val invoiceTrackingNumber: Long = json.getLong(InvoiceTrackingNumber.toString)
    val contents: JSONObject = json.getJSONObject(Contents.toString)
    val invoiceId: String = contents.getString(InvoiceId.toString)
    InvoiceTracking(invoiceTrackingNumber, "DELETE", timestamp, invoiceId, None, None, None, None, None, "PENDING_SUBMISSION")
  }

  private def putItem(invoiceTracking: InvoiceTracking): Unit = {
    val dynamoDBTable: Option[String] = Properties.envOrNone(EnvironmentVariable.DynamoDbTable)
    if (dynamoDBTable.isDefined) {
      val dynamoDbClient = AmazonDynamoDBClientBuilder.standard().build()
      val dynamoDB = new DynamoDB(dynamoDbClient)
      val table = dynamoDB.getTable(dynamoDBTable.get)

      val item = new Item()
        .withPrimaryKey(InvoiceTrackingNumber.toString, invoiceTracking.invoiceTrackingNumber)
        .withString(InvoiceId.toString, invoiceTracking.invoiceId)
        .withString(RequestType.toString, invoiceTracking.requestType)
        .withLong(Timestamp.toString, invoiceTracking.timestamp)
        .withString("trackingStatus", invoiceTracking.trackingStatus)

      table.putItem(item)
    } else {
      logger.warn("DynamoDB table name not configured yet.")
    }
  }
}
