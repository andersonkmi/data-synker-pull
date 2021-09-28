package org.codecraftlabs.octo.model

object RequestTypes extends Enumeration {
  type RequestType = Value
  val CREATE, UPDATE, DELETE, PATCH, UNKNOWN = Value

  def findByName(name: String): Value =
    values.find(_.toString.toLowerCase() == name.toLowerCase()).getOrElse(UNKNOWN)
}
