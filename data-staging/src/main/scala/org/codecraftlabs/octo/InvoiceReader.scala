package org.codecraftlabs.octo

import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.amazonaws.services.s3.model.GetObjectRequest
import org.apache.log4j.{LogManager, Logger}
import org.json.JSONObject

import java.io.{BufferedReader, InputStreamReader}

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
    }
  }

  private def extractJsonValues(json: JSONObject): Option[InvoiceTracking] = {
    val requestType = json.getString("requestType")

    requestType match {
      case "CREATE" => Some(extractInvoiceCreationJsonFields(json))
      case _ => None
    }
  }

  private def extractInvoiceCreationJsonFields(json: JSONObject): InvoiceTracking = {
    val timestamp: Long = json.getLong("timestamp")
    val invoiceTrackingNumber: String = json.getLong("invoiceTracking").toString
    val contents: JSONObject = json.getJSONObject("contents")
    val invoiceId: String = contents.getString("invoiceId")
    val name: String = contents.getString("name")
    val amount: Double = contents.getDouble("amount")
    val companyName: String = contents.getString("companyName")
    val billToName: String = contents.getString("billToName")
    InvoiceTracking(invoiceTrackingNumber,
                    "CREATE",
                    timestamp,
                    invoiceId,
                    Some(name),
                    Some(amount),
                    Some(companyName),
                    Some(billToName),
                    "PENDING_SUBMISSION")
  }
}
