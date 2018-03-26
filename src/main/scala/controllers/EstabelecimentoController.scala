package controllers

import com.typesafe.scalalogging.LazyLogging
import org.json4s.{DefaultFormats, Formats}
import org.scalatra.{CorsSupport, ScalatraServlet}
import org.scalatra.json.JacksonJsonSupport

class EstabelecimentoController extends ScalatraServlet with LazyLogging with CorsSupport with JacksonJsonSupport {
  override protected implicit def jsonFormats: Formats = DefaultFormats
  before() {
    contentType = formats("json")
  }

  options("/*") {
    response.setHeader(
      "Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers")
    )
  }


}
