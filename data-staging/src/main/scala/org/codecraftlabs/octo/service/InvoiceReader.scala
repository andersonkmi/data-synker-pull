package org.codecraftlabs.octo.service

import com.amazonaws.services.s3.AmazonS3ClientBuilder.standard
import com.amazonaws.services.s3.model.GetObjectRequest
import org.apache.log4j.{LogManager, Logger}
import org.codecraftlabs.octo.model.InvoiceJsonField.{Amount, BillToName, CompanyName, Contents, InvoiceId, InvoiceTrackingNumber, Name, RequestType, Status, Timestamp}
import org.codecraftlabs.octo.model.RequestTypes.{CREATE, DELETE, PATCH, UPDATE, findByName}
import org.codecraftlabs.octo.model.{InvoiceTracking, RequestTypes}
import org.json.JSONObject

import java.io.{BufferedReader, InputStreamReader}

object InvoiceReader {
  private val INITIAL_INVOICE_TRACKING_STATUS = "PENDING_SUBMISSION"
  private val logger: Logger = LogManager.getLogger(getClass)
  private val s3Service = standard().build()

  def readInvoiceTracking(bucket: String, key: String): Option[InvoiceTracking] = {
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
    extractJsonValues(json)
  }

  private def extractJsonValues(json: JSONObject): Option[InvoiceTracking] = {
    val requestType = json.getString(RequestType.toString)
    val request = findByName(requestType)

    request match {
      case CREATE => Some(extractInvoiceCreateOrUpdateJsonFields(json, CREATE))
      case UPDATE => Some(extractInvoiceCreateOrUpdateJsonFields(json, UPDATE))
      case PATCH => Some(extractInvoicePatchJsonFields(json))
      case DELETE => Some(extractInvoiceDeleteJsonFields(json))
      case _ => None
    }
  }

  private def extractBaseInfo(json: JSONObject): (Long, Long, JSONObject) = {
    val timestamp: Long = json.getLong(Timestamp.toString)
    val invoiceTrackingNumber: Long = json.getLong(InvoiceTrackingNumber.toString)
    val contents: JSONObject = json.getJSONObject(Contents.toString)
    (timestamp, invoiceTrackingNumber, contents)
  }

  private def extractInvoiceCreateOrUpdateJsonFields(json: JSONObject, requestType: RequestTypes.RequestType): InvoiceTracking = {
    val baseJsonInformation = extractBaseInfo(json)
    val contents: JSONObject = baseJsonInformation._3

    val invoiceId: String = contents.getString(InvoiceId.toString)
    val name: String = contents.getString(Name.toString)
    val amount: Double = contents.getDouble(Amount.toString)
    val companyName: String = contents.getString(CompanyName.toString)
    val billToName: String = contents.getString(BillToName.toString)
    val status: String = contents.getString(Status.toString)
    InvoiceTracking(baseJsonInformation._2,
      requestType.toString,
      baseJsonInformation._1,
      invoiceId,
      Some(name),
      Some(amount),
      Some(companyName),
      Some(billToName),
      Some(status),
      INITIAL_INVOICE_TRACKING_STATUS)
  }

  private def extractInvoicePatchJsonFields(json: JSONObject): InvoiceTracking = {
    val baseJsonInformation = extractBaseInfo(json)
    val contents: JSONObject = baseJsonInformation._3

    val invoiceId: String = contents.getString(InvoiceId.toString)
    var name: Option[String] = None
    var amount: Option[Double] = None
    var status: Option[String] = None

    if (contents.has(Name.toString)) {
      name = Some(contents.getString(Name.toString))
    }

    if (contents.has(Amount.toString)) {
      amount = Some(contents.getDouble(Amount.toString))
    }

    if (contents.has(Status.toString)) {
      status = Some(contents.getString(Status.toString))
    }

    InvoiceTracking(baseJsonInformation._2, PATCH.toString, baseJsonInformation._1, invoiceId, name, amount, None, None, status, INITIAL_INVOICE_TRACKING_STATUS)
  }

  private def extractInvoiceDeleteJsonFields(json: JSONObject): InvoiceTracking = {
    val baseJsonInformation = extractBaseInfo(json)
    val contents: JSONObject = baseJsonInformation._3
    val invoiceId: String = contents.getString(InvoiceId.toString)
    InvoiceTracking(baseJsonInformation._2, DELETE.toString, baseJsonInformation._1, invoiceId, None, None, None, None, None, INITIAL_INVOICE_TRACKING_STATUS)
  }
}
