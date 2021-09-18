package org.codecraftlabs.octo

case class InvoiceTracking(invoiceTrackingNumber: String,
                           requestType: String,
                           timestamp: Long,
                           invoiceId: String,
                           name: Option[String],
                           amount: Option[Double],
                           companyName: Option[String],
                           billToName: Option[String],
                           status: String)
