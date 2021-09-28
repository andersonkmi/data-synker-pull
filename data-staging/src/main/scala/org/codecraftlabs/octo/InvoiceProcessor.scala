package org.codecraftlabs.octo

import com.amazonaws.services.lambda.runtime.events.S3Event
import com.amazonaws.services.lambda.runtime.events.models.s3.S3EventNotification
import com.amazonaws.services.lambda.runtime.{Context, RequestHandler}
import org.apache.log4j.{LogManager, Logger}
import org.codecraftlabs.octo.service.DynamoDBService.putItem
import org.codecraftlabs.octo.service.InvoiceReader.readInvoiceTracking

import scala.jdk.CollectionConverters._

class InvoiceProcessor extends RequestHandler [S3Event, String] {
  private val logger: Logger = LogManager.getLogger(getClass)

  override def handleRequest(input: S3Event, context: Context): String = {
    logger.info("Processing json file")
    input.getRecords.asScala.foreach(item => processObject(item))
    "Done"
  }

  private def processObject(event: S3EventNotification.S3EventNotificationRecord): Unit = {
    val bucket = event.getS3.getBucket.getName
    val key = event.getS3.getObject.getKey
    logger.info(s"Processing object $key from bucket $bucket")
    val invoiceTracking = readInvoiceTracking(bucket, key)

    invoiceTracking match {
      case Some(invoiceTrackingItem) => logger.info(s"Invoice tracking record after parsing: '$invoiceTrackingItem'")
                                        putItem(invoiceTrackingItem)
      case None => logger.info("No invoice tracking created.")
    }
  }
}
