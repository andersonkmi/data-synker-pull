package org.codecraftlabs.octo

import com.amazonaws.services.lambda.runtime.{Context, RequestHandler}
import com.amazonaws.services.lambda.runtime.events.S3Event
import org.apache.logging.log4j.{LogManager, Logger}

class InvoiceProcessor extends RequestHandler [S3Event, String] {
  private val logger: Logger = LogManager.getLogger(getClass)

  override def handleRequest(input: S3Event, context: Context): String = {
    "Done"
  }
}
