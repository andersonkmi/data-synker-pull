package org.codecraftlabs.octo.service

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder.standard
import com.amazonaws.services.dynamodbv2.document.{DynamoDB, Item}
import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException
import org.apache.log4j.{LogManager, Logger}
import org.codecraftlabs.octo.model.InvoiceJsonField.{Amount, BillToName, CompanyName, InvoiceId, InvoiceTrackingNumber, Name, RequestType, Status, Timestamp}
import org.codecraftlabs.octo.model.InvoiceTracking
import org.codecraftlabs.octo.service.EnvironmentVar.DynamoDbTable

import scala.util.Properties

object DynamoDbUtil {
  private val logger: Logger = LogManager.getLogger(getClass)
  private val TrackingStatusColumn: String = "trackingStatus"

  def retrieveOpenItems(): Unit = {
    val dynamoDBTable: Option[String] = Properties.envOrNone(DynamoDbTable)
    if (dynamoDBTable.isDefined) {
      // here comes the code
      val dynamoDbClient = standard().build()
      val dynamoDB = new DynamoDB(dynamoDbClient)
      val table = dynamoDB.getTable(dynamoDBTable.get)

      table.
    }
  }
  def putItem(invoiceTracking: InvoiceTracking): Unit = {
    val dynamoDBTable: Option[String] = Properties.envOrNone(DynamoDbTable)
    if (dynamoDBTable.isDefined) {
      try {
        val dynamoDbClient = standard().build()
        val dynamoDB = new DynamoDB(dynamoDbClient)
        val table = dynamoDB.getTable(dynamoDBTable.get)

        val item = new Item()
          .withPrimaryKey(InvoiceTrackingNumber.toString, invoiceTracking.invoiceTrackingNumber)
          .withString(InvoiceId.toString, invoiceTracking.invoiceId)
          .withString(RequestType.toString, invoiceTracking.requestType)
          .withLong(Timestamp.toString, invoiceTracking.timestamp)
          .withString(TrackingStatusColumn, invoiceTracking.trackingStatus)

        if (invoiceTracking.name.isDefined) {
          item.withString(Name.toString, invoiceTracking.name.get)
        }

        if (invoiceTracking.amount.isDefined) {
          item.withNumber(Amount.toString, BigDecimal.valueOf(invoiceTracking.amount.get))
        }

        if (invoiceTracking.companyName.isDefined) {
          item.withString(CompanyName.toString, invoiceTracking.companyName.get)
        }

        if (invoiceTracking.billToName.isDefined) {
          item.withString(BillToName.toString, invoiceTracking.billToName.get)
        }
        if (invoiceTracking.invoiceStatus.isDefined) {
          item.withString(Status.toString, invoiceTracking.invoiceStatus.get)
        }
        table.putItem(item)
      } catch {
        case exception: ResourceNotFoundException => logger.warn(s"DynamoDB table '${dynamoDBTable.get}' either does not exist or is incorrectly configured.", exception)
        case exception: Exception => logger.error(s"Failed to submit invoice tracking to DynamoDB: '${exception.getMessage}'.",
          exception)
      }
    } else {
      logger.warn("DynamoDB table name not configured yet.")
    }
  }
}
