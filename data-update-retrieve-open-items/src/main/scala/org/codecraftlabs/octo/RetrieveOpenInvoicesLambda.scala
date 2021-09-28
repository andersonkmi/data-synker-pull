package org.codecraftlabs.octo

import com.amazonaws.services.lambda.runtime.events.{APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent}
import com.amazonaws.services.lambda.runtime.{Context, RequestHandler}

class RetrieveOpenInvoicesLambda extends RequestHandler [APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent] {
  override def handleRequest(request: APIGatewayProxyRequestEvent, context: Context): APIGatewayProxyResponseEvent = {
    val logger = context.getLogger
    logger.log("Processing API gateway event")
    // Retrieves invoices with open status
    new APIGatewayProxyResponseEvent().withStatusCode(200).withBody("{'status' : 'OK'}")
  }
}
