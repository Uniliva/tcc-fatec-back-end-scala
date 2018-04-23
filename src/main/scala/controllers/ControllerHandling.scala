package controllers

import org.json4s.{DefaultFormats, Formats}
import org.scalatra.{CorsSupport, ScalatraBase}
import org.scalatra.json.JacksonJsonSupport

trait ControllerHandling  extends CorsSupport with ScalatraBase with JacksonJsonSupport {
  override protected implicit def jsonFormats: Formats = DefaultFormats
  options("/*"){
    response.setHeader("Access-Control-Allow-Origin", "*")
  }
  before() {
    contentType = formats("json")
  }
}
