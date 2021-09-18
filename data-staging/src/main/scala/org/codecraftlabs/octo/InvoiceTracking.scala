package org.codecraftlabs.octo

case class InvoiceTracking(invoiceTrackingNumber: Long,
                           requestType: String,
                           timestamp: Long,
                           invoiceId: String,
                           name: Option[String],
                           amount: Option[Double],
                           companyName: Option[String],
                           billToName: Option[String],
                           invoiceStatus: Option[String],
                           trackingStatus: String)
