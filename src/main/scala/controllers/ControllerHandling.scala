package controllers

import org.json4s.ext.JodaTimeSerializers
import org.json4s.{DefaultFormats, Formats}
import org.scalatra.{CorsSupport, ScalatraBase}
import org.scalatra.json.JacksonJsonSupport

trait ControllerHandling  extends ScalatraBase with CorsSupport  with JacksonJsonSupport {
  override protected implicit def jsonFormats: Formats = DefaultFormats  ++ JodaTimeSerializers.all
  options("/*"){
    response.setHeader("Access-Control-Allow-Origin", "*")
  }
  before() {
    contentType = formats("json")
  }
}
