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
  }
}
