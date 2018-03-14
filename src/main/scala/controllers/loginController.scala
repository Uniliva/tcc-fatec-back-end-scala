package controllers

import com.typesafe.scalalogging.LazyLogging
import org.scalatra.ScalatraServlet

class loginController extends ScalatraServlet with LazyLogging{

  get("/"){
    logger.info("Acessando metodo get")
    "Teste"
  }

}
