package org.codecraftlabs.octo.model

case class InvoiceTracking(invoicePartition: String,
                            invoiceTrackingNumber: Long,
                           requestType: String,
                           timestamp: Long,
                           invoiceId: String,
                           name: Option[String],
                           amount: Option[Double],
                           companyName: Option[String],
                           billToName: Option[String],
                           invoiceStatus: Option[String],
                           trackingStatus: String)
