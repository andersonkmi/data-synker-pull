package org.codecraftlabs.octo

object InvoiceJsonField extends Enumeration {
  val Amount: InvoiceJsonField.Value = Value(0, "amount")
  val BillToName: InvoiceJsonField.Value = Value(1, "billToName")
  val CompanyName: InvoiceJsonField.Value = Value(2, "companyName")
  val Contents: InvoiceJsonField.Value = Value(3, "contents")
  val InvoiceId: InvoiceJsonField.Value = Value(4, "invoiceId")
  val InvoiceTrackingNumber: InvoiceJsonField.Value = Value(5, "invoiceTracking")
  val Name: InvoiceJsonField.Value = Value(6, "name")
  val RequestType: InvoiceJsonField.Value = Value(7, "requestType")
  val Status: InvoiceJsonField.Value = Value(8, "status")
  val Timestamp: InvoiceJsonField.Value = Value(9, "timestamp")
}
