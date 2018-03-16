package controllers

import com.typesafe.scalalogging.LazyLogging
import models.{Dados, Sensor, Usuario}
import org.scalatra.{CorsSupport, ScalatraServlet}
import com.github.aselab.activerecord.dsl._

class loginController extends ScalatraServlet with LazyLogging  with CorsSupport {

  options("/*") {
    response.setHeader(
      "Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers")
    )
  }

  get("/") {
    val x =Sensor.find(1).get

    x.dados.asJson
  }

}
